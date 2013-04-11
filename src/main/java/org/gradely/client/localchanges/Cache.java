/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.localchanges;

import org.gradely.client.FilePath;

/**
 * Simply utilties for moving files to and from the local cache.
 * @author Matthew P. Prichard <matthew@matthewprichard.net>
 */
public class Cache {

    //================= Fields =================================
    
    //================= Constructors ===========================

    private Cache() {

    }

    //================= Methods ================================
    
    /**
     * Moves the file to the local cache, and compresses the file. The hashsum (+.fil) becomes the new filename.
     * @param fileToMove the file in the box folder.
     */
    public static void moveFile(FilePath fileToMove, String hashsum)
    {
        
    }
    
    /**
     * Moves the file to the local cache, deletes an old file, and compresses the file.
     */
    public static void moveFile(FilePath fileToMove, FilePath fileToDelete)
    {
        
    }
    
    /**
     * Moves the file to the local cache, deletes an old file, and compresses the file, also encrypts the file using the specified key.
     * The file is first encrypted, then compressed.
     */
    public static void moveFileEncrypt(FilePath fileToMove, FilePath fileToDelete, byte[] key)
    {
        
    }
    
    /**
     * Moves the file to the local cache,compresses the file ,and ecrypes the file using the key.
     * The file is first encrypted, then compressed.
     */
    public static void moveFileEncrypt(FilePath fileToMove, byte[] key)
    {
        
    }
  
    
    //------------------ Getters and Setters -------------------
}
