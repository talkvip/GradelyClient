
package org.hwdb.client;

import java.nio.file.Path;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author Matt
 */

/**
 * This class is an object that represents the state of the file system.
 * @author Matt
 */
public class FileStructure {
    
    //============== Fields ============================== 
    
    private ArrayList<FileMetadata> files; 
    
    
    //============== Constructors ============================== 
            
    /**
     * This constructor creates, and populates an file structure object representing the file system.
     * 
     * @param rootPath The root path is the path of the top level box folder on the system. 
     */
    public FileStructure(Path rootPath)
    {
        
    }
    
    /**
     * This constructor creates, and populates an file structure object representing the file system from a server's JSON response.
     * @param jsonFileData jsonFileData is  JSON representing a file structure, as passed down from the server.
     */
    //public FileStructure(Json jsonFileData)
    //{
    //    
    //}
    
    /**
     * This constructor creates, and populates an file structure object representing the file system from the database of past file snapshots.
     * @param timeOfFilesystem The time of filesystem is the time at which the file structure existed. Input Date.now() for the database's current understanding of the file system.
     */
    public FileStructure(Date timeOfFilesystem)
    {
        
    }
    
    //============== Methods ============================== 
    
}
