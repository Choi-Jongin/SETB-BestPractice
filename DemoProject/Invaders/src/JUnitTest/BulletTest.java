package JUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import engine.DrawManager.SpriteType;
import entity.Bullet;

public class BulletTest {
	
	private static int positionX = 200;
	private static int positionY = 100;
	private static int speed = 3;
	
	@Test
	public void Bulletest() {
		Bullet bullet = new Bullet(positionX, positionY, speed,null,false);
		assertSame(Bullet.class, bullet.getClass());
	}

	@Test
	public void setSpriteTest() {
		Bullet plusSpeedBullet = new Bullet(positionX, positionY, 5,null,false);
		plusSpeedBullet.setSprite();
		assertEquals(SpriteType.EnemyBullet, plusSpeedBullet.getSpriteType());
		Bullet minusSpeedBullet = new Bullet(positionX, positionY, -5,null,false);
		assertEquals(SpriteType.Bullet, minusSpeedBullet.getSpriteType());
	}

	@Test
	public void updateTest() {
		Bullet bullet = new Bullet(positionX, positionY, speed,null,false);
		assertEquals(100, bullet.getPositionY());
		bullet.update(); // 100 + 3
		assertEquals(103, bullet.getPositionY());
	}
	
	@Test
	public void setSpeedtest() {
		Bullet bullet = new Bullet(positionX, positionY, speed,null,false);
		assertEquals(3, bullet.getSpeed());
		bullet.setSpeed(6);
		assertEquals(6, bullet.getSpeed());
	}
	
	@Test
	public void getSpeedtest() {
		Bullet bullet = new Bullet(positionX, positionY, speed,null,false);
		assertEquals(3, bullet.getSpeed());
	}
	
}