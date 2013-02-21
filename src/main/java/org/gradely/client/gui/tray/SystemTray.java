/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.gui.tray;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import org.gradely.client.FileLocationEnum;
import org.gradely.client.FilePath;
import org.gradely.client.gui.settings.SettingsDialog;
import org.gradely.client.logging.Logging;
import tray.SystemTrayAdapter;
import tray.SystemTrayProvider;
import tray.TrayIconAdapter;

/**
 * Creates and maintains the system tray. Uses the  taksan / native-tray-adapter for good display on linux too.
 * @author Matt
 */
public class SystemTray {

    //================= Fields =================================
    
    private static IconStatusEnum currentStatus;
    private static TrayIconAdapter trayIconAdapter;
    
    //================= Constructors ===========================

    private SystemTray() {

    }

    //================= Methods ================================
    
    /**
     * Creates the system tray.
     */
    public static void init()
    {
        try
        {
            SystemTrayAdapter trayAdapter = new SystemTrayProvider().getSystemTray();
            URL imageUrl = getImageUrl();  
            String tooltip = org.gradely.client.config.Constants.formalAppName;   
            PopupMenu popup = getPopupMenu();  
            trayIconAdapter = trayAdapter.createAndAddTrayIcon(imageUrl,tooltip,popup); 
        }
        catch (MalformedURLException e)
        {
            //Can't create tray due to missing icon file.
            Logging.fatal("Can't create tray due to missing icon file.",e);
        }
            
    }
    
    /**
     * Figures out where the image url exists, and prepares it for use.
     * @return 
     */
    public static URL getImageUrl() throws MalformedURLException
    {
        try
        {
            if (currentStatus == IconStatusEnum.SYNCED)
            {
                return new URL("file://"+new FilePath("/icons/synced.png", FileLocationEnum.INSTALL).getAbsolutePath());
            }
            else if (currentStatus == IconStatusEnum.SYNCING)
            {
                return new URL("file://"+new FilePath("/icons/syncing.png", FileLocationEnum.INSTALL).getAbsolutePath());
            }
            else if (currentStatus == IconStatusEnum.ERROR)
            {
                return new URL("file://"+new FilePath("/icons/error.png", FileLocationEnum.INSTALL).getAbsolutePath());
            }
            else
            {
                return new URL("file://"+new FilePath("/icons/error.png", FileLocationEnum.INSTALL).getAbsolutePath());
            }
        }
        catch(MalformedURLException ex)
        {
            Logging.warning("Cannot find a icon url. Trying to get icon "+currentStatus.name()+".",ex);
            throw ex;
        }

    }
    
    /**
     * Fills out the menu that pops up when you click on the system tray icon.
     * @return The appropriate popup menu
     */
    public static PopupMenu getPopupMenu()
    {
        PopupMenu p = new PopupMenu();
        
        MenuItem mi1 = new MenuItem();
        mi1.setLabel("Settings");
        mi1.setEnabled(true);
        mi1.addActionListener(new SettingsActionListener());
        p.add(mi1);
        
        MenuItem mi2 = new MenuItem();
        mi2.setLabel("Launch Website");
        mi2.setEnabled(true);
        mi2.addActionListener(new WebsiteActionListener());
        p.add(mi2);
        
        return p;
        
    }


    /**
     * The action listener for the "Settings" option on the menu.
     */
    public static class SettingsActionListener implements ActionListener
    {
        /**
         * Fires whenever "Settings" is clicked
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            SettingsDialog.init();
        }
    }
    
    /**
     * The action listener for the "Launch Website" option on the menu.
     */
    public static class WebsiteActionListener implements ActionListener
    {
        /**
         * Fires whenever "Launch Website" is clicked
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //TODO
        }
    }
    
    
    //------------------ Getters and Setters -------------------
    
        /**
     * @return the currentStatus
     */
    public static IconStatusEnum getCurrentStatus() {
        return currentStatus;
    }

    /**
     * @param aCurrentStatus the currentStatus to set
     */
    public static void setCurrentStatus(IconStatusEnum aCurrentStatus) {
        currentStatus = aCurrentStatus;
    }
    
    
}
