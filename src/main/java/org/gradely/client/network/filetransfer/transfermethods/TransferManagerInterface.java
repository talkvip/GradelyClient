package org.gradely.client.network.filetransfer.transfermethods;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.gradely.client.network.filetransfer.FileTransferDescription;
import java.net.URL;
import org.gradely.client.FilePath;
import org.gradely.client.network.filetransfer.FileTransferDescription;

/**
 * This interface is used for all classes that upload and download files.
 * @author Matt
 * @deprecated Use Syncany's plugins instead.
 */
public interface TransferManagerInterface {
    
    /**
     * Connects to the remote server.
     */
    public void connect();
    
    /**
     * Disconnects from the remote server.
     */
    public void disconnect();
            
    
    /**
     * Uploads a file to a remote source.
     * @param url The url to upload the file to.
     * @param filepath The file to upload.
     */
    public void upload(FileTransferDescription ftd) throws FileNotFoundException, IOException;
    
    /**
     * Downloads a file from a remote server.
     * @param url The url of the file.
     * @param filepath The filepath to download the file to.
     */
    public void download(FileTransferDescription ftd )throws FileNotFoundException, IOException;
    
}
