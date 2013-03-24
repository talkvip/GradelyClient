/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.deduplication;

import java.util.ArrayList;
import java.util.Date;

/**
 * Contains all the information needed to write a Deduplication file. Both the header data as well as the BlockEntries.
 * @author Matt
 */
public class Blocks {

    //================= Fields =================================
    
    private ArrayList<BlockEntry> blockEntries;
    private String fileName;
    private String previousFileName;
    private String SHA2Hashsum;
    private String timeOfCreation;
    
    //================= Constructors ===========================

    public Blocks(ArrayList<BlockEntry> blockEntries,String fileName,String SHA2Hashsum,String timeOfCreation) 
    {
        this.blockEntries = blockEntries;
        this.fileName = fileName;
        this.SHA2Hashsum = SHA2Hashsum;
        this.timeOfCreation = timeOfCreation;
    }
    
    public Blocks()
    {
        
    }

    //================= Methods ================================
    
    //------------------ Getters and Setters -------------------

    /**
     * @return the blockEntries
     */
    public ArrayList<BlockEntry> getBlockEntries() {
        return blockEntries;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the SHA2Hashsum
     */
    public String getSHA2Hashsum() {
        return SHA2Hashsum;
    }

    /**
     * @return the timeOfCreation
     */
    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    /**
     * @param blockEntries the blockEntries to set
     */
    public void setBlockEntries(ArrayList<BlockEntry> blockEntries) {
        this.blockEntries = blockEntries;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param SHA2Hashsum the SHA2Hashsum to set
     */
    public void setSHA2Hashsum(String SHA2Hashsum) {
        this.SHA2Hashsum = SHA2Hashsum;
    }

    /**
     * @param timeOfCreation the timeOfCreation to set
     */
    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    /**
     * @return the previousFileName
     */
    public String getPreviousFileName() {
        return previousFileName;
    }

    /**
     * @param previousFileName the previousFileName to set
     */
    public void setPreviousFileName(String previousFileName) {
        this.previousFileName = previousFileName;
    }
}
