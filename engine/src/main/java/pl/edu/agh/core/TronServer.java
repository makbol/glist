package pl.edu.agh.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class TronServer extends WebSocketServer {

    private static Logger l = LogManager.getLogger(TronServer.class);
    private static final int PORT = 1666;
    
    private static TronServer instance;
    
    public static TronServer getInstance() {
        try {
            return instance==null?instance=new TronServer(PORT):instance;
        }catch( IOException e ) {
            l.warn("Port zajęty. Inny serwer?",e);
        }
        return null;
     }

    private Room room  = new Room();

    private TronServer( int port ) throws IOException {
      super(new InetSocketAddress(port));
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public void onOpen(WebSocket ws, ClientHandshake ch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
   
    }
    

    @Override
    public void onError(WebSocket ws, Exception excptn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void onClose(WebSocket ws, int i, String string, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    
    
   
    public static void main(String[] args) throws IOException {
       TronServer.getInstance().start();
    }
}
