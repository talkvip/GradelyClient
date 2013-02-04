package org.hwdb.client.database;

import java.sql.Connection;
import org.hwdb.client.FileLocationEnum;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.hwdb.client.FilePath;

/**
 *
 * @author Matt
 */
public class ConnectionPoolTest {
    
    public ConnectionPoolTest() {
        
       try
       {
        ConnectionPool.getInstance().createDatabase(new FilePath("/desktop/testing",FileLocationEnum.USERPROFILE),"testing");
       }
       catch(Exception e)
       {
           //crap
       }
        
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
     * Test of getInstance method, of class ConnectionPool.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ConnectionPool result = ConnectionPool.getInstance();
        
        if(result == null)
        {
            fail("result is null");
        }

    }

    /**
     * Test of getConnection method, of class ConnectionPool.
     */
    @Test
    public void testGetConnection() throws Exception {
        System.out.println("getConnection");
        ConnectionPool instance = new ConnectionPool();
        Connection result = instance.getConnection();
        
        if(result == null)
        {
            fail("result is null");
        }

    }

    /**
     * Test of returnConnection method, of class ConnectionPool.
     */
    @Test
    public void testReturnConnection() {

    }
}
