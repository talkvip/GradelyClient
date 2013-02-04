package org.hwdb.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import java.util.zip.GZIPInputStream;

/**
 * This class zips and dezips files using gzip. This is a static class.
 *
 * @author Matt
 */
public class Zipper {

    private Zipper() {
    }

    /**
     * zips a file up for storage and transmission.
     *
     * @param in The file to be zipped
     * @param out The resulting zipped file
     */
    public static void zip(FilePath in, FilePath out) throws FileNotFoundException, IOException {

        //File <-- FileOutputSream <-- GZipOutputStream <-- BufferedOutputStream

        FileOutputStream fileOut = new FileOutputStream(out.getFileObject());

        GZIPOutputStream gos = new GZIPOutputStream(fileOut);

        BufferedOutputStream outStr = new BufferedOutputStream(gos);

        FileInputStream fileIn = new FileInputStream(in.getFileObject());

        BufferedInputStream inStr = new BufferedInputStream(fileIn); // use new GZIPInputStream(fileIn) for reading ziped data
        
        byte[] buffer = new byte[1024*2]; // Adjust if you want
        int bytesRead;
        //copy in to out
        while ((bytesRead = inStr.read(buffer)) != -1)
        {
            outStr.write(buffer, 0, bytesRead);
        }
        
        if(buffer.length > 0)
        {
            outStr.write(buffer, 0, buffer.length-1);
        }
        
        fileIn.close();
        inStr.close();
        outStr.close();
        gos.close();
        fileOut.close();

    }
    
    
    /**
     * Turns a gziped file into a decompressed file.
     * @param zipped The zipped file location.
     * @param out The filepath for the restulant decompressed file.
     */
    public static void unzip(FilePath zipped, FilePath out) throws FileNotFoundException, IOException
    {
        
        FileInputStream fileIn = new FileInputStream(zipped.getFileObject());
        
        GZIPInputStream gzipStream = new GZIPInputStream(fileIn, 1024*2);

        //BufferedInputStream inStr = new BufferedInputStream(gzipStream); 
        
        FileOutputStream fileOut = new FileOutputStream(out.getFileObject());

        BufferedOutputStream outStr = new BufferedOutputStream(fileOut);
        
        byte[] buffer = new byte[1024*2]; // Adjust if you want
        int bytesRead;

        //copy in to out
        while ((bytesRead = gzipStream.read(buffer,0,buffer.length-1)) != -1)
        {
            outStr.write(buffer, 0, bytesRead);
        }
        
        if(buffer.length > 0)
        {
            outStr.write(buffer, 0, buffer.length-1);
        }
        
        outStr.close();
        fileOut.close();
        //inStr.close();
        gzipStream.close();
        fileIn.close();
        
        
        
        
        
    }
}
