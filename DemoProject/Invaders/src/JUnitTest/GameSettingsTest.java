package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.GameSettings;

public class GameSettingsTest {
	private int formationWidth = 5;
	private int formationHeight = 4;
	private int baseSpeed = 60;
	private int shootingFrecuency = 2000;

	@Test
	public void testGameSettings() {
		GameSettings gameSettings = new GameSettings(formationWidth,formationHeight,baseSpeed,shootingFrecuency);
		assertSame(GameSettings.class, gameSettings.getClass());
	}

	@Test
	public void testGetFormationWidth() {
		GameSettings gameSettings = new GameSettings(formationWidth,formationHeight,baseSpeed,shootingFrecuency);
		assertEquals(5,gameSettings.getFormationWidth());
	}

	@Test
	public void testGetFormationHeight() {
		GameSettings gameSettings = new GameSettings(formationWidth,formationHeight,baseSpeed,shootingFrecuency);
		assertEquals(4, gameSettings.getFormationHeight());
	}

	@Test
	public void testGetBaseSpeed() {
		GameSettings gameSettings = new GameSettings(formationWidth,formationHeight,baseSpeed,shootingFrecuency);
		assertEquals(60, gameSettings.getBaseSpeed());
	}

	@Test
	public void testGetShootingFrecuency() {
		GameSettings gameSettings = new GameSettings(formationWidth,formationHeight,baseSpeed,shootingFrecuency);
		assertEquals(2000,gameSettings.getShootingFrecuency());
	}

}
