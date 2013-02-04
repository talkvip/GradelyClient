package org.hwdb.client;

/**
 * This class represents the settings the file should use when being modified or created.
 * @author Matt
 */
public class FileSettings {

    //================= Fields =================================
    
    /**
     * Sets the file as read only once created
     */
    private boolean isReadOnly;
    //private boolean isHidden;
    private boolean isDirectory;
    private FilePath filepath;
    
    
    //================= Constructors ===========================

    public FileSettings(FilePath filepath, boolean isDirectory, boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
        this.isDirectory = isDirectory;
        this.filepath = filepath;
    }

    //================= Methods ================================
    
    //------------------ Getters and Setters -------------------
}
