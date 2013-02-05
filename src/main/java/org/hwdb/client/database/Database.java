package org.hwdb.client.database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.hwdb.client.FilePath;

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
     * Creates all the tables in our database schema. Only need to run once, on install.
     */
    public static void createTables(FilePath tableDefCsv) throws ConnectionException, SQLException, FileNotFoundException {

        Connection c = ConnectionPool.getInstance().waitForConnection();
        StringBuilder sb = new StringBuilder();
        
        //site_user
        sb.append("CREATE TABLE site_user (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("user_name VARCHAR(100), ");
        sb.append("user_password VARCHAR(200), ");
        sb.append("user_type SMALLINT, ");
        sb.append("name VARCHAR(200), ");
        sb.append("PRIMARY KEY (id)");
        sb.append(");");
        sb.append("\r\n");
        
        //user_email
        sb.append("CREATE TABLE user_email (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("user_id INT, ");
        sb.append("email VARCHAR(320), ");
        sb.append("is_default SMALLINT, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id)");
        sb.append(");");
        sb.append("\r\n");

        //school
        sb.append("CREATE TABLE school (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("name VARCHAR(200), ");
        sb.append("user_id INT, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id)");
        sb.append(");");
        sb.append("\r\n");
        
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
        sb.append(");");
        sb.append("\r\n");
        
        //user_classes
        sb.append("CREATE TABLE user_classes (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("user_id INT, ");
        sb.append("class_id INT, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id), ");
        sb.append("FOREIGN KEY (class_id) REFERENCES class(id)");
        sb.append(");");
        sb.append("\r\n");
        
        //assignment 
        sb.append("CREATE TABLE assignment (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("name VARCHAR(200), ");
        sb.append("class_id INT, ");
        sb.append("due_date TIMESTAMP, ");
        sb.append("description VARCHAR, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (class_id) REFERENCES class(id)");
        sb.append(");");
        sb.append("\r\n");
        
        //file_resource
        sb.append("CREATE TABLE file_resource (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("hashsum CHAR(64), ");
        sb.append("url VARCHAR(512), ");
        sb.append("size BIGINT, ");
        sb.append("PRIMARY KEY (id)");
        sb.append(");");
        sb.append("\r\n");
        
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
        sb.append(");");
        sb.append("\r\n");
        
        //user_file
        sb.append("CREATE TABLE user_file ( ");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("user_id INT, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id)");
        sb.append(");");
        sb.append("\r\n");
        
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
        sb.append(");");
        sb.append("\r\n");
        
        //user_schools
        sb.append("CREATE TABLE user_schools (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("user_id INT, ");
        sb.append("school_id INT, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (school_id) REFERENCES school(id), ");
        sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id)");
        sb.append(");");
        sb.append("\r\n");
        
        //school file
        sb.append("CREATE TABLE school_file (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("school_id INT, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (school_id) REFERENCES school(id)");
        sb.append(");");
        sb.append("\r\n");
        
        //class_file
        sb.append("CREATE TABLE class_file (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("class_id INT, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (class_id) REFERENCES class(id)");
        sb.append("):");
        sb.append("\r\n");
        
        //group
        sb.append("CREATE TABLE group (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("assignment_id INT, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (assignment_id) REFERENCES assignment(id)");
        sb.append(");");
        sb.append("\r\n");
        
        //group_members
        sb.append("CREATE TABLE group_members (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("user_id INT, ");
        sb.append("group_id INT, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (group_id) REFERENCES group(id) ,");
        sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id) ");
        sb.append(");");
        sb.append("\r\n");
        
        //shared_file
        sb.append("CREATE TABLE shared_file (");
        sb.append("id INT NOT NULL GENERATED ALWAYS AS IDENTITY, ");
        sb.append("user_id INT, ");
        sb.append("user_file_id INT, ");
        sb.append("PRIMARY KEY (id), ");
        sb.append("FOREIGN KEY (user_file_id) REFERENCES user_file(id), ");
        sb.append("FOREIGN KEY (user_id) REFERENCES site_user(id)");
        sb.append(");");
        
        System.out.print(sb.toString());
        
        PreparedStatement s = c.prepareStatement(sb.toString());
        
        try
        {
            s.execute();
        }
        catch(SQLException e)
        {
            throw e;
        }

        s.execute();
        
    }
    
    //------------------ Getters and Setters -------------------
}
