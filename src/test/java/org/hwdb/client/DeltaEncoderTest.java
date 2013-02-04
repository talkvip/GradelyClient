


package org.hwdb.client;

import org.junit.Test;
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
public class DeltaEncoderTest {
    
    public DeltaEncoderTest() {
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
     * Test of generateDelta method, of class DeltaEncoder.
     */
    @Test
    public void testGenerateDelta() throws Exception {
        System.out.println("generateDelta");
        FilePath parent = new FilePath("/desktop/parent.doc",FileLocationEnum.USERPROFILE);
        FilePath child = new FilePath("/desktop/child.doc",FileLocationEnum.USERPROFILE);
        FilePath target = new FilePath("/desktop/delta.dif",FileLocationEnum.USERPROFILE);
        DeltaEncoder.generateDelta(parent, child, target);
        
        if (!target.fileExists())
        {
            fail("file was not created");
        }
        //fail("The test case is a prototype.");
    }

    /**
     * Test of applyDelta method, of class DeltaEncoder.
     */
    @Test
    public void testApplyDelta() throws Exception {
        System.out.println("applyDelta");
        FilePath parent = new FilePath("/desktop/parent.doc",FileLocationEnum.USERPROFILE);
        FilePath delta = new FilePath("/desktop/delta.dif",FileLocationEnum.USERPROFILE);
        FilePath target = new FilePath("/desktop/applied.doc",FileLocationEnum.USERPROFILE);

        DeltaEncoder.applyDelta(parent, delta, target);
        
        if (!target.fileExists())
        {
            fail("file was not created. File: "+target.getAbsolutePath());
        }
    }

}
