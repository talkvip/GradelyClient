///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package org.gradely.client.deduplication;
//
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Comparator;
//import org.gradely.client.FilePath;
//import org.gradely.client.SHA2Hashsum;
//import org.gradely.client.config.Constants;
//import org.gradely.client.logging.Logging;
//
///**
// * Writes, Reads, and applys deduplication files.
// * @see http://www.philippheckel.com/files/syncany-heckel-thesis.pdf 
// * @author Matt
// * @depreciated Removed in place of deltas
// */
//public class DeduplicationEncoder {
//
//    //================= Fields =================================
//    
//    //================= Constructors ===========================
//
//    private DeduplicationEncoder() {
//
//    }
//
//    //================= Methods ================================
//    
//    /**
//     * Creates a new Deduplication file.
//     * @param file The path to the file we want to make a deduplication file from.
//     * @param out The location the file will be written to.
//     */
//    public static void writeFile(FilePath file, FilePath out) throws FileNotFoundException, IOException
//    {
//        Blocks b = createBlocks(file);
//        
//        for
//    }
//    
//    /**
//     * Computes everything needed for the Deduplication File. To be used for files that have been just created.
//     * @param file The file that the deduplication will be written from.
//     * @return A Blocks object containing everything needed to write the deduplication file.
//     */
//    public static Blocks createBlocks(FilePath file) throws FileNotFoundException, IOException
//    {
//        ///Header Data
//        Blocks fileData = createHeader(file);
//        ArrayList<BlockEntry> blockLst = new ArrayList<BlockEntry>();
//        
//        //Start figuring out the blocks.
//        //Note: zero char offset
//        Adler32Hashsum hasher = new Adler32Hashsum();
//        
//        //page through large file to save memory
//        
//        BufferedInputStream buffStream = new BufferedInputStream(new FileInputStream(file.getFileObject()));
//        
//        byte[] buffer = new byte[Constants.blockSize];
//        int bytesRead;
//        
//        while((bytesRead = buffStream.read(buffer, 0, buffer.length-1)) != -1)
//        {
//            hasher.check(buffer, 0, bytesRead);
//            try
//            {
//                BlockEntry be = new BlockEntry(bytesRead, hasher.getValue(), SHA2Hashsum.computeHash(buffer));
//                blockLst.add(be);
//                buffer = new byte[Constants.blockSize];
//            }
//            catch (NoSuchAlgorithmException e)
//            {
//                Logging.error("Creating the BlockEntry failed due to the SHA2 hashsum failing due to an NoSuchAlgorithmException. This sould never had happened.", e);
//            }
//            
//        }
//        
//        buffStream.close();
//        
//        if(buffer.length > 0)
//        {
//            hasher.check(buffer, 0, buffer.length-1);
//            try
//            {
//                BlockEntry be = new BlockEntry(bytesRead, hasher.getValue(), SHA2Hashsum.computeHash(buffer));
//                blockLst.add(be);
//                
//            }
//            catch (NoSuchAlgorithmException e)
//            {
//                Logging.error("Creating the BlockEntry failed due to the SHA2 hashsum failing due to an NoSuchAlgorithmException. This sould never had happened.", e);
//            }
//        }
//        
//        fileData.setBlockEntries(blockLst);
//        
//        return fileData;
//    }
//    
////    /**
////     * Computes everything needed for the Deduplication file,  but also takes in to consideration the previous blocks.
////     * @param file
////     * @param previousDedup
////     * @return A Blocks object containing everything needed to write the deduplication file.
////     */
////    public static Blocks createBlocks(FilePath file, FilePath previousDedup) throws FileNotFoundException, IOException, NoSuchAlgorithmException
////    {
////        
////        //Let's read the previous file's blocks.
////        Blocks previousBlocks = DeduplicationDecoder.decode(file);
////
////        ///Header Data
////        Blocks fileData = createHeader(file);
////        fileData.setPreviousFileName(previousBlocks.getFileName());
////
////        ArrayList<BlockEntry> blockLst = new ArrayList<BlockEntry>();
////
////        //Start figuring out the blocks.
////        //Note: zero char offset
////        Adler32Hashsum hasher = new Adler32Hashsum();
////
////
////        //page through large file to save memory
////        BufferedInputStream buffStream; 
////
////        //Search for blocks contained within both files
////        //======================================================================
////
////        //Figure out the unique lengths of all blocks
////        int[] lengths = determineUniqueLengths(previousBlocks.getBlockEntries());
////
////        Adler32Hashsum[] hasherArr = new Adler32Hashsum[lengths.length-1];
////
////        for(int i=0;i<lengths.length; i++)
////        {
////            hasherArr[i] = new Adler32Hashsum();
////        }
////
////        //Here's how this works.
////        //Read in bytes utill at least one of the hashsums has enough data.
////        //Read in an single byte.
////            //Compute hashsum
////            //Check hashsum aginsts previous hashsums.
////            //Note hashums at the same location as the previous hashsums. We may need to use that same hashsum later.
////        //Continue untill all bytes have been read.
////        //Figure out which block goes where. This is done in a seperate function.
////
////       for(int i=0;i<lengths.length; i++)
////       {
////            BlockEntry previousEntry = previousBlocks.getBlockEntries().get(i);
////            BlockEntry entry = findExactMatch(lengths[i], previousEntry.getAdler32Hash(), previousEntry.getSha2Hash(), file); 
////
////            if(entry != null)
////            {
////                blockLst.add(entry);
////            }
////       }
////
////        //Figure out where the windows are
////        blockLst = blocksGoWhere(blockLst,file);
////
////        //Make shure hash data is present for all hash entries
////        
////        
////        fileData.setBlockEntries(blockLst);
////
////        return fileData;
////
////    }
////
////    
////    /**
////     * Takes an adler32 hashsum and finds a coorisponding adler32 hashsum in the file. 
////     * @param length The length of the block we are searching for.
////     * @param adler32Hash The adler hashsum to find.
////     * @param sha2Hash The sha2 hash for double checking the adler hashsum.
////     * @param file The file to search in.
////     * @return Either a completed BlockEntry or null if the match was not found.
////     */
////    private static BlockEntry findExactMatch(int length, int adler32Hash,byte[] sha2Hash, FilePath file) throws FileNotFoundException, IOException, NoSuchAlgorithmException
////    {
////        FileInputStream fileStream = null;
////        try
////        {
////
////            fileStream = new FileInputStream(file.getFileObject());
////
////            Adler32Hashsum hasher = new Adler32Hashsum();
////
////            byte[] buffer = new byte[length];
////
////            int bytesRead = fileStream.read(buffer);
////
////            if (bytesRead == -1)
////            {
////                //The file is empty
////                Logging.warning("A file was not as big as its block size. Somthing is wrong.");
////                throw new IOException("A file was not as big as its block size. Somthing is wrong.");
////            }
////
////            //set up the hashsum for the appropriate length
////            hasher.check(buffer, 0, buffer.length);
////
////            //Check to see if the hashsum matches
////            if(hasher.getValue() == adler32Hash)
////            {
////                //Check the sha2Hash for integraty
////                if (SHA2Hashsum.computeHash(buffer) == sha2Hash)
////                {
////                    BlockEntry entry = new BlockEntry();
////                    entry.setAdler32Hash(adler32Hash);
////                    entry.setSha2Hash(buffer);
////                    entry.setLength(length);
////                    entry.setStartLocation(0);
////                    return entry;
////                }
////            }
////
////            //Loop through the file looking for the sum
////            int intByte = 0; 
////            int locationCounter = length;
////            while((intByte = bytesRead) != -1)
////            {
////                locationCounter = locationCounter +1;
////
////                hasher.roll((byte)intByte);
////
////                //Check to see if the hashsum matches
////                if(hasher.getValue() == adler32Hash)
////                {
////                    //Check the sha2Hash for integraty
////                    if (SHA2Hashsum.computeHash(buffer) == sha2Hash)
////                    {
////                        BlockEntry entry = new BlockEntry();
////                        entry.setAdler32Hash(adler32Hash);
////                        entry.setSha2Hash(buffer);
////                        entry.setLength(length);
////                        entry.setStartLocation(locationCounter);
////                        return entry;
////                    }
////                }
////
////            }
////
////            return null;
////        
////        }
////        finally
////        {
////            fileStream.close();
////        }
////        
////    }
////    
////    
////    /**
////     * Determines exactly where the blocks should go in a file.
////     * @param ExactMatchList The list of blocks that are an exact match to the previous files.
////     * @param previousBlockList The previous file's blocklist
////     * @return The completed final block list.
////     */
////    private static ArrayList<BlockEntry> blocksGoWhere(ArrayList<BlockEntry> exactMatchList, FilePath currentFile) throws FileNotFoundException, IOException
////    {
////        //Find gaps in the exact matches
////        //======================================================================
////        if (exactMatchList.isEmpty())
////        {
////            return createBlocks(currentFile).getBlockEntries();
////        }
////        
////        //Find all gaps
////        //======================================================================
////        ArrayList<Gap> gapLst = new ArrayList<Gap>();
////        
////        Collections.sort(exactMatchList, new BlockEntryComparer());
////        
////
////        for(int i=0;i<exactMatchList.size()+1;i++)
////        {
////            BlockEntry current = null;
////            BlockEntry previous = null;
////            if(i == 0)
////            {
////                current = exactMatchList.get(i);
////                previous = new BlockEntry();
////                previous.setLength(0);
////                previous.setStartLocation(0);
////            }
////            else if (i == exactMatchList.size())
////            {
////                current = new BlockEntry();
////                current.setStartLocation(currentFile.getFileObject().length());
////                current.setLength(0);
////                previous = exactMatchList.get(i-1);
////            }
////            else
////            {
////                current = exactMatchList.get(i);
////                previous = exactMatchList.get(i-1);
////            }
////            
////            long gapLength = (previous.getStartLocation()+previous.getLength())-(current.getLength()+current.getStartLocation());
////            
////            if(gapLength != 0)
////            {
////                Gap g = new Gap();
////                g.length = gapLength;
////                g.startPosition = (previous.getStartLocation()+previous.getLength());
////                gapLst.add(g);
////            }
////
////            
////        }
////        
////        //Now That we have all gaps, Split gaps into blocks
////        //======================================================================================
////        
////        for(int i =0; i<gapLst.size(); i++)
////        {
////            Gap g = gapLst.get(i);
////            if(g.length > org.gradely.client.config.Constants.blockSize)
////            {
////                //Split up the block
////                //Big Block First
////                int blockSize = org.gradely.client.config.Constants.blockSize;
////                
////                long blocksNeeded = g.length/blockSize;
////                long leftOver = g.length%blockSize;
////                
////                for(long j=1; j<=blocksNeeded; j++)
////                {
////                    BlockEntry entry = new BlockEntry();
////                    entry.setLength(blockSize);
////                    entry.setStartLocation(g.startPosition+(j*blockSize));
////                    exactMatchList.add(entry);
////                }
////                
////                if(leftOver != 0)
////                {
////                    BlockEntry entry = new BlockEntry();
////                    entry.setLength((int)leftOver);
////                    entry.setStartLocation((blocksNeeded*blockSize)+g.startPosition);
////                    exactMatchList.add(entry);
////                }  
////            }
////        }
////        
////        return exactMatchList;
////        
////    }
//    
//    /**
//     * Sorts the Location of blocks from first to last.
//     * @param matchLst
//     * @return 
//     */
//    private static class BlockEntryComparer implements Comparator<BlockEntry>
//    {
//       
//        @Override
//        public int compare(BlockEntry a, BlockEntry b) {
//            if(a.getStartLocation() < b.getStartLocation())
//            {
//                return -1;
//            }
//            else if(a.getStartLocation() > b.getStartLocation())
//            {
//                return 1;
//            }
//            else
//            {
//                return 0;
//            }
//        }
//
//                
//    }
//    
//    
//    /**
//     * Describes the gap between blocks
//     */
//    private static class Gap
//    {
//        public long length;
//        public long startPosition;
//    }
//    
//    /**
//     * Returns the largest value in the array.
//     * @arr The array to starch for the largest value
//     * @return The smallest value in the array.
//     */
//    private static int getLargestValue(int[] arr)
//    {
//        
//        int canidate = arr[0];
//        
//        for (int i=0;i<arr.length;i++)
//        {
//            if(arr[i] > canidate)
//            {
//                canidate = arr[i];
//            }
//        }
//        
//        return canidate;
//        
//    }
//    
//    /**
//     * Simply gets the header information for the deduplication file. For future writing
//     * @param file The file to create the header information from.
//     * @return A partially filled out Blocks object.
//     */
//    private static Blocks createHeader(FilePath file)
//    {
//        Blocks b = new Blocks();
//        b.setFileName(file.getFileObject().getName());
//        
//        try
//        {
//            b.setSHA2Hashsum(SHA2Hashsum.computeHash(file));
//        }
//        catch(IOException|NoSuchAlgorithmException e)
//        {
//            Logging.warning("Cannot create the hashsum entry due to an error.", e);
//        }
//        
//        b.setTimeOfCreation(new SimpleDateFormat(org.gradely.client.config.Constants.dateFormat).format(Calendar.getInstance().getTime()));
//        
//        return b;
//    }
//    
//    /**
//     * Figures out the length of each chunk in the deduplication file. Only unique lengths are returned.
//     * @param lst The entries in the deduplication file.
//     * @return An array containing the lengths of the chunks 
//     */
//    private static int[] determineUniqueLengths(ArrayList<BlockEntry> lst)
//    {
//        int[] lengths = new int[lst.size()];
//        
//        int filledUpTo = 0;
//        for (int i =0; i<=lst.size(); i++)
//        {
//            int canidate = lst.get(i).getLength();
//
//            if(java.util.Arrays.binarySearch(lengths, canidate) < 0)
//            {
//                lengths[filledUpTo] = canidate;
//                filledUpTo = filledUpTo + 1;
//            }
//        }
//        
//        //Trim the array
//        int[] newLengths = new int[filledUpTo -1];
//        for(int i=0; i<lengths.length; i++)
//        {
//            if (lengths[i] != 0)
//            {
//                newLengths[i] = lengths[i]; 
//            }
//        }
//        
//        return newLengths;
//    }
//    
//    //------------------ Getters and Setters -------------------
//}
