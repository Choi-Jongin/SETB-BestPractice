package JUnitTest;

import static engine.IGameState.Difficult.NORMAL;
import static org.junit.Assert.*;

import org.junit.Test;

import engine.GameSettings;
import engine.GameState;
import screen.GameScreen;

public class GameScreenTest {
	private static final int WIDTH = 448;
	private static final int HEIGHT = 520;
	private static final int FPS = 60;
	/** Max lives. */
	private static final int MAX_LIVES = 3;
	/** Levels between extra life. */
	private static final int EXTRA_LIFE_FRECUENCY = 3;
	private static final GameSettings gameSettings =
			new GameSettings(5, 4, 60, 2000);
	private static final GameState gameState = new GameState(1, NORMAL, 0, MAX_LIVES, 0, 0);
	private static final boolean bonusLife = gameState.getLevel()
							% EXTRA_LIFE_FRECUENCY == 0
							&& gameState.getLivesRemaining() < MAX_LIVES;

	@Test
	public void testGameScreen() {
		GameScreen gameScreen = new GameScreen(gameState, gameSettings, bonusLife, WIDTH, HEIGHT, FPS);
		assertSame(GameScreen.class, gameScreen.getClass());
	}

	@Test
	public void testGetGameState() {
		GameScreen gamescreen = new GameScreen(gameState, gameSettings, bonusLife, WIDTH, HEIGHT, FPS);
		GameState gamestate = gamescreen.getGameState();
		assertEquals(1,gamestate.getLevel());
		assertEquals(0,gamestate.getScore());
		assertEquals(MAX_LIVES,gamestate.getLivesRemaining());
		assertEquals(0,gamestate.getBulletsShot());
		assertEquals(0,gamestate.getShipsDestroyed());
	}

}
