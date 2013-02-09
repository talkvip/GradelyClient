package org.gradely.client;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 *
 * @author Matt
 */

/**
 * This class encapulates all of the metadata gathered on a file.
 * 
 * @author Matt
 */
public class FileMetadata {
    
    //============== Fields ============================== 
    private long id;
    private boolean isCurrent;
    private String filename;
    private String filepath;
    private int version;
    private boolean isDeleted;
    private boolean isDirectory;
    private Date clientModifiedTime;
    private boolean isReturnedAssignment;
    private int assignmentId;
    private String note;
    private String returnedNote;
    
    /**
     * For directorys, the hashsum is the filepath. 
     */
    private String hashsum;

    //============== Constructors =========================
    
    
    
    //============== Methods ============================== 
    
    
    
    
    
    //-------------- Getters and Setters -----------------
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public Date getClientModifiedTime() {
        return clientModifiedTime;
    }

    public void setClientModifiedTime(Date clientModifiedTime) {
        this.clientModifiedTime = clientModifiedTime;
    }

    public boolean isIsReturnedAssignment() {
        return isReturnedAssignment;
    }

    public void setIsReturnedAssignment(boolean isReturnedAssignment) {
        this.isReturnedAssignment = isReturnedAssignment;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignment_id(int assignment_id) {
        this.assignmentId = assignment_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReturnedNote() {
        return returnedNote;
    }

    public void setReturnedNote(String returnedNote) {
        this.returnedNote = returnedNote;
    }

    public String getHashsum() {
        return hashsum;
    }

    public void setHashsum(String hashsum) {
        this.hashsum = hashsum;
    }
    
    
    
    
    
}
