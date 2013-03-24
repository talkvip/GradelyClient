/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gradely.client.delta;

import java.util.ArrayList;
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
public class RDiffDecoderTest {
    
    public RDiffDecoderTest() {
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
     * Test of add method, of class RDiffDecoder.
     */
    @Test
    public void testAdd() throws Exception {
        System.out.println("add");
        FilePath parent = null;
        FilePath diffFile = null;
        FilePath target = null;
        RDiffDecoder.add(parent, diffFile, target);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyOperations method, of class RDiffDecoder.
     */
    @Test
    public void testApplyOperations() throws Exception {
        System.out.println("applyOperations");
        ArrayList<DiffOperation> deleteLst = null;
        ArrayList<DiffOperation> insertLst = null;
        FilePath target = null;
        FilePath source = null;
        RDiffDecoder.applyOperations(deleteLst, insertLst, target, source);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchForInserts method, of class RDiffDecoder.
     */
    @Test
    public void testSearchForInserts() {
        System.out.println("searchForInserts");
        long index = 0L;
        ArrayList<DiffOperation> insertLst = null;
        int expResult = 0;
        int result = RDiffDecoder.searchForInserts(index, insertLst);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchForDeletes method, of class RDiffDecoder.
     */
    @Test
    public void testSearchForDeletes() {
        System.out.println("searchForDeletes");
        long index = 0L;
        ArrayList<DiffOperation> deleteLst = null;
        int expResult = 0;
        int result = RDiffDecoder.searchForDeletes(index, deleteLst);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subtract method, of class RDiffDecoder.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        FilePath child = null;
        FilePath diffFile = null;
        FilePath target = null;
        RDiffDecoder.subtract(child, diffFile, target);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
