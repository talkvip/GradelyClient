/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

import java.io.FileWriter;
import java.io.IOException;
import org.gradely.client.FilePath;

/**
 * Takes an operations and applies it to the file in question.
 * @author Matt
 */
public class RDiffApplicator {

    //================= Fields =================================
    
    FileWriter writer;
    
    //================= Constructors ===========================

    public RDiffApplicator(FilePath target) throws IOException
    {
        writer = new FileWriter(target.getFileObject());
    }

    //================= Methods ================================
    /**
     * Puts in some data into a file. Give an insert using the current files coordinate system.
     * @param op The full operation
     */
    public void applyInsert(DiffOperation op)
    {
        
    }
    
    /**
     * Deletes some data from the file.
     * @param op 
     */
    public void applyDelete(DiffOperation op)
    {
        
    }
    
    //------------------ Getters and Setters -------------------
}
