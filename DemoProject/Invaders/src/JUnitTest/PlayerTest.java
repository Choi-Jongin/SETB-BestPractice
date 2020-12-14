package JUnitTest;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import engine.GameSettings;
import engine.GameState;
import engine.DrawManager.SpriteType;
import engine.IGameState.Difficult;
import entity.Player;
import entity.Ship;
import screen.GameScreen;
import screen.Screen;

public class PlayerTest {
	/** Width of current screen. */
	private static final int WIDTH = 448;
	/** Height of current screen. */
	private static final int HEIGHT = 520;
	/** Max fps of current screen. */
	private static final int FPS = 60;
	int[] inputs = {1,2,3};

	@Test
	public void testPlayer() {
		Player player = new Player();
		assertSame(Player.class, player.getClass());
	}

	@Test
	public void testPlayerStringIntIntIntArray() {
		
		Player player = new Player("Son", 150, 2, inputs);
		assertSame(Player.class, player.getClass());
	}

	@Test
	public void testPlayerStringIntIntIntArrayBoolean() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertSame(Player.class, player.getClass());
	}

	@Test
	public void testPlayerColor() {
		assertEquals(Color.GREEN, Player.PlayerColor(0));
		assertEquals(Color.CYAN, Player.PlayerColor(1));
		assertEquals(Color.ORANGE, Player.PlayerColor(2));
		assertSame(Color.class, Player.PlayerColor(3).getClass()); //random color
		
	}

	@Test
	public void testIsClient() {
		Player noClientPlayer = new Player("Son", 150, 2, inputs);
		assertFalse(noClientPlayer.isClient());
		Player clientPlayer = new Player("Son", 150, 2, inputs, true);
		assertTrue(clientPlayer.isClient());
	}

	@Test
	public void testGetNameAndSetName() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals("Son", player.getName());
		player.setName("Park");
		assertEquals("Park", player.getName());
	}
	@Test
	public void testInit() {
		Player player = new Player("Son", 150, 2, inputs, true);
		Ship newShip = new Ship(100, 100);
		int newScore = 450;
		int newLives = 1;
		int[] newInputs = {5,3,7};
		player.Init(newShip, newScore, newLives, newInputs);
		assertEquals(newShip, player.getShip());
		assertEquals(newScore, player.getScore());
		assertEquals(newLives, player.getLives());
		assertEquals(newInputs, player.getInputs());
	}

	@Test
	public void testUpdate() {
		Player player = new Player("Son", 150, 2, inputs, true);
		player.setShip(new Ship(100,100));
		assertEquals(SpriteType.Ship, player.getShip().getSpriteType());
		player.getShip().destroy();
		player.update();
		assertEquals(SpriteType.ShipDestroyed, player.getShip().getSpriteType());
	}


	@Test
	public void testAddScore() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals(150, player.getScore());
		player.addScore(50);
		assertEquals(150+50, player.getScore());
	}

	@Test
	public void testAddlive() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals(2, player.getLives());
		player.addlive(3);
		assertEquals(2+3, player.getLives());
	}

	@Test
	public void testSubtractlive() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals(2, player.getLives());
		assertFalse(player.isDie());
		player.subtractlive(5);
		assertEquals(-3, player.getLives());
		assertTrue(player.isDie());
	}

	@Test
	public void testAddbulletsShot() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals(0, player.getBulletsShot());
		player.addbulletsShot(500);
		assertEquals(500, player.getBulletsShot());
	}

	@Test
	public void testAddshipsDestroyed() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals(0, player.getShipsDestroyed());
		player.addshipsDestroyed(40);
		assertEquals(40, player.getShipsDestroyed());
	}

	@Test
	public void testGetShipAndSetShip() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertNull(player.getShip());
		player.setShip(new Ship(100, 100));
		assertSame(Ship.class, player.getShip().getClass());
	}

	@Test
	public void testGetScoreAndSetScore() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals(150, player.getScore());
		player.setScore(777);
		assertEquals(777, player.getScore());
	}

	@Test
	public void testGetLivesAndSetLives() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals(2, player.getLives());
		player.setLives(1);
		assertEquals(1, player.getLives());
	}

	@Test
	public void testGetBulletsShotAndSetBulletsShot() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals(0, player.getBulletsShot()); // default bulletsShot = 0
		player.setBulletsShot(50);
		assertEquals(50, player.getBulletsShot());
	}

	@Test
	public void testGetShipsDestroyedAndSetShipsDestroyed() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals(0, player.getShipsDestroyed()); // default shipsDestroyed = 0
		player.setShipsDestroyed(10);
		assertEquals(10, player.getShipsDestroyed());
	}

	@Test
	public void testGetInputsAndSetInputs() {
		int[] inputs = {1,2,3};
		Player player = new Player("Son", 150, 2, inputs, true);
		assertEquals(inputs, player.getInputs());
		int[] newInputs = {7,8,9,10};
		player.setInputs(newInputs);
		assertEquals(newInputs, player.getInputs());
	}

	@Test
	public void testDieMethods() {
		Player player = new Player("Son", 150, 2, inputs, true);
		assertFalse(player.isDie());
		player.Die();
		assertTrue(player.isDie());
		player.setDie(false);
		assertFalse(player.isDie());
	}
}
