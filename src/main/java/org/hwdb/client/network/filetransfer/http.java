package org.hwdb.client.network.filetransfer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hwdb.client.config.Configuration;
import org.hwdb.client.FilePath;

/**
 * Uploads files using http. This is a static class.
 * 
 * @author Matt
 */
public class http implements FileTransferInterface {

    //================= Fields =================================
    
    //================= Constructors ===========================

    public http(){
        
    }

    //================= Methods ================================
    
     /**
     * Uploads a file with multiple requests using the using content-range header.
     * @param url
     * @param filePath the file to upload
     * @return the response from the webserver as a string.
     */
    public void uploadFile(FileTransferDescription ftd) throws FileNotFoundException, IOException
    {
        FilePath filepath = ftd.getFilepath();
        URL url = ftd.getUrl();
        
        //prechecks on the file
        if(!filepath.fileExists())
        {
            // well looks like the file does not exist.
            throw new java.io.FileNotFoundException("File "+filepath.getAbsolutePath()+" does not exist.");
        }
        else if(filepath.isDirectory())
        {
            //the file is actually a directory
            throw new java.io.FileNotFoundException("File "+filepath.getAbsolutePath()+" is actually a directory.");
        }
        
        HttpClient httpclient = new DefaultHttpClient();
        
        
        java.io.File file = filepath.getFileObject();
        
        long totalLength = file.length();
        
        int chunkSize = Configuration.getUploadChunkSize();
        
        long iterations = (totalLength/chunkSize)+1;
        
        FileInputStream fis = new FileInputStream(file);
        
        for (long i=0; i<iterations; i++)
        {
            HttpPost httppost = new HttpPost(url.toExternalForm());
            
            //Let's get the file out of the disk.
            byte[] b;
            if(i==iterations-1)
            {
                //last lap
                b = new byte[(int)(totalLength%chunkSize)];
            }
            
            b = new byte[chunkSize];
            int chunkLength = fis.read(b);
            
            //check the array size
            if (chunkLength < 0)
            {
                //Looks like we have reached the end of the file
                break;
            }
            else if(chunkLength < b.length)
            {
                //Seems we created more room than we needed
                byte[] newb = new byte[chunkLength];
                System.arraycopy(b, 0, newb, 0, chunkLength);
                b = newb; 
            }
            else
            {
                //Cool! looks like we filled up the array entirely.
            }
            
            
            //Go ahead and submit the http request

            httppost.addHeader("Content-Range", (chunkSize*i)+"-"+((chunkSize*i)+b.length)+"/"+totalLength);
            httppost.addHeader("Content-Length", Integer.toString(b.length));
            ByteArrayEntity entity = new ByteArrayEntity(b,ContentType.APPLICATION_OCTET_STREAM);
            
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //return httpclient.execute(httppost, responseHandler);
        }
    }
    
    /**
     * Downloads a file over http.
     * @param url
     * @param filepath 
     */
    public void downloadFile(FileTransferDescription ftd)
    {
        
    }
    
    //------------------ Getters and Setters -------------------
}
