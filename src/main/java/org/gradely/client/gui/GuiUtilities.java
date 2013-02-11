/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.gui;

import org.gradely.client.config.OperatingSystemEnum;
import org.gradely.client.logging.Logging;

/**
 * This class contains random misc. utilties pertaining to the GUI
 * @author Matt
 */
public class GuiUtilities {

    //================= Fields =================================
    
    //================= Constructors ===========================

    private GuiUtilities() {

    }

    //================= Methods ================================
    
    /**
     * Depending on the operating system, sets an appropriate "Look and Feel" for the GUI elements.
     * 
     */
    public static void setLookAndFeel()
    {
        
        OperatingSystemEnum os = org.gradely.client.config.Configuration.getInstance().getOs();
        
        try {

            if (os == OperatingSystemEnum.WINDOWS)
            {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());     
            }
            else if (os == OperatingSystemEnum.LINUX)
            {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            }
            else if (os == OperatingSystemEnum.OSX)
            {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            }
            else if (os == OperatingSystemEnum.OTHER)
            {
               javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); 
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            
            Logging.error("Could not set the Look and Feel",ex);
        }
    }
    
    //------------------ Getters and Setters -------------------
}
