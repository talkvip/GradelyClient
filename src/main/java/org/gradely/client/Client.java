
package org.gradely.client;

import java.io.File;
import org.gradely.client.config.Configuration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.gradely.client.database.ConnectionException;
import org.gradely.client.database.ConnectionPool;
import org.gradely.client.database.Database;
import org.gradely.client.database.OutOfConnectionsException;
import org.gradely.client.gui.tray.SystemTray;
import org.gradely.client.gui.wizards.InstallWizardProgression;
import org.gradely.client.gui.wizards.WizardDialog;
import org.gradely.client.localchanges.FileComparer;
import org.gradely.client.localchanges.FileWatcher;
import org.gradely.client.logging.Logging;

/**
 * The main class. Code goes in here that needs to run at bootup.
 * @author Matt
 */
public class Client {

    /**
     * This function runs at startup 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //--------------------------------------------------------
        //Parse Command line arguments
        //--------------------------------------------------------
        //First thing we need to do is figure out the configuration

        System.out.println("Welcome to "+org.gradely.client.config.Constants.formalAppName+".");
        
        try
        {
            Options options = new Options();
            options.addOption("c", "configuration", true, "The location of the configuration file. Usally stored in the user's appdata.");
            options.addOption("h", "help", false, "Print this message.");
            options.addOption("f", "firstrun", false, "This is a firstrun of the program. Use this to launch configuration.");

            CommandLineParser clip = new PosixParser();
            CommandLine cl = clip.parse(options, args); 

            // Help
            if (cl.hasOption("help")) {
                new HelpFormatter().printHelp(org.gradely.client.config.Constants.appName, options);
                System.exit(0);
            }
            
            if (cl.hasOption("firstrun")) {
                //Run the firstrun install utility
                firstrun();
                
                //restart the program
                main(new String[0]);
                
                System.exit(0);
                
            }

            Configuration.getInstance().load();


        }
        catch (FileNotFoundException e)
        {
            //Ok, crap. looks like we can't find the configuration file.
            //Not being able to read in the configuration is a fatal error.

            System.err.println("ERROR: Configuration file not found: "+e.getMessage());
            System.err.println("If this is a first-run or fresh install use the -f flag.");
            Logging.fatal("Configuration file not found", e);
            System.console().readLine("Press enter to close this program.");
            System.exit(1);
            
        }
        catch (IOException e)
        {
            //Seems we found the config file, but can't read it.
            System.err.println("ERROR: Cannot parse the configuration file: "+e.getMessage());
            Logging.fatal("Cannot parse the configuration file", e);
            System.console().readLine("Press enter to close this program.");
            System.exit(1);
            
        }
        catch (ParseException e)
        {
            //Cannot parse the command line
            System.err.println("ERROR: I can't understand you. Can you speak a little louder? use -h for help."+e.getMessage());
            System.console().readLine("Press enter to close this program.");
            System.exit(1);
        }

        //--------------------------------------------------------
        //Next, boot up and/or connect to the database
        
        try
        {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection c = pool.getConnection();
            pool.returnConnection(c);
        }
        catch (OutOfConnectionsException|ConnectionException e)
        {
            //Hmmm... Seems we can't connect to the database.
            //TODO: prompt the user.
            System.err.println("ERROR: Cannot connect up to the internal database for some weird reason: "+e.getMessage());
            Logging.fatal("Cannot connect up to the internal database for some weird reason", e);
            System.console().readLine("Press enter to close this program.");
            System.exit(1);
            
        }

        //--------------------------------------------------------
        //Launch the system tray
        //TODO
        SystemTray.init();
        
        //--------------------------------------------------------
        //Take an initial tally of the file system with the FileComparer class
        //TODO
        FileComparer.compare(new FilePath(Configuration.getInstance().getBoxFolderDirectory()));

        //--------------------------------------------------------
        //Start watching the file system for changes.
        //TODO
        File box = new File(Configuration.getInstance().getBoxFolderDirectory());
        if(!(box.exists()))
        {
            Logging.info("The box folder does not exist. Creating a new box folder.");
            box.mkdirs();
        }
        
        Thread watchThread = new Thread() {
            
            @Override
            public void run()
            {
                FileWatcher allSeeing = new FileWatcher(new FilePath("", FileLocationEnum.BOXFOLDER));
                try
                {
                    allSeeing.start();
                }
                catch(IOException|InterruptedException e)
                {
                    Logging.fatal("Cannot start the file watcher!", e);
                    System.console().readLine("Press enter to close this program.");
                    System.exit(1);
                }
            }
            
        };
        
        watchThread.setName("Gradely FileWatcher Thread");
        watchThread.start();
        
        

        //--------------------------------------------------------
        //Start listening to the remote server for changes
        //TODO
        
    }
    

    /**
     * This runs whenever a first run is needed. (Generally right after instalation)
     */
    public static void firstrun()
    {
        //Set up database
        //--------------------------------------------------------
        try
        {
            Database.initializeDatabase();
        }
        catch (ConnectionException | SQLException e)
        {
            Logging.fatal("Cannot Create the database!",e);
            System.console().readLine("Press enter to close this program.");
            System.exit(1);
        }
        
        //Run the install wizard
        //--------------------------------------------------------
        WizardDialog radagast = new WizardDialog(new InstallWizardProgression());
        radagast.init();
        
        radagast.dispose();

        //Set up config file
        try
        {
            Configuration c = Configuration.getInstance();
            c.setFirstRun(false);
            c.save();
        }
        catch(IOException e)
        {
            Logging.fatal("Cannot save to the configuration file. Your work may not be saved.", e);
            System.console().readLine("Press enter to close this program.");
            System.exit(1);
        }
            
        
    }
    
    /**
     * Performs a graceful shutdown of the program.
     */
    public static void shutdown()
    {
        try
        {
            Configuration.getInstance().save();
        }
        catch(IOException e)
        {
            Logging.warning("config file cannot be saved.");
        }
        System.exit(0);
    }
    

}
