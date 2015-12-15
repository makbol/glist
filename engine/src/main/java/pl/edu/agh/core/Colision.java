package pl.edu.agh.core;

import pl.edu.agh.model.Board;

public class Colision {

    private int xMin = 0;
    private int yMin = 0;
    private int xMax;
    private int yMax;

    public boolean detectColision(Board board, int xPlayerPosition, int yPlayerPosition) {
        xMax = board.getWidth();
        yMax = board.getHeight();

        if (xPlayerPosition >= xMax || xPlayerPosition < xMin || yPlayerPosition >= yMax || yPlayerPosition < yMin) {
            return true;
        }
        int value = board.tabBoard[xPlayerPosition][yPlayerPosition];
        return (value > 1) ? true : false;
    }

}
