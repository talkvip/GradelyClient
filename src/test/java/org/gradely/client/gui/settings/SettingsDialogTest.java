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
    public void testInit() throws Exception {
        System.out.println("init");
        SettingsDialog instance = new SettingsDialog();
        instance.init();
        Thread.sleep(10000);
        //Does it display correctly?

    }

}
