package org.hwdb.client;

import java.io.File;

/**
 * Represents a file path. This class takes care of realtive paths, and that directories are correct.
 * @author Matt
 */
public class FilePath {

    //================= Fields =================================
    
    private String filePath;
    private FileLocationEnum baseDirectory;
    private File fileObj;
    
    //================= Constructors ===========================

    /**
     * Creates a new file path object that points the realtive file path described.
     * @param filePath A realtive file path to the file or folder.
     * @param baseDirectory The directory 
     */
    public FilePath(String filePath, FileLocationEnum baseDirectory) {

        this.filePath = filePath;
        this.baseDirectory = baseDirectory;
        
        
        String firstPart = "/";
        
        switch (baseDirectory){
            
            case ROOT: firstPart = Configuration.getRootDirecory(); break;
            case USERPROFILE: firstPart = Configuration.getUserProfileDirectory(); break;
            case USERAPPS: firstPart = Configuration.getUserAppsDirectory(); break;
            case INSTALL: firstPart = Configuration.getInstallDirectory(); break;
            case BOXFOLDER: firstPart = Configuration.getBoxFolderDirectory(); break;
            
        }
        
       this.fileObj = new File(firstPart, filePath);
       
       

    }
    
    /**
     * Creates a new file path object that points the absolute file path described.
     * @param absoluteFilePath The absolute file path to the disk resource.
     */
    public FilePath(String absoluteFilePath) {
        
        this.filePath = absoluteFilePath;
        this.baseDirectory = FileLocationEnum.ROOT;
        this.fileObj = new File(absoluteFilePath);
    }

    //================= Methods ================================
    
    /**
     * Figures out if the file exists or not. Returns true if it is a directory or file.
     * @return Returns true if the file/folder exists, false otherwise
     */
    public boolean fileExists() {
        
        return fileObj.exists();
        
    }
    
    /**
     * Returns true if the path is a directory.
     * @return Returns true if it is a folder, false otherwise
     */
    public boolean isDirectory() {
        return fileObj.isDirectory();
    }
    
    
    
    /**
     * Gets the absolute path that this object represents.
     * @return A string represeting the absolute path usable by Java's file methods.
     */
    public String getAbsolutePath() {
        
        return fileObj.getPath();
        
    }
    
    /**
     * Much like get absolute path, execpt this returns a file object for use.
     * @return 
     */
    public File getFileObject() {
        return fileObj;
    } 
    
    //------------------ Getters and Setters -------------------

}
