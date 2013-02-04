package org.hwdb.client;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This class takes care of logging interesting data to file.
 * @author Matt
 */
public class Logging {

    //================= Fields =================================
    
    //================= Constructors ===========================
    
    /**
     * 
     */
    private Logging() {

    }

    //================= Methods ================================
    
    /**
     * Logs a message to file.
     * @param message 
     */
    public static void log(String message)
    {
        String fileLocation  = Configuration.getLogFilePath();
        
        try
        {
            FileWriter fw = new FileWriter(fileLocation);
            
            fw.write(message);
            fw.close();
        }
        catch(IOException e)
        {
            //I don't know...
        }
        
        
    }
    
    /**
     * Logs a message and the specifics of the execption.
     */
    public static void log(String message, Exception ex)
    {
        String fileLocation  = Configuration.getLogFilePath();
        
        try
        {
            FileWriter fw = new FileWriter(fileLocation);
            
            fw.write(message + " Exception : "+ ex.getMessage());
            fw.close();
        }
        catch(IOException e)
        {
            //I don't know...
        }
    }
    
    /**
     * Tells more about the log, including severity of the failure ,and where it was thrown.
     * @param message
     * @param methodName
     * @param severity 
     */
    //public static void log(string message,string methodName,String severity)
    //{
    //    
    //}
    
    //------------------ Getters and Setters -------------------
}
