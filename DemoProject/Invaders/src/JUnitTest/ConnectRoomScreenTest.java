package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import screen.ConnectRoomScreen;

public class ConnectRoomScreenTest {
	
	private static final int WIDTH = 448;
	private static final int HEIGHT = 520;
	private static final int FPS = 60;
	
	@Test
	public void testConnectRoomScreen() {
		ConnectRoomScreen connectRoomScreen = new ConnectRoomScreen(WIDTH, HEIGHT, FPS);
		assertSame(ConnectRoomScreen.class, connectRoomScreen.getClass());
	}
	
	@Test
	public void testGetMyname() {
		ConnectRoomScreen connectRoomScreen = new ConnectRoomScreen(WIDTH, HEIGHT, FPS);
		assertEquals("Player2", connectRoomScreen.getMyname());
	}

}
