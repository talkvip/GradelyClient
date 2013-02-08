
package org.hwdb.client.network.filetransfer;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * <p>This class represents a queue for the uploading and downloading of files. </p>
 * <p>Either when the remote server notifies the client to down load a file this is called from ServerWatcher, or it is called when we need to upload something, from FileChange </p>
 * @author Matt
 * TODO Make the queue actually do something. Pulling from the queue does not do anything.
 * NOTE currently throws a complier warning: "Some input files use unchecked or unsafe operations."
 */
public class FileTransferQueue{
    
    private final int nThreads;
    private final PoolWorker[] threads;
    private final LinkedList queue;

    public FileTransferQueue(int nThreads)
    {
        this.nThreads = nThreads;
        queue = new LinkedList();
        threads = new PoolWorker[nThreads];

        for (int i=0; i<nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void execute(Runnable r) {
        synchronized(queue) {
            queue.addLast(r);
            queue.notify();
        }
    }

    private class PoolWorker extends Thread {
        
        public void run() {
            Runnable r;

            while (true) {
                synchronized(queue) {
                    while (queue.isEmpty()) {
                        try
                        {
                            queue.wait();
                        }
                        catch (InterruptedException ignored)
                        {
                        }
                    }

                    r = (Runnable) queue.removeFirst();
                }

                // If we don't catch RuntimeException, 
                // the pool could leak threads
                try {
                    r.run();
                }
                catch (RuntimeException e) {
                    // You might want to log something here
                }
            }
        }
    }
}
    

