package org.hwdb.client.config;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * This configuration singleton class gets, contains, and writes, all of the possible application configuration values.
 * 
 * 
 * @author Matt
 */
public class Configuration {
    
    //================= Fields =================================
    // FOR THE LOVE OF GOD, PLEASE ADD THE NEW FIELD TO propertiesToConfiguration AND propertiesFromConfiguration!!!!
    
    //----------------- application -------------------------------
    private static boolean firstRun = true;
    
    //----------------- os -------------------------------
    private static String os = System.getProperty("os.name").toLowerCase();
    
    //----------------- app name -------------------------------
    //name of the application, no spaces, all lower case.
    private static String appName = "hwdb";
    private static String formalAppName = "H.W.D.B.";
    
    //----------------- config file -------------------------------
    private static String configurationFileName = appName + ".props";
    
    //----------------- Database -------------------------------
    private static int maxDatabaseConnections = 8;
    private static String databasePassword = "";
    private static String databaseUser = appName;
    private static String databaseDefinitionLocation;
    
    //----------------- File System ----------------------------
    // see FileLocationEnum for discriptions of these settings.
    private static String rootDirecory = "C:/";
    private static String userProfileDirectory = System.getProperty("user.home");
    private static String userAppsDirectory = System.getProperty("user.home")+"AppData";
    private static String installDirectory = "C:/Program Files/"+appName;
    private static String boxFolderDirectory = System.getProperty("user.home")+"Documents/"+appName;
    private static String logFilePath = System.getProperty("user.home")+"desktop/log.txt";
    
    
    //----------------- Network ---------------------------------
    private static String serverName = "http://www."+appName+".com";
    private static int uploadChunkSize = 10*1024; // int.max is equal to 2.174 Gigabytes or 2.0 Gigibytes
    
    //Constructor
    private static Configuration instance = new Configuration();
    
    //has read from the harddrive yet?
    private static boolean hasRead = false;
    
    //================= Constructors ===========================
    
    /**
     * This class is a singleton. use getInstance() to get an object.
     */
    private Configuration() {
        
    }
    
    //================= Methods ================================
    
    

    
    /**
     * Attempts to read the configuration file 
     * @throws FileNotFoundException Throws File Not Found if the configuration file cannot find the configuration file.
     */
    public static void readConfigurationFile() throws FileNotFoundException, IOException {

       
       File configFile = findConfigurationFile();
       //Cool! now that we have found the file, lets parse the file.
       
       Properties p = new Properties();
       p.load(new FileInputStream(configFile));
       
       if(p.isEmpty())
       {
           throw new IOException("Either the configuration file was empty, or the file is not a valid configuration file.");
       }
       
       propertiesToConfiguration(p);
       
        setHasRead(true);

    }
    
     /**
     * Attempts to read the configuration file. Usees a passed-in argument to find the file instead of the default location.
     * @throws FileNotFoundException Throws File Not Found if the configuration file cannot find the configuration file.
     */
    public static void readConfigurationFile(File configFile) throws FileNotFoundException, IOException {

       //Cool! now that we have found the file, lets parse the file.
       Properties p = new Properties();
       p.load(new FileInputStream(configFile));
       
       if(p.isEmpty())
       {
           throw new IOException("Either the configuration file was empty, or the file is not a valid configuration file.");
       }
       
       propertiesToConfiguration(p);

       setHasRead(true);

    }
    
    
     /** 
     * Writes and saves the current configuration file 
     */
    public static void writeConfigurationFile() throws FileNotFoundException, IOException {
        
        Properties p = propertiesFromConfiguration();

        OutputStream outstr = new FileOutputStream(findConfigurationFile(), false); //false id for don't append
        
        //Let us write a time stamp out for fun.
        Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        
        p.store(outstr, "Time of creation: "+sdf.format(cal.getTime()));
        
    }
    
    /**
     * Converts a properties key value to a populated configuration file. 
     * @param p a populated, open java.util.Properties
     */
    private static void propertiesToConfiguration(Properties p)
    {
        //application
        Configuration.setIsFirstRun(booleanFromString(p.getProperty("isfirstrun")));
        
        //os
        Configuration.setOs(p.getProperty("os"));
        
        //app name
        //Configuration.setAppName(p.getProperty("appName"));
        Configuration.setFormalAppName(p.getProperty("formalAppName"));
        
        //config file
        Configuration.setConfigurationFileName(p.getProperty("configurationFileName"));
        
        //Database
        Configuration.setDatabaseUser(p.getProperty("databaseUser"));
        Configuration.setDatabasePassword(p.getProperty("databasePassword"));
        Configuration.setMaxDatabaseConnections(Integer.parseInt(p.getProperty("maxDatabaseConnections")));
        Configuration.setDatabaseDefinitionLocation(p.getProperty("databaseDefinitionLocation"));
        
        //directory locations
        Configuration.setInstallDirectory(p.getProperty("installDirectory"));
        Configuration.setRootDirecory(p.getProperty("rootDirecory"));
        Configuration.setUserAppsDirectory(p.getProperty("userAppsDirectory"));
        Configuration.setBoxFolderDirectory(p.getProperty("boxFolderDirectory"));
        Configuration.setUserProfileDirectory(p.getProperty("userProfileDirectory"));
        Configuration.setLogFilePath(p.getProperty("logFilePath"));
        
        //network
        Configuration.setServerName(p.getProperty("serverName"));
        Configuration.setUploadChunkSize(Integer.parseInt(p.getProperty("uploadChunkSize")));
        
    }
    
    /**
     * Turns Configuration configuration file into a properties file for writing.
     * 
     */
    private static Properties propertiesFromConfiguration()
    {
        Properties p = new Properties();
        
        //application
        p.setProperty("isfirstrun", booleanToString(isFirstRun()));
        
        //os
        p.setProperty("os", getOs());
        
        //app name
        p.setProperty("appName", getAppName());
        p.setProperty("formalAppName", getFormalAppName());
        
        //config file
        p.setProperty("configurationFileName", getConfigurationFileName());
        
        //database
        p.setProperty("databaseUser", getDatabaseUser());
        p.setProperty("databasePassword", getDatabasePassword());
        p.setProperty("maxDatabaseConnections",Integer.toString(getMaxDatabaseConnections()));
        p.getProperty("databaseDefinitionLocation", getDatabaseDefinitionLocation());
        
        //directory locations
        p.setProperty("installDirectory", getInstallDirectory());
        p.setProperty("rootDirecory", getRootDirecory());
        p.setProperty("userAppsDirectory", getUserAppsDirectory());
        p.setProperty("boxFolderDirectory", getBoxFolderDirectory());
        p.setProperty("userProfileDirectory", getUserProfileDirectory());
        p.setProperty("logFilePath",getLogFilePath());
        
        //network
        p.setProperty("serverName", getServerName());
        p.setProperty("uploadChunkSize", Long.toString(getUploadChunkSize()));
        
        return p;
        
    }
    
    /**
     * Turns a boolean into a string.
     * @param b a boolean 
     * @return "true" or "false"
     */
    public static String booleanToString(boolean b)
    {
        if (b == true)
        {
            return "true";
        }
        
        return "false";
    }
    
    /**
     * Turns "true" or "false" into the appropriate boolean.
     * @param s A string encoding a boolean
     * @return returns false if string is of length zero.
     */
    public static boolean booleanFromString(String s)
    {
        s = s.toLowerCase();
        
        if (s.contains("true"))
        {
            return true;
        }
        
        if (s.contains("false"))
        {
            return false;
        }
        
        if (s.contains("t"))
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * This function determines, based on the operating system, the proper location of the configuration file.
     * @return Returns a file object containing the proper location for the configuration file.
     */
    private static File findConfigurationFile() throws FileNotFoundException
    {
       //We are going to assume that the configuration file is in:
       // C:/Users/username/appData/Roaming/appName
       // /home/username/.appName
       // /Macintosh HD/Users/<username>/Library/Application Support/appName
       
       String operatingSystem = System.getProperty("os.name").toLowerCase();
       
       String finishingDirectory = "";
       
       //use regex to search the string for os
       if( Pattern.matches("windows",operatingSystem))
       {
           //windows os
           finishingDirectory = "/appData/Roaming/"+getAppName();
       }
       else if (Pattern.matches("nux|nix|aix",operatingSystem))
       {
           //unix/linux
           finishingDirectory = "."+getAppName();
           
       }
       else if (Pattern.matches("mac|osx", operatingSystem))
       {
           //mac
           finishingDirectory = "Library/Application Support/"+getAppName();
       }
       else
       {
           //Search the file syetem for the config file
           //TODO: search the file system
           throw new FileNotFoundException("Unable to determine what operating system you are using.");
       }
       
      
       String startingDirectory = System.getProperty("user.home");
       
       File configLocation = new File(startingDirectory,finishingDirectory+"/"+Configuration.getConfigurationFileName());
       
       if(!configLocation.exists())
       {
           //well crap! We don't seem to be able to file the file.
           throw new FileNotFoundException("Unable to find the configuration file. Filepath searched: " + configLocation.getAbsolutePath());
       }
       
       return configLocation;
       
    }
    
    
    
    //---------------- Getters and Setters ---------------------------
     /**
     * @return the os
     */
    public static String getOs() {
        return os;
    }

    /**
     * @return the maxDatabaseConnections
     */
    public static int getMaxDatabaseConnections() {
        return maxDatabaseConnections;
    }

    /**
     * @param maxDatabaseConnections the maxDatabaseConnections to set
     */
    public static void setMaxDatabaseConnections(int maxDatabaseConnections) {
        Configuration.maxDatabaseConnections = maxDatabaseConnections;
    }
    
        /**
     * @return the databasePassword
     */
    public static String getDatabasePassword() {
        return databasePassword;
    }

    /**
     * @return the databaseUser
     */
    public static String getDatabaseUser() {
        return databaseUser;
    }

    /**
     * @param os the os to set
     */
    public static void setOs(String os) {
        Configuration.os = os;
    }

    /**
     * @param databasePassword the databasePassword to set
     */
    public static void setDatabasePassword(String databasePassword) {
        Configuration.databasePassword = databasePassword;
    }

    /**
     * @param databaseUser the databaseUser to set
     */
    public static void setDatabaseUser(String databaseUser) {
        Configuration.databaseUser = databaseUser;
    }

    /**
     * @return the rootDirecory
     */
    public static String getRootDirecory() {
        return rootDirecory;
    }

    /**
     * @param rootDirecory the rootDirecory to set
     */
    public static void setRootDirecory(String rootDirecory) {
        Configuration.rootDirecory = rootDirecory;
    }

    /**
     * @return the userProfileDirectory
     */
    public static String getUserProfileDirectory() {
        return userProfileDirectory;
    }

    /**
     * @param userProfileDirectory the userProfileDirectory to set
     */
    public static void setUserProfileDirectory(String userProfileDirectory) {
        Configuration.userProfileDirectory = userProfileDirectory;
    }

    /**
     * @return the userAppsDirectory
     */
    public static String getUserAppsDirectory() {
        return userAppsDirectory;
    }

    /**
     * @param userAppsDirectory the userAppsDirectory to set
     */
    public static void setUserAppsDirectory(String userAppsDirectory) {
        Configuration.userAppsDirectory = userAppsDirectory;
    }

    /**
     * @return the installDirectory
     */
    public static String getInstallDirectory() {
        return installDirectory;
    }

    /**
     * @param installDirectory the installDirectory to set
     */
    public static void setInstallDirectory(String installDirectory) {
        Configuration.installDirectory = installDirectory;
    }

    /**
     * @return the boxFolderDirectory
     */
    public static String getBoxFolderDirectory() {
        return boxFolderDirectory;
    }

    /**
     * @param boxFolderDirectory the boxFolderDirectory to set
     */
    public static void setBoxFolderDirectory(String boxFolderDirectory) {
        Configuration.boxFolderDirectory = boxFolderDirectory;
    }

    /**
     * @return the appName
     */
    public static String getAppName() {
        return appName;
    }


    /**
     * @return the formalAppName
     */
    public static String getFormalAppName() {
        return formalAppName;
    }

    /**
     * @param formalAppName the formalAppName to set
     */
    public static void setFormalAppName(String formalAppName) {
        Configuration.formalAppName = formalAppName;
    }

    /**
     * @return the configurationFileName
     */
    public static String getConfigurationFileName() {
        return configurationFileName;
    }

    /**
     * @param configurationFileName the configurationFileName to set
     */
    public static void setConfigurationFileName(String configurationFileName) {
        Configuration.configurationFileName = configurationFileName;
    }

    /**
     * @return the serverName
     */
    public static String getServerName() {
        return serverName;
    }

    /**
     * @param serverName the serverName to set
     */
    public static void setServerName(String serverName) {
        Configuration.serverName = serverName;
    }

    /**
     * @return the uploadChunkSize
     */
    public static int getUploadChunkSize() {
        return uploadChunkSize;
    }

    /**
     * @param uploadChunkSize the uploadChunkSize to set
     */
    public static void setUploadChunkSize(int uploadChunkSize) {
        Configuration.uploadChunkSize = uploadChunkSize;
    }

    /**
     * @return the logFilePath
     */
    public static String getLogFilePath() {
        return logFilePath;
    }

    /**
     * @param logFilePath the logFilePath to set
     */
    public static void setLogFilePath(String logFilePath) {
        Configuration.logFilePath = logFilePath;
    }

    /**
     * @param aAppName the appName to set
     */
    public static void setAppName(String aAppName) {
        appName = aAppName;
    }

    /**
     * @return the databaseDefinitionLocation
     */
    public static String getDatabaseDefinitionLocation() {
        return databaseDefinitionLocation;
    }

    /**
     * @param aDatabaseDefinitionLocation the databaseDefinitionLocation to set
     */
    public static void setDatabaseDefinitionLocation(String aDatabaseDefinitionLocation) {
        databaseDefinitionLocation = aDatabaseDefinitionLocation;
    }

    /**
     * @return the instance
     */
    public static Configuration getInstance() {
        return instance;
    }

    /**
     * @param aInstance the instance to set
     */
    public static void setInstance(Configuration aInstance) {
        instance = aInstance;
    }

    /**
     * @return the hasRead
     */
    public static boolean isHasRead() {
        return hasRead;
    }

    /**
     * @param aHasRead the hasRead to set
     */
    public static void setHasRead(boolean aHasRead) {
        hasRead = aHasRead;
    }

    /**
     * @return the isFirstRun
     */
    public static boolean isFirstRun() {
        return firstRun;
    }

    /**
     * @param aIsFirstRun the isFirstRun to set
     */
    public static void setIsFirstRun(boolean firstRun) {
        firstRun = firstRun;
    }


}
