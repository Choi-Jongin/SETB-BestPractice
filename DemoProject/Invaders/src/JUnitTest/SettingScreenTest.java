package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.IGameState.Difficult;
import screen.SettingScreen;

public class SettingScreenTest {
	
	/** Width of current screen. */
	private static final int WIDTH = 448;
	/** Height of current screen. */
	private static final int HEIGHT = 520;
	/** Max fps of current screen. */
	private static final int FPS = 60;
	private static Difficult difficult = Difficult.NORMAL;
	
	@Test
	public void testSettingScreen() {
		SettingScreen settingScreen = new SettingScreen(WIDTH, HEIGHT, FPS, difficult);
		assertSame(SettingScreen.class, settingScreen.getClass());
	}

	@Test
	public void testGetDifficult() {
		SettingScreen settingScreen = new SettingScreen(WIDTH, HEIGHT, FPS, difficult);
		assertEquals(Difficult.NORMAL, settingScreen.getDifficult());
	}

}
