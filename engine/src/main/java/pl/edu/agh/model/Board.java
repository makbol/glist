package pl.edu.agh.model;

import pl.edu.agh.util.BoardSizeException;

import java.util.UUID;

public class Board {
    private int timeofGame;

    private int height;
    private int width;
    public char[][] tabBoard;

    public Board(int width, int height) throws BoardSizeException {
        super();
        if (width > 1 && height > 1) {
            this.width = width;
            this.height = height;
        } else {
            throw new BoardSizeException("Selected board size is stupid. Get your sh*t together.");
        }
        tabBoard = new char[height][width];
    }

    public void drawBoard() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(tabBoard[j][i] + " ");
                if ( i == width - 1)
                    System.out.println();
            }
        }
    }

    public void setPlayerPosition(int x, int y, UUID playerId) throws BoardSizeException {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new BoardSizeException("Player position can't be at [" + x + ", " + y + "]. Get your sh*t together.");
        }
        tabBoard[x][y] = playerId.toString().charAt(0);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
