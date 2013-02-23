/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gradely.client.gui;

import javax.swing.JPanel;

/**
 * Why is this abstract class here? Let me tell you. There are several JFrames (UserPanel, DirectoryPanel) that are reused in different contexts. (Wizards, Settings ect.)
 * This class contains functions needed to set-up and tear-down the JFrame while saving its information. 
 * @author Matt
 */
public abstract class PanelAbstractClass extends JPanel {
    
    /**
     * Takes user-inputted form information and saves it. Probably saves it to the Configuration Object, which saves the config to disk.
     */
    public abstract void saveForms() throws Exception;
    
    /**
     * Loads appropriate values into the forms. Called when we want to display the JPanel.
     */
    public abstract void loadForms();
    
}
