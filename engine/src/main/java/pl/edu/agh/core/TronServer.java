package pl.edu.agh.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import pl.edu.agh.commands.JoinGameCommand;

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
    private final Map<InetSocketAddress,ClientEntry> clientRegister;

    private TronServer( int port ) throws IOException {
      super(new InetSocketAddress(port));
      clientRegister = Collections.synchronizedMap(new HashMap<>());
    }

    protected Player getPlayer( WebSocket socket ) {
        ClientEntry entry = clientRegister.get(socket.getRemoteSocketAddress());
        if( entry == null ) return null;
        return entry.getPlayer();
    }
    protected void acceptPlayer( Player player, WebSocket socket ) {
        l.debug("accepting player {1}",player.getUsername());
        clientRegister.put(socket.getRemoteSocketAddress(), new ClientEntry(player, socket));
    }
    protected void forgetPlayer( WebSocket  socket ) {
        if(l.isDebugEnabled()) {
            Player p = getPlayer(socket);
            l.debug("player left: "+p.getUsername());
        }
        
        clientRegister.remove(socket.getRemoteSocketAddress());
    }
    
    public Room getRoom() {
        return room;
    }

    @Override
    public void onOpen(WebSocket ws, ClientHandshake ch) {
        ws.send(new StringBuilder("Welcome to TronServer(")
                .append(getAddress().toString())
                .append(")\nIssue your Commands!")
                .toString());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        try {
          String[] request = message.split(",");
          BaseCommand command = BaseCommand.getCommand(request);
          Player player = getPlayer(conn);
          if( player == null ) {
              if( command instanceof JoinGameCommand ) {
                   room.executeCommand(command, player);  
                   player = ((JoinGameCommand)command).getAddedPlayer();
                   if( command.wasSuccessful() ) {
                       acceptPlayer(player, conn);
                   }
              } else {
                  conn.send(BaseCommand.createNoPlayerResponse());
                  return;
              }
             
          } else {
              room.executeCommand(command, player);
          }
          if( command.wasSuccessful() ) {
              conn.send(command.getResultResponse());
          } else {
              conn.send(command.getErrorResponse());
          }
        }catch(Exception npe) {
            npe.printStackTrace();
        }
    }
    

    @Override
    public void onError(WebSocket ws, Exception excptn) {
       try {
      if( ws == null ) {
           l.warn("Server communication error",
                excptn);
      } else {
          Player p = getPlayer(ws);
          l.warn(new StringBuilder("Player: ")
                    .append(p.getUsername())
                    .append(" expeirenced problem")
                    .toString(),
                excptn);
      }
       }catch(Exception e) {
           e.printStackTrace();
       }
    }
    
    @Override
    public void onClose(WebSocket ws, int i, String string, boolean bln) {
        forgetPlayer(ws);
    }
    
   
    public static void main(String[] args) throws IOException {
       TronServer.getInstance().start();
    }  
    
    private static class ClientEntry {
        private final Player player;
        private final WebSocket socket;

        public ClientEntry(Player player, WebSocket socket) {
            this.player = player;
            this.socket = socket;
        }

        public Player getPlayer() {
            return player;
        }

        public WebSocket getSocket() {
            return socket;
        }
    }
    
}
