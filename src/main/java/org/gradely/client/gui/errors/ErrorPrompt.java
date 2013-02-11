/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.gui.errors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Creates a no-config, simple, always works error dialog. Explains to the user what went wrong, and how to fix it.
 * @author Matt
 */
public class ErrorPrompt {

    //================= Fields =================================
    
    //================= Constructors ===========================

    private ErrorPrompt() {

    }

    //================= Methods ================================
    
    /**
     * Shows an error prompt, and displays the message.
     */
    public static void init(String message)
    {
        String appName = org.gradely.client.config.Constants.appName;
        //Need to capitilize the appName
        appName = org.apache.commons.lang3.StringUtils.capitalize(appName);
        
        //custom title, warning icon
        JOptionPane.showMessageDialog(null, message,appName+" Error",JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Displays a hep message along with the error message in the error prompt.
     * @param message
     * @param helpMessage 
     */
    public static void init(String message, String helpMessage)
    {
        String totalMessage = message + "\r\n" + helpMessage;
        
        String appName = org.gradely.client.config.Constants.appName;
        //Need to capitilize the appName
        appName = org.apache.commons.lang3.StringUtils.capitalize(appName);
                
        
        //custom title, warning icon
        JOptionPane.showMessageDialog(null,totalMessage,appName+" Error",JOptionPane.WARNING_MESSAGE);
    }
    
    //------------------ Getters and Setters -------------------
}
