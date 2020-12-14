package screen;

import engine.*;
import entity.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CustomScoreScreen extends Screen {

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;
    /** Maximum number of high scores. */
    private static final int MAX_HIGH_SCORE_NUM = 7;
    /** Code of first mayus character. */
    private static final int FIRST_CHAR = 65;
    /** Code of last mayus character. */
    private static final int LAST_CHAR = 90;

    /** Current score. */
    private int score[];
    /** Player lives left. */
    private int livesRemaining[];
    /** Total bullets shot by the player. */
    private int bulletsShot[];
    /** Total ships destroyed by the player. */
    private int shipsDestroyed[];
    /** List of past high scores. */
    private List<Score> highScores;
    /** Checks if current score is a new high score. */
    private boolean isNewRecord[];
    /** Player name for record input. */
    private char[][] name;
    /** Character of players name selected for change. */
    private int nameCharSelected[];
    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    private int player;
    private int playernum;
    private String[] playerName;

    CustomGameState.MultiMethod method;
    String difficultstr = "";

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     * @param gameState
     *            Current game state.
     */
    public CustomScoreScreen(final int width, final int height, final int fps,
                       final CustomGameState gameState) {
        super(width, height, fps);

        player = 0;
        playernum = gameState.getPlayernum();
        this.score = new int[playernum];
        this.livesRemaining = new int[playernum];
        this.bulletsShot = new int[playernum];
        this.shipsDestroyed = new int[playernum];
        this.isNewRecord = new boolean[playernum];
        this.name = new char[playernum][3];
        this.nameCharSelected = new int[playernum];
        this.playerName = new String[playernum];
        this.method =  gameState.getMethod();

        switch (gameState.getDifficult()){
            case EASY -> {difficultstr = "easy";}
            case NORMAL -> {difficultstr = "normal";}
            case HARD -> {difficultstr = "hard";}
        }

        for( int i = player ; i < playernum ; i++){
            Player p = gameState.getPlayers().get(i);

            this.score[i] = p.getScore();
            this.livesRemaining[i] = p.getLives();
            this.bulletsShot[i] = p.getBulletsShot();
            this.shipsDestroyed[i] = p.getShipsDestroyed();
            this.isNewRecord[i] = false;
            this.name[i] = "AAA".toCharArray();
            this.nameCharSelected[i] = 0;
            this.playerName[i] = p.getName();


            if( this.method == CustomGameState.MultiMethod.P2PCLIENT && i == 0 )
                continue;
            if( this.method == CustomGameState.MultiMethod.P2PHOST && i == 1 )
                continue;
            try {
                this.highScores = Core.getFileManager().loadHighScores(difficultstr+"scores");
                if (highScores.size() < MAX_HIGH_SCORE_NUM
                        || highScores.get(highScores.size() - 1).getScore()
                        < this.score[i])
                    this.isNewRecord[i] = true;

            } catch (IOException e) {
                logger.warning("Couldn't load high scores!");
            }

            this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
            this.selectionCooldown.reset();
        }

    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        draw();
        if (this.inputDelay.checkFinished()) {

            if (this.isNewRecord[player] && this.selectionCooldown.checkFinished()) {
                if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) {
                    this.nameCharSelected[player] = this.nameCharSelected[player] == 2 ? 0
                            : this.nameCharSelected[player] + 1;
                    this.selectionCooldown.reset();
                }
                if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) {
                    this.nameCharSelected[player] = this.nameCharSelected[player] == 0 ? 2
                            : this.nameCharSelected[player] - 1;
                    this.selectionCooldown.reset();
                }
                if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
                    this.name[player][this.nameCharSelected[player]] =
                            (char) (this.name[player][this.nameCharSelected[player]]
                                    == LAST_CHAR ? FIRST_CHAR
                                    : this.name[player][this.nameCharSelected[player]] + 1);
                    this.selectionCooldown.reset();
                }
                if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
                    this.name[player][this.nameCharSelected[player]] =
                            (char) (this.name[player][this.nameCharSelected[player]]
                                    == FIRST_CHAR ? LAST_CHAR
                                    : this.name[player][this.nameCharSelected[player]] - 1);
                    this.selectionCooldown.reset();
                }
            }


            if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
                // Return to main menu.
                if (this.isNewRecord[player])
                    saveScore();
                if (++player >= playernum) {
                    this.returnCode = -1;
                    this.isRunning = false;
                }

                this.inputDelay.reset();
            } else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                // recode.
                if (this.isNewRecord[player])
                    saveScore();
                if (++player >= playernum) {
                    this.returnCode = 3;
                    this.isRunning = false;
                }

                this.inputDelay.reset();
            }
        }

    }

    /**
     * Saves the score as a high score.
     */
    private void saveScore() {
        highScores.add(new Score(new String(this.name[player]), score[player]));
        Collections.sort(highScores);
        if (highScores.size() > MAX_HIGH_SCORE_NUM)
            highScores.remove(highScores.size() - 1);

        try {
            Core.getFileManager().saveHighScores(highScores,difficultstr+"scores");
        } catch (IOException e) {
            logger.warning("Couldn't load high scores!");
        }
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawGameOver(this, this.inputDelay.checkFinished(),
                this.isNewRecord[player], difficultstr);
        drawManager.drawCenterText(this, playerName[player], (this.getHeight()/5)-10, Color.WHITE);
        drawManager.drawResults(this, this.score[player], this.livesRemaining[player],
                this.shipsDestroyed[player], (float) this.shipsDestroyed[player]
                        / this.bulletsShot[player], this.isNewRecord[player]);

        if (this.isNewRecord[player])
            drawManager.drawNameInput(this, this.name[player], this.nameCharSelected[player]);

        drawManager.completeDrawing(this);
    }
}
