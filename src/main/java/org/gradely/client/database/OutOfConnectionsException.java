package org.gradely.client.database;

/**
 * This execption is thrown whenever there are no more connections in the connection pool.
 * @author Matt
 */
public class OutOfConnectionsException extends java.lang.Exception {

    //================= Fields =================================
    
    //================= Constructors ===========================

    public OutOfConnectionsException(String message) {
        super(message);
    }
    
    public OutOfConnectionsException() {
        super();
    }

    //================= Methods ================================
    
    //------------------ Getters and Setters -------------------
}
