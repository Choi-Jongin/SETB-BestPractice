package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.IGameState.Difficult;
import screen.HighScoreScreen;

public class HighScoreScreenTest {
	private static final int WIDTH = 448;
	private static final int HEIGHT = 520;
	private static final int FPS = 60;
	Difficult difficult = Difficult.NORMAL;
	
	@Test
	public void testHighScoreScreen() {
		HighScoreScreen highscorescreen = new HighScoreScreen(WIDTH, HEIGHT, FPS, difficult);
		assertSame(HighScoreScreen.class, highscorescreen.getClass());
	}

}
