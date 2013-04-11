package org.gradely.client.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.gradely.client.logging.Logging;
import org.jooq.impl.Factory;

/**
 *
 * @author Matt
 */
public class Database {

    //================= Fields =================================
    
    //================= Constructors ===========================

    public Database() {

    }

    //================= Methods ================================
    

    
    
    
    /**
     * Gets the database ready to be written and read from. Also creates the database. 
     * 
     * <ul>
     * <li>Creates a database Schema</li>
     * <li>Set the database Schema active</li>
     * <li>Creates all tables</li>
     * </ul>
     */
    public static void initializeDatabase() throws ConnectionException, SQLException
    {
        //Create the Database
        try
        {
            ConnectionPool.getInstance().createDatabase();      
        }
        catch(ClassNotFoundException |IllegalAccessException|InstantiationException|SQLException e)
        {
            Logging.warning("Cannot create the database.", e);
        }
        
        Connection c = ConnectionPool.getInstance().waitForConnection();
        StringBuilder sb = new StringBuilder();
        
        //Create the Schema
        PreparedStatement a = c.prepareStatement("CREATE SCHEMA "+org.gradely.client.config.Constants.appName);
               
        try
        {
            a.execute();
        }
        catch(SQLException e)
        {
            Logging.error("Executing CREATE SCHEMA SQL statement threw an error.", e);
            throw e;
        }
        
        //Set the Schema
        PreparedStatement b = c.prepareStatement("SET SCHEMA "+org.gradely.client.config.Constants.appName);
               
        try
        {
            b.execute();
        }
        catch(SQLException e)
        {
            Logging.error("Executing SET SCHEMA SQL statement threw an error.", e);
            throw e;
        }
        
        createTables();
        
    }
    
    
    /**
     * Creates all the tables in our database schema. Only need to run once, on install.
     */
    public static void createTables() throws ConnectionException, SQLException {

        Connection c = ConnectionPool.getInstance().waitForConnection();
        StringBuilder sb = new StringBuilder();
        
        try
        {

            //site_user
            sb.append("CREATE TABLE site_user (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("user_name VARCHAR(100), ");
            sb.append("user_password VARCHAR(200), ");
            sb.append("user_type SMALLINT, ");
            sb.append("name VARCHAR(200), ");
            sb.append("PRIMARY KEY (id)");
            sb.append(")");

            PreparedStatement s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE site_user");
            s.execute();
            sb = new StringBuilder();
            
            //user_email
            sb.append("CREATE TABLE user_email (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("user_id INT, ");
            sb.append("email VARCHAR(320), ");
            sb.append("is_default SMALLINT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE user_email");
            s.execute();
            sb = new StringBuilder();

            //school
            sb.append("CREATE TABLE school (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("name VARCHAR(200), ");
            sb.append("user_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE school");
            s.execute();
            sb = new StringBuilder();

            //class
            sb.append("CREATE TABLE class (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("user_id INT, ");
            sb.append("start_date TIMESTAMP, ");
            sb.append("end_date TIMESTAMP, ");
            sb.append("school_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id), ");
            sb.append("FOREIGN KEY (school_id) REFERENCES school(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE class");
            s.execute();
            sb = new StringBuilder();

            //user_classes
            sb.append("CREATE TABLE user_classes (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("user_id INT, ");
            sb.append("class_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id), ");
            sb.append("FOREIGN KEY (class_id) REFERENCES class(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE user_classes");
            s.execute();
            sb = new StringBuilder();

            //assignment 
            sb.append("CREATE TABLE assignment (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("name VARCHAR(200), ");
            sb.append("class_id INT, ");
            sb.append("due_date TIMESTAMP, ");
            sb.append("description VARCHAR(32000), ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (class_id) REFERENCES class(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE assignment");
            s.execute();
            sb = new StringBuilder();

            //file_resource
            sb.append("CREATE TABLE file_resource (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("hashsum CHAR(64), ");
            sb.append("url VARCHAR(512), ");
            sb.append("size BIGINT, ");
            sb.append("PRIMARY KEY (id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE file_resource");
            s.execute();
            sb = new StringBuilder();

            //user_file
            sb.append("CREATE TABLE user_file ( ");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("user_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE user_file");
            s.execute();
            sb = new StringBuilder();
            
            //user_file_version
            sb.append("CREATE TABLE user_file_version (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("user_file_id INT, ");
            sb.append("is_current SMALLINT, ");
            sb.append("filename VARCHAR(256), ");
            sb.append("filepath VARCHAR(512), ");
            sb.append("file_resource_id INT, ");
            sb.append("version INT,");
            sb.append("is_deleted SMALLINT, ");
            sb.append("is_directory SMALLINT, ");
            sb.append("server_modified_time TIMESTAMP, ");
            sb.append("client_modified_time TIMESTAMP, ");
            sb.append("note VARCHAR(32672), ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (user_file_id) REFERENCES user_file(id), ");
            sb.append("FOREIGN KEY (file_resource_id) REFERENCES file_resource(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE user_file_version");
            s.execute();
            sb = new StringBuilder();

            //returned assignment
            sb.append("CREATE TABLE returned_assignment (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("user_file_id INT ,");
            sb.append("assignment_id INT, ");
            sb.append("returned_note VARCHAR(32672), ");
            sb.append("file_response_user_file_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (assignment_id) REFERENCES assignment(id), ");
            sb.append("FOREIGN KEY (user_file_id) REFERENCES user_file(id), ");
            sb.append("FOREIGN KEY (file_response_user_file_id) REFERENCES user_file(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE returned_assignment");
            s.execute();
            sb = new StringBuilder();

            //user_schools
            sb.append("CREATE TABLE user_schools (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("user_id INT, ");
            sb.append("school_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (school_id) REFERENCES school(id), ");
            sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE user_schools");
            s.execute();
            sb = new StringBuilder();

            //school file
            sb.append("CREATE TABLE school_file (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("school_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (school_id) REFERENCES school(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE school_file");
            s.execute();
            sb = new StringBuilder();

            //class_file
            sb.append("CREATE TABLE class_file (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("class_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (class_id) REFERENCES class(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE class_file");
            s.execute();
            sb = new StringBuilder();

            //group
            sb.append("CREATE TABLE user_group (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("assignment_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (assignment_id) REFERENCES assignment(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE group");
            s.execute();
            sb = new StringBuilder();

            //group_members
            sb.append("CREATE TABLE group_members (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("user_id INT, ");
            sb.append("group_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (group_id) REFERENCES user_group(id) ,");
            sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id) ");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE group_members");
            s.execute();
            sb = new StringBuilder();

            //shared_file
            sb.append("CREATE TABLE shared_file (");
            sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
            sb.append("user_id INT, ");
            sb.append("user_file_id INT, ");
            sb.append("PRIMARY KEY (id), ");
            sb.append("FOREIGN KEY (user_file_id) REFERENCES user_file(id), ");
            sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id)");
            sb.append(")");
            
            s = c.prepareStatement(sb.toString());
            System.out.println("CREATE TABLE shared_file");
            s.execute();

        }
        catch(SQLException e)
        {
            Logging.warning("Executing CREATE TABLES SQL statement threw an error.", e);
            throw e;
        }
        finally
        {
            ConnectionPool.getInstance().returnConnection(c);
        }
        
        
        
    }
    
//    /**
//     * Sets all the database functions the program will need to function.
//     */
//    public static void createFunctions() throws ConnectionException, SQLException{
//        
//        Connection c = ConnectionPool.getInstance().waitForConnection();
//        StringBuilder sb = new StringBuilder();
//        
//        try
//        {
//            //Get renamed files FileChange.isRenamed()
//            sb.append("");
//            sb.append("");
//            sb.append("");
//            sb.append("");
//            sb.append("");
//            sb.append("");
//            sb.append("");
//            sb.append("");
//
//            PreparedStatement s = c.prepareStatement(sb.toString());
//            s.execute();
//            sb = new StringBuilder();
//        
//        }
//        catch (SQLException e)
//        {
//            Logging.warning("Unable to create database functions.", e);
//        }
//        finally
//        {
//            ConnectionPool.getInstance().returnConnection(c);
//        }
//        
//        
//    }
    
    //------------------ Getters and Setters -------------------
}
