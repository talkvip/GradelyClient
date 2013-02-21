/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gradely.client.gui.wizards;

import org.gradely.client.gui.PanelAbstractClass;

/**
 * An interface for each WizardProgression. When making a new wizard, write a new WizardProgression and use it to instaniate a WizardDialog.
 * When it is time to figure out the next panel to display in a wizard, this interface takes care of it.
 * @author Matt
 */
public interface WizardProgressionInterface {
    
    
     /**
     * Used to determine if the BottomButtons should be FinalButtons instead.
     * @return True if there is not another panel in the wizard.
     */
    public boolean isFinalPanel();

    
    
    /**
     * Gets the next panel in the progression of the wizard
     * @return a JPanel ready for insertion in WizardDialog
     */
    public PanelAbstractClass getNextPanel() throws IndexOutOfBoundsException;

    
    /**
     * Gets that last panel again in the progression of the panels.
     * @return a JPanel ready for insertion in WizardDialog
     */
    public PanelAbstractClass getPreviousPanel() throws IndexOutOfBoundsException;

    
    /**
     * Gets a panel of a zero-based way of the wizard.
     * @param i The number in order of the panel
     * @return a JPanel ready for insertion in WizardDialog
     * @throws IndexOutOfBoundsException Thrown if there is no panel there.
     */
    public PanelAbstractClass getNthPanel(int i) throws IndexOutOfBoundsException;

    
}
