package JUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import engine.DrawManager.SpriteType;
import entity.Bullet;
import entity.Entity;
import entity.Ship;

public class BulletTest {
	
	private static int positionX = 200;
	private static int positionY = 100;
	private static int speed = 3;
	boolean isEnemy = false;
	Entity shooter = new Ship(500, 500);
	
	@Test
	public void Bulletest() {
		Bullet bullet = new Bullet(positionX, positionY, speed, shooter, isEnemy);
		assertSame(Bullet.class, bullet.getClass());
	}

	@Test
	public void setSpriteTest() {
		Bullet plusSpeedBullet = new Bullet(positionX, positionY, 5, shooter, isEnemy);
		plusSpeedBullet.setSprite();
		assertEquals(SpriteType.EnemyBullet, plusSpeedBullet.getSpriteType());
		Bullet minusSpeedBullet = new Bullet(positionX, positionY, -5, shooter, isEnemy);
		assertEquals(SpriteType.Bullet, minusSpeedBullet.getSpriteType());
	}

	@Test
	public void updateTest() {
		Bullet bullet = new Bullet(positionX, positionY, speed, shooter, isEnemy);
		assertEquals(100, bullet.getPositionY());
		bullet.update(); // 100 + 3
		assertEquals(103, bullet.getPositionY());
	}
	
	@Test
	public void setSpeedtest() {
		Bullet bullet = new Bullet(positionX, positionY, speed, shooter, isEnemy);
		assertEquals(3, bullet.getSpeed());
		bullet.setSpeed(6);
		assertEquals(6, bullet.getSpeed());
	}
	
	@Test
	public void getSpeedtest() {
		Bullet bullet = new Bullet(positionX, positionY, speed, shooter, isEnemy);
		assertEquals(3, bullet.getSpeed());
	}
	
	@Test
	public void testGetShooter() {
		Bullet bullet = new Bullet(positionX, positionY, speed, shooter, isEnemy);
		assertEquals(shooter, bullet.getShooter());
	}

	@Test
	public void testSetShooter() {
		Bullet bullet = new Bullet(positionX, positionY, speed, shooter, isEnemy);
		assertEquals(shooter, bullet.getShooter());
		Ship newShooter = new Ship(100,100);
		bullet.setShooter(newShooter);
		assertEquals(newShooter, bullet.getShooter());
	}
	
	@Test
	public void testIsEnemy() {
		Bullet bullet = new Bullet(positionX, positionY, speed, shooter, isEnemy);
		assertFalse(bullet.isEnemy());
	}

	@Test
	public void testSetEnemy() {
		Bullet bullet = new Bullet(positionX, positionY, speed, shooter, isEnemy);
		assertFalse(bullet.isEnemy());
		bullet.setEnemy(true);
		assertTrue(bullet.isEnemy());
	}
	
}