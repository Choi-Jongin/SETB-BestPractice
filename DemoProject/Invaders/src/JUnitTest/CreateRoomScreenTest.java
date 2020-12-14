package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import screen.CreateRoomScreen;
import screen.RoomPacket;

public class CreateRoomScreenTest {
	
	private static final int WIDTH = 448;
	private static final int HEIGHT = 520;
	private static final int FPS = 60;
	
	@Test
	public void testCreateRoomScreen() {
		CreateRoomScreen createRoomScreen = new CreateRoomScreen(WIDTH, HEIGHT, FPS);
		assertSame(CreateRoomScreen.class, createRoomScreen.getClass());
	}
	
	@Test
	public void testGetMyname() {
		CreateRoomScreen createRoomScreen = new CreateRoomScreen(WIDTH, HEIGHT, FPS);
		assertEquals("Player1", createRoomScreen.getMyname());
	}

	@Test
	public void testGetRoomPacket() {
		CreateRoomScreen createRoomScreen = new CreateRoomScreen(WIDTH, HEIGHT, FPS);
		assertSame(RoomPacket.class, createRoomScreen.getRoomPacket().getClass());
	}

}
