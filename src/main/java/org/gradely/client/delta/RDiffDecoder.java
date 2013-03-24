/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.gradely.client.FilePath;
import org.gradely.client.logging.Logging;

/**
 *
 * @author Matt
 */
public class RDiffDecoder {

    //================= Fields =================================
    
    //================= Constructors ===========================

    public RDiffDecoder() {

    }

    //================= Methods ================================
    
    /**
     * Converts a old file and a diff file into a updated file   parent+diffFile=childFile. The parent file is the older file.
     * @param child
     * @param diffFile 
     * @param target The output file. Will contain the child version
     */
    public static void add(FilePath parent, FilePath diffFile, FilePath target) throws FileNotFoundException, IOException, MalformedFileException
    {
        //Apply deletes from back to front
        //Apply inserts from front to back
        
        //Read in the data

        RDiffReader reader = new RDiffReader(target);

        //Read in the file
        ArrayList<DiffOperation> opLst = new ArrayList<DiffOperation>();
        
        
        while(reader.hasMoreEntries())
        {
            try
            {
                DiffOperation op = reader.readEntry();
                opLst.add(op);
            }
            catch(NoMoreDataException e)
            {
                Logging.warning("Hit the end of the file unexpectadlly.", e);
                break;
            }
            
        }
        
        //Sort into insert and deletes
        
        ArrayList<DiffOperation> deleteLst = new ArrayList<DiffOperation>();
        ArrayList<DiffOperation> insertLst = new ArrayList<DiffOperation>();
        
        for (int i=0; i<opLst.size(); i++)
        {
            DiffOperation op = opLst.get(i);
            
            if(op.getIsUniqueToParent() == true)
            {
                deleteLst.add(op);
            }
            else
            {
                insertLst.add(op);
            }
        }
        
        //Sort the insert and deletes
        Collections.sort(deleteLst, new DiffOperationStartValueComparator());
        Collections.sort(insertLst, new DiffOperationStartValueComparator());
        
        //Start applying the inserts
        //Copy over the file
        //Keep track of the files original coordiates
        //Keep track of the cooridates with deletes applied
        
        
        
    }

    /**
     * Takes the lists of operations and creates a new file with all of the appropriate changes.
     * @param deleteLst The list of all data unique to the other version of the file.
     * @param insertLst The list of all data unique to this version of the file.
     * @param target The file to write to
     * @param source Either the parent or the child file.
     */
    public static void applyOperations(ArrayList<DiffOperation> deleteLst, ArrayList<DiffOperation> insertLst, FilePath target, FilePath source) throws IOException
    {
        //Start applying the inserts
        //Copy over the file
        //Keep track of the files original coordiates
        //Keep track of the cooridates with deletes applied
        
        long originalIndex = 0;
        long deleteIndex = 0;
        long insertIndex = 0;
        
        boolean isGo = true;
        boolean incrementOriginalIndex  = true;
        boolean incrementDeleteIndex = true;
        boolean hasData = false;
        
        boolean readInData = false;

        DataOutputStream writer = null;
        LookAheadFileInputStream reader = null;
        
        try
        {
            
            writer = new DataOutputStream(new FileOutputStream(target.getFileObject()));
            reader = new LookAheadFileInputStream(source);

            while(isGo)
            {
                //Look for actions at this index
                int insertLstIndex = searchForInserts(deleteIndex, insertLst);
                int deleteLstIndex = searchForDeletes(originalIndex, deleteLst);

                if(insertLstIndex != -1)
                {
                    //It's time to insert some data
                    writer.write(insertLst.get(insertLstIndex).getData());
                    insertIndex = insertIndex+insertLst.get(insertLstIndex).getLength();
                    insertLst.remove(insertLstIndex);
                }

                if(deleteLstIndex != -1)
                {
                    //It's time to delete some data
                    reader.skip(deleteLst.get(deleteLstIndex).length);
                    originalIndex = originalIndex + deleteLst.get(deleteLstIndex).length;
                    deleteLst.remove(deleteLstIndex);
                }

                if((deleteLstIndex != -1)&&(insertLstIndex != -1))
                {
                    readInData = true;
                }
                else
                {
                    readInData = false;
                    incrementDeleteIndex = false;
                    incrementOriginalIndex = false;
                }

                if(readInData == true)
                {
                    //Read in a byte from this file 
                    byte thisByte;
                    try
                    {
                        thisByte = reader.getNextByte();
                        hasData = true;
                    }
                    catch(NoMoreDataException e)
                    {
                        incrementOriginalIndex  = false;
                        hasData = false;
                        break;
                    }
                }

                if((hasData == false)&&(deleteLst.isEmpty())&&(insertLst.isEmpty()))
                {
                    isGo = true;
                    incrementOriginalIndex = true;
                }

                if(incrementDeleteIndex == true)
                {
                    deleteIndex = deleteIndex+1;
                }

                if(incrementOriginalIndex == true)
                {
                    originalIndex = originalIndex+1;
                }

                if((hasData == false)&&(!deleteLst.isEmpty()))
                {
                    Logging.warning("The file is out of data but not all deletes have been applied.");
                }

                if((hasData == false)&&(deleteLst.isEmpty())&&(insertLst.isEmpty()))
                {
                  isGo = false;   
                }


            }

        }
        finally
        {
            writer.close();
            reader.close();
        }
        
        
    }
    
    /**
     * Searches for insert to put in the file
     * @param index The index the file is at
     * @param insertLst The list of insert operations.
     * @return -1 if no operation is found, otherwise the index of what we are looking for.
     */
    public static int searchForInserts(long index, ArrayList<DiffOperation> insertLst)
    {
        for(int i=0; i<insertLst.size(); i++)
        {
            if(insertLst.get(i).getStartLocation() == index)
            {
                return i;
            }
        }
        
        return -1;
    }
    
    
    
    /**
     * Searches for insert to put in the file
     * @param index The index the file is at
     * @param deleteLst The list of insert operations.
     * @return -1 if no operation is found, otherwise the index of what we are looking for.
     */
    public static int searchForDeletes(long index, ArrayList<DiffOperation> deleteLst)
    {
        for(int i=0; i<deleteLst.size(); i++)
        {
            if(deleteLst.get(i).getStartLocation() == index)
            {
                return i;
            }
        }
        
        return -1;
    }
    
    
    /**
     * Converts a new file and a diff File into an older file. child-diffFile=parent
     * @param child The newer file version
     * @param diffFile The difffile between the two file versions
     * @param target The output file. Will contain the parent version
     */
    public static void subtract(FilePath child, FilePath diffFile, FilePath target)
    {
        
    }
    

    
    //------------------ Getters and Setters -------------------
}
