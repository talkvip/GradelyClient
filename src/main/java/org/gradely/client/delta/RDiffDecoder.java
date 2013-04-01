/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.delta;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
    public static void add(FilePath parent, FilePath diffFile, FilePath target) throws FileNotFoundException, IOException, MalformedFileException, NoMoreDataException
    {
        //Apply deletes from back to front
        //Apply inserts from front to back
        
        //Read in the data

        RDiffReader reader = new RDiffReader(diffFile);

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
                Logging.warning("Hit the end of the diff file unexpectadlly.", e);
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
        
        List<FileOperation> insertOpsLst = generateFileSections( deleteLst,  insertLst,  parent);
        
        applyOperations(insertOpsLst,target,parent);
        
        
        
    }
    
    /**
     * Turns the list of inserts and deletes into actions that can be used to create the new file.
     * @param deleteLst The list of deletes, hopefully sorted.
     * @param insertLst The list of inserts
     * @param source The file to be copied from. One of the versions of the file.
     */
    public static List<FileOperation> generateFileSections(ArrayList<DiffOperation> deleteLst, ArrayList<DiffOperation> insertLst, FilePath source)
    {
        long fullLengthofFile = source.getFileObject().length();
        
        //Apply deletes from back to front
        //Apply inserts from front to back
        
        List<FileOperation> insertOpsLst = new LinkedList<FileOperation>();
        
        for(int i=deleteLst.size()-1; i>0; i--)
        {
            DiffOperation upperBound = deleteLst.get(i);
            DiffOperation lowerBound = deleteLst.get(i-1);
            
            insertOpsLst.add(createDeleteFileOperation(upperBound,lowerBound));

        }
        
        //Now worry about the inserts
        for(int i=0; i<insertLst.size(); i++)
        {
            insertOpsLst = editOpsLstWithInsert(insertLst.get(i),insertOpsLst);
        }
        
        return insertOpsLst;
        
        
    }
    
    /**
     * Takes two delete operations and turns it in to the section of the file needed to be copied.
     * @param upperBound The upper, larger starting value, delete operation
     * @param lowerBound The lower delete operation
     */
    private static FileOperation createDeleteFileOperation(DiffOperation upperBound,DiffOperation lowerBound)
    {
        long startLocation = lowerBound.startLocation+lowerBound.length;
        long length = startLocation - upperBound.startLocation;
        
        return new FileOperation(startLocation,length, false);
    }
    
    /**
     * Takes the list of existing operations, and adds an insert at the appropriate spot. 
     * @param insert
     * @param insertOpsLst
     * @return 
     */
    private static List<FileOperation> editOpsLstWithInsert(DiffOperation insert, List<FileOperation> insertOpsLst)
    {
        //Check where it is.
        //Check if it is cohabital with a current operation
        
        //This check for codeletes
        for(int i=0; i<insertOpsLst.size(); i++)
        {

            if(insertOpsLst.get(i).getStartLocationOtherFile() == insert.getStartLocation())
            {
                //Cool! we dont need to do anything!
                
                FileOperation op = new FileOperation(insert.getStartLocation(),insert.getLength(),true,insert.getData());
                
                
                if(i+1 >= insertOpsLst.size())
                {
                    insertOpsLst.add(op);
                    return insertOpsLst;
                }
                else
                {
                    insertOpsLst.add(i+1, op);
                    return insertOpsLst;
                }

            }

        }
        
        //Ok, lets check for a new data addition
        for(int i=0; i<insertOpsLst.size(); i++)
        {
           FileOperation op = insertOpsLst.get(i);
           
           long startLocation = op.getStartLocationOtherFile();
           long endPoint = startLocation+op.length;
           
           if((insert.getStartLocation() > startLocation)&&(insert.getStartLocation() < endPoint))
           {
               //Split up the fileoperation
               FileOperation lower = new FileOperation();
               lower.isInsert=op.isInsert;
               lower.startLocation=op.startLocation;
               lower.startLocationOtherFile=op.startLocationOtherFile;
               lower.length=Math.abs(startLocation-insert.getStartLocation());
               
               FileOperation middle = new FileOperation();
               middle.isInsert = true;
               middle.length = insert.length;
               middle.startLocation = insert.startLocation;
               middle.data = insert.data;
               
               FileOperation upper = new FileOperation();
               upper.isInsert=op.isInsert;
               upper.startLocation=op.startLocation;
               upper.startLocationOtherFile=op.startLocationOtherFile;
               upper.length=Math.abs(insert.getStartLocation()-endPoint);
               
              insertOpsLst.set(i, lower);
              insertOpsLst.add(i+1, upper);
              insertOpsLst.add(i+1, middle);
               
               
           }
           
        }
        
        return insertOpsLst;
        
        
    }
    
    /**
     * Takes the list of operations, and recomputes its current location within the files, with deletes and iinserts incluided. This helps change the file from the current coordinates to the other file's coordinates.
     * @param fileOpsLst List of file operations
     * @return The same list, but with updated StartLocationOtherFile fields.
     */
    private static List calculatePositionWithOperations(List<FileOperation> fileOpsLst)
    {
        long otherFileOffset = 0;
        
        
        long lastStartPosition = 0;
        long lastEndPosition = 0;
        
        for(int i=0; i<fileOpsLst.size()-1; i++)
        {
            FileOperation upper = fileOpsLst.get(i+1);
            FileOperation lower = fileOpsLst.get(i);
            
            long lowerEnd = lower.getStartLocation()+lower.getLength();
            
            if(lowerEnd < upper.startLocation)
            {
                //There is a delete
                otherFileOffset = otherFileOffset +(lowerEnd - upper.startLocation);
                upper.startLocationOtherFile = upper.startLocation-otherFileOffset;
                fileOpsLst.set(i+1, upper);
            }
            else if(lowerEnd > upper.startLocation)
            {
                //There is an insert
            }
            
            
        }
        
        return fileOpsLst;

    }
  
    
    
    /**
     * Takes the lists of operations and creates a new file with all of the appropriate changes..
     * @param target The file to write to
     * @param source Either the parent or the child file.
     */
    public static void applyOperations(List<FileOperation> insertOpsLst, FilePath target, FilePath source) throws FileNotFoundException, IOException, NoMoreDataException
    {
        LookAheadFileInputStream sourceStream = new LookAheadFileInputStream(source);
        DataOutputStream targetStream = new DataOutputStream(new FileOutputStream(target.getFileObject()));
        
        try
        {
        
            for(int i=0; i<insertOpsLst.size(); i++)
            {
               FileOperation  op = insertOpsLst.get(i);

               if(op.isInsert)
               {
                   targetStream.write(op.data);
               }
               else
               {
                   if (sourceStream.getLocation() < op.startLocation)
                   {
                       sourceStream.skip(op.startLocation-sourceStream.getLocation());
                   }
                   if (sourceStream.getLocation() > op.startLocation)
                   {
                       Logging.error("The insertOpsLst is out of order. It requires going backwards in the file. Disregarding instruction");
                   }

                   for (int j=0; j<op.length; j++)
                   {
                       targetStream.writeByte((int)sourceStream.getNextByte());
                   }
               }
            }
        
        }
        finally
        {
            sourceStream.close();
            targetStream.close();
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
