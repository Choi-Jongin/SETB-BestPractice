package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.Score;

public class ScoreTest {

	@Test
	public void testInitializeScore() {
		Score score = new Score("ABC",100);
		assertSame(Score.class, score.getClass());
	}

	@Test
	public void testGetName() {
		String name = "ABC";
		Score score = new Score(name, 100);
		assertEquals("ABC", score.getName());
	}

	@Test
	public void testGetScore() {
		Score score = new Score("ABC", 100);
		assertEquals(100, score.getScore());
	}

	@Test
	public void testCompareTo() {
		Score score = new Score("AAA", 100);
		Score biggerScore = new Score("BBB", 200);
		Score smallerScore = new Score("CCC", 0);
		Score sameScore = new Score("DDD", 100);
		assertEquals(1,score.compareTo(biggerScore));
		assertEquals(-1,score.compareTo(smallerScore));
		assertEquals(0,score.compareTo(sameScore));
	}

}
