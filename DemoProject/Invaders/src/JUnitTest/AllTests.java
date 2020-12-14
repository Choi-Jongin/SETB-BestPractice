package JUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BulletPoolTest.class, BulletTest.class, CoreTest.class, SettingScreenTest.class,
		EnemyShipFormationTest.class, EnemyShipTest.class, EntityTest.class, FrameTest.class,
		GameScreenTest.class, GameSettingsTest.class, GameStateTest.class, HighScoreScreenTest.class,
		ScoreScreenTest.class, ScoreTest.class, ScreenTest.class, OnlineSettingScreenTest.class,
		ShipTest.class, TitleScreenTest.class, RoomPacketTest.class, MovePacketTest.class,
		CustomScoreScreenTest.class, CustomGameScreenTest.class, CreateRoomScreenTest.class,
		ConnectRoomScreenTest.class, PlayerTest.class, CustomGameStateTest.class,
		GameServerTest.class, GameServerClientTest.class })
public class AllTests {

}
