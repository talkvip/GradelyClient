/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.deduplication;

/**
 * This class is a container for a deduplication block's length and hashsums.
 * @author Matt
 */
public class BlockEntry {

    //================= Fields =================================
    
    private int length;
    /**
     * Only used to create the file. Not actually recorded within.
     */
    private long startLocation;
    private int adler32Hash;
    
    //This byte should have a length of 32
    private byte[] sha2Hash;
    
    /**
     * Represent the RDelta binary of the file.
     */
    private byte[] deltaData;
    
    //================= Constructors ===========================

    public BlockEntry(int length, int adler32Hash, byte[] sha2Hash) 
    {
        this.length = length;
        this.adler32Hash = adler32Hash;
        this.sha2Hash = sha2Hash;
    }
    
    public BlockEntry()
    {
        
    }

    //================= Methods ================================
    
    //------------------ Getters and Setters -------------------

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the adler32Hash
     */
    public int getAdler32Hash() {
        return adler32Hash;
    }

    /**
     * @return the sha2Hash
     */
    public byte[] getSha2Hash() {
        return sha2Hash;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @param adler32Hash the adler32Hash to set
     */
    public void setAdler32Hash(int adler32Hash) {
        this.adler32Hash = adler32Hash;
    }

    /**
     * @param sha2Hash the sha2Hash to set
     */
    public void setSha2Hash(byte[] sha2Hash) {
        this.sha2Hash = sha2Hash;
    }

    /**
     * @return the deltaData
     */
    public byte[] getDeltaData() {
        return deltaData;
    }

    /**
     * @param deltaData the deltaData to set
     */
    public void setDeltaData(byte[] deltaData) {
        this.deltaData = deltaData;
    }

    /**
     * @return the startLocation
     */
    public long getStartLocation() {
        return startLocation;
    }

    /**
     * @param startLocation the startLocation to set
     */
    public void setStartLocation(long startLocation) {
        this.startLocation = startLocation;
    }
}
