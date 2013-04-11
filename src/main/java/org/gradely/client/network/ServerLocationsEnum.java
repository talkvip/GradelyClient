/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.network;

/**
 * Lists all possible locations the the client will contact the server with.
 * @author Matthew P. Prichard <matthew@matthewprichard.net>
 */
public enum ServerLocationsEnum {

        CLIENTFILECHANGE,
        LISTEN,
        LOGIN,
        PASSWORD,
        EDITUSER,
        EDITCLASS,
        EDITGROUPS,
        EDITSCHOOL,
        EDITSTORAGE,
        EDITASSIGNMENT,
        ASSIGNMENTSUBMIT,
        ABSOLUTE
        
}
