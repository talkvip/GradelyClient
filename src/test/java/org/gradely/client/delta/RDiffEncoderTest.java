/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gradely.client.delta;

import org.gradely.client.FileLocationEnum;
import org.gradely.client.FilePath;
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
public class RDiffEncoderTest {
    
    public RDiffEncoderTest() {
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
     * Takes a delta and applies a delta
     * @throws Exception 
     */
    @Test
    public void totalTest() throws Exception
    {
        FilePath parent = new FilePath("/desktop/parent.doc",FileLocationEnum.USERPROFILE);
        FilePath child = new FilePath("/desktop/child.doc",FileLocationEnum.USERPROFILE);
        FilePath diffFile = new FilePath("/desktop/delta.dif",FileLocationEnum.USERPROFILE);
        FilePath newFile = new FilePath("/desktop/reconstitutedParent.doc",FileLocationEnum.USERPROFILE);
        
        RDiffEncoder.generateDelta(parent, child, diffFile);
        
        RDiffDecoder.add(parent, diffFile, newFile);
        
        if(!newFile.fileExists())
        {
            throw new Exception("New file was not created.");
        }
        
        if(!diffFile.fileExists())
        {
            throw new Exception("The diff file does not exist.");
        }

    }
    
//    /**
//     * Test of generateDelta method, of class RDiffEncoder.
//     */
//    @Test
//    public void testGenerateDelta() throws Exception {
//        System.out.println("generateDelta");
//        FilePath parent = new FilePath("/desktop/parent.doc",FileLocationEnum.USERPROFILE);
//        FilePath child = new FilePath("/desktop/child.doc",FileLocationEnum.USERPROFILE);
//        FilePath target = new FilePath("/desktop/delta.dif",FileLocationEnum.USERPROFILE);
//        
//        RDiffEncoder.generateDelta(parent, child, target);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
