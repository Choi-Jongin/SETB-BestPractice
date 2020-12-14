package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import screen.TitleScreen;

public class TitleScreenTest {
	/** Width of current screen. */
	private static final int WIDTH = 448;
	/** Height of current screen. */
	private static final int HEIGHT = 520;
	/** Max fps of current screen. */
	private static final int FPS = 60;

	@Test
	public void testTitleScreen() {
		TitleScreen titleScreen = new TitleScreen(WIDTH, HEIGHT, FPS);
		assertSame(TitleScreen.class, titleScreen.getClass());
	}

}
