package org.gradely.client.network;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;




/**
 * 
 * This class is responsible for handling the connection with the server, file transfer is handled through the FileTransfer Class` String must be utf-8
 * @author Matt
 */
public class Network {
    

    
    /**
     * Submits a get request 
     * @param url The url to submit to
     * @param var the variables after the question mark
     * @return 
     */
    public static String get(UrlPath url) throws IOException
    {
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(url.getURL().toString());

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            
            String responseBody = httpclient.execute(httpget, responseHandler);
            
            return responseBody;

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }
    
        
    /**
     * Submits a get request 
     * @param url The url to submit to, without a "?" or parameters.
     * @param var the variables after the question mark 
     * @return 
     */
    public static String get(UrlPath url, Map<String, String> var) throws IOException
    {
        String question = URLEncodedUtils.format(mapToKeyValueList(var), "UTF-8");
        String urlStr = url.getURL().toString()+question;
        
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(urlStr);

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            
            String responseBody = httpclient.execute(httpget, responseHandler);

            return responseBody;

        } finally {
            
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }
    
    /**
     * Converts a map to a key-value pair list.
     * @param m a map
     * @return 
     */
    public static List<NameValuePair> mapToKeyValueList(Map<String, String> m)
    {
        String[] keys = (String[])m.keySet().toArray();
        
        List<NameValuePair> lst = new ArrayList<NameValuePair>();
        
        for(int i=0; i<m.size(); i++)
        {
            lst.add(new BasicNameValuePair(keys[i], m.get(keys[i])));
        }
        
        return lst;
    }
    
    
    /**
     * Sends a post request with the specified string and mimetype.
     * @param url 
     * @param data The data to be put in the request body to be sent.
     * @param mimeType The mime type of the data to be sent to the webserver. "text/json"
     * @return Returns a string containing the reponse from the webserver.
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    public static String Post(UrlPath url, String data, String mimeType) throws UnsupportedEncodingException, IOException
    {
     
        HttpClient httpClient = new DefaultHttpClient();
        try 
        {
            HttpPost httpPost = new HttpPost(url.getURL().toString());
            HttpEntity ent = new StringEntity(data,mimeType,"UTF-8");
            httpPost.setEntity(ent);

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            
            String responseBody = httpClient.execute(httpPost, responseHandler);
                        
            return responseBody;

        }
        finally 
        {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpClient.getConnectionManager().shutdown();
        }
        
    }
    

}
