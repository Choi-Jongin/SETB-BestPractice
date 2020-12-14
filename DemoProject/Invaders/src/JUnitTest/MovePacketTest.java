package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import screen.MovePacket;

public class MovePacketTest {

	@Test
	public void testMovePacket() {
		MovePacket movePacket = new MovePacket(1234);
		assertSame(MovePacket.class, movePacket.getClass());
	}

	@Test
	public void testIsLeft() {
		MovePacket leftPacket = new MovePacket(1);
		assertTrue(leftPacket.isLeft());
		MovePacket noleftPacket = new MovePacket(2);
		assertFalse(noleftPacket.isLeft());
		
	}

	@Test
	public void testIsRight() {
		MovePacket rightPacket = new MovePacket(10);
		assertTrue(rightPacket.isRight());
		MovePacket noRightPacket = new MovePacket(20);
		assertFalse(noRightPacket.isRight());
	}

	@Test
	public void testIsATTACK() {
		MovePacket attackPacket = new MovePacket(100);
		assertTrue(attackPacket.isATTACK());
		MovePacket noattackPacket = new MovePacket(200);
		assertFalse(noattackPacket.isATTACK());
	}

	@Test
	public void testToString() {
		MovePacket movePacket = new MovePacket(1234);
		assertEquals("1234", movePacket.toString());
	}

}
