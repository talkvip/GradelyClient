package org.gradely.client.network.remotechanges;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;

/**
 * This class watches the server for changes made by the server or other clients.  
 * @author Matt
 */
public class ServerWatcher {

    //================= Fields =================================
    private URL url;
    private boolean stop = false;
    private int pollInterval = 60; //sec
    private DefaultHttpClientConnection resusedConn;
    
    //================= Constructors ===========================

    public ServerWatcher(URL url) {
        this.url = url;
    }

    //================= Methods ================================

    /**
     * Connects to the remote server and polls and starts listening for changes to the filesystem. Will persist. Needs to run in its own thread.
     * @throws IOException Thrown if something goes wrong with the Transport layer (tcp or lower) implementation
     * @throws InterruptedException Thrown if the sleep command gets weird
     * @throws HttpException Thrown if there is a fatal http error.
     */
    public void start() throws IOException, InterruptedException, HttpException {
        poll();
        Thread.sleep(getPollInterval()*60);
        
        if(isStop() == false)
        {
            start();
        }
    }
    
    /**
     * Polls the server looking for changes.
     * @see http://hc.apache.org/httpcomponents-core-ga/examples.html
     */
    private void poll() throws IOException, HttpException
    {
        HttpParams params = new SyncBasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "UTF-8");
        HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
        HttpProtocolParams.setUseExpectContinue(params, true);

        HttpProcessor httpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
                // Required protocol interceptors
                new RequestContent(),
                new RequestTargetHost(),
                // Recommended protocol interceptors
                new RequestConnControl(),
                new RequestUserAgent(),
                new RequestExpectContinue()});

        HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

        HttpContext context = new BasicHttpContext(null);
        HttpHost host = new HttpHost("localhost", 8080);

        DefaultHttpClientConnection conn;
        if (resusedConn != null && resusedConn.isOpen() && !resusedConn.isStale())
        {
            conn = resusedConn;
        }
        else
        {
            conn = new DefaultHttpClientConnection();
        }
        
        ConnectionReuseStrategy connStrategy = new DefaultConnectionReuseStrategy();

        context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
        context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, host);

        try {

                if (!conn.isOpen()) {
                    Socket socket = new Socket(host.getHostName(), host.getPort());
                    conn.bind(socket, params);
                }
                BasicHttpRequest request = new BasicHttpRequest("GET", getUrl().toString());
                //System.out.println(">> Request URI: " + request.getRequestLine().getUri());

                request.setParams(params);
                httpexecutor.preProcess(request, httpproc, context);
                HttpResponse response = httpexecutor.execute(request, conn, context);
                response.setParams(params);
                httpexecutor.postProcess(response, httpproc, context);

                //System.out.println("<< Response: " + response.getStatusLine());
                //System.out.println(EntityUtils.toString(response.getEntity()));
                //System.out.println("==============");

                if (!connStrategy.keepAlive(response, context)) 
                {
                    conn.close();
                } 
                else {
                    //System.out.println("Connection kept alive...");
                }
                
        } finally {
            resusedConn = conn;
        }
    }

    
    /**
     * Closes the connection to the remote server and stops watching.
     */
    public void stop() {
        
        setStop(true);
        
    } 
    
    //------------------ Getters and Setters -------------------

    /**
     * @return the url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * @return the stop
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * If true, the Watcher will finish it's connections and exit.
     * @param stop the stop to set
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
     * @return the pollInterval
     */
    public int getPollInterval() {
        return pollInterval;
    }

    /**
     * @param pollInterval the pollInterval to set
     */
    public void setPollInterval(int pollInterval) {
        this.pollInterval = pollInterval;
    }
}
