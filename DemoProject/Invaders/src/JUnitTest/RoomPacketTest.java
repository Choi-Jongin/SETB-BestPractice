package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import screen.RoomPacket;

public class RoomPacketTest {
    private String name = "Son";
    private String password = "7";

	@Test
	public void testRoomPacket() {
		RoomPacket roomPacket = new RoomPacket(name, password);
		assertSame(RoomPacket.class, roomPacket.getClass());
	}
	
	@Test
	public void testIsConnAndSetRoom() {
		RoomPacket roomPacket = new RoomPacket(name, password);
		assertFalse(roomPacket.isConn()); // default isConn = false
		roomPacket.setRoom(new RoomPacket("Park", "qwe"));
		assertTrue(roomPacket.isConn());
		
	}

	@Test
	public void testGetNameAndSetName() {
		RoomPacket roomPacket = new RoomPacket(name, password);
		assertEquals("Son", roomPacket.getName());
		roomPacket.setName("Park");
		assertEquals("Park", roomPacket.getName());
	}

	@Test
	public void testGetPasswordAndSetPassword() {
		RoomPacket roomPacket = new RoomPacket(name, password);
		assertEquals("7", roomPacket.getPassword());
		roomPacket.setPassword("qwe");
		assertEquals("qwe", roomPacket.getPassword());
	}

}
