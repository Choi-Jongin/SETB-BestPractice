package JUnitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import engine.CustomGameState;
import engine.CustomGameState.MultiMethod;
import engine.IGameState.Difficult;
import entity.Player;

public class CustomGameStateTest {
	private int level = 3;
	ArrayList<Player> players = new ArrayList<Player>();
	
	@Test
	public void testCustomGameState() {
		CustomGameState customGameState = new CustomGameState(level, Difficult.NORMAL, players, MultiMethod.P2PCLIENT);
		assertSame(CustomGameState.class, customGameState.getClass());
	}

	@Test
	public void testGetMaxLivesAndGetMinLives() {
		int[] inputs = {1,2,3};
		Player player1 = new Player("Player1", 100, 4, inputs);
		Player player2 = new Player("Player2", 200, 1, inputs);
		Player player3 = new Player("Player2", 200, 6, inputs);
		players.add(player1);
		players.add(player2);
		players.add(player3);
		CustomGameState customGameState = new CustomGameState(level, Difficult.NORMAL, players, MultiMethod.P2PCLIENT);
		assertEquals(6, customGameState.getMaxLives());
		assertEquals(1, customGameState.getMinLives());
	}

	@Test
	public void testGetPlayernum() {
		int[] inputs = {1,2,3};
		Player player1 = new Player("Player1", 100, 4, inputs);
		Player player2 = new Player("Player2", 200, 1, inputs);
		Player player3 = new Player("Player2", 200, 6, inputs);
		players.add(player1);
		players.add(player2);
		players.add(player3);
		CustomGameState customGameState = new CustomGameState(level, Difficult.NORMAL, players, MultiMethod.P2PCLIENT);
		assertEquals(3, customGameState.getPlayernum());
	}

	@Test
	public void testGetMethodAndSetMethod() {
		CustomGameState customGameState = new CustomGameState(level, Difficult.NORMAL, players, MultiMethod.P2PCLIENT);
		assertEquals(MultiMethod.P2PCLIENT, customGameState.getMethod());
		customGameState.setMethod(MultiMethod.LOCAL);
		assertEquals(MultiMethod.LOCAL, customGameState.getMethod());
	}

	@Test
	public void testGetPlayersAndSetPlayers() {
		CustomGameState customGameState = new CustomGameState(level, Difficult.NORMAL, players, MultiMethod.P2PCLIENT);
		assertEquals(players, customGameState.getPlayers());
		ArrayList<Player> newPlayers = new ArrayList<Player>();
		customGameState.setPlayers(newPlayers);
		assertEquals(newPlayers, customGameState.getPlayers());
	}

	@Test
	public void testToString() {
		int[] inputs = {1,2,3};
		Player player1 = new Player("Player1", 100, 1, inputs);
		Player player2 = new Player("Player2", 200, 2, inputs);
		players.add(player1);
		players.add(player2);
		CustomGameState customGameState = new CustomGameState(level, Difficult.NORMAL, players, MultiMethod.P2PCLIENT);
		assertEquals("[Player1: 1 lives remaining, 0 bullets shot and 0 ships destroyed.]\n" + 
				"[Player2: 2 lives remaining, 0 bullets shot and 0 ships destroyed.]\n", customGameState.toString());
	}

}
