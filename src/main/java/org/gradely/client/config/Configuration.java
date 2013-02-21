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
import org.apache.commons.lang3.StringUtils;
import org.gradely.client.logging.Logging;

/**
 * This configuration singleton class gets, contains, and writes, all of the possible application configuration values.
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
    /* Moved to Constants
    private final String appName = "gradely";
    private final String formalAppName = "Gradely";
    */
    
    //----------------- config file -------------------------------
    private final String configurationFileName = Constants.appName+".props";// = appName + ".props";
    //has read from the harddrive yet?
    private boolean hasRead = false;
    
    //----------------- Database -------------------------------
    private int maxDatabaseConnections; //Must be non-zero in order to connect to the database. // = 8;
    private String databasePassword; // = "";
    private String databaseUser; // = appName;
    private String databaseLocation;
    //private String databaseDefinitionLocation;
    
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

       File configFile = Defaults.defaultConfigurationFile();
       //Cool! now that we have found the file, lets parse the file.
       
       Properties p = new Properties();
       p.load(new FileInputStream(configFile));
       
       if(p.isEmpty())
       {
           throw new IOException("Either the configuration file was empty, or the file is not a valid configuration file.");
       }
       
       propertiesToConfiguration(p);
       
       hasRead = true;

    }
    
    /**
     * Tries to read the properties file, but instead of putting the result into the object, it just gets the properties.
     * Used for the getters.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Properties getProperties() throws FileNotFoundException, IOException
    {
       File configFile = Defaults.defaultConfigurationFile();
        
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

       hasRead = true;

    }
    
    
     /** 
     * Writes and saves the current configuration file 
     */
    @Override
    public void save() throws  IOException {
        
        Properties p = propertiesFromConfiguration();

        File configFileLocation = Defaults.defaultConfigurationFile();
        if (configFileLocation.exists() == false)
        {
           configFileLocation.mkdirs();
           configFileLocation.createNewFile();
        }
        
        OutputStream outstr = new FileOutputStream(Defaults.defaultConfigurationFile(), false); //false is for don't append
        
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
        try
        {
            //application
            setFirstRun(booleanFromString(p.getProperty("isfirstrun", "false")));

            //os
            setOs(OperatingSystemEnum.valueOf(p.getProperty("os", Defaults.defaultOperatingSystem().toString())));

            //app name
            //Configuration.setAppName(p.getProperty("appName"));
            //setFormalAppName(p.getProperty("formalAppName"));

            //config file
            //setConfigurationFileName(p.getProperty("configurationFileName"));

            //Database
            setDatabaseUser(p.getProperty("databaseUser", Defaults.defaultDatabaseUsername()));
            setDatabasePassword(p.getProperty("databasePassword", Defaults.defaultDatabasePassword()));
            setMaxDatabaseConnections(Integer.parseInt(p.getProperty("maxDatabaseConnections", Integer.toString(Defaults.defaultMaxDatabaseConnection()))));
            setDatabaseLocation(p.getProperty("databaseLocation", Defaults.defaultDatabaseLocation()));
            //setDatabaseDefinitionLocation(p.getProperty("databaseDefinitionLocation"));


            //directory locations
            setInstallDirectory(p.getProperty("installDirectory"));
            setRootDirecory(p.getProperty("rootDirecory", Defaults.defaultRootDirectory()));
            setUserAppsDirectory(p.getProperty("userAppsDirectory", Defaults.defaultUserAppsDirectory().getAbsolutePath()));
            setBoxFolderDirectory(p.getProperty("boxFolderDirectory", Defaults.defaultUserAppsDirectory().getAbsolutePath()));
            setUserProfileDirectory(p.getProperty("userProfileDirectory", System.getProperty("user.home")));
            setLogFilePath(p.getProperty("logFilePath", Defaults.defaultLogFilePath()));

            //network
            setServerName(p.getProperty("serverName"));
            //setUploadChunkSize(Integer.parseInt(p.getProperty("uploadChunkSize")));

        }
        catch (Exception e)
        {
            Logging.info("Was not able to read in a property.", e);
        }
        
    }
    
    /**
     * Turns Configuration configuration file into a properties file for writing.
     * 
     */
    private Properties propertiesFromConfiguration()
    {
        Properties p = new Properties();
        
        try
        {
            //application
            p = setProperty("isfirstrun", booleanToString(isFirstRun()),p);

            //os
            p = setProperty("os", getOs().name(),p);

            //app name MOVED TO CONSTANTS
            //p.setProperty("appName", getAppName());
            //p.setProperty("formalAppName", getFormalAppName());

            //config file
            //p.setProperty("configurationFileName", getConfigurationFileName());

            //database
            p = setProperty("databaseUser", getDatabaseUser(),p);
            p = setProperty("databasePassword", getDatabasePassword(),p);
            p = setProperty("maxDatabaseConnections",Integer.toString(getMaxDatabaseConnections()),p);
            p = setProperty("databaseLocation", getDatabaseLocation(),p);
            //p.getProperty("databaseDefinitionLocation", getDatabaseDefinitionLocation());

            //directory locations
            p = setProperty("installDirectory", getInstallDirectory(),p);
            p = setProperty("rootDirecory", getRootDirecory(),p);
            p = setProperty("userAppsDirectory", getUserAppsDirectory(),p);
            p = setProperty("boxFolderDirectory", getBoxFolderDirectory(),p);
            p = setProperty("userProfileDirectory", getUserProfileDirectory(),p);
            p = setProperty("logFilePath",getLogFilePath(),p);

            //network
            p = setProperty("serverName", getServerName(),p);
            //p.setProperty("uploadChunkSize", Long.toString(getUploadChunkSize()));
        }
        catch (Exception e)
        {
            Logging.info("Was not able to write a property.", e);
        }
        
        return p;
        
    }
    
    /**
     * Sets the Property for writing to file. Contains logic to deal with null values, so there are no NullPointerExceptions thrown.
     * @return the same Properties p, but with a new key-value.
     */
    public Properties setProperty(String key, String value, Properties p)
    {
        if (value == null)
        {
            //If this is not here the program will throw a NullPointerException
            return p;
        }
        else
        {
            p.setProperty(key, value);
        }
        
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
        if(s == null)
        {
            return false;
        }
        
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
    
  
    
    
    //---------------- Getters and Setters ---------------------------

    /**
     * @return the firstRun
     */
    public boolean isFirstRun() {
        return firstRun;
    }

    /**
     * @return the os
     */
    public OperatingSystemEnum getOs() {
        
        if (os == null)
        {
            os = Defaults.defaultOperatingSystem();
        }
        
        return os;
    }

    /**
     * @return the configurationFileName
     */
    public String getConfigurationFileName() {
        return configurationFileName;
    }

    /**
     * @return the maxDatabaseConnections
     */
    public int getMaxDatabaseConnections() {
        
        if(maxDatabaseConnections == 0)
        {
            maxDatabaseConnections = Defaults.defaultMaxDatabaseConnection();
        }
        
        return maxDatabaseConnections;
    }

    /**
     * @return the databasePassword
     */
    public String getDatabasePassword() {
        return databasePassword;
    }

    /**
     * @return the databaseUser
     */
    public String getDatabaseUser() {
        return databaseUser;
    }

     /**
     * @return the databaseLocation
     */
    public String getDatabaseLocation() {
        
        if(databaseLocation == null)
        {
            databaseLocation = Defaults.defaultDatabaseLocation();
        }
        
        return databaseLocation;
    }
    
    /**
     * @return the rootDirecory
     */
    public String getRootDirecory() {
        return rootDirecory;
    }

    /**
     * @return the userProfileDirectory
     */
    public String getUserProfileDirectory() {
        
        if (userProfileDirectory == null)
        {
            userProfileDirectory = System.getProperty("user.home");
        }
        
        return userProfileDirectory;
    }

    /**
     * @return the userAppsDirectory
     */
    public String getUserAppsDirectory() {
        
        if(userAppsDirectory == null)
        {
            userAppsDirectory = Defaults.defaultUserAppsDirectory().getAbsolutePath();
        }
        
        return userAppsDirectory;
    }

    /**
     * @return the installDirectory
     */
    public String getInstallDirectory() {
        return installDirectory;
    }

    /**
     * @return the boxFolderDirectory
     */
    public String getBoxFolderDirectory() {
        return boxFolderDirectory;
    }

    /**
     * @return the logFilePath
     */
    public String getLogFilePath() {
        
        if (StringUtils.isEmpty(logFilePath))
        {
            logFilePath = Defaults.defaultLogFilePath();
        }
        
        return logFilePath;
    }

    /**
     * @return the serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * @param firstRun the firstRun to set
     */
    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

    /**
     * @param os the os to set
     */
    public void setOs(OperatingSystemEnum os) {
        this.os = os;
    }

    /**
     * @param maxDatabaseConnections the maxDatabaseConnections to set
     */
    public void setMaxDatabaseConnections(int maxDatabaseConnections) {
        this.maxDatabaseConnections = maxDatabaseConnections;
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
     * @param rootDirecory the rootDirecory to set
     */
    public void setRootDirecory(String rootDirecory) {
        this.rootDirecory = rootDirecory;
    }

    /**
     * @param userProfileDirectory the userProfileDirectory to set
     */
    public void setUserProfileDirectory(String userProfileDirectory) {
        this.userProfileDirectory = userProfileDirectory;
    }

    /**
     * @param userAppsDirectory the userAppsDirectory to set
     */
    public void setUserAppsDirectory(String userAppsDirectory) {
        this.userAppsDirectory = userAppsDirectory;
    }

    /**
     * @param installDirectory the installDirectory to set
     */
    public void setInstallDirectory(String installDirectory) {
        this.installDirectory = installDirectory;
    }

    /**
     * @param boxFolderDirectory the boxFolderDirectory to set
     */
    public void setBoxFolderDirectory(String boxFolderDirectory) {
        this.boxFolderDirectory = boxFolderDirectory;
    }

    /**
     * @param logFilePath the logFilePath to set
     */
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    /**
     * @param serverName the serverName to set
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }



    /**
     * @param databaseLocation the databaseLocation to set
     */
    public void setDatabaseLocation(String databaseLocation) {
        this.databaseLocation = databaseLocation;
    }
  

}
