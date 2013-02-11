
package org.gradely.client;

import org.gradely.client.config.Configuration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.gradely.client.database.ConnectionException;
import org.gradely.client.database.ConnectionPool;
import org.gradely.client.database.OutOfConnectionsException;
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
                //Run it
                //Make shure the config file is written
                
                //restart the program
                
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
        //Take an initial tally of the file system with the FileComparer class
        //TODO
        
        //--------------------------------------------------------
        //Launch the system tray
        //TODO
        
        
        //--------------------------------------------------------
        //Start watching the file system for changes.
        //TODO

        //--------------------------------------------------------
        //Start listening to the remote server for changes
        //TODO
        

        
    }
    


}
