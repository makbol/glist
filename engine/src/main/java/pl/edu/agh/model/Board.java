package pl.edu.agh.model;

public class Board {
    private int timeofGame;
    private int height = 20;
    private int width = 20;
    public int[][] tabBoard = new int[height][width];

    public void displayOrCleanTabBoard(boolean clean) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
