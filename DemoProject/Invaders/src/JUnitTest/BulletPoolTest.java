package JUnitTest;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import entity.Bullet;
import entity.BulletPool;

public class BulletPoolTest {
	private Set<Bullet> bullets;
	private static int positionX = 200;
	private static int positionY = 100;
	private static int speed = 3;

	@Test
	public void testEmptyGetBullet() {
		assertEquals(300-3,BulletPool.getBullet(positionX+100, positionY+100, speed+10).getPositionX());
		assertEquals(200,BulletPool.getBullet(positionX+100, positionY+100, speed+10).getPositionY());
		assertEquals(13,BulletPool.getBullet(positionX+100, positionY+100, speed+10).getSpeed());	
		assertSame(Bullet.class, BulletPool.getBullet(positionX,positionY,speed).getClass());
	}

	@Test
	public void testNoEmptyGetBullet() {
		bullets = new HashSet<Bullet>();
		bullets.add(new Bullet(positionX,positionY,speed));
		BulletPool.recycle(bullets);
		assertEquals(300-3,BulletPool.getBullet(positionX+100, positionY+100, speed+10).getPositionX());
		assertEquals(200,BulletPool.getBullet(positionX+100, positionY+100, speed+10).getPositionY());
		assertEquals(13,BulletPool.getBullet(positionX+100, positionY+100, speed+10).getSpeed());	
		assertSame(Bullet.class, BulletPool.getBullet(positionX,positionY,speed).getClass());
	}

}
