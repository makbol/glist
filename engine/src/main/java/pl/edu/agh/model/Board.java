package pl.edu.agh.model;

import java.util.List;
import java.util.UUID;

import pl.edu.agh.core.Colision;
import pl.edu.agh.util.BoardSizeException;

public class Board {
	private int timeofGame;

	private int height;
	private int width;
	private Colision colision;
	public char[][] tabBoard;

	public Board(int width, int height, List<Player> playerList) throws BoardSizeException {
		super();
		colision = new Colision();
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
				if (i == width - 1)
					System.out.println();
			}
		}
	}

	public void setPlayerPosition(int x, int y, UUID playerId) throws BoardSizeException {
		if (colision.detectColision(this, x, y)) {
			Player player = null;
			// int timeOfDeath = player.getTimeOfDeath();
			System.out.println("KOLIZJA");

		} else {
			tabBoard[x][y] = playerId.toString().charAt(0);
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}
