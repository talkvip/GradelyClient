package org.hwdb.client.network;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.net.URL;
import java.nio.charset.Charset;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hwdb.client.config.Configuration;
import org.hwdb.client.FilePath;




/**
 * 
 * This class is responsible for handling the connection with the server, file transfer is handled through the FileTransfer Class` String must be utf-8
 * @author Matt
 */
public class Network {
    
    /**
    * Authenticates the client to the server
    */
    public void authenticate() {
        
        //TODO
        //send username to server.
        //server responds with a random number, as well as that is encrypted using the password.
        //Client decrypts the password using the known password.
        //Client sends the random number to the server to prove that the client has the password.
        
    }
    
    
    /**
     * 
     * @param url 
     * @param data The data to be put in the request body to be sent.
     * @param mimeType The mime type of the data to be sent to the webserver. "text/json"
     * @return Returns a string containing the reponse from the webserver.
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    public static String Post(URL url, String data, String mimeType) throws UnsupportedEncodingException, IOException
    {
     
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httppost = new HttpPost(url.toExternalForm());


            InputStream inputStream = new ByteArrayInputStream(data.getBytes("UTF-8"));

            
            InputStreamEntity reqEntity = new InputStreamEntity(inputStream, -1);
            reqEntity.setContentType(mimeType);
            
            //reqEntity.setChunked(true);
            
            // It may be more appropriate to use FileEntity class in this particular
            // instance but we are using a more generic InputStreamEntity to demonstrate
            // the capability to stream out data from any arbitrary source
            //
            // FileEntity entity = new FileEntity(file, "binary/octet-stream");

            httppost.setEntity(reqEntity);
            
            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httppost, responseHandler);

            return responseBody;
            
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        
    }
    

}
