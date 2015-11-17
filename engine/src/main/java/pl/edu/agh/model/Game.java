package pl.edu.agh.model;

import java.util.List;

/**
 * Created by mkostrzewa on 2015-11-17.
 */
public class Game implements Runnable{
    private Board board;
    private List<Player> playersList;

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }

    public void run() {
        Game game = new Game();
        Player player1 = new Player();
        Board board = new Board();
        int startXPosition = 1;
        int startYPosition = 1;
        player1.setX(startXPosition);
        player1.setY(startYPosition);
        //TODO sprawdzenie kierunku
        player1.setDirection(Player.Direction.S);
        board.displayOrCleanTabBoard(true);
        while(true) {
            try {
                board.tabBoard[player1.getX()][player1.getY()] = 1;
                player1.setX(++startXPosition);
                board.displayOrCleanTabBoard(false);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
