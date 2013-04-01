/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.gradely.client.FilePath;
import org.gradely.client.logging.Logging;

/**
 * Wraps an input stream with some special functions for reading in diffFiles. 
 * @author Matt
 */
public class RDiffReader {

    //================= Fields =================================
    private DataInputStream input;
    boolean hasMagicNumbers;
    
    
    //================= Constructors ===========================

    public RDiffReader(FilePath diffFile) throws FileNotFoundException, IOException, MalformedFileException
    {
        input = new DataInputStream(new BufferedInputStream(new FileInputStream(diffFile.getFileObject()))); 

        boolean isValidated = validate();
        hasMagicNumbers = isValidated;
        
        if(!diffFile.fileExists())
        {
            throw new FileNotFoundException("The diff file does not exist.");
        }
        
        if(isValidated == false)
        {
            throw new MalformedFileException("The file did not have the appropriate magic numbers at the top of the file. This file is either corrupt or not a diff file.");
        }
        
    }

    //================= Methods ================================
    
    /**
     * Validates the file by looking for the magic numbers at the start of the file.
     * @return True if it looks like a valid file.
     */
    private boolean validate() throws IOException
    {
        byte[] magicJohnson = new byte[8];
        magicJohnson[0] = (byte)0x27;
        magicJohnson[1] = (byte)0x89;
        magicJohnson[2] = (byte)0x2e;
        magicJohnson[3] = (byte)0xb4;
        magicJohnson[4] = (byte)0xe3;
        magicJohnson[5] = (byte)0x02;
        magicJohnson[6] = (byte)0x3e;
        magicJohnson[7] = (byte)0xb9;
        
        byte[] test = new byte[8];
        input.read(test);
        
        if(areArraysEqual(test,magicJohnson))
        {
            return true;
        }
        else
        {
            
            Logging.info("Magic numbers are not equal. Length:"+test.length+" toString: "+test.toString());
            return false;
            
        }
        
    }
    
    /**
     * Determines if there is more in the file or not.
     * @return true if there is more in the file
     */
    public boolean hasMoreEntries()
    {
        input.mark(64);
        
        try
        {
            input.readBoolean();
        }
        catch(IOException e)
        {
            return false;
        }
        finally
        {
            try
            {
                input.reset();
            }
            catch(IOException e)
            {
                Logging.warning("Was not able to reset the input stream. This may be fatal.", e);
            }
        }
        
        return true;
    }
    
    /**
     * Reads in the next diff operation at the input's cusor.
     * @return The complete diff operation.
     */
    public DiffOperation readEntry() throws IOException, NoMoreDataException
    {
        DiffOperation op = new DiffOperation();
        
        op.setIsUniqueToParent(input.readBoolean());
        op.setStartLocation(input.readLong());
        int length = input.readInt();
        op.setLength(length);
        
        byte[] b = new byte[length];
        int bytesRead = input.read(b);
        
        if(bytesRead < length)
        {
            throw new NoMoreDataException("The file ended unexpectadly while reading data.");
        }
        
        op.setData(b);
        
        return op;
        
    }
    
    /**
     * Check each value of the array
     * @param one
     * @param two
     * @return True if the arrays are equal
     */
    private static boolean areArraysEqual(byte[] one, byte[] two)
    {
        
        if(one.length != two.length)
        {
            return false;
        }
        
        for(int i=0; i<one.length; i++)
        {
            if(one[i] != two[i])
            {
                return false;
            }
        }
        
        return true;
    }
    
    
    //------------------ Getters and Setters -------------------
}
