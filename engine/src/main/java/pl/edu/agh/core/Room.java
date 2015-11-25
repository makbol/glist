package pl.edu.agh.core;

import pl.edu.agh.model.Game;
import pl.edu.agh.model.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasa pokoju na serwerze.
 */
public class Room {

    /** Numer pokoju */
    private static long roomCounter = 0;

    /** Licznik pokoi */
    private final long roomNo = ++roomCounter;

    /** Uczestnicy w pokoju */
    private List<Player> players = new ArrayList<>();

    /** Gra jesli jest rozpoczeta. */
    private Game game = null;

    public void startNewGame() {
        game = new Game(players);
    }

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        if( player == null ) {
            throw new NullPointerException("player must be not null");
        } else {
            this.players.add(player);
        }
    }

    public long getRoomNo() {
        return roomNo;
    }

    public Game getGame() {
        return game;
    }
}
