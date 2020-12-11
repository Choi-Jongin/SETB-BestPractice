package screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import engine.*;
import entity.*;

/**
 * Implements the game screen, where the action happens.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class CustomGameScreen extends Screen {

    /** Milliseconds until the screen accepts user input. */
    private static final int INPUT_DELAY = 6000;
    /** Bonus score for each life remaining at the end of the level. */
    private static final int LIFE_SCORE = 100;
    /** Minimum time between bonus ship's appearances. */
    private static final int BONUS_SHIP_INTERVAL = 20000;
    /** Maximum variance in the time between bonus ship's appearances. */
    private static final int BONUS_SHIP_VARIANCE = 10000;
    /** Time until bonus ship explosion disappears. */
    private static final int BONUS_SHIP_EXPLOSION = 500;
    /** Time from finishing the level to screen change. */
    private static final int SCREEN_CHANGE_INTERVAL = 1500;
    /** Height of the interface separation line. */
    private static final int SEPARATION_LINE_HEIGHT = 55;

    /** Current game difficulty settings. */
    private GameSettings gameSettings;
    /** Current difficulty level number. */
    private int level;
    /** Formation of enemy ships. */
    private EnemyShipFormation enemyShipFormation;
//	/** Player's ship. */
//	private Ship ship;
    /** Bonus enemy ship that appears sometimes. */
    private EnemyShip enemyShipSpecial;
    /** Minimum time between bonus ship appearances. */
    private Cooldown enemyShipSpecialCooldown;
    /** Time until bonus ship explosion disappears. */
    private Cooldown enemyShipSpecialExplosionCooldown;
    /** Time from finishing the level to screen change. */
    private Cooldown screenFinishedCooldown;
    /** Set of all bullets fired by on screen ships. */
    private Set<Bullet> bullets;
//	/** Current score. */
//	private int score;
//	/** Player lives left. */
//	private int lives;
//	/** Total bullets shot by the player. */
//	private int bulletsShot;
//	/** Total ships destroyed by the player. */
//	private int shipsDestroyed;
    /** Moment the game starts. */
    private long gameStartTime;
    /** Checks if the level is finished. */
    private boolean levelFinished;
    /** Checks if a bonus life is received. */
    private boolean bonusLife;

    ArrayList<Player> players;
    CustomGameState.MultiMethod multimethod;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param gameState
     *            Current game state.
     * @param gameSettings
     *            Current game settings.
     * @param bonusLife
     *            Checks if a bonus life is awarded this level.
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public CustomGameScreen(final CustomGameState gameState,
                      final GameSettings gameSettings, final boolean bonusLife, int MAX_LIVES,
                      final int width, final int height, final int fps, int playernum) {
        super(width, height, fps);
        CustomGameState customGameState = gameState;
        this.players = gameState.getPlayers();
        this.gameSettings = gameSettings;
        this.bonusLife = bonusLife;
        this.level = customGameState.getLevel();
        this.multimethod = customGameState.getMethod();
        if (this.bonusLife) {
            for( Player p :  players){
                if( p.addlive(1) == 1 )
                    p.setDie(false);
            };
        }

        this.returnCode = 1;
    }

    /**
     * Initializes basic screen properties, and adds necessary elements.
     */
    public final void initialize() {
        super.initialize();

        enemyShipFormation = new EnemyShipFormation(this.gameSettings);
        enemyShipFormation.attach(this);

        for( int i = 0 ; i < players.size() ; i++){
            Player p = players.get(i);
            Ship ship = new Ship((this.width / (1+players.size()))*(i+1), this.height - 30, Player.color[i]);
            ship.setPlayer(p);
            p.setShip(ship);
        }

        // Appears each 10-30 seconds.
        this.enemyShipSpecialCooldown = Core.getVariableCooldown(
                BONUS_SHIP_INTERVAL, BONUS_SHIP_VARIANCE);
        this.enemyShipSpecialCooldown.reset();
        this.enemyShipSpecialExplosionCooldown = Core
                .getCooldown(BONUS_SHIP_EXPLOSION);
        this.screenFinishedCooldown = Core.getCooldown(SCREEN_CHANGE_INTERVAL);
        this.bullets = new HashSet<Bullet>();

        // Special input delay / countdown.
        this.gameStartTime = System.currentTimeMillis();
        this.inputDelay = Core.getCooldown(INPUT_DELAY);
        this.inputDelay.reset();
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        for (Player p : players) {
            p.addScore(LIFE_SCORE * (p.getLives() - 1));
            this.logger.info("Screen cleared with player " +p.getName() + "score of " );
        }

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        if (this.inputDelay.checkFinished() && !this.levelFinished) {

            for(Player p : players) {
                Ship ship = p.getShip();
                if( p.isDie() ) continue;
                if (!ship.isDestroyed()) {
                    boolean isRightBorder = ship.getPositionX()
                            + ship.getWidth() + ship.getSpeed() > this.width - 1;
                    boolean isLeftBorder = ship.getPositionX()
                            - ship.getSpeed() < 1;

                    if (inputManager.isKeyDown(p.getInputs()[0]) && !isRightBorder) {
                        ship.moveRight();
                    }
                    if (inputManager.isKeyDown(p.getInputs()[1]) && !isLeftBorder) {
                        ship.moveLeft();
                    }
                    if (inputManager.isKeyDown(p.getInputs()[2]))
                        if (ship.shoot(this.bullets))
                            p.addbulletsShot(1);
                }
                p.update();
            }
            if (this.enemyShipSpecial != null) {
                if (!this.enemyShipSpecial.isDestroyed())
                    this.enemyShipSpecial.move(2, 0);
                else if (this.enemyShipSpecialExplosionCooldown.checkFinished())
                    this.enemyShipSpecial = null;

            }
            if (this.enemyShipSpecial == null
                    && this.enemyShipSpecialCooldown.checkFinished()) {
                this.enemyShipSpecial = new EnemyShip();
                this.enemyShipSpecialCooldown.reset();
                this.logger.info("A special ship appears");
            }
            if (this.enemyShipSpecial != null
                    && this.enemyShipSpecial.getPositionX() > this.width) {
                this.enemyShipSpecial = null;
                this.logger.info("The special ship has escaped");
            }

            this.enemyShipFormation.update();
            this.enemyShipFormation.shoot(this.bullets);
        }

        manageCollisions();
        cleanBullets();
        draw();


        if ((this.enemyShipFormation.isEmpty() || allPlayerDie() )
                && !this.levelFinished) {
            this.levelFinished = true;
            this.screenFinishedCooldown.reset();
        }

        if (this.levelFinished && this.screenFinishedCooldown.checkFinished())
            this.isRunning = false;

    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        for(Player p : players) {
            Ship ship = p.getShip();
            if( !p.isDie() )
            drawManager.drawEntity(ship, ship.getPositionX(),
                    ship.getPositionY());
        }
        if (this.enemyShipSpecial != null)
            drawManager.drawEntity(this.enemyShipSpecial,
                    this.enemyShipSpecial.getPositionX(),
                    this.enemyShipSpecial.getPositionY());

        enemyShipFormation.draw();

        for (Bullet bullet : this.bullets)
            drawManager.drawEntity(bullet, bullet.getPositionX(),
                    bullet.getPositionY());

        // Interface.
        for( int i = 0 ; i < players.size() ; i++){
            drawManager.drawScore(this, players.get(i).getScore(),i);
            drawManager.drawLives(this, players.get(i).getLives(),i);
            drawManager.drawHorizontalLine(this, SEPARATION_LINE_HEIGHT - 1);
        }
        // Countdown to game start.
        if (!this.inputDelay.checkFinished()) {
            int countdown = (int) ((INPUT_DELAY
                    - (System.currentTimeMillis()
                    - this.gameStartTime)) / 1000);
            drawManager.drawCountDown(this, this.level, countdown,
                    this.bonusLife);
            drawManager.drawHorizontalLine(this, this.height / 2 - this.height
                    / 12);
            drawManager.drawHorizontalLine(this, this.height / 2 + this.height
                    / 12);
        }

        drawManager.completeDrawing(this);
    }

    /**
     * Cleans bullets that go off screen.
     */
    private void cleanBullets() {
        Set<Bullet> recyclable = new HashSet<Bullet>();
        for (Bullet bullet : this.bullets) {
            bullet.update();
            if (bullet.getPositionY() < SEPARATION_LINE_HEIGHT
                    || bullet.getPositionY() > this.height)
                recyclable.add(bullet);
        }
        this.bullets.removeAll(recyclable);
        BulletPool.recycle(recyclable);
    }

    /**
     * Manages collisions between bullets and ships.
     */
    private void manageCollisions() {
        Set<Bullet> recyclable = new HashSet<Bullet>();


        for (Bullet bullet : this.bullets) {
            if (bullet.isEnemy()) {
                for (Player p : players) {
                    Ship ship = p.getShip();
                    if (p.isDie()) continue;
                    if (checkCollision(bullet, ship) && !this.levelFinished) {
                        recyclable.add(bullet);
                        if (!ship.isDestroyed()) {
                            ship.destroy();
                            p.subtractlive(1);
                            this.logger.info("Hit on " + p.getName() + " ship, " + p.getLives()
                                    + " lives remaining.");
                        }
                    }
                }
            } else {
                for (EnemyShip enemyShip : this.enemyShipFormation) {
                    if (!enemyShip.isDestroyed()
                            && checkCollision(bullet, enemyShip)) {
                        ((Ship) bullet.getShooter()).getPlayer().addScore(enemyShip.getPointValue());
                        ((Ship) bullet.getShooter()).getPlayer().addshipsDestroyed(1);
                        this.enemyShipFormation.destroy(enemyShip);
                        recyclable.add(bullet);
                    }
                }
                if (this.enemyShipSpecial != null
                        && !this.enemyShipSpecial.isDestroyed()
                        && checkCollision(bullet, this.enemyShipSpecial)) {
                    ((Ship)bullet.getShooter()).getPlayer().addScore(this.enemyShipSpecial.getPointValue());
                    ((Ship)bullet.getShooter()).getPlayer().addshipsDestroyed(1);
                    this.enemyShipSpecial.destroy();
                    this.enemyShipSpecialExplosionCooldown.reset();
                    recyclable.add(bullet);
                }
            }

        }
        this.bullets.removeAll(recyclable);
        BulletPool.recycle(recyclable);

    }

    /**
     * Checks if two entities are colliding.
     *
     * @param a
     *            First entity, the bullet.
     * @param b
     *            Second entity, the ship.
     * @return Result of the collision test.
     */
    private boolean checkCollision(final Entity a, final Entity b) {
        // Calculate center point of the entities in both axis.
        int centerAX = a.getPositionX() + a.getWidth() / 2;
        int centerAY = a.getPositionY() + a.getHeight() / 2;
        int centerBX = b.getPositionX() + b.getWidth() / 2;
        int centerBY = b.getPositionY() + b.getHeight() / 2;
        // Calculate maximum distance without collision.
        int maxDistanceX = a.getWidth() / 2 + b.getWidth() / 2;
        int maxDistanceY = a.getHeight() / 2 + b.getHeight() / 2;
        // Calculates distance.
        int distanceX = Math.abs(centerAX - centerBX);
        int distanceY = Math.abs(centerAY - centerBY);

        return distanceX < maxDistanceX && distanceY < maxDistanceY;
    }

    /**
     * Returns a GameState object representing the status of the game.
     *
     * @return Current game state.
     */
    public final CustomGameState getGameState() {
        return new CustomGameState(this.level, players, multimethod);
    }

    public boolean allPlayerDie(){
        for(Player p : players){
            if(! p.isDie())
                return false;
        }
        return true;
    }
}