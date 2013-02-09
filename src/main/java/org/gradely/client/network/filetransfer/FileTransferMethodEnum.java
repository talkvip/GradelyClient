
package org.gradely.client.network.filetransfer;

/**
 * One enum for each method of upload and download. Upload and download methods must implement FileTransferInterface
 * @author Matt
 */
public enum FileTransferMethodEnum {
    
    HTTP,
    BOX,
    FTP,
    GS,
    IMAP,
    LOCAL,
    PICASA,
    POP3_STMP,
    RACKSPACE,
    REST,
    S3,
    SAMBA,
    SFTP,
    WEBDAV

    
    
}
