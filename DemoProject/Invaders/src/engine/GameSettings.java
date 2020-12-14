package engine;

import java.io.Serializable;

/**
 * Implements an object that stores a single game's difficulty settings.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameSettings implements Serializable {

	/** Width of the level's enemy formation. */
	private int formationWidth;
	/** Height of the level's enemy formation. */
	private int formationHeight;
	/** Speed of the enemies, function of the remaining number. */
	private int baseSpeed;
	/** Frequency of enemy shootings, +/- 30%. */
	private int shootingFrecuency;


	private IGameState.Difficult difficult;

	/**
	 * Constructor.
	 * 
	 * @param formationWidth
	 *            Width of the level's enemy formation.
	 * @param formationHeight
	 *            Height of the level's enemy formation.
	 * @param baseSpeed
	 *            Speed of the enemies.
	 * @param shootingFrecuency
	 *            Frecuency of enemy shootings, +/- 30%.
	 */
	public GameSettings(final int formationWidth, final int formationHeight,
			final int baseSpeed, final int shootingFrecuency) {
		this.formationWidth = formationWidth;
		this.formationHeight = formationHeight;
		this.baseSpeed = baseSpeed;
		this.shootingFrecuency = shootingFrecuency;
		difficult = IGameState.Difficult.NORMAL;
	}

	/**
	 * @return the formationWidth
	 */
	public final int getFormationWidth() {
		return formationWidth;
	}

	/**
	 * @return the formationHeight
	 */
	public final int getFormationHeight() {
		return formationHeight;
	}

	/**
	 * @return the baseSpeed
	 */
	public final int getBaseSpeed() {
		return baseSpeed;
	}

	/**
	 * @return the shootingFrecuency
	 */
	public final int getShootingFrecuency() {
		return (int)(shootingFrecuency*getDifficultFreFrecuency());
	}

	private double getDifficultFreFrecuency(){
		switch (difficult){
			case EASY -> {
				return 1.5;
			}
			case NORMAL -> {
				return 1.0;
			}
			case HARD -> {
				return 0.6;
			}
		}
		return 1.0;
	}

	public IGameState.Difficult getDifficult() {
		return difficult;
	}

	public void setDifficult(IGameState.Difficult difficult) {
		this.difficult = difficult;
	}
}
