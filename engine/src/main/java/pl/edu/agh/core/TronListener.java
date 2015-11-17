/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author uriel
 */
public class TronListener extends Thread {
    private static final Logger l = Logger.getLogger(TronListener.class.getName());
    private static final String LISTENER_THREAD_NAME = "TronListener";
    
    private int serverPort;
    private final Map<Integer,TronClientSession> connections;
    
    public TronListener( int listenPort ) throws IOException {
        super(LISTENER_THREAD_NAME);
        connections = new HashMap<>();
    }

    @Override
    public void run() {
        ServerSocket server = null;
        
        try{
            server = new ServerSocket( serverPort );
           
        }catch( IOException e ) {
            l.severe("Cannot open port: "+serverPort);
            return;
        }
        
        while( !Thread.interrupted() ) {
            try{
                Socket client = server.accept();
                TronClientSession session = 
                        new TronClientSession(client, 
                                              0, 
                                              this::handleConnectionTermination);
                synchronized(connections) {
                    connections.put(findFreeSessionID(), session);
                }
            }catch( IOException e ) {
                l.warning("wait for client exception");
            }
        }
        
    }
    
    private int findFreeSessionID() {
        int i = 1;
        while( connections.containsKey(i) ) {
            ++i;
        }
        return i;
    }
    
    /**
     * Obsługa zakońćzenia połączenia.
     * 
     * Uwaga! wykonuje się w innym wątku niż reszta kodu. Uwaga na synchronizację.
     * 
     * @param tcs
     */
    private Void handleConnectionTermination( TronClientSession tcs ) {
        synchronized(connections) {
            connections.remove(tcs.getSessionID());
        }
       return null;
    }
    
}
