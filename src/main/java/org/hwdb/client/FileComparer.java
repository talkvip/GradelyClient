package org.hwdb.client;


import java.nio.file.Path;
/**
 *
 * @author Matt
 */

/**
 * This class is responaceble for determining diffrences in the file structure of the site.
 * 
 * The changes in file structure can occur when the computer is shut off, the program stops running, or a change is created on the server or another client. 
 * 
 * @author Matt
 */
public class FileComparer {
    
    //============== Fields ============================== 
    
    //FilePath rootPath;
    
    //============== Constructors ============================== 
    /*public*/ private FileComparer(FilePath rootPath){
        
    }
    
    //============== Methods ==============================  
    
    public static void compare(FilePath rootPath)
    {
        //TODO
        //Loop through all files
        //for each file check the database to confirm match 
            //Check file last modified time
                //If the file last modified time is closer to now than the databases client modified time, treat the file as a change.
        //For changed, missing, renamed, created files, send it over to FileChange.fileRouter
    }
}
