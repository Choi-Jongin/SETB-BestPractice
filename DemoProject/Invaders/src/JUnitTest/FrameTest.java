package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.Frame;

public class FrameTest {
	private int width = 100;
	private int height = 200;

	@Test
	public void testGetWidth() {
		Frame frame = new Frame(width, height);
		assertEquals(86,frame.getWidth());
	}

	@Test
	public void testGetHeight() {
		Frame frame = new Frame(width, height);
		assertEquals(177,frame.getHeight());
	}

	@Test
	public void testFrameIntInt() {
		Frame frame = new Frame(width, height);
		assertSame(Frame.class, frame.getClass());
	}

}