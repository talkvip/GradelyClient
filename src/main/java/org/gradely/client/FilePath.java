package org.gradely.client;

import org.gradely.client.config.Configuration;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        
       firstPart = resolveEnumValue(baseDirectory);
        
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
     * Turns the enum into the appropriate file path.
     * @param fle BoxFolder, InstallFolder, ect.
     * @return string
     */
    private static String resolveEnumValue(FileLocationEnum baseDirectory)
    {
        String firstPart = "";
        
        Configuration config = Configuration.getInstance();
        
        switch (baseDirectory){

            case ROOT: firstPart = config.getRootDirecory(); break;
            case USERPROFILE: firstPart = config.getUserProfileDirectory(); break;
            case USERAPPS: firstPart = config.getUserAppsDirectory(); break;
            case INSTALL: firstPart = config.getInstallDirectory(); break;
            case BOXFOLDER: firstPart = config.getBoxFolderDirectory(); break;
            
        }
        
        return firstPart;
    }
    
    /**
     * Figures out what the lower, realtive path is to the enum folder.
     * @return 
     */
    public String getLowerPart()
    {
        return new File(resolveEnumValue(baseDirectory)).toURI().relativize(fileObj.toURI()).getPath();
    }
    
    /**
     * Turns the File object long time representation into human readable time.
     */
    public String getTimeModified()
    {
        long time = fileObj.lastModified();
        
        Date date = new Date(time);
        Format format = new SimpleDateFormat(org.gradely.client.config.Constants.dateFormat);
        return format.format(date).toString();
    }
    
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
    
    
    /**
     * For comparisons with other filepaths.
     * @param e 
     */
    public boolean equals(FilePath other)
    {
        String o = other.getAbsolutePath().toLowerCase();
        String t = this.getAbsolutePath().toLowerCase();
        
        if (o == t)
        {
            return true;
        }
        
        return false;
    }
    
    //------------------ Getters and Setters -------------------

}
