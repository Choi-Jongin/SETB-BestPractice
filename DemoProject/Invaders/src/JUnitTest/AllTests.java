package JUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BulletPoolTest.class, BulletTest.class, CoreTest.class,
		EnemyShipFormationTest.class, EnemyShipTest.class, EntityTest.class, FrameTest.class,
		GameScreenTest.class, GameSettingsTest.class, GameStateTest.class, HighScoreScreenTest.class,
		ScoreScreenTest.class, ScoreTest.class, ScreenTest.class,
		ShipTest.class, TitleScreenTest.class })
public class AllTests {

}
