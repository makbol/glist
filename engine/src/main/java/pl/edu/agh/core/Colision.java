package pl.edu.agh.core;

import pl.edu.agh.model.Board;

public class Colision {

	private int xMin;
	private int yMin;
	private int xMax;
	private int yMax;

	public Colision() {
		xMin = 0;
		yMin = 0;
	}

	public boolean detectBoundColision(Board board, int xPos, int yPos) {
		//TODO nadpisac xMax i yMax
//		xMax = board.getWidth();
//		yMax = board.getHeight();

		if (xPos > xMax || xPos < xMin || yPos > yMax || yPos < yMin) {
			return true;
		}
		return false;
	}

	public boolean detectPlayerColision() {
		// int value = board.tabBoard[xPos][yPos];
		return false;
	}
}
