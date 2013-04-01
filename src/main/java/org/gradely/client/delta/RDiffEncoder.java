package org.gradely.client.delta;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;;
import java.util.LinkedList;
import java.util.List;
import org.gradely.client.FilePath;
import org.gradely.client.logging.Logging;
import java.util.LinkedList;
import java.util.List;
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
        List<DiffOperation> opLst = compareFiles(parent,child);
        
        if(opLst.isEmpty())
        {
            Logging.info("The files compared in RDiff are the same. Operation List is empty.  ParentFile = "+parent.getAbsolutePath()+" Length = "+parent.getFileObject().length()+" bytes. ChildFile = "+child.getAbsolutePath()+" Length = "+child.getFileObject().length()+" bytes.");
        }
        else
        {
            Logging.debug("There were "+opLst.size()+" changes between the files.");
        }
        
        //Write out the file
        writeDiffFile(opLst, target);

        
        
    }
    
    /**
     * Writes out the diff file using an already computed set of diffrences.
     * @param opLst The list of unique data between each file.
     * @param target The filepath to write to.
     */
    private static void writeDiffFile(List<DiffOperation> opLst, FilePath target) throws FileNotFoundException, IOException
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
    private static List<DiffOperation> compareFiles(FilePath parent, FilePath child) throws FileNotFoundException, IOException
    {
        LookAheadFileInputStream parentStream = new LookAheadFileInputStream(parent);
        LookAheadFileInputStream childStream = new LookAheadFileInputStream(child);
        
        List parentBuffer = new LinkedList();
        List childBuffer = new LinkedList();
        
        long parentIndex = -1;
        long childIndex = -1;
        
        List over = new LinkedList();
        
        while((parentStream.hasMoreData())|(childStream.hasMoreData()))
        {
            //System.out.println("Going around again.");
            //System.out.println("ParentIndex = "+parentIndex);
            //System.out.println("ChildIndex = "+childIndex);
            try
            {
                parentBuffer.add(parentStream.getNextByte());
                parentIndex++;
            }
            catch(NoMoreDataException e)
            {
                
            }
            
            try
            {
                childBuffer.add(childStream.getNextByte());
                childIndex++;
            }
            catch(NoMoreDataException e)
            {
                
            }
            
            //Search the buffers for matches
            Locations l = searchForMatches(parentBuffer,childBuffer);
            
            if(l.childIndex < 0)
            {
                //No match found
                continue;
            }
            else
            {
                
                if(l.childIndex >= 0)
                {
                    DiffOperation op = new DiffOperation();

                    DataNewList stripped = stripList(childBuffer, l.childIndex);
                    childBuffer = stripped.newList;
                    
                    if(stripped.data.length > 0)
                    {
                        op.setData(stripped.data);
                        op.setIsUniqueToParent(false);
                        op.setLength(op.data.length);
                        op.setStartLocation(childIndex);
                        over.add(op);
                    }
                    
                }
                
                if(l.parentIndex >= 0)
                {
                    DiffOperation op = new DiffOperation();

                    DataNewList stripped = stripList(parentBuffer, l.parentIndex);
                    parentBuffer = stripped.newList;
                    
                    if(stripped.data.length > 0)
                    {
                        op.setData(stripped.data);
                        op.setIsUniqueToParent(false);
                        op.setLength(op.data.length);
                        op.setStartLocation(parentIndex);
                        over.add(op);
                    }
                }
                
            }

        }
        
        return over;
  
    }
    
    /**
     * Removes values up to, and including the index from the list. 
     * @param buffer
     * @param index 
     */
    public static DataNewList stripList(List buffer, int index)
    {
        byte[] data = new byte[index];
        
        for(int i=0; i<index; i++)
        {
            data[i] = (byte)buffer.remove(0);
            
        }
        
        //Remove the one common to both
        buffer.remove(0);
        
        return new DataNewList(data, buffer);
    }
    
    
    /**
     * Searches for matches between the two lists.
     */
    public static Locations searchForMatches(List parentBuffer, List childBuffer)
    {
        
        for(int i=0; i<parentBuffer.size(); i++)
        {
            for(int j=0; j<childBuffer.size(); j++)
            {
                if(parentBuffer.get(i) == childBuffer.get(j))
                {
                    return new Locations(i,j);
                }
            }
        }
        
        return new Locations(-1,-1);
        
    }
   
    public static class DataNewList
    {
        byte[] data;
        List newList;
        
        DataNewList(byte[] data, List newList)
        {
            this.data = data;
            this.newList = newList;
        }
    }
    
    public static class Locations
    {
        public int parentIndex;
        public int childIndex;
        
        Locations(int parentIndex, int childIndex)
        {
            this.parentIndex = parentIndex;
            this.childIndex = childIndex;
        }
    }
      
    //------------------ Getters and Setters -------------------
}
