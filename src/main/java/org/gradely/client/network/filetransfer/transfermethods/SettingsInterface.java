/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.network.filetransfer.transfermethods;

/**
 * Replaces the Syncany Connection Class. The connection takes care of all configuration of the uploading and downloading.
 * @author Matt
 */
public interface SettingsInterface {

    //================= Fields =================================
    
    //================= Constructors ===========================

    //================= Methods ================================

        /**
     * A no-spaces, no funny characters string representing the transfter method.
     */
    public String getId();
    
    /**
     * The human-readable name for the transfer method (e.g. Amazon S3)
     */
    public String getName();
    
    /**
     * A human-readable description of the file transfer method (e.g. Stores files on the Amazon S3 storage servers.)
     */
    public String getDescripton();

    
    //------------------ Getters and Setters -------------------
}
