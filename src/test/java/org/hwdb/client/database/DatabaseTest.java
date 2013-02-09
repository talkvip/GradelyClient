
package org.hwdb.client.database;

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

    /**
     * Test of createTables method, of class Database.
     */
    @Test
    public void testCreateTables() throws Exception {
        System.out.println("createTables");
        FilePath tableDefCsv = null;
        
        try
        {
            Database.createTables(tableDefCsv);
        }
        catch(SQLException e)
        {
            fail("Message: "+e.getMessage()+" Error Code: "+e.getErrorCode()+" SQL State: "+e.getSQLState());
        }

    }
}
