package org.gradely.client;



import org.gradely.client.FileLocationEnum;
import org.gradely.client.FilePath;
import org.gradely.client.SHA2Hashsum;
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
public class SHA2HashsumTest {
    
    public SHA2HashsumTest() {
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
     * Test of computeHash method, of class Hashsum.
     */
    @Test
    public void testComputeHash() throws Exception {
        System.out.println("computeHash");
        FilePath in = new FilePath("/desktop/parent.doc",FileLocationEnum.USERPROFILE);
        
        String expResult = "d3f77bec9d021ead072a7b631b2fc6112b7db80180c529def18cb0a9c1a39aff";
        
        String result = SHA2Hashsum.computeHash(in);
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testComputeHashBytes() throws Exception 
    {
        System.out.println("computeHashBytes");
        FilePath in = new FilePath("/desktop/parent.doc",FileLocationEnum.USERPROFILE);
        
        byte[] result = SHA2Hashsum.computeHashBytes(in);
        
        System.out.println("computeHashBytes().length = "+result.length);
        
    }

    /**
     * Test of bytesToHex method, of class Hashsum.
     */
    @Test
    public void testBytesToHex() {
        System.out.println("bytesToHex");
        byte[] bytes = {0xa,0xb,0xc,0xd};
        String expResult = "0A0B0C0D";
        String result = SHA2Hashsum.bytesToHex(bytes);
        assertEquals(expResult, result);

    }
}
