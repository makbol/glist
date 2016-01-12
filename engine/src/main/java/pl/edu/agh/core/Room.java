package pl.edu.agh.core;

import com.google.gson.GsonBuilder;
import pl.edu.agh.model.Game;
import pl.edu.agh.model.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.model.GameEvent;
import pl.edu.agh.model.IGameEventHandler;

/**
 * Klasa pokoju na serwerze.
 */
public class Room implements IGameEventHandler {

     private static Logger l = LogManager.getLogger(TronServer.class);
    
    /** Numer pokoju */
    private static long roomCounter = 0;

    /** Licznik pokoi */
    private final long roomNo = ++roomCounter;

    /** Uczestnicy w pokoju */
    private List<Player> players = new ArrayList<>();

    /** Gra jesli jest rozpoczeta. */
    private Game game = null;

    /** Wątek przetwarzajacy logikę gry.  */
    private Thread gameWorker;
    
    /** Lista obiektów nasłuchujących zdarzeń z gry  */
    private final List<IGameEventHandler> gameEventListeners;
    
    public Room() {
        gameEventListeners = new ArrayList(1); // Naraize tylko TronServer słucha
    }
    
    //<editor-fold defaultstate="collapsed" desc="Accessors">
    public long getRoomNo() {
        return roomNo;
    }
    public Game getGame() {
        return game;
    }
//</editor-fold>
    
    
    /**
     * Wywolanie komendy w danym pokoju. Logika zalezna od komendy.
     * @param command komenda do wylowania
     * @param player uzytkownik wolajacy komende (null jesli komenda dla wszystkich userow)
     * @return zwraca komende po wywolaniu
     */
    public <T extends BaseCommand> BaseCommand executeCommand(T command, Player player) {
        command.execute(this, player);
        if(player != null) {
            player.setLastCommandDate(new Date());
        }
        return command;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Player Management">
    /**
     * Dołącza nowego gracza do pokoju.
     * UWaga ignoruje null argument
     * @param p
     */
    public void registerPlayer( Player p ) {
        if( p == null ) return;
        
        players.add(p);
    }
    /**
     * Usunięcie gracza z powodu rozłączaenia.
     * Powoduje usuniecie gracza z pokoju oraz jego natychmiastowe zabice
     * w grze jezeli takowa istnieje. Przewidzaine na okoliczność nagłego rozłączenia
     */
    public void removePlayer( Player p ) {
        if( p == null ) return;
        if( isGameRunning() ) {
            game.killPlayer(p);
        }
        players.remove(p);
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GameManagement">
    public void startNewGame() throws RoomException {
        if( isGameRunning() ) {
            throw new RoomException(RoomException.CAUSE.GAME_RUNNING);
        }
        if( players.size() < 2 ) {
            throw new RoomException(RoomException.CAUSE.INSUFFICENT_PLAYERS);
        }
        
        game = new Game(players);
        game.setEventHandler(this);
        gameWorker = new Thread(game);
        gameWorker.setName("GameWorker");
        gameWorker.setDaemon(true);
        gameWorker.start();
    }
    
    public void waitForGameInit() throws RoomException {
        try{
            game.waitForGameToStart();
        }catch( InterruptedException ie ) {
            throw new RoomException(RoomException.CAUSE.WAIT_INTERRUPTED);
        }
    }
    
    public boolean isGameRunning() {
        return game!=null && gameWorker.isAlive();
    }
    
    public List<Player> getGamePlayer() {
        return game.getPlayersList();
    }
   
    public void endGame() {
        gameWorker.interrupt();
        game = null;
        l.info("GameEnd");
    }
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GameEventHandling">
    public void registerGameEventHandler( IGameEventHandler geh ) {
        synchronized(gameEventListeners) {
            gameEventListeners.add(geh);
        }
    }
    public void unregisterGameEventHandler( IGameEventHandler geh ) {
        synchronized(gameEventListeners) {
            gameEventListeners.remove(geh);
        }
    }
    @Override
    public void handleEvent(GameEvent e) {
        synchronized(gameEventListeners) {
            for( IGameEventHandler geh : gameEventListeners ) {
                geh.handleEvent(e);
            }
        }
    }
//</editor-fold>
    
    
    
}
