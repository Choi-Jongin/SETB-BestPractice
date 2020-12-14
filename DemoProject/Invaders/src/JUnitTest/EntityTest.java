package JUnitTest;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import entity.Entity;

public class EntityTest {

	private static int positionX = 200;
	private static int positionY = 100;
	private static int width = 20;
	private static int height = 10;
	private static Color color = Color.RED; 

	@Test
	public void testEntity() {
		Entity entity = new Entity(positionX, positionY, width, height, color);
		assertSame(Entity.class, entity.getClass());
	}

	@Test
	public void testGetColor() {
		Entity entity = new Entity(positionX, positionY, width, height, color);
		assertEquals(Color.RED, entity.getColor());
	}

	@Test
	public void testGetPositionX() {
		Entity entity = new Entity(positionX, positionY, width, height, color);
		assertEquals(200, entity.getPositionX());
	}

	@Test
	public void testGetPositionY() {
		Entity entity = new Entity(positionX, positionY, width, height, color);
		assertEquals(100, entity.getPositionY());
	}

	@Test
	public void testSetPositionX() {
		Entity entity = new Entity(positionX, positionY, width, height, color);
		assertEquals(200, entity.getPositionX());
		entity.setPositionX(400);
		assertEquals(400, entity.getPositionX());
		
	}

	@Test
	public void testSetPositionY() {
		Entity entity = new Entity(positionX, positionY, width, height, color);
		assertEquals(100, entity.getPositionY());
		entity.setPositionY(200);
		assertEquals(200, entity.getPositionY());
	}

	@Test
	public void testGetWidth() {
		Entity entity = new Entity(positionX, positionY, width, height, color);
		assertEquals(20, entity.getWidth());
	}

	@Test
	public void testGetHeight() {
		Entity entity = new Entity(positionX, positionY, width, height, color);
		assertEquals(10, entity.getHeight());
	}

}
