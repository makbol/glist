package pl.edu.agh.core;

import java.util.List;

/**
 * Klasa gry
 */
public class Game {

    /** Lista uzytkownikow */
    private final List<Player> players;

    public Game(List<Player> players) {
        this.players = players;
        for(Player player : players) {
            player.setAlive(true);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }
}
