package JUnitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import java.util.List;

import engine.GameSettings;
import entity.Bullet;
import entity.EnemyShip;
import entity.EnemyShipFormation;
import screen.Screen;

public class EnemyShipFormationTest {
	private int formationWidth = 5;
	private int formationHeight = 4;
	private int baseSpeed = 60;
	private int shootingFrecuency = 2000;
	/** Width of current screen. */
	private static final int WIDTH = 448;
	/** Height of current screen. */
	private static final int HEIGHT = 520;
	/** Max fps of current screen. */
	private static final int FPS = 60;	
	GameSettings gameSettings = new GameSettings(formationWidth,formationHeight,baseSpeed,shootingFrecuency);
	Screen screen = new Screen(WIDTH, HEIGHT, FPS);
	Set<Bullet> bullets;

	@Test
	public void testEnemyShipFormation() {
		EnemyShipFormation enemyShipFormation = new EnemyShipFormation(gameSettings);
		assertSame(EnemyShipFormation.class, enemyShipFormation.getClass());
	}

	@Test
	public void testShoot() {
		EnemyShipFormation enemyShipFormation = new EnemyShipFormation(gameSettings);
		bullets = new HashSet<Bullet>();
		bullets.add(new Bullet(200, 100 , 2));
		enemyShipFormation.update();
		enemyShipFormation.shoot(bullets); // Not Shooting Cooldown, No add bullet
		assertEquals(1, bullets.size());
	}

	@Test
	public void testGetNextShooter() {
		EnemyShipFormation enemyShipFormation = new EnemyShipFormation(gameSettings);
		List<EnemyShip> enemyShipList = new ArrayList<EnemyShip>();
		for(EnemyShip enemyShip : enemyShipFormation) {
			enemyShipList.add(enemyShip);
		}
		assertSame(EnemyShip.class, enemyShipFormation.getNextShooter(enemyShipList).getClass());
	}

	@Test
	public void testDestroyAndIsEmpty() {
		GameSettings gameSettings = new GameSettings(1,1,baseSpeed,shootingFrecuency);
		EnemyShipFormation enemyShipFormation = new EnemyShipFormation(gameSettings);
		assertFalse(enemyShipFormation.isEmpty());  // 1 EnemyShip
		enemyShipFormation.destroy(enemyShipFormation.iterator().next()); // destroy EnemyShip
		assertTrue(enemyShipFormation.isEmpty()); // 0 EnemyShip
	}

}
