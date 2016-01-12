package pl.edu.agh.model;

import pl.edu.agh.util.BoardSizeException;
import pl.edu.agh.util.EnumIdOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pl.edu.agh.model.game.event.GameEndEvent;
import pl.edu.agh.model.game.event.PlayerDiedEvent;
import pl.edu.agh.model.game.event.PlayerPositionChangedEvent;

public class Game implements Runnable {
    private Board board;
    private IGameEventHandler eventHandler;
    private final List<Player> playersList;
    /**
     *  Częstotliwość przetwarzania pętli głównej gry wyrażona w liczbie
     *  pętli na sekunde
     */
    private int frequency = 10;

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }

    public Game() {
       playersList = new ArrayList<>();
    }

    public List<Player> getPlayersList() {
        return playersList;
    }
    
    
    

    public void setEventHandler(IGameEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public IGameEventHandler getEventHandler() {
        return eventHandler;
    }
    
    public void onPlayerDied( Player p ) {
        p.setTimeOfDeath(0);
        synchronized(playersList) {
            playersList.remove(p);
        }
        firePlayerDied(p);
    }
    
    
    public Game(List<Player> playersList) {
        // FIX: Nie powinnismy uwspólniać listy graczy w pokoju z graczami w grze
        // bo jak ktos przyjdzie do pokoju po rozpoczęcu gry
        // to NIE może dołączyć do gry
        this.playersList = new ArrayList(playersList);
        
        // FIX: Z punktu widzenia wieloœatkowosci czytelniej będzie jak 
        // inicjalizcja czyli funkcj amoyfikujaca stan obiektu będzie
        // wykoanna w dedeykowanym do tego wątku a nie na zewnątrz
        //initGame();
    }

    private void initGame() {
         int width = 2000;
        int height = 2000;
        try {
            board = new Board(width, height, playersList);
        } catch (BoardSizeException e) {
            e.printStackTrace();
        }

        Random random = new Random();

        for (Player p : playersList) {
            p.setX(random.nextInt(width));
            p.setY(random.nextInt(height));
            try {
                p.setDirection(Player.Direction.parse(random.nextInt(4)));
                p.setVx(0);
                p.setVy(0);
                if(p.getDirection() == Player.Direction.N) {
                    p.setVy(1);
                } else if(p.getDirection() == Player.Direction.S) {
                    p.setVy(-1);
                } else if(p.getDirection() == Player.Direction.E) {
                    p.setVx(1);
                } else if(p.getDirection() == Player.Direction.W) {
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
        initGame();
        while (!Thread.interrupted()) {
            // Zawiadamiam wszystkie wątki  które czekały by na zakończenie
            // inicjalizacji Gry
             synchronized(this) {
                notifyAll();
            }
            try {
                List<Player> currentPlayers = null;
                synchronized(playersList) {
                    currentPlayers = new ArrayList<>(playersList);
                }
                for (Player p : currentPlayers ) {
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

                Thread.sleep(1000/frequency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        fireGameEvent(new GameEndEvent());
    }

    public synchronized void waitForGameToStart() throws InterruptedException {
        wait();
    }
    
    public void killPlayer( Player p ) {
        if( p == null )  {
            throw new NullPointerException();
        }
        synchronized(playersList) {
            playersList.remove(p);
        }
    }
    
    
    public void calculateGameTick() {

    }
    
    protected void fireGameEvent(GameEvent g) {
        if( eventHandler != null ) eventHandler.handleEvent(g);
    }
    protected void firePlayerPositionChanged(Player p,int x,int y) {
        fireGameEvent(new PlayerPositionChangedEvent(p, x, y));
    }
    protected void firePlayerDied(Player p) {
        fireGameEvent(new PlayerDiedEvent(p));
    }
    
}
