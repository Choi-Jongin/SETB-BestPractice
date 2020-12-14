package JUnitTest;

import static org.junit.Assert.*;

import java.net.InetAddress;

import org.junit.Test;

import engine.GameServer;

public class GameServerTest {
	

	@Test
	public void testGameServer() {
		GameServer gameServer = new GameServer();
		assertSame(GameServer.class, gameServer.getClass());
	}

	@Test
	public void testHasConn() {
		GameServer gameServer = new GameServer();
		assertFalse(gameServer.hasConn());
	}


	@Test
	public void testIsWaitingAndSetWaiting() {
		GameServer gameServer = new GameServer();
		assertFalse(gameServer.isWaiting());
		gameServer.setWaiting(true);
		assertTrue(gameServer.isWaiting());
	}

	@Test
	public void testGetInstance() {
		GameServer gameServer = new GameServer();
		assertSame(GameServer.class, gameServer.getInstance().getClass());
	}

	@Test
	public void testGetLocalip() throws Exception {
		GameServer gameServer = new GameServer();
		String localip = InetAddress.getLocalHost().getHostAddress();
		assertEquals(localip, gameServer.getLocalip());
	}

}
