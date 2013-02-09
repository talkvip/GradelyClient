/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hwdb.client.network.remotechanges;

import org.hwdb.client.network.remotechanges.FileUpdater;

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
            
        }
        
        
    }

    /**
     * Spawns a thread for file deleteion. 
     */
    public static class FileDelete extends Thread {

        @Override
        public void run(){
            
            FileUpdater.deleteFile();
            
        }
    }
    
        /**
     * Spawns a thread for file modification and download. 
     */
    public static class FileModify extends Thread {

        @Override
        public void run(){
            
            FileUpdater.modifyFile();
            
        }
    }
    
    
     /**
     * Spawns a thread for file renaming. 
     */
    public static class FileRename extends Thread {

        @Override
        public void run(){
            
            FileUpdater.renameFile();
            
        }
    }
    
     /**
     * Spawns a thread to restore a file from the 
     */
    public static class FileRestore extends Thread {

        @Override
        public void run(){
            
            FileUpdater.restoreFile();
            
        }
    }
    
    //================= Methods ================================
    
    //------------------ Getters and Setters -------------------
}
