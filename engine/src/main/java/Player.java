
public class Player {
		
	private static final double POINTS_PER_FRAME = 0.1666667;

	private int score;	
	
	
	public int getScore() {
		return score;
	}
	
	public void updateScore(int deathTime) {
		score += calculatePointForTheRound(deathTime);
	}
		
	private int calculatePointForTheRound(int deathTime) {
		return (int) (deathTime * POINTS_PER_FRAME);
	}

}
