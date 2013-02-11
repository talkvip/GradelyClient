/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gradely.client.gui.errors;

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
public class ErrorPromptTest {
    
    public ErrorPromptTest() {
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
     * Test of init method, of class ErrorPrompt.
     */
    @Test
    public void testInit_String() {
        System.out.println("init");
        String message = "This is a test.";
        ErrorPrompt.init(message);
    }

    /**
     * Test of init method, of class ErrorPrompt.
     */
    @Test
    public void testInit_String_String() {
        System.out.println("init");
        String message = "This is a test.";
        String helpMessage = "This is a help message.";
        ErrorPrompt.init(message, helpMessage);
    }
}
