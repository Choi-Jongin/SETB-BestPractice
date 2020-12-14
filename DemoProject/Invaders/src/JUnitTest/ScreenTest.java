package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import screen.Screen;

public class ScreenTest {

	/** Width of current screen. */
	private static final int WIDTH = 448;
	/** Height of current screen. */
	private static final int HEIGHT = 520;
	/** Max fps of current screen. */
	private static final int FPS = 60;
	
	@Test
	public void testScreen() {
		Screen screen = new Screen(WIDTH, HEIGHT, FPS);
		assertSame(Screen.class, screen.getClass());
	}

	@Test
	public void testGetWidth() {
		Screen screen = new Screen(WIDTH, HEIGHT, FPS);
		assertEquals(WIDTH, screen.getWidth());
	}

	@Test
	public void testGetHeight() {
		Screen screen = new Screen(WIDTH, HEIGHT, FPS);
		assertEquals(HEIGHT, screen.getHeight());
	}

}
