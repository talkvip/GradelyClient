/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.config;

import java.io.File;
import java.util.regex.Pattern;
import org.gradely.client.FileLocationEnum;
import org.gradely.client.FilePath;
import org.apache.commons.lang3.SystemUtils;

/**
 * Many configurations need defaults that are computed, or operating system specific. This class contains the defaults.
 * @author Matt
 */
public class Defaults {

    //================= Methods ================================
    
      /**
     * Finds the users application settings directory. (i.e. .../username/appdata/gradely)
     * @return A file representing the path
     */
    public static File defaultUserAppsDirectory()
    {
       OperatingSystemEnum operatingSystem = defaultOperatingSystem();
       String finishingDirectory = "";
       
       if(operatingSystem == OperatingSystemEnum.WINDOWS)
       {
           finishingDirectory = "/appData/Roaming/"+Constants.appName;
       }
       else if(operatingSystem == OperatingSystemEnum.OSX)
       {
           finishingDirectory = "."+Constants.appName;
       }
       else if(operatingSystem == OperatingSystemEnum.LINUX)
       {
           finishingDirectory = "Library/Application Support/"+Constants.appName;
       }
       else if(operatingSystem == OperatingSystemEnum.OTHER)
       {
           finishingDirectory = "."+Constants.appName;
       }

       String startingDirectory = System.getProperty("user.home");
       
       File configLocation = new File(startingDirectory,finishingDirectory);
       
       return configLocation;
    }
    
    /**
     * The operating system dependent root directory.
     * @return "C:/" on Windows, "/" on other
     */
    public static String defaultRootDirectory()
    {

        OperatingSystemEnum os = defaultOperatingSystem();
        
        if(os == OperatingSystemEnum.WINDOWS)
        {
            return "C:/";
// This was supposed to find the root programatically, but it won't retirn the drive letter.            
//            File f = new File(System.getProperty("user.home"));
//            
//            int i = 0;
//            while (true)
//            {
//               String s = f.getParent();
//               if (s == null)
//               {
//                   return  f.getAbsolutePath();
//               }
//               else
//               {
//                   f = new File(f.getParent());
//               }
//               
//               if(1 >= 100)
//               {
//                   return "C:/";
//               }
//               
//               i = i+1;
//            }
        }
        else 
        {
            return "/";
        }
        
    }
    
    
    /**
     * Figures out, based on the operating system, where the configuation folder is.
     * @return A file object representing the configuration directory.
     */
    public static File defaultConfigurationDirectory()
    {
       OperatingSystemEnum operatingSystem = defaultOperatingSystem();
       String finishingDirectory = "";
       
       if(operatingSystem == OperatingSystemEnum.WINDOWS)
       {
           finishingDirectory = "/appData/Roaming/"+Constants.appName+"/config";
       }
       else if(operatingSystem == OperatingSystemEnum.OSX)
       {
           finishingDirectory = "."+Constants.appName+"/config";
       }
       else if(operatingSystem == OperatingSystemEnum.LINUX)
       {
           finishingDirectory = "Library/Application Support/"+Constants.appName+"/config";
       }
       else if(operatingSystem == OperatingSystemEnum.OTHER)
       {
           finishingDirectory = "."+Constants.appName+"/config";
       }

       String startingDirectory = System.getProperty("user.home");
       
       File configLocation = new File(startingDirectory,finishingDirectory);
       
       return configLocation;
    }
    
    /**
     * This function determines, based on the operating system, the proper location of the configuration file.
     * @return Returns a file object containing the proper location for the configuration file.
     */
    public static File defaultConfigurationFile() //throws FileNotFoundException
    {
       //We are going to assume that the configuration file is in:
       // C:/Users/username/appData/Roaming/appName
       // /home/username/.appName
       // /Macintosh HD/Users/<username>/Library/Application Support/appName
       
       File directoryLocation = defaultConfigurationDirectory();
       
       File configLocation = new File(directoryLocation,"/"+Configuration.getInstance().getConfigurationFileName());
       
//       if(!configLocation.exists())
//       {
//           //well crap! We don't seem to be able to file the file.
//           throw new FileNotFoundException("Unable to find the configuration file. Filepath searched: " + configLocation.getAbsolutePath());
//       }
       
       return configLocation;
       
    }
    
    /**
     * The default master log file path. (i.e AppData/Gradely/log.txt)
     */
    public static String defaultLogFilePath()
    {
        return new File(defaultUserAppsDirectory(),"log.txt").getAbsolutePath();
    }
    
    
    /**
     * Figures out what operating system the JVM is running on.
     * @return WINDOWS, OSX, LINUX, OTHER
     */
    public static OperatingSystemEnum defaultOperatingSystem()
    {
       //use regex to search the string for os
       if(SystemUtils.IS_OS_WINDOWS) //Pattern.matches("windows",operatingSystem))
       {
           //windows os
           return OperatingSystemEnum.WINDOWS;
       }
       else if (SystemUtils.IS_OS_UNIX)//Pattern.matches("nux|nix|aix",operatingSystem))
       {
           //unix/linux
           return OperatingSystemEnum.LINUX; 
       }
       else if (SystemUtils.IS_OS_MAC_OSX)//Pattern.matches("mac|osx", operatingSystem))
       {
           //mac
           return OperatingSystemEnum.OSX;
       }
       else
       {
           return OperatingSystemEnum.OTHER;
       }
    }
    
    /**
     * If max database connections is zero, no one will be able to connect to the database.
     * @return 8 
     */
    public static int defaultMaxDatabaseConnection()
    {
        return 8;
    }
    
    /**
     * The default database location in app data
     * @return 
     */
    public static String defaultDatabaseLocation()
    {
        FilePath fp = new FilePath("database", FileLocationEnum.USERAPPS);
        return fp.getAbsolutePath();
    }
    
    /**
     * Default of the database username
     * @return gradelyuser
     */
    public static String defaultDatabaseUsername()
    {
        return Constants.appName+"user";
    }
    
    /**
     * Default database password
     * @return a specific random string: 8dj4jd7y4nsdu95
     */
    public static String defaultDatabasePassword()
    {
        return "8dj4jd7y4nsdu95";
    }

}
