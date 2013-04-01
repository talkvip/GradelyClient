/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.gradely.client.FilePath;
import org.gradely.client.logging.Logging;

/**
 * Incapsulates a specialized output stream with function for writing a RDiff file. 
 * @author Matt
 */
public class RDiffWriter {

    //================= Fields =================================
    
    private DataOutputStream output;
    
    //================= Constructors ===========================

    public RDiffWriter(FilePath target) throws FileNotFoundException, IOException {
        
        if(!target.fileExists())
        {
            target.getFileObject().createNewFile();
        }
        
        output = new DataOutputStream(new FileOutputStream(target.getFileObject())); 
        
        
        
        startFile();

    }

    //================= Methods ================================
    
    private void startFile() throws IOException
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
        
        output.write(magicJohnson);
        output.flush();
        
    }
    
    /**
     * Writes a insert command to the diff writer file.
     * @param startLocation The start location of the insert data.
     * @param data The data to be inserted.
     */
    public void writeOperation(DiffOperation d) throws IOException
    {
        
        output.writeBoolean(d.getIsUniqueToParent());
        output.writeLong(d.getStartLocation());
        output.writeInt(d.getLength());
        output.write(d.getData());
    }
    
//    /**
//     * Writes a reversable delete command to the diff file.
//     * @param startLocation The start of the data to delete.
//     * @param data The data to be deleated.
//     */
//    public void writeDelete(Long startLocation, byte[] data) throws IOException
//    {
//        output.writeBoolean(false);
//        output.writeLong(startLocation);
//        output.writeInt(data.length);
//        output.write(data);
//    }
    
    public void dispose()
    {
        try
        {
            output.flush();
            output.close();
        }
        catch(IOException e)
        {
            Logging.warning("Was not able to close the output stream.");
        }
    }
    
    //------------------ Getters and Setters -------------------
}
