/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gradely.client.network;

import java.io.IOException;
import java.util.Map;
import org.gradely.client.logging.Logging;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matthew P. Prichard <matthew@matthewprichard.net>
 */
public class NetworkTest {
    
    public NetworkTest() {
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
     * Test of Post method, of class Network.
     */
    @Test
    public void testPost() throws Exception {
        System.out.println("Post");
        UrlPath url = new UrlPath("http://httpbin.org/post",ServerLocationsEnum.ABSOLUTE);
        String data = "{\"test\":\"123\"}";
        String mimeType = "test/json";
        
        try
        {
            String result = Network.Post(url, data, mimeType);
            System.out.println(result);
        }
        catch(IOException e)
        {
            Logging.debug("testPost()", e);
            throw e;
        }
        
        

    }

    /**
     * Test of get method, of class Network.
     */
    @Test
    public void testGet() throws Exception {
        System.out.println("get");
        UrlPath url = new UrlPath("http://google.com", ServerLocationsEnum.ABSOLUTE);
        String result = Network.get(url);
        
        System.out.println(result);
        
    }
}
