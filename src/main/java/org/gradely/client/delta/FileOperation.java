/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

/**
 * The operations needed to construct the new file. This is computed from a list of DiffOperatios and is only used internally to RDiff.
 * @author Matt
 */
public class FileOperation {

    //================= Fields =================================
    
    public long startLocation;
    public long length;
    
    public long startLocationOtherFile;
    
    /**
     * If this is false, to means copy this data from the original file. If true it means insert data from this object.
     */
    public boolean isInsert;
    
    public byte[] data;
    
    //================= Constructors ===========================

    public FileOperation(long startLocation, long length, boolean isInsert) 
    {
        this.startLocation = startLocation;
        this.length = length;
        this.isInsert = isInsert;
    }
    
    public FileOperation(long startLocation, long length, boolean isInsert, byte[] data) 
    {
        this.startLocation = startLocation;
        this.length = length;
        this.isInsert = isInsert;
        this.data = data;
    }
    
    
    public FileOperation()
    {
        
    }
    

    //================= Methods ================================
    
    //------------------ Getters and Setters -------------------

    /**
     * @return the startLocation
     */
    public long getStartLocation() {
        return startLocation;
    }

    /**
     * @return the length
     */
    public long getLength() {
        return length;
    }

    /**
     * @param startLocation the startLocation to set
     */
    public void setStartLocation(long startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the startLocationOtherFile
     */
    public long getStartLocationOtherFile() {
        return startLocationOtherFile;
    }

    /**
     * @return the isInsert
     */
    public boolean isIsInsert() {
        return isInsert;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param length the length to set
     */
    public void setLength(long length) {
        this.length = length;
    }

    /**
     * @param startLocationOtherFile the startLocationOtherFile to set
     */
    public void setStartLocationOtherFile(long startLocationOtherFile) {
        this.startLocationOtherFile = startLocationOtherFile;
    }

    /**
     * @param isInsert the isInsert to set
     */
    public void setIsInsert(boolean isInsert) {
        this.isInsert = isInsert;
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }
}
