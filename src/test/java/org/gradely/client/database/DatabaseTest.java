
package org.gradely.client.database;

import org.gradely.client.database.Database;
import java.sql.SQLException;
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
public class DatabaseTest {
    
    public DatabaseTest() {
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
    
    @Test
    public void testInitilizeDatabase() throws Exception {
        
         System.out.println("initilizeDatabase");
        FilePath tableDefCsv = null;
        
        try
        {
            Database.initializeDatabase();
        }
        catch(SQLException e)
        {
            fail("Message: "+e.getMessage()+" Error Code: "+e.getErrorCode()+" SQL State: "+e.getSQLState());
        }
        
    }
    
}
