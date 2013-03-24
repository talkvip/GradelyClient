

package org.gradely.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Matt
 */
public class SHA2Hashsum {

    //================= Fields =================================
    
    //================= Constructors ===========================

    public SHA2Hashsum() {

    }

    //================= Methods ================================
    
    /**
     * This function computes the SHA2 hash of a arbitary binary file.
     * @param filepath The filepath of the file that this function hashes. 
     * @return Returns the SHA2 hash of the file
     * @throws NoSuchAlgorithmException Thrown if "SHA-2" is not supported.
     * @throws FileNotFoundException Thrown if the file is not found.
     * @throws IOException Thrown if something goes wrong with reading the file.
     */
    public static String computeHash(FilePath filepath) throws NoSuchAlgorithmException, FileNotFoundException, IOException
    {
        byte[] hashArr = computeHashBytes(filepath);
        
        //Convert hashArr to Hex
        String hashStr = bytesToHex(hashArr);
        
        return hashStr.toLowerCase();
    }
    
    
    /**
     * Computes the SHA2 hashsum on a file and returns the ray bytes output by the SHA2 algorthem.
     * @param filepath The file to hash
     * @return 32 bytes of the SHA2 hashsum
     * @throws NoSuchAlgorithmException If sha 256 is not valis
     * @throws FileNotFoundException If the file cannot be found.
     * @throws IOException If something goes wrong reading the file.
     */
    public static byte[] computeHashBytes(FilePath filepath) throws NoSuchAlgorithmException, FileNotFoundException, IOException
    {
        //Get the file 
        
        //need to convert to binary
        
        //Set up the java built in SHA libary
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        
        //page through large file to save memory
        BufferedInputStream buffStream = new BufferedInputStream(new FileInputStream(filepath.getFileObject()));

        byte[] buffer = new byte[1024*2]; //buffer size
        int bytesRead;
        
        while ((bytesRead = buffStream.read(buffer,0,buffer.length-1)) != -1)
        {
            //Update(byte[]) adds the bytes to the hash sum
            md.update(buffer, 0, bytesRead);
        }
        
        if(buffer.length > 0)
        {
            md.update(buffer, 0, buffer.length-1);
        }

        //digest finishes the task and spits out the hash
        //byte[] hashArr = md.digest();
        
        return md.digest();
    }
    
    
     /**
     * For small strings, like passwords
     * @param input A small string to be hashed.
     * @return A byte array containing the hashed value. 
     */
    public static byte[] computeHash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
            //Start by taking the sha256 hash
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes("UTF-8"));
            byte[] hash = md.digest();
            return hash;
    }
    
    /**
     * Computes the SHA2 hash on an array of bytes.
     * @param input The byte array to hash.
     * @return A byte array containing the raw output of the hash.
     */
    public static byte[] computeHash(byte[] input) throws NoSuchAlgorithmException
    {
         //Start by taking the sha256 hash
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input);
            byte[] hash = md.digest();
            return hash;
    }
    
    /**
     * From maybeWeCouldStealAVan at http://stackoverflow.com. Converts a byte array to hexidecimal.
     * @param bytes
     * @return A string representing the data in hex format
     */
    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF; //and bitwise operator
            hexChars[j * 2] = hexArray[v >>> 4]; //bit shifting right
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    
    /**
     * Trims an array to a specified length
     * @param arrayLength
     * @return 
     */
    private static byte[] trimToSize(byte[] arr, int arrayLength){
        
        byte[] newArr = new byte[arrayLength-1];
        System.arraycopy(arr, 0, newArr, 0, arrayLength);
        
        return newArr;
        
    }
    

    
    
    //------------------ Getters and Setters -------------------
}
