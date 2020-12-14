package JUnitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import engine.CustomGameState;
import engine.GameSettings;
import engine.CustomGameState.MultiMethod;
import engine.IGameState.Difficult;
import entity.Player;
import screen.CustomGameScreen;

public class CustomGameScreenTest {
	private static final int WIDTH = 448;
	private static final int HEIGHT = 520;
	private static final int FPS = 60;
	private static final int MAX_LIVES = 3;
	private static final int EXTRA_LIFE_FRECUENCY = 3;
	private static final GameSettings gameSettings =
			new GameSettings(5, 4, 60, 2000);
	ArrayList<Player> players = new ArrayList<Player>();
	CustomGameState customGameState = new CustomGameState(1, Difficult.NORMAL, players, MultiMethod.P2PCLIENT);
	boolean bonusLife = false;
	int playernum = 1;
	
	@Test
	public void testCustomGameScreen() {
		CustomGameScreen customGameScreen = new CustomGameScreen(customGameState, gameSettings, bonusLife, MAX_LIVES, WIDTH, HEIGHT, FPS, playernum);
		assertSame(CustomGameScreen.class, customGameScreen.getClass());
	}

	@Test
	public void testGetGameState() {
		CustomGameScreen customGameScreen = new CustomGameScreen(customGameState, gameSettings, bonusLife, MAX_LIVES, WIDTH, HEIGHT, FPS, playernum);
		assertSame(CustomGameState.class, customGameScreen.getGameState().getClass());
	}

	@Test
	public void testAllPlayerDie() {
		Player player = new Player(); 
		players.add(player);
		CustomGameScreen customGameScreen = new CustomGameScreen(customGameState, gameSettings, bonusLife, MAX_LIVES, WIDTH, HEIGHT, FPS, playernum);
		assertFalse(customGameScreen.allPlayerDie());
		player.Die();
		assertTrue(customGameScreen.allPlayerDie());
		
	}

}
