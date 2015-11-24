package pl.edu.agh.model;

import pl.edu.agh.util.BoardSizeException;
import pl.edu.agh.util.EnumIdOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements Runnable {
    private Board board;
    private List<Player> playersList;

    public static void main(String[] args) {
        Game game = new Game();
        game.playersList = new ArrayList<>();
        game.run();
    }

    public void run() {
        int width = 20;
        int height = 20;
        try {
            board = new Board(width, height);
        } catch (BoardSizeException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < 5; i++)
            playersList.add(new Player(i));

        Random random = new Random();

        for (Player p : playersList) {
            p.setX(random.nextInt(width));
            p.setY(random.nextInt(height));
            try {
                p.setDirection(Player.Direction.parse(random.nextInt(4)));
                board.setPlayerPosition(p.getX(), p.getY(), p.getId());
            } catch (EnumIdOutOfBoundsException | BoardSizeException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            try {
                for (Player p : playersList) {
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
                        board.setPlayerPosition(p.getX(), p.getY(), p.getId());
                    } catch (BoardSizeException e) {
                        e.printStackTrace();
                    }
                }
                board.drawBoard();

                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void calculateGameTick() {

    }
}
