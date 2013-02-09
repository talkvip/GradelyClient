package org.hwdb.client.logging;

import java.io.File;
import org.hwdb.client.config.Configuration;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
     * Writes a new log entry to file
     * @param output The string to append to the log file.
     */
    private static void write(String output)
    {
        String fileLocation  = Configuration.getInstance().getLogFilePath();
        try
        {
            FileWriter fw = new FileWriter(fileLocation);
            fw.write(output);
            fw.close();
        }
        catch(IOException e)
        {
            //Let's trouble shoot this.
            //Does the file exist?
            File logFile = new File(fileLocation); 
            if(!logFile.exists())
            {
                try
                {
                    logFile.createNewFile();
                    write(output);
                }
                catch(IOException ea)
                {
                    //TODO notify user that the log file cannot be created.
                }
            }
            
            else if(!logFile.canWrite())
            {
                //There's our problem
                logFile.setWritable(true);
                
                if (logFile.canWrite())
                {
                    write(output);
                }
                else
                {
                    //TODO notify user
                }
            }
            
            else if(!logFile.isFile())
            {
                //Why are we writing to a folder!
                //TODO notifiy user
            }
            
            else if(!logFile.canRead())
            {
                logFile.setReadable(true);
                
                if(logFile.canWrite() && logFile.canRead())
                {
                    write(output);
                }
                else
                {
                    //TODO notifiy user
                }
            }
        }
    }
    
    /**
     * Formats a log entry. Also figures out where the log was called from, what class it was called from, and the type of error. 
     * @param level 
     * @param message
     * @param e 
     */
    private static void format(LoggingLevel level, String message, Throwable e)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(level.toString() + "/r/n");
        
        //Get the current Time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        dateFormat.format(cal.getTime());

        sb.append("     ");
        sb.append(dateFormat.format(cal.getTime()));
        sb.append(" \r\n");
        
        //Get the Stack Trace
        StackTraceElement ste = getStackTraceElement(3);
        
        sb.append("     ");
        sb.append("Logged From: ").append(ste.getClassName()).append(".").append(ste.getMethodName()).append("() ");
        sb.append(" \r\n");
        
        
        //Error
        if (e != null)
        {
            sb.append("     ");
            sb.append("Exception: ");
            sb.append(e.toString());
            sb.append(" \r\n");
        }
        
        //Message
        sb.append("     ");
        sb.append("Message: ");
        sb.append(message);
        sb.append(" \r\n");
        sb.append("============================================================ \r\n");
        
        write(sb.toString());
        
        
    }
    
    private static StackTraceElement getStackTraceElement(final int deep) {

            StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
            if (stackTraceElements.length > deep) {
                    return stackTraceElements[deep];
            } else {
                    return new StackTraceElement("<unknown>", "<unknown>", "<unknown>", 0);
            }
    }
    
    
    /**
     * Logs a message to file.
     * @param message 
     */
    public static void info(String message)
    {
        format(LoggingLevel.INFO, message, null);
    }
    
    /**
     * Logs a message and the specifics of the execption.
     */
    public static void info(String message, Exception ex)
    {
        format(LoggingLevel.INFO, message, ex);
    }
    
     /**
     * Logs a message to file.
     * @param message 
     */
    public static void warning(String message)
    {
        format(LoggingLevel.WARNING, message, null);
    }
    
    /**
     * Logs a message and the specifics of the execption.
     */
    public static void warning(String message, Exception ex)
    {
        format(LoggingLevel.WARNING, message, ex);
    }
    
         /**
     * Logs a message to file.
     * @param message 
     */
    public static void error(String message)
    {
        format(LoggingLevel.ERROR, message, null);
    }
    
    /**
     * Logs a message and the specifics of the execption.
     */
    public static void error(String message, Exception ex)
    {
        format(LoggingLevel.ERROR, message, ex);
    }
    
     /**
     * Logs a message to file.
     * @param message 
     */
    public static void fatal(String message)
    {
        format(LoggingLevel.FATAL, message, null);
    }
    
    /**
     * Logs a message and the specifics of the execption.
     */
    public static void fatal(String message, Exception ex)
    {
        format(LoggingLevel.FATAL, message, ex);
    }
    
     /**
     * Logs a message to file.
     * @param message 
     */
    public static void debug(String message)
    {
        format(LoggingLevel.DEBUG, message, null);
    }
    
    /**
     * Logs a message and the specifics of the execption.
     */
    public static void debug(String message, Exception ex)
    {
        format(LoggingLevel.DEBUG, message, ex);
    }
    
         /**
     * Logs a message to file.
     * @param message 
     */
    public static void trace(String message)
    {
        format(LoggingLevel.TRACE, message, null);
    }
    
    /**
     * Logs a message and the specifics of the execption.
     */
    public static void trace(String message, Exception ex)
    {
        format(LoggingLevel.TRACE, message, ex);
    }
    

    
    /**
     * Tells more about the log, including severity of the failure ,and where it was thrown.
     * @param message
     * @param methodName
     * @param severity 
     */
    //public static void log(string message,string className,string methodName)
    //{

    //}
    
    //------------------ Getters and Setters -------------------
}
