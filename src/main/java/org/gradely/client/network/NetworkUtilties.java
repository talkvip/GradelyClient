/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.network;

/**
 * Just a collection of utililtie functions to help out with sending data around.
 * @author Matthew P. Prichard <matthew@matthewprichard.net>
 */
public class NetworkUtilties {

    //================= Fields =================================
    
    //================= Constructors ===========================

    private NetworkUtilties() {

    }

    //================= Methods ================================
    
    /**
    * Authenticates the client to the server
    */
    public void authenticate() {
        
        //TODO
        //send username to server.
        //server responds with a random number, as well as that is encrypted using the password.
        //Client decrypts the password using the known password.
        //Client sends the random number to the server to prove that the client has the password.
        
    }
    
    //------------------ Getters and Setters -------------------
}
