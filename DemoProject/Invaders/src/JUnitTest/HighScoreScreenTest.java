package JUnitTest;

import static org.junit.Assert.*;

import engine.IGameState;
import org.junit.Test;

import screen.HighScoreScreen;

public class HighScoreScreenTest {
	private static final int WIDTH = 448;
	private static final int HEIGHT = 520;
	private static final int FPS = 60;

	@Test
	public void testHighScoreScreen() {
		HighScoreScreen highscorescreen = new HighScoreScreen(WIDTH, HEIGHT, FPS, IGameState.Difficult.NORMAL);
		assertSame(HighScoreScreen.class, highscorescreen.getClass());
	}

}
