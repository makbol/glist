package pl.edu.agh.core;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
public class TronServer {

    private static Logger l = LogManager.getLogger(TronServer.class);
    private static final int PORT = 1666;
    
    private static TronServer instance;
    
    public static TronServer getInstance() {
        try {
         return instance==null?instance=new TronServer():instance;
        }catch( IOException e ) {
              l.warn("Port zajęty. Inny serwer?",e);
        }
        return null;
     }
    
    private final TronListener listener;
    private Room room  = new Room();

    private TronServer() throws IOException {
       this.listener = new TronListener(PORT);
    }

    public Room getRoom() {
        return room;
    }
    
    public void start() {
        listener.start();
    }
   
    public static void main(String[] args) throws IOException {
        TronServer.getInstance().start();
    }
}
