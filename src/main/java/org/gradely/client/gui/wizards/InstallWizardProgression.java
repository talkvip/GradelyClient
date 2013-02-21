/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.gui.wizards;

import org.gradely.client.gui.PanelAbstractClass;
import org.gradely.client.gui.UserPanel;

/**
 * When it is time to figure out the next panel to display in a wizard, this class takes care of it. 
 * @author Matt
 */
public class InstallWizardProgression implements WizardProgressionInterface {

    //================= Fields =================================
    private int currentPanel;
    
    //================= Constructors ===========================

    public InstallWizardProgression() {
        //Negitive 1 so that the first getNextPanel will return the first panel.
        currentPanel = -1;

    }

    //================= Methods ================================
    
    /**
     * Used to determine if the BottomButtons should be FinalButtons instead.
     * @return True if there is not another panel in the wizard.
     */
    @Override
    public boolean isFinalPanel()
    {
        int i = currentPanel+1;
        
        try
        {
            getNthPanel(i);
        }
        catch (IndexOutOfBoundsException e)
        {
            return true;
        }
        
        return false;
    }
    
    
    /**
     * Gets the next panel in the progression of the wizard
     * @return a JPanel ready for insertion in WizardDialog
     */
    @Override
    public PanelAbstractClass getNextPanel() throws IndexOutOfBoundsException
    {
        currentPanel = currentPanel+1;
        return getNthPanel(currentPanel);
    }
    
    /**
     * Gets that last panel again in the progression of the panels. Also sets current panel back one.
     * @return a JPanel ready for insertion in WizardDialog
     */
    @Override
    public PanelAbstractClass getPreviousPanel() throws IndexOutOfBoundsException
    {
        currentPanel = currentPanel-1;
        return getNthPanel(currentPanel);
    }
    
    /**
     * Gets a panel of a zero-based way of the wizard.
     * @param i The number in order of the panel
     * @return a JPanel ready for insertion in WizardDialog
     * @throws IndexOutOfBoundsException Thrown if there is no panel there.
     */
    @Override
    public PanelAbstractClass getNthPanel(int i) throws IndexOutOfBoundsException
    {
        
        switch(i)
        {
            case 0: return new UserPanel();
            default: throw new IndexOutOfBoundsException("There is no panel at i="+i);
        }
        
    }
    
    //------------------ Getters and Setters -------------------
}
