package JUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
	
	@Test
	public void testPause() {
		Screen screen = new Screen(WIDTH, HEIGHT, FPS);
		assertFalse(screen.isPause());
		screen.setPause(true);
		assertTrue(screen.isPause());
	}
}
