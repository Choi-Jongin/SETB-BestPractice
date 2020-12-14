package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import entity.EnemyShip;
import engine.DrawManager.SpriteType;

public class EnemyShipTest {
	/** Point value of a type A enemy. */
	private static final int A_TYPE_POINTS = 10;
	/** Point value of a type B enemy. */
	private static final int B_TYPE_POINTS = 20;
	/** Point value of a type C enemy. */
	private static final int C_TYPE_POINTS = 30;
	/** Point value of a bonus enemy. */
	private static final int BONUS_TYPE_POINTS = 100;
	private static int positionX = 200;
	private static int positionY = 100;

	@Test
	public void testEnemyShip() {
		EnemyShip enemyShip = new EnemyShip(positionX, positionY, SpriteType.EnemyShipA1);
		assertSame(EnemyShip.class, enemyShip.getClass());
	}

	@Test
	public void testEnemyShipSpecial() {
		EnemyShip enemyShipSpecial = new EnemyShip();
		assertEquals(SpriteType.EnemyShipSpecial, enemyShipSpecial.getSpriteType());
		assertEquals(false, enemyShipSpecial.isDestroyed());
		assertEquals(BONUS_TYPE_POINTS, enemyShipSpecial.getPointValue());
	}



	@Test
	public void testGetPointValue() {
		EnemyShip enemyShipA1 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipA1);
		assertEquals(A_TYPE_POINTS, enemyShipA1.getPointValue());
		EnemyShip enemyShipA2 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipA2);
		assertEquals(A_TYPE_POINTS, enemyShipA2.getPointValue());
		EnemyShip enemyShipB1 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipB1);
		assertEquals(B_TYPE_POINTS, enemyShipB1.getPointValue());
		EnemyShip enemyShipB2 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipB2);
		assertEquals(B_TYPE_POINTS, enemyShipB2.getPointValue());
		EnemyShip enemyShipC1 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipC1);
		assertEquals(C_TYPE_POINTS, enemyShipC1.getPointValue());
		EnemyShip enemyShipC2 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipC2);
		assertEquals(C_TYPE_POINTS, enemyShipC2.getPointValue());
		EnemyShip enemyShipBullet = new EnemyShip(positionX, positionY, SpriteType.Bullet);
		assertEquals(0, enemyShipBullet.getPointValue());

	}

	@Test
	public void testMove() {
		EnemyShip enemyShip = new EnemyShip(positionX, positionY, SpriteType.EnemyShipA1);
		enemyShip.move(10,20);
		assertEquals(210, enemyShip.getPositionX()); // 200 + 10
		assertEquals(120, enemyShip.getPositionY()); // 100 + 20
	}

	@Test
	public void testUpdate() {
		EnemyShip enemyShipA1 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipA1);
		enemyShipA1.update();
		assertEquals(SpriteType.EnemyShipA2, enemyShipA1.getSpriteType());
		EnemyShip enemyShipA2 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipA2);
		enemyShipA2.update();
		assertEquals(SpriteType.EnemyShipA1, enemyShipA2.getSpriteType());
		EnemyShip enemyShipB1 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipB1);
		enemyShipB1.update();
		assertEquals(SpriteType.EnemyShipB2, enemyShipB1.getSpriteType());
		EnemyShip enemyShipB2 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipB2);
		enemyShipB2.update();
		assertEquals(SpriteType.EnemyShipB1, enemyShipB2.getSpriteType());
		EnemyShip enemyShipC1 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipC1);
		enemyShipC1.update();
		assertEquals(SpriteType.EnemyShipC2, enemyShipC1.getSpriteType());
		EnemyShip enemyShipC2 = new EnemyShip(positionX, positionY, SpriteType.EnemyShipC2);
		enemyShipC2.update();
		assertEquals(SpriteType.EnemyShipC1, enemyShipC2.getSpriteType());
	}

	@Test
	public void testDestroy() {
		EnemyShip enemyShip = new EnemyShip(positionX, positionY, SpriteType.EnemyShipA1);
		enemyShip.destroy();
		assertEquals(true, enemyShip.isDestroyed());
		assertEquals(SpriteType.Explosion, enemyShip.getSpriteType());
	}

	@Test
	public void testIsDestroyed() {
		EnemyShip enemyShip = new EnemyShip(positionX, positionY, SpriteType.EnemyShipA1);
		assertEquals(false, enemyShip.isDestroyed());
		enemyShip.destroy();
		assertEquals(true, enemyShip.isDestroyed());
	}

}
