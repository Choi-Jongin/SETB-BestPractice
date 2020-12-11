package engine;

/**
 * Implements an object that stores the state of the game between levels.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameState {

	/** Current game level. */
	private int level;
	/** Current score. */
	private int score;
	private int score2;
	/** Lives currently remaining. */
	private int livesRemaining;
	private int livesRemaining2;
	/** Bullets shot until now. */
	private int bulletsShot;
	private int bulletsShot2;
	/** Ships destroyed until now. */
	private int shipsDestroyed;
	private int shipsDestroyed2;

	/**
	 * Constructor.
	 * 
	 * @param level
	 *            Current game level.
	 * @param score
	 *            Current score.
	 * @param livesRemaining
	 *            Lives currently remaining.
	 * @param bulletsShot
	 *            Bullets shot until now.
	 * @param shipsDestroyed
	 *            Ships destroyed until now.
	 */
	public GameState(final int level, final int score, final int score2,
			final int livesRemaining, final int livesRemaining2,
			final int bulletsShot, final int bulletsShot2,
			final int shipsDestroyed, final int shipsDestroyed2) {
		this.level = level;
		this.score = score;
		this.livesRemaining = livesRemaining;
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
		this.score2 = score2;
		this.livesRemaining2 = livesRemaining2;
		this.bulletsShot2 = bulletsShot2;
		this.shipsDestroyed2 = shipsDestroyed2;
	}

	/**
	 * @return the level
	 */
	public final int getLevel() {
		return level;
	}

	/**
	 * @return the score
	 */
	public final int getScore() {
		return score;
	}
	
	public final int getScore2() {
		return score2;
	}

	/**
	 * @return the livesRemaining
	 */
	public final int getLivesRemaining() {
		return livesRemaining;
	}
	
	public final int getLivesRemaining2() {
		return livesRemaining2;
	}

	/**
	 * @return the bulletsShot
	 */
	public final int getBulletsShot() {
		return bulletsShot;
	}

	public final int getBulletsShot2() {
		return bulletsShot2;
	}
	
	/**
	 * @return the shipsDestroyed
	 */
	public final int getShipsDestroyed() {
		return shipsDestroyed;
	}
	
	public final int getShipsDestroyed2() {
		return shipsDestroyed2;
	}

}
