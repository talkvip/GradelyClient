/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.network.remotechanges;

/**
 * This class takes the parsed json and modifies the file structure to reflect changes by other clients and the server.
 * Network --> JsonParser --> FileUpdater --> FileTransfer & Database & Filesystem 
 * @author Matt
 */
public class FileUpdater {

    //================= Fields =================================
    
    //================= Constructors ===========================

    public FileUpdater() {

    }

    //REMEMBER: Modifiy the database, then create or apply the file change. This should result in atomic operations.
    //================= Methods ================================
    
    /**
     * Creates a new file on the filesystem by downloading it from the server. Takes care of the database too.
     */
    public static void createFile()
    {
        
    }
    
    /**
     * Modifies a file of the file system, probally by downloading a diff file and applying it.
     */
    public static void modifyFile()
    {
        
    }
    
    /**
     * Renames an existing file.
     */
    public static void renameFile()
    {
        
    }
            
    /**
     * Deletes a file from the 
     */
    public static void deleteFile()
    {
        
    }
    
    /**
     * Restores a file from the cache
     */
    public static void restoreFile()
    {
        
    }
    //------------------ Getters and Setters -------------------
}
