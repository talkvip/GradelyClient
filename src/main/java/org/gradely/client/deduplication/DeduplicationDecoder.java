/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.deduplication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.gradely.client.FilePath;
import org.gradely.client.logging.Logging;

/**
 * Reads and parses deduplication files.
 * @author Matt
 */
public class DeduplicationDecoder {

    //================= Fields =================================
    
    public static final String headingSeperator = (char)0xb4 + (char)0xbc + (char)0x98 + (char)0x34 + (char)0xa4 + (char)0xb1 + (char)0xed + (char)0x3c + "";
    
    public static byte[] headingSeperatorArr()
    {
        byte[] arr = new byte[8]; 
        
        arr[0] = (byte)0xb4;
        arr[1] = (byte)0xbc;
        arr[2] = (byte)0x98;
        arr[3] = (byte)0x34;
        arr[4] = (byte)0xa4;
        arr[5] = (byte)0xb1;
        arr[6] = (byte)0xed;
        arr[7] = (byte)0x3c;
        
        return arr;
    }
    
    public static byte[] diffSeperator()
    {
        byte[] arr = new byte[9]; 
        //0x 00 df b0 a2 39 81 54 ee 6b \n\r
        arr[0] = (byte)0x00;
        arr[1] = (byte)0xdf;
        arr[2] = (byte)0xb0;
        arr[3] = (byte)0xa2;
        arr[4] = (byte)0x39;
        arr[5] = (byte)0x81;
        arr[6] = (byte)0x54;
        arr[7] = (byte)0xee;
        arr[8] = (byte)0x6b;
        
        return arr;
    }
    
    //================= Constructors ===========================

    public DeduplicationDecoder() {

    }

    //================= Methods ================================
    
    /**
     * Takes a file and parses it to figure out what blocks are contained.
     * @param dedupFile The deduplication file to parse.
     * @return A Blocks object containing everything we wish to know.
     */
    public static Blocks decode(FilePath dedupFile) throws FileNotFoundException, IOException
    {
        //Open the file
        BufferedInputStream buffStream = new BufferedInputStream(new FileInputStream(dedupFile.getFileObject()));
        
        ArrayList<BlockEntry> blockLst = new ArrayList<BlockEntry>();
        Blocks b = new Blocks();
       
        //Parse header data
        //======================================================================
        try
        {
            b = readHeaders(dedupFile);
        }
        catch(FileNotFoundException e)
        {
            Logging.error("Deduplication file not found.", e);
            throw e;   
        }
        catch(IOException e)
        {
            Logging.warning("Was not able to read in the dedup file. IOError. ",e);
        }
        
        
        //Look for end of header string
        //======================================================================
        
        byte[] byteArr = new byte[1024*5];
        int bytesRead = -1;
        
        int startOfHeaderSeperator;
        int startOfData = 0 ;
        while((bytesRead = buffStream.read(byteArr))!= -1)
        {
            if((startOfHeaderSeperator = containsSubarray(byteArr, headingSeperatorArr())) != -1)
            {
                //Sweet! we have a end of header!
                //The first entry in the file will start 10 bytes after the start
                startOfData = startOfHeaderSeperator+10;
                break;
            }
            else
            {
                byteArr = new byte[1024*5];
            }
        }
        
        //Start reading off entries
        //======================================================================
        //Each entry is 40 bytes long.
        boolean hasData = false;
        
        if (bytesRead != -1)
        {
            //Takes care of existing data in buffer
            hasData = true;
        }

        //This loop restarts whenever we need to read more of the file.  
        while(hasData == true)
        {
            ByteBuffer byteBuffer = ByteBuffer.wrap(byteArr);

            //check to see if the entry is cut off due to buffer size
            if(byteArr.length < startOfData+4+8+72)
            {
                //The entry was cut off! Load in more data!
                if(startOfData < byteArr.length)
                {
                    //This means that there is more unparsed data in the array.
                    byte[] newArr = new byte[1024*5];
                    bytesRead = buffStream.read(newArr);
                    
                    if(bytesRead != -1)
                    {
                        byteArr = addArrays(byteArr, startOfData, newArr);
                    }
                    else
                    {
                        Logging.error("The file ends in the middle of a entry!");
                    }

                }
                
                BlockEntry entry = readEntry(byteArr, startOfData);
                
                //seek to next entry and read off delta file in the process.
                int endOfDelta = containsSubarray(byteArr, startOfData, diffSeperator());
                if (endOfDelta == -1)
                {
                    Logging.warning("No delta exists for this deduplication entry.");
                }
                else
                {
                    //Data between startOfData and endOfDelta is the delta file
                    entry.setDeltaData(org.apache.commons.lang3.ArrayUtils.subarray(byteArr, startOfData, endOfDelta));
                    startOfData = endOfDelta + 8;
                }
                
                blockLst.add(entry);
                
            }

        }
        
       b.setBlockEntries(blockLst);
       
       return b;
        
        
    }
    
    /**
     * Attaches two arrays to each other so that array one is in front of array two. 
     * @param one The first array
     * @peram onePosition Everything after this index will be added to the array. Inclusive.
     * @param two the secoend array
     * @return The concatinated arrays.
     */
    private static byte[] addArrays(byte[] one, int onePosition,  byte[] two)
    {
        byte[] concat = new byte[one.length+two.length];
        
        System.arraycopy(one, onePosition, concat, 0, onePosition-one.length);
        
        System.arraycopy(two, 0, concat, one.length, two.length);
        
        return concat;
    }
    
    /**
     * Reads off a single record, the bits in the dedup file entry
     * @param byteBuffer A ByteBuffer containing the data to be read as a byte array.
     * @param startOfData The index if the first byte if data in the byte buffer.
     * @return A filled BlockEntry containing the data.
     */
    private static BlockEntry readEntry(byte[] data, int startOfData)
    {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        
        BlockEntry entry = new BlockEntry();
        entry.setLength(byteBuffer.getInt(startOfData));
        entry.setAdler32Hash(byteBuffer.getInt(startOfData+4));
        entry.setSha2Hash(org.apache.commons.lang3.ArrayUtils.subarray(data, startOfData+8, startOfData+72));
        
        return entry;
    }
    
    /**
     * Searches for a subarray in a larger array. 
     * @param arr The array to search in.
     * @param subArr The array to search for.
     * @return -1 if the sub array does not exist. Otherwise returns the index of the start of the substring.
     */
    private static int containsSubarray(byte[] arr, byte[] subArr)
    {
        int startingIndex = -1;
        int subCounter = 0;
        for(int i=0; i<arr.length; i++)
        {
            if(arr[i] == subArr[subCounter])
            {
                if (subCounter == 0)
                {
                    startingIndex = i;
                }
                
                if (subCounter < subArr.length)
                {
                    subCounter = subCounter+1;
                }
                
                if (subCounter == subArr.length)
                {
                    //If this is the case, then the substring exists
                    return startingIndex;
                }
            }
            else
            {
                startingIndex = -1;
                subCounter = 0;
            }
        }
        
        return startingIndex;
    }
    
    /**
     * Searches for a subarray in a larger array. Returns the first instance found.
     * @param arr The array to search in.
     * @param startIndex Where to start searching in the array.
     * @param subArr The array to search for.
     * @return -1 if the sub array does not exist. Otherwise returns the index of the start of the substring.
     */
    private static int containsSubarray(byte[] arr, int startIndex, byte[] subArr)
    {
        int startingIndex = -1;
        int subCounter = 0;
        for(int i=startIndex; i<arr.length; i++)
        {
            if(arr[i] == subArr[subCounter])
            {
                if (subCounter == 0)
                {
                    startingIndex = i;
                }
                
                if (subCounter < subArr.length)
                {
                    subCounter = subCounter+1;
                }
                
                if (subCounter == subArr.length)
                {
                    //If this is the case, then the substring exists
                    return startingIndex;
                }
            }
            else
            {
                startingIndex = -1;
                subCounter = 0;
            }
        }
        
        return startingIndex;
    }
    
    
    /**
     * Reads in the headers from a deduplication file.
     * @param dedupFile The file to parse.
     * @return A fill-out Blocks object containing the header data. 
     * @throws FileNotFoundException 
     * @throws IOException 
     */
    public static Blocks readHeaders(FilePath dedupFile) throws FileNotFoundException, IOException
    {
        //Open the file
        BufferedReader buffRdr = new BufferedReader(new InputStreamReader(new FileInputStream(dedupFile.getFileObject())));
        
        //The header data is all in a text file 
        Blocks b = new Blocks();
        
        //read in a line
        b.setTimeOfCreation(buffRdr.readLine());
        b.setFileName(buffRdr.readLine());
        b.setSHA2Hashsum(buffRdr.readLine());
        
        //determine if there is a previous file entry
        String nextLine = buffRdr.readLine();
        
        if(!nextLine.contains(headingSeperator))
        {
            b.setPreviousFileName(nextLine);
        }

        buffRdr.close();
        
        return b;
    }
    
    //------------------ Getters and Setters -------------------
}
