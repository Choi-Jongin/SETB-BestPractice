package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.GameServerClient;

public class GameServerClientTest {

	@Test
	public void testGameServerClient() {
		GameServerClient gameServerClient = new GameServerClient();
		assertSame(GameServerClient.class, gameServerClient.getClass());
	}

	@Test
	public void testIsConnectAndSetConnect() {
		GameServerClient gameServerClient = new GameServerClient();
		assertFalse(gameServerClient.isConnect());
		gameServerClient.setConnect(true);
		assertTrue(gameServerClient.isConnect());
	}

	@Test
	public void testGetInstance() {
		assertSame(GameServerClient.class, GameServerClient.getInstance().getClass());
	}

}
