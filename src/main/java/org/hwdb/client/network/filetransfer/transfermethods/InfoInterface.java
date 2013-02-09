/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hwdb.client.network.filetransfer.transfermethods;

/**
 * Replaces the Syncany PluginInfo interface. This class describes the File Transfer information and constants.
 * @author Matt
 * @deprecated Functionality here has been added to the SettingsInterface which replaces the Syncany Connection class.
 */
public interface InfoInterface {

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
