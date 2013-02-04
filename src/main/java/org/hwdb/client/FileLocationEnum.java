package org.hwdb.client;

/**
 * This enum represents directory locations important to the program.
 * @author Matt
 */
public enum FileLocationEnum {
    /**
     * Root of the file system. Ex. C:/, /
     */
    ROOT, 
    
    /**
     * User home folder. Ex. C:/Users/username, /home/username
     */
    USERPROFILE,
    
    /**
     * User app data directory. Ex. C:/Users/username/AppData/Roaming/appName, /home/.appname
     */
    USERAPPS,
    
    /**
     * Install directory. Ex. C:/Program Files/WhatEverNameWeGiveThisProgram
     */
    INSTALL,
    
    /**
     * Box location, the directory to be synced. Ex. C:/Users/username/Documents/BoxFolder
     */
    BOXFOLDER
    
    
}
