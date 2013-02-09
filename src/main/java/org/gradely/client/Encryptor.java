package org.gradely.client;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
/**
 * This class encrypts files and strings.
 * @author Matt
 */
public class Encryptor {

    //================= Fields =================================
    
    //================= Constructors ===========================

    private Encryptor() {

    }

    //================= Methods ================================
    
    /**
     * Takes a string and returns an encrypted string.
     * @param plaintext the string to encrypt
     * @peram password the password that will become the key
     * @throws Exception thrown if anything goes wrong.
     * @return 
     */
    private static String encrypt(String plaintext, String password) throws EncryptionException
    {
        try
        {
            byte[] hash = Hashsum.computeHash(password);

            SecretKeySpec key = new SecretKeySpec(hash, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cryptText = cipher.doFinal(plaintext.getBytes("UTF-8"));
            return new String(cryptText);
            
        }
        catch(NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e)
        {
            throw new EncryptionException("Something went wrong while encrypting. Message: "+e.toString());
        }
    }
    
    /**
     * Decrypts a string using the SHA-256 hash of the password as the key.
     * @param cryptText The base 64 encoded string containg the encrypted text
     * @param password the user's plain old password.
     * @return UTF-8 Java standard string.
     */
    public static String decrypt(String cryptText, String password) throws EncryptionException
    {
        try
        {
            byte[] hash = Hashsum.computeHash(password);

            //Convert the string to bytes
            byte[] crypt = decodeBase64toByte(cryptText);

            SecretKeySpec key = new SecretKeySpec(hash, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = cipher.doFinal(crypt);
            return new String(plainText);
        }
        catch(Exception e)
        {
            throw new EncryptionException("Something went wrong while decrypting. Message: "+e.toString());
        }
    }
    
    /**
     * Encodes a string into a more compact Base64 string. Takes use of the Apache Commons Codec libary.
     * @param stringToEncode String to encode
     * @return base64 string
     */
    public static String encodeBase64(String stringToEncode)
    {
            return Base64.encodeBase64String(stringToEncode.getBytes());
    }
    
    /**
     * Encodes binary data to base64 string encodeing. Takes use of the Apache Commons Codec libary.
     * @param binaryToEncode
     * @return 
     */
    public static String encodeBase64(byte[] binaryToEncode)
    {
            return Base64.encodeBase64String(binaryToEncode);
    }

    /**
     * Decodes a base64 string into a readable string containing the same data.
     * @param base64ToDecode
     * @return 
     */
    public static String decodeBase64toString(String base64ToDecode)
    {
            return new String(Base64.decodeBase64(base64ToDecode.getBytes()));
    }
    
    /**
     * Turns a base64 string into bytes.
     * @param base64ToDecode
     * @return a byte array containg the string.
     */
    public static byte[] decodeBase64toByte(String base64ToDecode)
    {
            return Base64.decodeBase64(base64ToDecode.getBytes());
    }


    //------------------ Getters and Setters -------------------
}
