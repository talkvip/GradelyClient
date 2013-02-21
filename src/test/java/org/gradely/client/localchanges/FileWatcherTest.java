/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gradely.client.localchanges;

import org.gradely.client.FileLocationEnum;
import org.gradely.client.FilePath;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Matt
 */
public class FileWatcherTest {
    
    public FileWatcherTest() {
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
     * Test of start method, of class FileWatcher.
     */
    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        FileWatcher instance = new FileWatcher(new FilePath("",FileLocationEnum.USERPROFILE));
        instance.start();
        //If it starts, good enough for me!
        instance.stop();
        //If it stops, that's good too.
    }

}
