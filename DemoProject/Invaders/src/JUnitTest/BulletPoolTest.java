package JUnitTest;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import entity.Bullet;
import entity.BulletPool;
import entity.Entity;
import entity.Ship;

public class BulletPoolTest {
	private Set<Bullet> bullets;
	private static int positionX = 200;
	private static int positionY = 100;
	private static int speed = 3;
	Entity shooter = new Ship(100,100);
	boolean isEnemy = false;
	
	@Test
	public void testEmptyGetBullet() {
		assertEquals(300-3,BulletPool.getBullet(positionX+100, positionY+100, speed+10, shooter, isEnemy).getPositionX());
		assertEquals(200,BulletPool.getBullet(positionX+100, positionY+100, speed+10, shooter, isEnemy).getPositionY());
		assertEquals(13,BulletPool.getBullet(positionX+100, positionY+100, speed+10, shooter, isEnemy).getSpeed());	
		assertSame(Bullet.class, BulletPool.getBullet(positionX,positionY,speed, shooter, isEnemy).getClass());
	}

	@Test
	public void testNoEmptyGetBullet() {
		bullets = new HashSet<Bullet>();
		bullets.add(new Bullet(positionX,positionY,speed, shooter, isEnemy));
		BulletPool.recycle(bullets);
		assertEquals(300-3,BulletPool.getBullet(positionX+100, positionY+100, speed+10, shooter, isEnemy).getPositionX());
		assertEquals(200,BulletPool.getBullet(positionX+100, positionY+100, speed+10, shooter, isEnemy).getPositionY());
		assertEquals(13,BulletPool.getBullet(positionX+100, positionY+100, speed+10, shooter, isEnemy).getSpeed());	
		assertSame(Bullet.class, BulletPool.getBullet(positionX,positionY,speed, shooter, isEnemy).getClass());
	}

}
