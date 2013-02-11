package org.gradely.client.database;

import com.sun.org.apache.bcel.internal.Constants;
import java.io.File;
import org.gradely.client.config.Configuration;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.ArrayList;
import org.gradely.client.FilePath;
import org.gradely.client.logging.Logging;

/**
 * The connection pool gets and returns database connections, allowing for reuse. This class is a singleton.
 * This class also takes care of starting up the database if neccessary.
 * @author Matt
 */
public class ConnectionPool {

    //================= Fields =================================
    
    private int currentConnections; // connections in list + connections out
    private int maxConnections;
    private FilePath databaseLocation;
    private String databaseName;
    private ArrayList<Connection> connectionLst = new ArrayList<Connection>();
    private boolean isStarted = false; //shows if the database is up and running;
    private String user;
    private String password;
    
    private static ConnectionPool instance;
    
    //================= Constructors ===========================
    
    /**
     * Singleton class. USe getInstance() instead
     */
    public ConnectionPool() {
        //How many database connection should we obtain?
        Configuration config = Configuration.getInstance();
        maxConnections = config.getMaxDatabaseConnections();
        user = config.getDatabaseUser();
        password = config.getDatabasePassword();
        databaseName = org.gradely.client.config.Constants.appName;
        databaseLocation = new FilePath(config.getDatabaseLocation());
    }

    //================= Methods ================================
    
    /**
     * 
     * @return Returns the singleton connection pool. 
     */
    public static ConnectionPool getInstance(){
        
        if (ConnectionPool.instance == null)
        {
            ConnectionPool.instance = new ConnectionPool();
        }
        
        return ConnectionPool.instance;
        
    }
    
    /**
     * Gets (or creates) a database connection
     * @return Returns a SQL connection to the appropriate database.
     */
    public Connection getConnection() throws OutOfConnectionsException, ConnectionException {
        
        if(currentConnections >= maxConnections)
        {
            //Well, Looks like we don't need to instaniate any connections.
            
            if (connectionLst.isEmpty())
            {
                //We don't have any connections in a pool.
                throw new OutOfConnectionsException();
            }
            else
            {
                //give a connection out of our connection list.
                //we need to check to make shure it is a good connection, so that future procedures don't trip up.
                Connection c = connectionLst.remove(connectionLst.size()-1);
                if(isConnectionGood(c) == false)
                {
                    return getConnection();
                }
                
                return c;
            }
        }
        else
        {
            if (connectionLst.size() > 0)
            {
                //give a connection out of our connection list.
                //we need to check to make shure it is a good connection, so that future procedures don't trip up.
                Connection c = connectionLst.remove(connectionLst.size()-1);
                if(isConnectionGood(c) == false)
                {
                    return getConnection();
                }
                
                return c;
                
            }
            else
            {
                //well, looks like we need to instanceate a new connection
                try
                {
                    Connection conn = createConnection();
                    return conn;
                }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex)
                {
                    throw new ConnectionException("We cannot create a new connection. "+ex.getMessage());
                }

            }
        }
    }
    
    /**
     * Determines if the connection has expired or otherwise gone moldy.
     * @param c the connection to validate.
     * @return true if the connection is good, connected, and valid false otherwise.
     */
    private boolean isConnectionGood(Connection c)
    {
        try
        {
            if (c.isClosed() == true)
            {
                return false;
            }

            if(c.isValid(7) == false) // 7 is the timeout in sec
            {
                return false;
            }
            
        
        }
        catch(SQLException e)
        {
            Logging.warning("Somthing went wrong when validating a connection.  ", e);
            return false;
        }

        return true;
    }
    
    /**
     * Waits until a connection becomes available. Will return a connection unless a connection can not be made.
     * @return A connection to the database.
     */
    public Connection waitForConnection() throws ConnectionException
    {
        //get a connection
        Connection c = null;
        try
        {
            c = ConnectionPool.getInstance().getConnection();
            return c;
        }
        catch(OutOfConnectionsException e)
        {
            //wait for a new connection to open up
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {
                Logging.warning("There was an InterruptedException on a sleep call.", ex);
            }
            finally
            {
                return waitForConnection();
            }
        }
        catch(ConnectionException e)
        {
            throw e;
        }
        
        
    }
    
    
    /**
     * Returns a connection to the connection pool
     * @param c The connection to return.
     */
    public void returnConnection(Connection c){
        
        connectionLst.add(c);
        
    }
        
    /**
     * Creates/instanciate a new database connection.
     * @throws ClassNotFoundException If it can't find the class that means the libary was bundled incorrectly
     */
    private Connection createConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
        //Is the database even running?
        if (isStarted == false)
        {
            startUp();
        }

        File databasePath = new File(databaseLocation.getAbsolutePath(),databaseName);
        
        Connection conn = DriverManager.getConnection("jdbc:derby:"+databasePath.getAbsolutePath(), user, password); 
        
        return conn;

    }
    
    /**
     * This creates a brand new database on the computer. It does this through the connection string.
     * @return Returns an open connection for use.
     */
    public Connection createDatabase()throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
        //Is the database even running?
        if (isStarted == false)
        {
            startUp();
        }
        
        File databasePath = new File(databaseLocation.getAbsolutePath(),databaseName);

        Connection conn = DriverManager.getConnection("jdbc:derby:"+databasePath.getAbsolutePath()+";create=true", user, password);
        
        return conn;
    }
    
    /**
     * Starts Apache Derby Running
     * @throws ClassNotFoundException If it can't find the class that means the libary was bundled incorrectly
     */
    private void startUp() throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        Object newInstance = Class.forName(driver).newInstance();
        
        isStarted = true;
    }
    
    /**
     * Shuts Apache Derby Down
     */
    private void shutDown(){
        
        try
        {
            DriverManager.getConnection("jdbc:derby:MyDbTest;shutdown=true");
        }
        catch(SQLException ex)
        {
            org.gradely.client.logging.Logging.error("Database did not shutdown.", ex);
        }
        
    }
    
    //------------------ Getters and Setters -------------------
}
