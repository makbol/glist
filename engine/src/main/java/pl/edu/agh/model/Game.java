package pl.edu.agh.model;

import pl.edu.agh.model.game.event.GameEndEvent;
import pl.edu.agh.model.game.event.PlayerDiedEvent;
import pl.edu.agh.model.game.event.PlayerPositionChangedEvent;
import pl.edu.agh.util.BoardSizeException;
import pl.edu.agh.util.EnumIdOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements Runnable {
    public static final int DEFAULT_WIDTH = 2000;
    public static final int DEFAULT_HEIGHT = 2000;
    private final List<Player> playersList;
    private Board board;

    private IGameEventHandler eventHandler;
    /**
     * Częstotliwość przetwarzania pętli głównej gry wyrażona w liczbie
     * pętli na sekunde
     */
    private int frequency = 1;
    private int roundNumber = 1;

    public Game() {
        playersList = new ArrayList<>();
    }

    public Game(List<Player> playersList) {
        this.playersList = playersList;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }

    public IGameEventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(IGameEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void onPlayerDied(Player p) {
        p.setTimeOfDeath(0);
        synchronized (playersList) {
            playersList.remove(p);
        }
        firePlayerDied(p);
    }

    public void preparePlayerForAnotherGreatRoundOfOurSuperbInteractiveScalableMultiplayerSuperfragaliciousGameOfTron() {
        Random random = new Random();

        for (Player p : playersList) {
            p.setX(random.nextInt(DEFAULT_WIDTH));
            p.setY(random.nextInt(DEFAULT_HEIGHT));
            try {
                p.setDirection(Player.Direction.parse(random.nextInt(4)));
                p.setVx(0);
                p.setVy(0);
                if (p.getDirection() == Player.Direction.N) {
                    p.setVy(1);
                } else if (p.getDirection() == Player.Direction.S) {
                    p.setVy(-1);
                } else if (p.getDirection() == Player.Direction.E) {
                    p.setVx(1);
                } else if (p.getDirection() == Player.Direction.W) {
                    p.setVx(-1);
                }
                board.setPlayerPosition(p.getX(), p.getY(), p.getUserId());
            } catch (EnumIdOutOfBoundsException | BoardSizeException e) {
                e.printStackTrace();
            }
            firePlayerPositionChanged(p, p.getX(), p.getY());
        }
    }

    public void run() {
        try {
            board = new Board(DEFAULT_WIDTH, DEFAULT_HEIGHT, playersList);
        } catch (BoardSizeException e) {
            e.printStackTrace();
        }

        preparePlayerForAnotherGreatRoundOfOurSuperbInteractiveScalableMultiplayerSuperfragaliciousGameOfTron();

        while (!Thread.interrupted()) {
            try {
                List<Player> currentPlayers = null;

                synchronized (playersList) {
                    currentPlayers = new ArrayList<>(playersList);
                }

                if (currentPlayers.size() < 2) {
                    break;
                }

                for (Player p : currentPlayers) {
                    switch (p.getDirection()) {
                        case N:
                            p.setY(p.getY() + 1);
                            break;
                        case S:
                            p.setY(p.getY() - 1);
                            break;
                        case E:
                            p.setX(p.getX() + 1);
                            break;
                        case W:
                            p.setX(p.getX() - 1);
                            break;
                    }
                    try {
                        board.setPlayerPosition(p.getX(), p.getY(), p.getUserId());
                        firePlayerPositionChanged(p, p.getX(), p.getY());
                    } catch (BoardSizeException e) {
                        onPlayerDied(p);
                    }
                }
//                board.drawBoard();

                Thread.sleep(1000 / frequency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fireGameEvent(new GameEndEvent());
    }

    public void playerLeft(Player p) {
        synchronized (playersList) {
            playersList.remove(p);
        }
    }

    public void calculateGameTick() {

    }

    protected void fireGameEvent(GameEvent g) {
        if (eventHandler != null) eventHandler.handleEvent(g);
    }

    protected void firePlayerPositionChanged(Player p, int x, int y) {
        fireGameEvent(new PlayerPositionChangedEvent(p, x, y));
    }

    protected void firePlayerDied(Player p) {
        fireGameEvent(new PlayerDiedEvent(p));
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
