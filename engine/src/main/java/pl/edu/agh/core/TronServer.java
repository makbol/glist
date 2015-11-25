package pl.edu.agh.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import pl.edu.agh.commands.JoinGameCommand;
import pl.edu.agh.commands.KillServerCommand;
import pl.edu.agh.model.Player;

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

    private Room room;
    private final Map<InetSocketAddress,ClientEntry> clientRegister;
    private final List<ICommandExecutedHandler> commandHandlers;

    private TronServer( int port ) throws IOException {
      super(new InetSocketAddress(port));
      clientRegister = Collections.synchronizedMap(new HashMap<>());
      commandHandlers = new ArrayList();
      commandHandlers.add(this::onJoinExecuted);
      commandHandlers.add(this::onKillExecuted);
      room = createRoom();
    }
    private Room createRoom() {
        Room room = new Room();
        return room;
    }

    protected Player getPlayer(WebSocket socket ) {
        ClientEntry entry = clientRegister.get(socket.getRemoteSocketAddress());
        if( entry == null ) return null;
        return entry.getPlayer();
    }
    protected void acceptPlayer( Player player, WebSocket socket ) {
        l.debug(()->{
            return Log.message("player accepted: %s", player.toDebugString());
        });
        clientRegister.put(socket.getRemoteSocketAddress(), new ClientEntry(player, socket));
    }
    protected void forgetPlayer( WebSocket  socket ) {
        l.debug(()->{ 
            return Log.message("player left %s",
                                getPlayer(socket)
                                        .toDebugString());
        });
        clientRegister.remove(socket.getRemoteSocketAddress());
    }
    
    protected void brodcastMessage( String message ) {
        broadcastMessage((p)->{return message;});
    }
    protected void broadcastMessage( Function<Player,String> messageFunction ) {
        Collection<WebSocket> connections = connections();
        synchronized(connections) {
            for( WebSocket ws : connections ) {
                Player p = getPlayer(ws);
                if( p == null ) {
                    // Możliwe jeżeli rozłączyło klienta w trakcie broadcastowania
                    // Wystarczy zignorować
                    continue;
                }
                String message = messageFunction.apply(p);
                if( message != null ) {
                    ws.send(message);
                }
                
            }
        }
    }
    
    protected boolean onKillExecuted( WebSocket socket, Player player, BaseCommand command ) {
        if( command instanceof KillServerCommand ) {
            try {
                stop(1000);
            }catch(Exception e) {
                throw new IllegalStateException("Unable to stop server",e);
            }
            return true;
       }
       return false;
    }
    protected boolean onJoinExecuted( WebSocket socket, Player player, BaseCommand command ) {
       if( command instanceof JoinGameCommand ) {
            JoinGameCommand jgc = (JoinGameCommand)command;
            player = jgc.getAddedPlayer();
            acceptPlayer(player, socket);
            return true;
       }
       return false;
    }
    protected boolean onBroadcastExecuted( WebSocket socket, Player player, BaseCommand command ) {
        if(  command instanceof BroadcastCommand ) {
            BroadcastCommand cmd = (BroadcastCommand)command;
            broadcastMessage(cmd::getResponseFor);
        }
        return false;
    }
    /**
     * Obsługuje commendę
     * @param socket socket przez który jest połączony gracz player
     * @param player gracz kóry wykonuje polecenie
     * @param command polecenie do wykonania
     * @return komenda dowykonania przez serwer
     */
    protected String onCommandExecuted( WebSocket socket, Player player, BaseCommand command ) {
       if( command.wasSuccessful() ) {
           for( ICommandExecutedHandler handler : commandHandlers ) {
               if(handler.onCommandExecuted(socket, player, command)){
                   break;
               }
           }
           return command.getResultResponse();
       } else {
           return command.getErrorResponse();
       }
       
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
      String[] request = message.split(",");
      Player player = getPlayer(conn);
      BaseCommand command = BaseCommand.getCommand(request);

      if( command != null ) {
        command = room.executeCommand(command, player);
        if( command != null ) {
            String result = onCommandExecuted(conn, player, command);
            if( result != null ) {
                conn.send(result);
            }
        } else {
            conn.send(BaseCommand.createRoomConsumed(message));
        }
      } else {
          conn.send(BaseCommand.createNoSuchCommand(request[0]));
      }
      
    }
    

    @Override
    public void onError(WebSocket ws, Exception excptn) {
      
      if( ws == null ) {
           l.warn("Server communication error",excptn);
      } else {
          l.warn(()-> {
               Player p = getPlayer(ws);
               if( p == null ) {
                   return Log.message("Guest expiriened problem",excptn);
               } else {
                   return Log.message("Player %s expeirenced problem",excptn, p.getUserId());
               }
          });
          
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
