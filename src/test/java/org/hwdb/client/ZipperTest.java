


package org.hwdb.client;

import java.io.FileNotFoundException;
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
public class ZipperTest {
    
    public ZipperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        try
        {
        
            Configuration.writeConfigurationFile();
        }
        catch(Exception e)
        {

        }
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of zip method, of class Zipper.
     */
    @Test
    public void testZip() throws Exception {
        System.out.println("zip");
        FilePath in = new FilePath("/desktop/parent.doc",FileLocationEnum.USERPROFILE);
        FilePath out = new FilePath("/desktop/targetgzip.gz",FileLocationEnum.USERPROFILE);
        Zipper.zip(in, out);
        // TODO review the generated test code and remove the default call to fail.
        
        if(out.fileExists())
        {
            
        }
        else
        {
            fail("File was not created");
        }
        
    }

    /**
     * Test of unzip method, of class Zipper.
     */
    @Test
    public void testUnzip() throws Exception {
        System.out.println("unzip");
        
        FilePath zipped = new FilePath("/desktop/targetgzip.gz",FileLocationEnum.USERPROFILE);
        FilePath out = new FilePath("/desktop/parentunzipped.doc",FileLocationEnum.USERPROFILE);
        
        if(!zipped.fileExists())
        {
            testZip();
        }
        
        Zipper.unzip(zipped, out);
        
        if(!out.fileExists())
        {
            fail("the file does not exist");
        }
        
    }
}
