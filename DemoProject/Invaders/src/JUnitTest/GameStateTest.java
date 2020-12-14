package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.GameState;

public class GameStateTest {
	private int level = 1;
	private int score = 100;
	private int livesRemaining = 2;
	private int bulletsShot = 6;
	private int shipsDestroyed = 3;

	@Test
	public void testGameState() {
		GameState gameState = new GameState(level, score, livesRemaining, bulletsShot, shipsDestroyed);
		assertSame(GameState.class, gameState.getClass());
	}

	@Test
	public void testGetLevel() {
		GameState gameState = new GameState(level, score, livesRemaining, bulletsShot, shipsDestroyed);
		assertEquals(1, gameState.getLevel());
	}

	@Test
	public void testGetScore() {
		GameState gameState = new GameState(level, score, livesRemaining, bulletsShot, shipsDestroyed);
		assertEquals(100, gameState.getScore());
	}

	@Test
	public void testGetLivesRemaining() {
		GameState gameState = new GameState(level, score, livesRemaining, bulletsShot, shipsDestroyed);
		assertEquals(2, gameState.getLivesRemaining());
	}

	@Test
	public void testGetBulletsShot() {
		GameState gameState = new GameState(level, score, livesRemaining, bulletsShot, shipsDestroyed);
		assertEquals(6, gameState.getBulletsShot());
	}

	@Test
	public void testGetShipsDestroyed() {
		GameState gameState = new GameState(level, score, livesRemaining, bulletsShot, shipsDestroyed);
		assertEquals(3, gameState.getShipsDestroyed());
	}

}
