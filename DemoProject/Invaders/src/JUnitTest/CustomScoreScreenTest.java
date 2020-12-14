package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

import engine.CustomGameState;
import engine.CustomGameState.MultiMethod;
import engine.IGameState.Difficult;
import entity.Player;
import screen.CustomScoreScreen;

public class CustomScoreScreenTest {

	private static final int WIDTH = 448;
	private static final int HEIGHT = 520;
	private static final int FPS = 60;
	ArrayList<Player> players = new ArrayList<Player>();
	CustomGameState customGameState = new CustomGameState(1, Difficult.NORMAL, players, MultiMethod.P2PCLIENT);
	
	@Test
	public void testCustomScoreScreen() {
		CustomScoreScreen customScoreScreen = new CustomScoreScreen(WIDTH, HEIGHT, FPS, customGameState);
		assertSame(CustomScoreScreen.class, customScoreScreen.getClass());
	}

}
