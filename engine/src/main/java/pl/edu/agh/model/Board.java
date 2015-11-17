package pl.edu.agh.model;

import java.security.Timestamp;

/**
 * Created by mkostrzewa on 2015-11-17.
 */
public class Board {
    private Timestamp timeofGame;
    private int height = 20;
    private int width = 20;
    public int[][] tabBoard = new int[height][width];

    public void displayOrCleanTabBoard(boolean clean) {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (clean) {
                    tabBoard[i][j] = 0;
                } else {
                    System.out.print(tabBoard[j][i] + " ");
                }
            }
            if (!clean) System.out.println("");
        }

    }
}
