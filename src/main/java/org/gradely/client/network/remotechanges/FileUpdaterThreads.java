/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.network.remotechanges;

import org.gradely.client.network.remotechanges.FileUpdater;

/**
 * This class contains thread subclasses so that when a new file change comes in from the server, the program can process the result in paralell.
 * @author Matt
 */
public class FileUpdaterThreads {

    //================= Fields =================================
    
    //================= Constructors ===========================

    private FileUpdaterThreads() {

    }
    
    /**
     * Thread for file create. When a file needs to be downloaded from a server.
     */
    public static class FileCreate extends Thread {
        
        @Override
        public void run(){
            
            FileUpdater.createFile();
            this.setName("Gradely File Create");
            
        }
        
        
    }

    /**
     * Spawns a thread for file deleteion. 
     */
    public static class FileDelete extends Thread {

        @Override
        public void run(){
            
            FileUpdater.deleteFile();
            this.setName("Gradely File Delete");
            
        }
    }
    
        /**
     * Spawns a thread for file modification and download. 
     */
    public static class FileModify extends Thread {

        @Override
        public void run(){
            
            FileUpdater.modifyFile();
            this.setName("Gradely File Modify");
            
        }
    }
    
    
     /**
     * Spawns a thread for file renaming. 
     */
    public static class FileRename extends Thread {

        @Override
        public void run(){
            
            FileUpdater.renameFile();
            this.setName("Gradely File Rename");
            
        }
    }
    
     /**
     * Spawns a thread to restore a file from the 
     */
    public static class FileRestore extends Thread {

        @Override
        public void run(){
            
            FileUpdater.restoreFile();
            this.setName("Gradely File Restore");
            
        }
    }
    
    //================= Methods ================================
    
    //------------------ Getters and Setters -------------------
}
