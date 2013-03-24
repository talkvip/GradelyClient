/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.gradely.client.FilePath;
import org.gradely.client.logging.Logging;

/**
 *
 * @author Matt
 */
public class RDiffEncoder {

    //================= Fields =================================
    
    //================= Constructors ===========================

    private RDiffEncoder() {

    }

    //================= Methods ================================
    
    /**
     * Creates and writes a new delta file. 
     * @param parent The older file to compare.
     * @param child The younger file to compare.
     * @param target The delta file to be written.
     */
    public static void generateDelta(FilePath parent, FilePath child, FilePath target) throws FileNotFoundException, IOException
    {
        //Compare the files to find the unique data to each file
        ArrayList<DiffOperation> opLst = compareFiles(parent,child);
        
        //Write out the file
        writeDiffFile(opLst, target);

        
        
    }
    
    /**
     * Writes out the diff file using an already computed set of diffrences.
     * @param opLst The list of unique data between each file.
     * @param target The filepath to write to.
     */
    private static void writeDiffFile(ArrayList<DiffOperation> opLst, FilePath target) throws FileNotFoundException, IOException
    {
        
            RDiffWriter w = new RDiffWriter(target);
        try
        {
            Collections.sort(opLst, new DiffOperationComparator());

            for(int i=0; i<opLst.size(); i++)
            {
                DiffOperation op = opLst.get(i);
                w.writeOperation(op);
            }

            
        }
        finally
        {
            w.dispose();
        }

    }
    
    
    
    /**
     * Compares the two files to find the operations required.
     * @param parent
     * @param child 
     */
    private static ArrayList<DiffOperation> compareFiles(FilePath parent, FilePath child) throws FileNotFoundException, IOException
    {
                //This happens in two parts:
        //First, merge the two data sets togeather, keeping track of what was required to insert into either set.
        //Change the inserts required to change the child into the merged set into deletes
        //Write out the file
        
        LookAheadFileInputStream parentStream = new LookAheadFileInputStream(parent);
        LookAheadFileInputStream childStream = new LookAheadFileInputStream(child);
        
        LastValueOnlyList combined = new LastValueOnlyList();
        
        //Loop though the files to figure out what we need
        ArrayList parentBuffer = new ArrayList();
        ArrayList childBuffer = new ArrayList();
        
        
        int parentBytesRead = 0;
        int childBytesRead = 0;
        
        long parentIndex = 0;
        long childIndex = 0;
        
        boolean stoppedFlag = true;
        
        boolean parentHasData = true;
        boolean childHasData = true;
        
        boolean outOfData = false;
        
        boolean parentWasRead = false;
        boolean childWasRead = false;
        
        ArrayList<DiffOperation> uniqueList = new ArrayList<DiffOperation>();
        
        while(outOfData == false)
        {

            if (parentHasData == true)
            {
                try
                {
                    byte parentByte = parentStream.getNextByte();
                    parentBuffer.add(parentByte);
                    parentWasRead = true;
                }
                catch(IOException e)
                {
                    Logging.warning("Can't read in the data ", e);
                }
                catch(NoMoreDataException e)
                {
                    parentHasData = false;
                }
            }
            
            if(childHasData == true)
            {
                try
                {
                    byte childByte = parentStream.getNextByte();
                    childBuffer.add(childByte);
                    childWasRead = true;
                }
                catch(IOException e)
                {
                    Logging.warning("Can't read in the data ", e);
                }
                catch(NoMoreDataException e)
                {
                    childHasData = false;
                }
            }
            
            
            //Search for matches between sets
            for(int i=0; i<parentBuffer.size()-1; i++)
            {
                for(int j=0; j<childBuffer.size()-1; i++ )
                {
                    if(parentBuffer.get(i) == childBuffer.get(j))
                    {
                        if(i>0)
                        {
                            DiffOperation d = new DiffOperation();
                            byte[] data = new byte[i];
                            
                            for(int k=0; k<i; k++)
                            {
                                data[k] = (byte)parentBuffer.get(k);
                            }
                            
                            d.setData(data);
                            d.setLength(i);
                            d.setIsUniqueToParent(true);
                            d.setStartLocation(parentIndex);
                            
                            uniqueList.add(d);
                        }
                        
                        
                        if(j>0)
                        {
                            DiffOperation d = new DiffOperation();
                            byte[] data = new byte[j];
                            
                            for(int k=0; k<j; k++)
                            {
                                data[k] = (byte)childBuffer.get(k);
                            }
                            
                            d.setData(data);
                            d.setLength(j);
                            d.setIsUniqueToParent(false);
                            d.setStartLocation(parentIndex); 
                            
                            uniqueList.add(d);
                        }
                        
                    }
                }
            }
            
            //Main while loop
            if(parentWasRead == true)
            {
                parentIndex = parentIndex+1;
            }
            
            if(childWasRead == true)
            {
                childIndex = childIndex+1;
            }
            
            //check if we have read out all data
            parentHasData = parentStream.hasMoreData();
            childHasData = childStream.hasMoreData();
            
            if((parentHasData == false)&&(childHasData == false))
            {
                outOfData = false;
            }
            else
            {
                outOfData = true;
            }
            
            
        }
        
        return uniqueList;
        
    }
    
    //------------------ Getters and Setters -------------------
}
