package org.hwdb.client;

/**
 * Used to collect exceptions dealing with the Encryptor static class.
 * @author Matt
 */
public class EncryptionException extends java.lang.Exception{

    //================= Fields =================================
    
    //================= Constructors ===========================

    public EncryptionException(String message) {
        super(message);
    }
    
    public EncryptionException() {
        super();
    }

    //================= Methods ================================
    
    //------------------ Getters and Setters -------------------
}
