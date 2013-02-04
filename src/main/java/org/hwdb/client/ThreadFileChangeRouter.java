
package org.hwdb.client;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * Starts up a new thread and starts figuring out what to do with this new file change.
 * @author Matt
 */
public class ThreadFileChangeRouter extends Thread {

    //================= Fields =================================
    private FilePath modifiedFile;
    private WatchEvent.Kind<Path> typeOfChange;
    
    //================= Constructors ===========================

    public ThreadFileChangeRouter(FilePath modifiedFile, WatchEvent.Kind<Path> typeOfChange) {

           this.modifiedFile = modifiedFile;
           this.typeOfChange = typeOfChange;
    }

    //================= Methods ================================
    
     /**
     * Run implements the Runnable interface and takes care of launching the new thread.
     */
    @Override
    public void run() {
        FileChange.fileModify(this.getModifiedFile());
    }
    
    //------------------ Getters and Setters -------------------

    /**
     * @return the modifiedFile
     */
    public FilePath getModifiedFile() {
        return modifiedFile;
    }

    /**
     * @param modifiedFile the modifiedFile to set
     */
    public void setModifiedFile(FilePath modifiedFile) {
        this.modifiedFile = modifiedFile;
    }

    /**
     * @return the typeOfChange
     */
    public WatchEvent.Kind<Path> getTypeOfChange() {
        return typeOfChange;
    }

    /**
     * @param typeOfChange the typeOfChange to set
     */
    public void setTypeOfChange(WatchEvent.Kind<Path> typeOfChange) {
        this.typeOfChange = typeOfChange;
    }
}
