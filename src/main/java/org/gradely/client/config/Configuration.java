package org.gradely.client.config;


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
public class Configuration implements Configurable {
    
    //================= Fields =================================
    // FOR THE LOVE OF GOD, PLEASE ADD THE NEW FIELD TO propertiesToConfiguration AND propertiesFromConfiguration!!!!
    
    //----------------- application -------------------------------
    private boolean firstRun = true;
    
    //----------------- os -------------------------------
    private OperatingSystemEnum os;
    
    //----------------- app name -------------------------------
    //name of the application, no spaces, all lower case.
    private String appName = "hwdb";
    private String formalAppName = "H.W.D.B.";
    
    //----------------- config file -------------------------------
    private String configurationFileName;// = appName + ".props";
    //has read from the harddrive yet?
    private boolean hasRead = false;
    
    //----------------- Database -------------------------------
    private int maxDatabaseConnections;// = 8;
    private String databasePassword; // = "";
    private String databaseUser; // = appName;
    private String databaseDefinitionLocation;
    
    //----------------- File System ----------------------------
    // see FileLocationEnum for discriptions of these settings.
    private String rootDirecory; // = "C:/";
    private String userProfileDirectory; // = System.getProperty("user.home");
    private String userAppsDirectory; // = System.getProperty("user.home")+"AppData";
    private String installDirectory; //"C:/Program Files/"+appName;
    private String boxFolderDirectory;// = System.getProperty("user.home")+"Documents/"+appName;
    private String logFilePath;// = System.getProperty("user.home")+"AppData/log.txt";
    
    
    //----------------- Network ---------------------------------
    private String serverName;// = "http://www."+appName+".com";
    //private int uploadChunkSize;// = 10*1024; // int.max is equal to 2.174 Gigabytes or 2.0 Gigibytes
    
    //Constructor
    private static Configuration instance;
    
    
    
    
    //================= Constructors ===========================
    
    /**
     * This class is a singleton. use getInstance() to get an object.
     */
    private Configuration() {
        
    }
    
    public static Configuration getInstance()
    {
        if(instance == null)
        {
            instance = new Configuration();
        }
        
        return instance;
    }
    
    
    //================= Methods ================================

    /**
     * Attempts to read the configuration file 
     * @throws FileNotFoundException Throws File Not Found if the configuration file cannot find the configuration file.
     */
    @Override
    public void load() throws FileNotFoundException, IOException {

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
     * Tries to read the properties file, but instead of putting the result into the object, it just gets the properties.
     * Used for the getters.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Properties getProperties() throws FileNotFoundException, IOException
    {
        File configFile = findConfigurationFile();
        
       Properties p = new Properties();
       p.load(new FileInputStream(configFile));
       
       if(p.isEmpty())
       {
           throw new IOException("Either the configuration file was empty, or the file is not a valid configuration file.");
       }
       
       return p;
        
    }
    
     /**
     * Attempts to read the configuration file. Usees a passed-in argument to find the file instead of the default location.
     * @throws FileNotFoundException Throws File Not Found if the configuration file cannot find the configuration file.
     */
    public void load(File configFile) throws FileNotFoundException, IOException {

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
    @Override
    public void save() throws  IOException {
        
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
    private void propertiesToConfiguration(Properties p)
    {
        //application
        setFirstRun(booleanFromString(p.getProperty("isfirstrun")));
        
        //os
        setOs(OperatingSystemEnum.valueOf(p.getProperty("os")));
        
        //app name
        //Configuration.setAppName(p.getProperty("appName"));
        setFormalAppName(p.getProperty("formalAppName"));
        
        //config file
        setConfigurationFileName(p.getProperty("configurationFileName"));
        
        //Database
        setDatabaseUser(p.getProperty("databaseUser"));
        setDatabasePassword(p.getProperty("databasePassword"));
        setMaxDatabaseConnections(Integer.parseInt(p.getProperty("maxDatabaseConnections")));
        //setDatabaseDefinitionLocation(p.getProperty("databaseDefinitionLocation"));
        
        //directory locations
        setInstallDirectory(p.getProperty("installDirectory"));
        setRootDirecory(p.getProperty("rootDirecory"));
        setUserAppsDirectory(p.getProperty("userAppsDirectory"));
        setBoxFolderDirectory(p.getProperty("boxFolderDirectory"));
        setUserProfileDirectory(p.getProperty("userProfileDirectory"));
        setLogFilePath(p.getProperty("logFilePath"));
        
        //network
        setServerName(p.getProperty("serverName"));
        //setUploadChunkSize(Integer.parseInt(p.getProperty("uploadChunkSize")));
        
    }
    
    /**
     * Turns Configuration configuration file into a properties file for writing.
     * 
     */
    private Properties propertiesFromConfiguration()
    {
        Properties p = new Properties();
        
        //application
        p.setProperty("isfirstrun", booleanToString(isFirstRun()));
        
        //os
        p.setProperty("os", getOs().name());
        
        //app name
        p.setProperty("appName", getAppName());
        p.setProperty("formalAppName", getFormalAppName());
        
        //config file
        p.setProperty("configurationFileName", getConfigurationFileName());
        
        //database
        p.setProperty("databaseUser", getDatabaseUser());
        p.setProperty("databasePassword", getDatabasePassword());
        p.setProperty("maxDatabaseConnections",Integer.toString(getMaxDatabaseConnections()));
        //p.getProperty("databaseDefinitionLocation", getDatabaseDefinitionLocation());
        
        //directory locations
        p.setProperty("installDirectory", getInstallDirectory());
        p.setProperty("rootDirecory", getRootDirecory());
        p.setProperty("userAppsDirectory", getUserAppsDirectory());
        p.setProperty("boxFolderDirectory", getBoxFolderDirectory());
        p.setProperty("userProfileDirectory", getUserProfileDirectory());
        p.setProperty("logFilePath",getLogFilePath());
        
        //network
        p.setProperty("serverName", getServerName());
        //p.setProperty("uploadChunkSize", Long.toString(getUploadChunkSize()));
        
        return p;
        
    }
    
    /**
     * Turns a boolean into a string.
     * @param b a boolean 
     * @return "true" or "false"
     */
    public String booleanToString(boolean b)
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
    public boolean booleanFromString(String s)
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
     * Finds the users application settings directory. (i.e. .../username/appdata/gradely)
     * @return A file representing the path
     */
    public File findUserAppsDirectory()
    {
       OperatingSystemEnum operatingSystem = findOperatingSystem();
       String finishingDirectory = "";
       
       if(operatingSystem == OperatingSystemEnum.WINDOWS)
       {
           finishingDirectory = "/appData/Roaming/"+getAppName();
       }
       else if(operatingSystem == OperatingSystemEnum.OSX)
       {
           finishingDirectory = "."+getAppName()+"/config";
       }
       else if(operatingSystem == OperatingSystemEnum.LINUX)
       {
           finishingDirectory = "Library/Application Support/"+getAppName();
       }
       else if(operatingSystem == OperatingSystemEnum.OTHER)
       {
           finishingDirectory = "."+getAppName();
       }

       String startingDirectory = System.getProperty("user.home");
       
       File configLocation = new File(startingDirectory,finishingDirectory);
       
       return configLocation;
    }
    
    /**
     * Figures out, based on the operating system, where the configuation folder is.
     * @return A file object representing the configuration directory.
     */
    public File findConfigurationDirectory()
    {
       OperatingSystemEnum operatingSystem = findOperatingSystem();
       String finishingDirectory = "";
       
       if(operatingSystem == OperatingSystemEnum.WINDOWS)
       {
           finishingDirectory = "/appData/Roaming/"+getAppName()+"/config";
       }
       else if(operatingSystem == OperatingSystemEnum.OSX)
       {
           finishingDirectory = "."+getAppName()+"/config";
       }
       else if(operatingSystem == OperatingSystemEnum.LINUX)
       {
           finishingDirectory = "Library/Application Support/"+getAppName()+"/config";
       }
       else if(operatingSystem == OperatingSystemEnum.OTHER)
       {
           finishingDirectory = "."+getAppName()+"/config";
       }

       String startingDirectory = System.getProperty("user.home");
       
       File configLocation = new File(startingDirectory,finishingDirectory);
       
       return configLocation;
    }
    
    
    /**
     * This function determines, based on the operating system, the proper location of the configuration file.
     * @return Returns a file object containing the proper location for the configuration file.
     */
    private File findConfigurationFile() throws FileNotFoundException
    {
       //We are going to assume that the configuration file is in:
       // C:/Users/username/appData/Roaming/appName
       // /home/username/.appName
       // /Macintosh HD/Users/<username>/Library/Application Support/appName
       
       File directoryLocation = findConfigurationDirectory();
       
       File configLocation = new File(directoryLocation,"/"+getConfigurationFileName());
       
       if(!configLocation.exists())
       {
           //well crap! We don't seem to be able to file the file.
           throw new FileNotFoundException("Unable to find the configuration file. Filepath searched: " + configLocation.getAbsolutePath());
       }
       
       return configLocation;
       
    }
    
    /**
     * Figures out what operating system the JVM is running on.
     * @return WINDOWS, OSX, LINUX, OTHER
     */
    public static OperatingSystemEnum findOperatingSystem()
    {
       String operatingSystem = System.getProperty("os.name").toLowerCase();
       //use regex to search the string for os
       if( Pattern.matches("windows",operatingSystem))
       {
           //windows os
           return OperatingSystemEnum.WINDOWS;
       }
       else if (Pattern.matches("nux|nix|aix",operatingSystem))
       {
           //unix/linux
           return OperatingSystemEnum.LINUX; 
       }
       else if (Pattern.matches("mac|osx", operatingSystem))
       {
           //mac
           return OperatingSystemEnum.OSX;
       }
       else
       {
           return OperatingSystemEnum.OTHER;
       }
    }
    
    
    
    //---------------- Getters and Setters ---------------------------
     /**
     * @return the os
     */
    public OperatingSystemEnum getOs() {
        
        //Will be null if file read was unsucessful
        if (os == null)
        {
            //Set Default
            os = findOperatingSystem();
        }
        
        return os;
    }

    /**
     * @return the maxDatabaseConnections
     */
    public int getMaxDatabaseConnections() {
        
        if(maxDatabaseConnections == 0)
        {
            try
            {
                Properties p = getProperties();
                maxDatabaseConnections = Integer.parseInt(p.getProperty("maxDatabaseConnections"));
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }
                //Will be null if file read was unsucessful
        if (maxDatabaseConnections == 0)
        {
            //Set Default
            maxDatabaseConnections = 8;
        }
        
        return maxDatabaseConnections;
    }

    /**
     * @param maxDatabaseConnections the maxDatabaseConnections to set
     */
    public void setMaxDatabaseConnections(int maxDatabaseConnections) {
        this.maxDatabaseConnections = maxDatabaseConnections;
    }
    
        /**
     * @return the databasePassword
     */
    public String getDatabasePassword() {
        
        if(databasePassword == null)
        {
            try
            {
                Properties p = getProperties();
                databasePassword = p.getProperty("databasePassword");
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }
                //Will be null if file read was unsucessful
        if (databasePassword == null)
        {
            //Set Default
            databasePassword = "";
        }
        
        return databasePassword;
    }

    /**
     * @return the databaseUser
     */
    public String getDatabaseUser() {
        
        if(databaseUser == null)
        {
            try
            {
                Properties p = getProperties();
                databaseUser = p.getProperty("databaseUser");
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }
                //Will be null if file read was unsucessful
        if (databaseUser == null)
        {
            //Set Default
            databaseUser = getAppName();
        }
        
        return databaseUser;
    }

    /**
     * @param os the os to set
     */
    public void setOs(OperatingSystemEnum os) {
        this.os = os;
    }

    /**
     * @param databasePassword the databasePassword to set
     */
    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    /**
     * @param databaseUser the databaseUser to set
     */
    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    /**
     * @return the rootDirecory
     */
    public String getRootDirecory() {
        
        if(rootDirecory == null)
        {
            try
            {
                Properties p = getProperties();
                rootDirecory = p.getProperty("rootDirecory");
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }
        
        return rootDirecory;
    }

    /**
     * @param rootDirecory the rootDirecory to set
     */
    public void setRootDirecory(String rootDirecory) {
        this.rootDirecory = rootDirecory;
    }

    /**
     * @return the userProfileDirectory
     */
    public String getUserProfileDirectory() {

        if(userProfileDirectory == null)
        {
            try
            {
                Properties p = getProperties();
                userProfileDirectory = p.getProperty("userProfileDirectory");
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }
                //Will be null if file read was unsucessful
        if (userProfileDirectory == null)
        {
            //Set Default
            userProfileDirectory = System.getProperty("user.home");
        }
        
        return userProfileDirectory;
    }

    /**
     * @param userProfileDirectory the userProfileDirectory to set
     */
    public void setUserProfileDirectory(String userProfileDirectory) {
        this.userProfileDirectory = userProfileDirectory;
    }

    /**
     * @return the userAppsDirectory
     */
    public String getUserAppsDirectory() {
        
        if(userAppsDirectory == null)
        {
            try
            {
                Properties p = getProperties();
                userAppsDirectory = p.getProperty("userAppsDirectory");
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }
                //Will be null if file read was unsucessful
        if (userAppsDirectory == null)
        {
            //Set Default
            userAppsDirectory = findUserAppsDirectory().getAbsolutePath();
        }
        
        return userAppsDirectory;
    }

    /**
     * @param userAppsDirectory the userAppsDirectory to set
     */
    public void setUserAppsDirectory(String userAppsDirectory) {
        this.userAppsDirectory = userAppsDirectory;
    }

    /**
     * @return the installDirectory
     */
    public String getInstallDirectory() {
        
       if(installDirectory == null)
        {
            try
            {
                Properties p = getProperties();
                installDirectory = p.getProperty("installDirectory");
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }
                //Will be null if file read was unsucessful
        if (installDirectory == null)
        {
            //Set Default
            //TODO where do we install?
            //installDirectory = ;
        }

        return installDirectory;
    }

    /**
     * @param installDirectory the installDirectory to set
     */
    public void setInstallDirectory(String installDirectory) {
        this.installDirectory = installDirectory;
    }

    /**
     * @return the boxFolderDirectory
     */
    public String getBoxFolderDirectory() {
        
        if(boxFolderDirectory == null)
        {
            try
            {
                Properties p = getProperties();
                boxFolderDirectory = p.getProperty("boxFolderDirectory");
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }

        return boxFolderDirectory;
    }

    /**
     * @param boxFolderDirectory the boxFolderDirectory to set
     */
    public void setBoxFolderDirectory(String boxFolderDirectory) {
        this.boxFolderDirectory = boxFolderDirectory;
    }

    /**
     * @return the appName
     */
    public String getAppName() {
        return appName;
    }


    /**
     * @return the formalAppName
     */
    public String getFormalAppName() {
        return formalAppName;
    }

    /**
     * @param formalAppName the formalAppName to set
     */
    public void setFormalAppName(String formalAppName) {
        this.formalAppName = formalAppName;
    }

    /**
     * @return the configurationFileName
     */
    public String getConfigurationFileName() {
        
        if(configurationFileName == null)
        {
            try
            {
                Properties p = getProperties();
                configurationFileName = p.getProperty("configurationFileName");
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }
                //Will be null if file read was unsucessful
        if (configurationFileName == null)
        {
            //Set Default
            configurationFileName = "config.props";
        }

        return configurationFileName;
    }

    /**
     * @param configurationFileName the configurationFileName to set
     */
    public void setConfigurationFileName(String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }

    /**
     * @return the serverName
     */
    public String getServerName() {
        
        if(serverName == null)
        {
            try
            {
                Properties p = getProperties();
                serverName = p.getProperty("ServerName");
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }

        
        return serverName;
    }

    /**
     * @param serverName the serverName to set
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * @return the logFilePath
     */
    public String getLogFilePath() {
        
                if(logFilePath == null)
        {
            try
            {
                Properties p = getProperties();
                logFilePath = p.getProperty("logFilePath");
            }
            catch (Exception e)
            {
                //Can't get then properties file, no wonder the thing came up null. //TODO log
            }
        }
                //Will be null if file read was unsucessful
        if (logFilePath == null)
        {
            //Set Default
            logFilePath = new File(findUserAppsDirectory(),"/"+appName+".log").getAbsolutePath();
        }

        
        return logFilePath;
    }

    /**
     * @param logFilePath the logFilePath to set
     */
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    /**
     * @param aAppName the appName to set
     */
    public void setAppName(String aAppName) {
        appName = aAppName;
    }

    /**
     * @return the firstRun
     */
    public boolean isFirstRun() {
        return firstRun;
    }

    /**
     * @return the hasRead
     */
    public boolean isHasRead() {
        return hasRead;
    }

    /**
     * @param firstRun the firstRun to set
     */
    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

    /**
     * @param hasRead the hasRead to set
     */
    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }


}
