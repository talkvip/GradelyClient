
package org.hwdb.client.network.filetransfer;

/**
 * One enum for each method of upload and download. Upload and download methods must implement FileTransferInterface
 * @author Matt
 */
public enum FileTransferMethodEnum {
    
    http,
    box,
    ftp,
    gs,
    imap,
    local,
    picasa,
    pop3_stmp,
    rackspace,
    rest,
    s3,
    samba,
    sftp,
    webdav
    
    
}
