package org.hwdb.client;

/**
 * This is a tuple encoding a filepath and a tuple.
 * @author Matt
 */
public class BooleanFilepathChecksum {

    //================= Fields =================================
    private FilePath filepath;
    private boolean renamed;
    private String checksum;
    //================= Constructors ===========================

    public BooleanFilepathChecksum(boolean b,FilePath filepath,String checksum) {
        
        this.filepath = filepath;
        this.renamed = b;
        this.checksum = checksum;
        
    }
    


    //================= Methods ================================
    
    
    //------------------ Getters and Setters -------------------

    /**
     * @return the filepath
     */
    public FilePath getFilepath() {
        return filepath;
    }

    /**
     * @param filepath the filepath to set
     */
    public void setFilepath(FilePath filepath) {
        this.filepath = filepath;
    }

    /**
     * @return the b
     */
    public boolean isRenamed() {
        return renamed;
    }

    /**
     * @param b the b to set
     */
    public void setRenamed(boolean b) {
        this.renamed = b;
    }

    /**
     * @return the checksum
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * @param checksum the checksum to set
     */
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
