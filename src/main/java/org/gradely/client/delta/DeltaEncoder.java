package org.gradely.client.delta;

import com.nothome.delta.*;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.gradely.client.FilePath;

/**
 * This class is responsable for generating, merging, and applying the delta compression on documents. This is a static class. 
 * @author Matt
 * @deprecated Replaced with DeduplicationEncoder, which has the advantages of being reversible and decreases total needed storage space.
 */
public class DeltaEncoder {

    //================= Fields =================================
    
    //================= Constructors ===========================

    private DeltaEncoder() {

    }

    //================= Methods ================================

     /**
     * Converts a new file and a diff File into an older file. child-diffFile=parent
     * @param child The newer file version
     * @param diffFile The difffile between the two file versions
     * @param target The output file. Will contain the parent version
     */
    public static void subtract(FilePath child, FilePath diffFile, FilePath target) throws IOException, PatchException, PatchException
    {
        applyDelta(child, diffFile, target);
    }
    
     /**
     * Converts a old file and a diff file into a updated file   parent+diffFile=childFile. The parent file is the older file.
     * @param parent The older file version
     * @param diffFile the forward diff file
     * @param target The output file. Will contain the child version
     */
    public static void add(FilePath parent, FilePath diffFile, FilePath target) throws IOException, PatchException
    {
        applyDelta(parent, diffFile, target);
    }
    
    /**
     * Generates a diff file between the parent and the child so that the parent can be transformed into the child.
     * @param parent
     * @param child
     * @param target 
     */
    public static void generateForwardDiff(FilePath parent, FilePath child, FilePath target) throws IOException, DeltaException
    {
        generateDelta(parent, child, target);
    }
    
    /**
     * Generates a diff file so that the child can be turned into the parent.
     * @param parent
     * @param child
     * @param target 
     */
    public static void generateReverseDiff(FilePath parent, FilePath child, FilePath target) throws IOException, DeltaException
    {
         generateDelta(child, parent, target);
    }
    
    
    /**
     * This method generates the delta file on two files (parent, and child) and writes a file containing the delta to a third target path.
     * @param parent The parent is one of the two files to generate a delta from.
     * @param child The child is the other file to generate a delta from.
     * @param target The target is file name the delta will be written to.
     */
    private static void generateDelta(FilePath parent, FilePath child, FilePath target) throws IOException, DeltaException
    {
        OutputStream outStream = new FileOutputStream(target.getFileObject());
        DataOutputStream dataStream = new DataOutputStream(outStream);
        GDiffWriter gdw = new GDiffWriter(dataStream);
        
        Delta.computeDelta(parent.getFileObject(), child.getFileObject(), gdw);
        
        gdw.close();
        dataStream.close();
        outStream.close();
        
        
    }
    
    /**
     * Applies a delta to a file and saves the resulting file. 
     * @param parent The parent is the fill we will apply the delta to.
     * @param delta The file containing the delta.
     * @param target The location to write the resultant file to.
     */
    private static void applyDelta(FilePath source, FilePath delta, FilePath target)throws IOException, PatchException, FileNotFoundException
    {
        
        if( (!source.fileExists()) || (!delta.fileExists()) )
        {
            throw new FileNotFoundException("Either the parent or delta file does not exist.");
        }
        
        if(source.getFileObject().length() > Integer.MAX_VALUE)
        {
            throw new IOException("The parent file is too long. The delta patcher currently only supports files < 2.0GB. This is a problem with Java int.");
        }
        
        if(delta.getFileObject().length() > Integer.MAX_VALUE)
        {
            throw new IOException("The delta file is too long. The delta patcher currently only supports files < 2.0GB. This is a problem with Java int.");
        }

        GDiffPatcher patcher = new GDiffPatcher(source.getFileObject(), delta.getFileObject(), target.getFileObject());
    }
    

    
    
    //------------------ Getters and Setters -------------------
}
