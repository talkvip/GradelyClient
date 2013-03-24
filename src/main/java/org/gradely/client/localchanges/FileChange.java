package org.gradely.client.localchanges;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent.Kind;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.gradely.client.FilePath;
import org.gradely.client.SHA2Hashsum;
import org.gradely.client.database.ConnectionException;
import org.gradely.client.database.ConnectionPool;
import org.gradely.client.logging.Logging;

/**
 * This class handles the file changes. Create Database entry get file from server, finish database entry.
 * @author Matt
 */
public class FileChange {
    
    //============== Fields ============================== 
    
    /**
     * This is for files that are known to be renamed. This is added to when files are renamed.
     */
    public static ArrayList<FilePath> recentlyRenamed = new ArrayList<FilePath>();
    
    /**
     * This gets added to whenever the program adds a file for whatever reason.
     */
    public static ArrayList<FilePath> recentlyCreated = new ArrayList<FilePath>();
    
    /**
     * When ever update a file from the server.
     */
    public static ArrayList<FilePath> recentlyModified = new ArrayList<FilePath>();
   
    /**
     * Whenever deleted a file of purpose.
     */
    public static ArrayList<FilePath> recentlyDeleted = new ArrayList<FilePath>();
    
    //============== Constructors ========================

    //============== Methods ============================= 
    
    
    /**
     * This method figures out what type of file change occured, and what to do about it.
     * @param changedFile The path of the file changed.
     * @param typeOfChange CREATE | DELETE | MODIFY
     */
    public static void fileChangeRouter(FilePath changedFile, Kind<Path> typeOfChange)
    {
        
        //Is it a purpously changed file?
        if (isPurposelyModified(changedFile, typeOfChange))
        {
            //Cool! I don't think we need to do anything. Subject to change.
            return;
        }
        
        //Is this file supposed to change.
        else if (isReadOnly(changedFile))
        {
            //If this file is not supposed to change, we need to alert the user
        }

        
        //Is it modified?
        else if (typeOfChange == StandardWatchEventKinds.ENTRY_MODIFY)
        {
            fileModify(changedFile);
            return;
        }
        
        else if (typeOfChange == StandardWatchEventKinds.ENTRY_DELETE)
        {
            fileDelete(changedFile);
        }
        
        //Is it a rename?
        try
        {
            BooleanFilepathChecksum bf = isRenamed(changedFile);
            if (bf.isRenamed())
            {
                fileRename(changedFile,bf.getFilepath(),bf.getChecksum());
            }
        }
        catch (IOException | SQLException ex)
        {
            Logging.warning("isRename threw an error.", ex);
        }

        fileCreate(changedFile);
        
    }
    
    
    /**
     * This method records and syncs up the new, created file. 
     * @param createdFile The file path containing the file that was created
     */
    public static void fileCreate(FilePath createdFile)
    {
        //Collect metadata 
            //Calculate chacksum
        //String hashsum = Hashsum.computeHash(createdFile);
        
        
        //Send metadata to server
        //Parse server's response
        //upload, (or don't upload) the file to the server.
        //move file into cache.
        //Modify local database.
        
    }
    
    /**
     * This takes care of recording a syncing a deleted file.
     * @param deletedFile The filepath of the now non-existant deleted file.
     */
    public static void fileDelete(FilePath deletedFile)
    {
        //Check database to find deleted file.
        //Inform remote server of file's deletion.
        //Add "IsDeleted = True" to the database.
    }
    
    /**
     * This method takes care of syncing and recording file modifications. 
     * @param modifiedFile The file path of the modified file.
     */
    public static void fileModify(FilePath modifiedFile)
    {
        //Check if file is read only.
        
        //Collect Metadata
        //Create Diff File
            //Save Diff file
            //add diff file to database
            //move current file into cache
            //delete old file from cache
        //Send metadata to server.
        //Parse servers response.
        //Upload (or don't) the diff file tothe server.
        //modify the database to reflect the change.
    }
    
    /**
     * Takes care of a renamed or moved file
     * @param deleted The old filepath that no longer exists.
     * @param renamed The new, renamed filepath.
     * @param checksum The checksum of the the two files is computed in isRestored().
     */
    public static void fileRename(FilePath deleted, FilePath renamed, String checksum)
    {
        //Collect metadata
        //add a new entry to the database reflecting the file move/rename.
    }
    
    /**
     * Determines if a file is renamed. Returns the filepath of the deleted file here because it is more efficent on the database to do so. Will always return the deleted filepath if it is a renamed file. 
     * @param previous
     * @param renamed
     * @return 
     */
    public static BooleanFilepathChecksum isRenamed(FilePath created) throws IOException, SQLException
    {
        //Ok, to determine if a file is renamed
        //find a previous version of the file in the database that was deleted, or is about to be deleted, that has the same hash sum
        //put the two trains togeather.
        
        try
        {
            //Check the in-memory store
            if (recentlyRenamed.contains(created))
            {
                return new BooleanFilepathChecksum(true, created, SHA2Hashsum.computeHash(created));
            }

            //calculate check sum
            String hashsum = SHA2Hashsum.computeHash(created);

            //query database to see if check sum exists
            Connection c  = ConnectionPool.getInstance().waitForConnection();
            //This command gets all file ids where the hashsum = the hashsum and it is a current version. 
            
            String sql = "SELECT filepath, is_current, is_deleted FROM user_file_version JOIN file_resource ON user_file_version.user_file_id = file_resource.id WHERE file_resource.hashsum = "+hashsum;
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(hashsum);
            r.first();
            
            if (r.first())
            {
                //There is a current file with that hashsum
                //if check sum is in database:
                //if file is marked as deleted, it is a rename - return true.
                    //If we get this far it is deleted
                return new BooleanFilepathChecksum(false, new FilePath(r.getString("filepath")), hashsum);
                
                
                //if file was deleted recently (within a min. or so) it is a rename.
                
            }
            else
            {
                //if check sum is not in database, return false - not a rename.
                return new BooleanFilepathChecksum(false, null, hashsum);
            }
            
            
            //if check sum is in database:
            //if file is marked as deleted, it is a rename - return true.
            //if file was deleted recently (within a min. or so) it is a rename.
            //check to see if file exists on file system
            //If file does exist - it is a copy'd file, return false.
            //If file does not exist, it is a rename, return true.
        
        }
        catch (NoSuchAlgorithmException | IOException e)
        {
            Logging.error("Seems the file does not exist.", e);
            throw new IOException(e.getMessage());
        }
        catch (ConnectionException e)
        {
            Logging.fatal("Can't get a connection with the database.");
        }
            
        return new BooleanFilepathChecksum(false,null,null);
    }
    
    /**
     * Figures out if the file was changed by this program. 
     * @param changed The filepath changed
     * @return True if this filepath was changed by the program, false otherwise.
     */
    public static boolean isPurposelyModified(FilePath changed, Kind<Path> typeOfChange)
    {
        //Check the in memory store
            //if found,remove the record from the memory list. return true.
        
        //Check the database:
            //If the file is deleted:
                //is the database isDeleted == true. if true return true.
            //If the file is created:
                //Does the database have a record of this file? If it has a maching record, return true.
            //If the file is modified:
                //Compute checksum
                //check if the file modified matches the check sum in the database. If it matches return true.
    
        return false;
    }
    
    
    /**
     * This determines if we just restored a file from the cache. Tends to have false positives, be careful.
     * @param created The file path of the file that was just created.
     * @return 
     */
    private static boolean isRestored(FilePath created){
        
        //check the in memory store, if it is in the memory store return true.
        
        //check the database to see if the file should be deleted.
        //If the file should be deleted, but is not deleted, then it must have been restored, return true.
        //If the file should be present, and is present, then it must have been restored, return true.
        //If the file does not exist in the database, and is present in the filesystem, return false.

        return false;
    }
    
    /**
     * Asks the database if a file is read-only, and should not be changed.
     * @param fm
     * @return True if the file is read only.
     */
    public static boolean isReadOnly(FilePath modified)
    {
        //Check the database if the file is read-only, or should be modified.
        
        return false;
    }

}
