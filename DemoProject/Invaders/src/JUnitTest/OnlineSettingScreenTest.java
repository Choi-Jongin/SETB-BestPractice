package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import screen.OnlineSettingScreen;

public class OnlineSettingScreenTest {
	private static final int WIDTH = 448;
	private static final int HEIGHT = 520;
	private static final int FPS = 60;
	
	@Test
	public void testOnlineSettingScreen() {
		OnlineSettingScreen onlineSettingScreen = new OnlineSettingScreen(WIDTH, HEIGHT, FPS);
		assertSame(OnlineSettingScreen.class, onlineSettingScreen.getClass());
	}

	@Test
	public void testGetSelectitem() {
		OnlineSettingScreen onlineSettingScreen = new OnlineSettingScreen(WIDTH, HEIGHT, FPS);
		assertEquals(0, onlineSettingScreen.getSelectitem()); //default selectitem = 0
	}

}
