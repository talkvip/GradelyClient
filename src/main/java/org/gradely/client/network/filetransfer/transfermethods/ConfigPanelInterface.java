/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.network.filetransfer.transfermethods;

/**
 * This interface allows for a GUI config panel to be displayed.
 * @author Matt
 */
public interface ConfigPanelInterface {

    //================= Fields =================================
    
    //================= Constructors ===========================

    //================= Methods ================================
    
    /**
     * Writes the user's form input into a SettingsInterfaceObject, and then saves to file.
     * 
     */
    public void save();
    
    /**
     * Loads the configuration file to the GUI form.
     */
    public void load();
    
    /**
     * Displays a GUI the user uses to modify connection settings.
     */
    public void init();
    
    //------------------ Getters and Setters -------------------
}
