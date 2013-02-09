package org.hwdb.client.network.filetransfer;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.hwdb.client.FilePath;

/**
 * This describes (and contains all info needed for) a file transfer operation. Describes commands from the server. Used before we ever hit the Syncany plugins.
 * @author Matt
 */
public class FileTransferDescription {

    //================= Fields =================================
    
    private UploadOrDownloadEnum uploadOrDownload;
    private FilePath localFile;
    private FileTransferMethodEnum method;
    
    /**
     * All information specific for each method of file transfer is stored here. Including, (but not limited to) password, username, url, bucket, uploaded file name.
     */
    private Map settingsStore;
    
    //private URL url;
    
    
    
    //================= Constructors ===========================

    public FileTransferDescription(UploadOrDownloadEnum uploadOrDownload,FileTransferMethodEnum method,URL url,FilePath filepath) {

        this.uploadOrDownload = uploadOrDownload;
        this.method = method;
        this.localFile = filepath;
        //this.url = url;
        
        //This obbject does traverse at least one thread, so synchronizedMap will keep everything running smoothly.
        settingsStore = Collections.synchronizedMap(new HashMap());
        
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
     * @return the localFile
     */
    public FilePath getLocalFile() {
        return localFile;
    }

    /**
     * @param localFile the localFile to set
     */
    public void setLocalFile(FilePath localFile) {
        this.localFile = localFile;
    }

    /**
     * @return the settingsStore
     */
    public Map getSettingsStore() {
        return settingsStore;
    }

    /**
     * @param settingsStore the settingsStore to set
     */
    public void setSettingsStore(Map settingsStore) {
        this.settingsStore = settingsStore;
    }
}
