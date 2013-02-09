/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.hwdb.client.localchanges;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.hwdb.client.FileLocationEnum;
import org.hwdb.client.FilePath;


/**
 *
 * @author Matt
 */
public class FileWatcher {
    
    //flag indicating that the file watching needs to be stoped.
    private boolean stop = false;

    
    private FilePath watchDir;
    
    /**
     * This is the constructor for a new file watcher class. 
     * @param watchDir The root path is the path to the top level folder of our box.
     */
    public FileWatcher(FilePath watchDir){
        
        this.watchDir = watchDir;
    }
    
    /**
     * Start() starts watching the file system for changes.
     * @see http://e-blog-java.blogspot.com/2011/03/how-to-watch-file-system-for-changes-in.html
     */
    public void start() throws IOException, InterruptedException
    {
        
        //Figure out where the watcher watches.
        Path dir = Paths.get(watchDir.getAbsolutePath());
        
        FileSystem filesystem = FileSystems.newFileSystem(dir, null);
        WatchService watcher = filesystem.newWatchService();
        
        dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, 
           StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        
        
        while (true) {
            // retrieve key
            WatchKey key = watcher.take();

            // process events
            for (WatchEvent event: key.pollEvents()) {

                   if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        //Start it up.
                        FilePath fp = new FilePath((String)event.context(),FileLocationEnum.BOXFOLDER);
                        ThreadFileChangeRouter threader = new ThreadFileChangeRouter(fp,event.kind());
                        threader.start();
                   }
                   if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        //Process that change!
                        FilePath fp = new FilePath((String)event.context(),FileLocationEnum.BOXFOLDER);
                        ThreadFileChangeRouter threader = new ThreadFileChangeRouter(fp,event.kind());
                        threader.start();
                   }
                   if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        //Yaaa, that's right, send that modification to the server!
                        FilePath fp = new FilePath((String)event.context(),FileLocationEnum.BOXFOLDER);
                        ThreadFileChangeRouter threader = new ThreadFileChangeRouter(fp,event.kind());
                        threader.start();
                   }
            }

            // reset the key
            boolean valid = key.reset();
            if (!valid) {
                // object no longer registered, we are no longer watching the file system.
                //TODO: something to tell the rest of the program that we are no longer watching.
                break;
            }

            if (stop == true)
            {
                //looks like we no longer want to watch the file.
                break;
            }
        }
    }
    
    /**
     * Stop() stops watching the file system for changes. 
     */
    public void stop()
    {
        //set the stop flag and hope that start() picks it up.
        stop = true;
    }
    
    
}
