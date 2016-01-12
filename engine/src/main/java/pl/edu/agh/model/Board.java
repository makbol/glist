package pl.edu.agh.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pl.edu.agh.core.Colision;
import pl.edu.agh.util.BoardSizeException;

public class Board {
	private int timeofGame;

	private int height;
	private int width;
	private Colision colision;
	public char[][] tabBoard;

	private final Map<UUID, Player> players;

	public Board(int width, int height, List<Player> playerList) throws BoardSizeException {
		super();

		players = new HashMap<>();
		for (Player player : playerList) {
			players.put(player.getUserId(), player);
		}

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

	public void setPlayerPosition(int x, int y, UUID playerId, Game game) throws BoardSizeException {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			throw new BoardSizeException("Board size exeeded");
		}
		if (colision.detectColision(this, x, y)) {
			Player player = players.get(playerId);
			if (player == null || player.getTimeOfDeath() != null)
				return;
			int timeOfDeath = player.getTimeOfDeath();
			player.updateScore(timeOfDeath);
			game.killPlayer(player);

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
