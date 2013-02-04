package org.hwdb.client.network.filetransfer;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.hwdb.client.network.filetransfer.FileTransferDescription;
import java.net.URL;
import org.hwdb.client.FilePath;

/**
 * This interface is used for all classes that upload and download files.
 * @author Matt
 */
public interface FileTransferInterface {
    
    /**
     * Uploads a file to a remote source.
     * @param url The url to upload the file to.
     * @param filepath The file to upload.
     */
    public void uploadFile(FileTransferDescription ftd) throws FileNotFoundException, IOException;
    
    /**
     * Downloads a file from a remote server.
     * @param url The url of the file.
     * @param filepath The filepath to download the file to.
     */
    public void downloadFile(FileTransferDescription ftd )throws FileNotFoundException, IOException;
    
}
