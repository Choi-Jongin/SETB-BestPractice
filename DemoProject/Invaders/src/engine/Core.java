package engine;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.Player;
import screen.*;

/**
 * Implements core game logic.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 */
public final class Core implements Serializable {

    /**
     * Width of current screen.
     */
    private static final int WIDTH = 448;
    /**
     * Height of current screen.
     */
    private static final int HEIGHT = 520;
    /**
     * Max fps of current screen.
     */
    private static final int FPS = 60;

    /**
     * Max lives.
     */
    private static final int MAX_LIVES = 3;
    /**
     * Levels between extra life.
     */
    private static final int EXTRA_LIFE_FRECUENCY = 3;
    /**
     * Total number of levels.
     */
    private static final int NUM_LEVELS = 7;

    /**
     * Difficulty settings for level 1.
     */
    private static final GameSettings SETTINGS_LEVEL_1 =
            new GameSettings(5, 4, 60, 2000);
    /**
     * Difficulty settings for level 2.
     */
    private static final GameSettings SETTINGS_LEVEL_2 =
            new GameSettings(5, 5, 50, 2500);
    /**
     * Difficulty settings for level 3.
     */
    private static final GameSettings SETTINGS_LEVEL_3 =
            new GameSettings(6, 5, 40, 1500);
    /**
     * Difficulty settings for level 4.
     */
    private static final GameSettings SETTINGS_LEVEL_4 =
            new GameSettings(6, 6, 30, 1500);
    /**
     * Difficulty settings for level 5.
     */
    private static final GameSettings SETTINGS_LEVEL_5 =
            new GameSettings(7, 6, 20, 1000);
    /**
     * Difficulty settings for level 6.
     */
    private static final GameSettings SETTINGS_LEVEL_6 =
            new GameSettings(7, 7, 10, 1000);
    /**
     * Difficulty settings for level 7.
     */
    private static final GameSettings SETTINGS_LEVEL_7 =
            new GameSettings(8, 7, 2, 500);

    /**
     * Frame to draw the screen on.
     */
    private static Frame frame;
    /**
     * Screen currently shown.
     */
    private static Screen currentScreen;
    /**
     * Difficulty settings list.
     */
    private static List<GameSettings> gameSettings;
    /**
     * Application logger.
     */
    private static final transient Logger LOGGER = Logger.getLogger(Core.class
            .getSimpleName());
    /**
     * Logger handler for printing to disk.
     */
    private static transient Handler fileHandler;
    /**
     * Logger handler for printing to console.
     */
    private static transient ConsoleHandler consoleHandler;


    /**
     * Test implementation.
     *
     * @param args Program args, ignored.
     */
    public static void main(final String[] args) {
        try {
            LOGGER.setUseParentHandlers(false);

            fileHandler = new FileHandler("log");
            fileHandler.setFormatter(new MinimalFormatter());

            consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new MinimalFormatter());

            LOGGER.addHandler(fileHandler);
            LOGGER.addHandler(consoleHandler);
            LOGGER.setLevel(Level.ALL);

        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
        }

        frame = new Frame(WIDTH, HEIGHT);
        DrawManager.getInstance().setFrame(frame);
        int width = frame.getWidth();
        int height = frame.getHeight();

        gameSettings = new ArrayList<GameSettings>();
        gameSettings.add(SETTINGS_LEVEL_1);
        gameSettings.add(SETTINGS_LEVEL_2);
        gameSettings.add(SETTINGS_LEVEL_3);
        gameSettings.add(SETTINGS_LEVEL_4);
        gameSettings.add(SETTINGS_LEVEL_5);
        gameSettings.add(SETTINGS_LEVEL_6);
        gameSettings.add(SETTINGS_LEVEL_7);

        int returnCode = -1;
        int startlives = MAX_LIVES;
        IGameState.Difficult difficult = IGameState.Difficult.NORMAL;
        CustomGameState customGameState = null;
        ArrayList<Player> players = new ArrayList<Player>();
        int playernum = 6;
        int input[][] = new int[playernum][];
        do {

            switch (returnCode) {
                case -1:
                    // Main menu.
                    currentScreen = new TitleScreen(width, height, FPS);
                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " title screen at " + FPS + " fps.");
                    returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing title screen.");
                    break;

                case 0:
                    // Game & score.
                    startlives = MAX_LIVES;
                    if(difficult == IGameState.Difficult.EASY ) startlives += 2;
                    if(difficult == IGameState.Difficult.HARD ) startlives -= 2;
                    GameState gameState = new GameState(1, difficult,0, startlives, 0, 0);
                    do {
                        // One extra live every few levels.
                        boolean bonusLife = gameState.getLevel()
                                % EXTRA_LIFE_FRECUENCY == 0
                                && gameState.getLivesRemaining() < MAX_LIVES;

                        currentScreen = new GameScreen(gameState,
                                gameSettings.get(gameState.getLevel() - 1),
                                bonusLife, width, height, FPS);
                        LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                + " game screen at " + FPS + " fps.");
                        frame.setScreen(currentScreen);
                        LOGGER.info("Closing game screen.");

                        gameState = ((GameScreen) currentScreen).getGameState();

                        gameState = new GameState(gameState.getLevel() + 1, gameState.getDifficult(),
                                gameState.getScore(),
                                gameState.getLivesRemaining(),
                                gameState.getBulletsShot(),
                                gameState.getShipsDestroyed());

                    } while (gameState.getLivesRemaining() > 0
                            && gameState.getLevel() <= NUM_LEVELS);

                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " score screen at " + FPS + " fps, with a score of "
                            + gameState.getScore() + ", "
                            + gameState.getLivesRemaining() + " lives remaining, "
                            + gameState.getBulletsShot() + " bullets shot and "
                            + gameState.getShipsDestroyed() + " ships destroyed.");
                    currentScreen = new ScoreScreen(width, height, FPS, gameState);
                    returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing score screen.");
                    break;
                case 1:
                    // 2P CustomGame & score.
                    playernum = 2;
                    input = new int[playernum][];
                    input[0] = new int[]{KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_W};
                    input[1] = new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP};
//                    input[2] = new int[]{KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD5};
//                    for( int i = 3 ; i < playernum ;++i)
//                        input[i] = new int[]{KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD5};

                    for( int i = 0 ; i < playernum ; i++)
                        players.add( new Player("Player"+(i+1), 0,MAX_LIVES, input[i]));
                    startlives = MAX_LIVES;
                    if(difficult == IGameState.Difficult.EASY ) startlives += 2;
                    if(difficult == IGameState.Difficult.HARD ) startlives -= 2;
                    customGameState = new CustomGameState(1, difficult,  players, CustomGameState.MultiMethod.LOCAL);

                    do {
                        // One extra live every few levels.
                        boolean bonusLife = customGameState.getLevel()
                                % EXTRA_LIFE_FRECUENCY == 0
                                && customGameState.getMinLives() < MAX_LIVES;

                        currentScreen = new CustomGameScreen(customGameState,
                                gameSettings.get(customGameState.getLevel() - 1),
                                bonusLife,MAX_LIVES, width, height, FPS,2);
                        LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                + " game screen at " + FPS + " fps.");
                        frame.setScreen(currentScreen);
                        LOGGER.info("Closing game screen.");

                        customGameState = ((CustomGameScreen) currentScreen).getGameState();
                        customGameState = new CustomGameState(customGameState.getLevel() + 1, customGameState.getDifficult(), customGameState.getPlayers(), customGameState.getMethod() );

                    } while (customGameState.getMaxLives() > 0
                            && customGameState.getLevel() <= NUM_LEVELS);

                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " score screen at " + FPS + " fps, with a score of "
                            + customGameState.toString() );
                    currentScreen = new CustomScoreScreen(width, height, FPS, customGameState);
                    returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing score screen.");
                    break;
                case 2:
                    // Settings.
                    currentScreen = new SettingScreen(width, height, FPS, difficult);
                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " Settings Screen at " + FPS + " fps.");
                    returnCode = frame.setScreen(currentScreen);
                    difficult = ((SettingScreen)currentScreen).getDifficult();
                    for( GameSettings gs : gameSettings)
                               gs.setDifficult(difficult);
                    LOGGER.info("Closing Settings Screen screen.");
                    break;
                case 3:
                    // High scores.
                    currentScreen = new HighScoreScreen(width, height, FPS, difficult);
                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " high score screen at " + FPS + " fps.");
                    returnCode = frame.setScreen(currentScreen);

                    LOGGER.info("Closing high score screen.");

                    break;
                case 4: /*EXIT*/ break;
                case 5:
                    difficult = IGameState.Difficult.NORMAL;
                    int ONSselect = 0;  //ONLINE SETTING SELECTION
                    currentScreen = new OnlineSettingScreen(width, height, FPS);
                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " Online Setting screen at " + FPS + " fps.");
                    returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing Online Setting screen.");
                    ONSselect = ((OnlineSettingScreen)currentScreen).getSelectitem();
                    switch (ONSselect){
                        //Create Room
                        case 0 -> {
                            LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                    + " Create Room at " + FPS + " fps");
                            currentScreen = new CreateRoomScreen(width, height, FPS);
                            returnCode = frame.setScreen(currentScreen);
                            LOGGER.info("Closing Create Room screen.");

                            if(returnCode == 1) {

                                playernum = 2;
                                input = new int[playernum][];
                                input[0] = new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_SPACE};
                                input[1] = new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_SPACE};

                                players.add(new Player(((CreateRoomScreen)currentScreen).getMyname(), 0, MAX_LIVES, input[0],false));
                                players.add(new Player(((CreateRoomScreen)currentScreen).getRoomPacket().getName(), 0, MAX_LIVES, input[1],true));

                                startlives = MAX_LIVES;
                                if (difficult == IGameState.Difficult.EASY) startlives += 2;
                                if (difficult == IGameState.Difficult.HARD) startlives -= 2;
                                customGameState = new CustomGameState(1, difficult, players, CustomGameState.MultiMethod.P2PHOST);

                                do {
                                    // One extra live every few levels.
                                    boolean bonusLife = customGameState.getLevel()
                                            % EXTRA_LIFE_FRECUENCY == 0
                                            && customGameState.getMinLives() < MAX_LIVES;

                                    currentScreen = new CustomGameScreen(customGameState,
                                            gameSettings.get(customGameState.getLevel() - 1),
                                            bonusLife, MAX_LIVES, width, height, FPS, 2);
                                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                            + " game screen at " + FPS + " fps.");
                                    frame.setScreen(currentScreen);
                                    LOGGER.info("Closing game screen.");

                                    customGameState = ((CustomGameScreen) currentScreen).getGameState();
                                    customGameState = new CustomGameState(customGameState.getLevel() + 1, customGameState.getDifficult(), customGameState.getPlayers(), customGameState.getMethod());

                                } while (customGameState.getMaxLives() > 0
                                        && customGameState.getLevel() <= NUM_LEVELS);

                                GameServer.getInstance().stopServer();

                                LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                        + " score screen at " + FPS + " fps, with a score of "
                                        + customGameState.toString());
                                currentScreen = new CustomScoreScreen(width, height, FPS, customGameState);
                                returnCode = frame.setScreen(currentScreen);
                                LOGGER.info("Closing score screen.");
                                break;
                            }

                        }
                        //ConnectRoom
                        case 1 -> {
                            LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                    + " Connect Room at " + FPS + " fps");
                            currentScreen = new ConnectRoomScreen(width, height, FPS);
                            returnCode = frame.setScreen(currentScreen);
                            LOGGER.info("Closing Connect Room screen.");

                            if(returnCode == 1) {

                                playernum = 2;
                                input = new int[playernum][];
                                input[0] = new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_SPACE};
                                input[1] = new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_SPACE};

                                players.add(new Player(((ConnectRoomScreen) currentScreen).getMyname(), 0, MAX_LIVES, input[0],false));
                                players.add(new Player(((ConnectRoomScreen) currentScreen).getMyname(), 0, MAX_LIVES, input[1],true));

                                startlives = MAX_LIVES;
                                if (difficult == IGameState.Difficult.EASY) startlives += 2;
                                if (difficult == IGameState.Difficult.HARD) startlives -= 2;
                                customGameState = new CustomGameState(1, difficult, players, CustomGameState.MultiMethod.P2PCLIENT);

                                do {
                                    // One extra live every few levels.
                                    boolean bonusLife = customGameState.getLevel()
                                            % EXTRA_LIFE_FRECUENCY == 0
                                            && customGameState.getMinLives() < MAX_LIVES;

                                    currentScreen = new CustomGameScreen(customGameState,
                                            gameSettings.get(customGameState.getLevel() - 1),
                                            bonusLife, MAX_LIVES, width, height, FPS, 2);
                                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                            + " game screen at " + FPS + " fps.");
                                    frame.setScreen(currentScreen);
                                    LOGGER.info("Closing game screen.");

                                    customGameState = ((CustomGameScreen) currentScreen).getGameState();
                                    customGameState = new CustomGameState(customGameState.getLevel() + 1, customGameState.getDifficult(), customGameState.getPlayers(), customGameState.getMethod());

                                } while (customGameState.getMaxLives() > 0
                                        && customGameState.getLevel() <= NUM_LEVELS);

                                GameServerClient.getInstance().stopClient();
                                LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                        + " score screen at " + FPS + " fps, with a score of "
                                        + customGameState.toString());
                                currentScreen = new CustomScoreScreen(width, height, FPS, customGameState);
                                returnCode = frame.setScreen(currentScreen);
                                LOGGER.info("Closing score screen.");
                                break;
                            }

                        }
                    }
                    break;

                default:
                    break;
            }

        } while (returnCode != 4);

        fileHandler.flush();
        fileHandler.close();
        System.exit(0);
    }

    /**
     * Constructor, not called.
     */
    private Core() {

    }

    /**
     * Controls access to the logger.
     *
     * @return Application logger.
     */
    public static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Controls access to the drawing manager.
     *
     * @return Application draw manager.
     */
    public static DrawManager getDrawManager() {
        return DrawManager.getInstance();
    }

    /**
     * Controls access to the input manager.
     *
     * @return Application input manager.
     */
    public static InputManager getInputManager() {
        return InputManager.getInstance();
    }

    /**
     * Controls access to the file manager.
     *
     * @return Application file manager.
     */
    public static FileManager getFileManager() {
        return FileManager.getInstance();
    }

    /**
     * Controls creation of new cooldowns.
     *
     * @param milliseconds Duration of the cooldown.
     * @return A new cooldown.
     */
    public static Cooldown getCooldown(final int milliseconds) {
        return new Cooldown(milliseconds);
    }

    /**
     * Controls creation of new cooldowns with variance.
     *
     * @param milliseconds Duration of the cooldown.
     * @param variance     Variation in the cooldown duration.
     * @return A new cooldown with variance.
     */
    public static Cooldown getVariableCooldown(final int milliseconds,
                                               final int variance) {
        return new Cooldown(milliseconds, variance);
    }
}