/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.network;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Like a file path, but instead a urlPath. Makes it easy to form a complete url.
 * @author Matthew P. Prichard <matthew@matthewprichard.net>
 */
public class UrlPath {

    //================= Fields =================================
    
    private String urlString;
    private URL formedUrl;
    private String serverName = org.gradely.client.config.Configuration.getInstance().getServerName();
    
    //================= Constructors ===========================

    public UrlPath(String realtivePath) throws MalformedURLException {
        
        this.urlString = realtivePath;
        
        this.formedUrl = new URL("http",serverName,-1,realtivePath);

    }
    
    public UrlPath(ServerLocationsEnum loc) throws MalformedURLException {
        
        this.urlString = enumToString(loc);
        
        this.formedUrl = new URL("http",serverName,-1,this.urlString);

    }
    
    /**
     * gives a URL with the value of the absolute paht.
     * @param absolutePath The absolute, complete url.
     * @param loc should be ABSOLUTE, otherwise it will act like UrlPath(ServerLocationsEnum loc).
     * @throws MalformedURLException 
     */
    public UrlPath(String absolutePath, ServerLocationsEnum loc) throws MalformedURLException {
        
        this.urlString = absolutePath;
        
        if(loc != ServerLocationsEnum.ABSOLUTE)
        {
            this.urlString = enumToString(loc);
            this.formedUrl = new URL("http",serverName,-1,this.urlString);
            
            return;
        }
        
        this.formedUrl = new URL(absolutePath);

    }

    //================= Methods ================================
    
    public URL getURL()
    {
        return formedUrl;
    }
    
    
    /**
     * Turns a ServerLocationsEnum into its appropriate string.
     * @return the part of the url that is not the host.
     */
    private static String enumToString(ServerLocationsEnum loc)
    {
        switch(loc)
        {
            case ASSIGNMENTSUBMIT: return "/client/assignmentsubmit";
            case CLIENTFILECHANGE: return "/client/file";
            case EDITASSIGNMENT: return "/client/editassignment";
            case EDITCLASS: return "/client/editclass";
            case EDITGROUPS: return "/client/editgroups";
            case EDITSCHOOL: return "/cliet/editschool";
            case EDITSTORAGE: return "/client/editstorage";
            case EDITUSER: return "/client/edituser";
            case LISTEN: return "/client/listen";
            case LOGIN: return "/client/login";
            case PASSWORD: return "/client/password";
            case ABSOLUTE: return "";
        }
        
        return "";
    }
    
    //------------------ Getters and Setters -------------------
}
