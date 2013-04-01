/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.gradely.client.FilePath;

/**
 * This class is similar to a buffered input stream, except that it has functions for determining how much of the file is left to be read.
 * @author Matt
 */
public class LookAheadFileInputStream {

    //================= Fields =================================
    private FileInputStream fileStream;
    private byte[] buffer;
    private int bufferLocation;
    private int endOfBuffer;
    
    private long location = 0;
    
    private FilePath file; 
    
    /**
     * If the file has no more data to read, this is true
     */
    private boolean fileReadsOutOfData = false;
    
    /**
     * If all data has been read out of the buffer too and there is no more data in the file, this is true.
     */
    private boolean outOfData = false;
    
    
    //================= Constructors ===========================

    public LookAheadFileInputStream(FilePath file) throws FileNotFoundException, IOException 
    {
        this.file = file;
        fileStream = new FileInputStream(file.getFileObject());
        
        buffer = new byte[1024*5];
        
        readInFile(); 

    }

    //================= Methods ================================
    
    /**
     * Skips n   values of the file.
     * @param n 
     */
    public void skip(long n) throws IOException
    {
        fileStream.skip(n);
        location = location+n;
        
    }
    
    /**
     * Reads in some of the file into the buffer
     */
    private void readInFile() throws IOException
    {
        int bytesRead = fileStream.read(buffer); 
        
        if (bytesRead == -1)
        {
            fileReadsOutOfData = true;
        }

        endOfBuffer = bytesRead-1;
        bufferLocation = 0;

        
    }
    
    /**
     * Figures out if there is still more unread data.
     * @return True if there is more data, false if all the data is already read.
     */
    public boolean hasMoreData()
    {
        
        if(fileReadsOutOfData == true)
        {
            if(bufferLocation > endOfBuffer)
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Returns the next byte in the file.
     * @return A single byte.
     */
    public byte getNextByte() throws NoMoreDataException, IOException
    {

        if(bufferLocation > endOfBuffer)
        {
            if (fileReadsOutOfData == true)
            {
                throw new NoMoreDataException();
            }
            else
            {
                readInFile();
            }

        }
        
        byte rodger = buffer[bufferLocation]; 
        
        bufferLocation = bufferLocation+1;
        
        location = location+1;
        return rodger;
        
    }
    
    
    /** 
     * Closes the file and releases the file resource
     * 
     */
    public void close()
    {
        try
        {
            fileStream.close();
        }
        catch(IOException e)
        {
            org.gradely.client.logging.Logging.warning("Cannot close the FileInputStream", e);
        }
    }
    
    //------------------ Getters and Setters -------------------

    /**
     * @return the location
     */
    public long getLocation() {
        return location;
    }


}
