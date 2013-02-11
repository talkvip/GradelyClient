/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gradely.client.gui.settings;

import org.gradely.client.gui.PanelAbstractClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matt
 */
public class SettingsDialogTest {
    
    public SettingsDialogTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class SettingsDialog.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        SettingsDialog instance = new SettingsDialog();
        instance.init();
        
        //Does it display correctly?

    }

    /**
     * Test of changeRightPanel method, of class SettingsDialog.
     */
    @Test
    public void testChangeRightPanel() {
        System.out.println("changeRightPanel");
        PanelAbstractClass panel = null;
        SettingsDialog.changeRightPanel(panel);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
