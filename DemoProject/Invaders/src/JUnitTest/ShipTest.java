package JUnitTest;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import engine.DrawManager.SpriteType;
import entity.Bullet;
import entity.Ship;

public class ShipTest {
	private static int positionX = 200;
	private static int positionY = 100;
	private Set<Bullet> bullets = new HashSet<Bullet>();

	@Test
	public void testShip() {
		Ship ship = new Ship(positionX, positionY);
		assertSame(Ship.class, ship.getClass());
	}

	@Test
	public void testMoveRight() {
		Ship ship = new Ship(positionX, positionY);
		ship.moveRight();
		assertEquals(202, ship.getPositionX()); // 200 + 2
	}

	@Test
	public void testMoveLeft() {
		Ship ship = new Ship(positionX, positionY);
		ship.moveLeft();
		assertEquals(198, ship.getPositionX()); // 200 - 2
	}

	@Test
	public void testShoot() {
		Ship ship = new Ship(positionX, positionY);
		Bullet bullet = new Bullet(200, 100 , 2,null,false);
		bullets.add(bullet);
		assertEquals(1, bullets.size());
		assertTrue(ship.shoot(bullets));
		assertEquals(2, bullets.size());
	}

	@Test
	public void testUpdate() {
		Ship ship = new Ship(positionX, positionY);
		assertEquals(SpriteType.Ship, ship.getSpriteType());
		ship.destroy();
		ship.update();
		assertEquals(SpriteType.ShipDestroyed, ship.getSpriteType());
	}

	@Test
	public void testDestroy() {
		Ship ship = new Ship(positionX, positionY);
		ship.destroy();
		assertTrue(ship.isDestroyed());
	}

	@Test
	public void testIsDestroyed() {
		Ship ship = new Ship(positionX, positionY);
		assertFalse(ship.isDestroyed());
		ship.destroy();
		assertTrue(ship.isDestroyed());
	}

	@Test
	public void testGetSpeed() {
		Ship ship = new Ship(positionX, positionY);
		assertEquals(2, ship.getSpeed());
	}

}
