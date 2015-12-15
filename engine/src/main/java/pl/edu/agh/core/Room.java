package pl.edu.agh.core;

import pl.edu.agh.model.Game;
import pl.edu.agh.model.IGameEventHandler;
import pl.edu.agh.model.Player;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasa pokoju na serwerze.
 */
public class Room {

    /**
     * Numer pokoju
     */
    private static long roomCounter = 0;

    /**
     * Licznik pokoi
     */
    private final long roomNo = ++roomCounter;

    /**
     * Uczestnicy w pokoju
     */
    private List<Player> players = new ArrayList<>();

    /**
     * Gra jesli jest rozpoczeta.
     */
    private Game game = null;

    /**
     * Wątek przetwarzajacy logikę gry.
     */
    private Thread gameWorker;

    private IGameEventHandler gameEventHandler;

    public void startNewGame() {
        if (isGameRunning()) {
            System.out.println("Game alredy running");
            throw new IllegalStateException("Game already running");
        }

        game = new Game(players);
        game.setEventHandler(gameEventHandler);
        gameWorker = new Thread(game);
        gameWorker.setName("GameWorker");
        gameWorker.setDaemon(true);
        gameWorker.start();
    }

    public boolean isGameRunning() {
        return game != null && gameWorker.isAlive();
    }

    public void registerGameEventHandler(IGameEventHandler gevh) {
        if (game != null) {
            game.setEventHandler(gevh);
        }
        throw new IllegalStateException("No game in room");
    }

    /**
     * Wywolanie komendy w danym pokoju. Logika zalezna od komendy.
     *
     * @param command komenda do wylowania
     * @param player  uzytkownik wolajacy komende (null jesli komenda dla wszystkich userow)
     * @return zwraca komende po wywolaniu
     */
    public <T extends BaseCommand> BaseCommand executeCommand(T command, Player player) {
        command.execute(this, player);
        if (player != null) {
            player.setLastCommandDate(new Date());
        }
        return command;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        if (player == null) {
            throw new NullPointerException("player must be not null");
        } else {
            this.players.add(player);
        }
    }

    public void playerLeft(Player p) {
        players.remove(p);
        if (game != null) {
            game.playerLeft(p);
        }
    }

    public long getRoomNo() {
        return roomNo;
    }

    public Game getGame() {
        return game;
    }

    public void endGame() {
        gameWorker.interrupt();
        game = null;
        if (permissionForNextRoundFromAllThePlayers()) {
            startNewGame();
        }
    }

    public boolean permissionForNextRoundFromAllThePlayers() {
        //TODO: zapytać :>
        return true;
    }


    public IGameEventHandler getGameEventHandler() {
        return gameEventHandler;
    }

    public void setGameEventHandler(IGameEventHandler gameEventHandler) {
        this.gameEventHandler = gameEventHandler;
    }

}
