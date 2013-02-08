package org.hwdb.client.network.filetransfer;

import java.net.URL;
import org.hwdb.client.FilePath;

/**
 * This describes a file transfer operation. Describes commands from the server. Used before we ever hit the Syncany plugins.
 * @author Matt
 */
public class FileTransferDescription {

    //================= Fields =================================
    
    private UploadOrDownloadEnum uploadOrDownload;
    private FileTransferMethodEnum method;
    private URL url;
    private FilePath filepath;
    
    
    //================= Constructors ===========================

    public FileTransferDescription(UploadOrDownloadEnum uploadOrDownload,FileTransferMethodEnum method,URL url,FilePath filepath) {

        this.uploadOrDownload = uploadOrDownload;
        this.method = method;
        this.url = url;
        this.filepath = filepath;
        
    }

    //================= Methods ================================
    
    //------------------ Getters and Setters -------------------

    /**
     * @return the uploadOrDownload
     */
    public UploadOrDownloadEnum getUploadOrDownload() {
        return uploadOrDownload;
    }

    /**
     * @param uploadOrDownload the uploadOrDownload to set
     */
    public void setUploadOrDownload(UploadOrDownloadEnum uploadOrDownload) {
        this.uploadOrDownload = uploadOrDownload;
    }

    /**
     * @return the method
     */
    public FileTransferMethodEnum getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(FileTransferMethodEnum method) {
        this.method = method;
    }

    /**
     * @return the url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * @return the filepath
     */
    public FilePath getFilepath() {
        return filepath;
    }

    /**
     * @param filepath the filepath to set
     */
    public void setFilepath(FilePath filepath) {
        this.filepath = filepath;
    }
}
