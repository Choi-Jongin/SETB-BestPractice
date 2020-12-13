package screen;

import engine.Cooldown;
import engine.Core;
import engine.GameSettings;
import engine.IGameState;
import entity.Bullet;
import entity.EnemyShip;
import entity.EnemyShipFormation;
import entity.Entity;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

abstract public class IGameScreen extends Screen {

    /** Milliseconds until the screen accepts user input. */
    protected static final int INPUT_DELAY = 6000;
    /** Bonus score for each life remaining at the end of the level. */
    protected static final int LIFE_SCORE = 100;
    /** Minimum time between bonus ship's appearances. */
    protected static final int BONUS_SHIP_INTERVAL = 20000;
    /** Maximum variance in the time between bonus ship's appearances. */
    protected static final int BONUS_SHIP_VARIANCE = 10000;
    /** Time until bonus ship explosion disappears. */
    protected static final int BONUS_SHIP_EXPLOSION = 500;
    /** Time from finishing the level to screen change. */
    protected static final int SCREEN_CHANGE_INTERVAL = 1500;
    /** Height of the interface separation line. */
    protected static final int SEPARATION_LINE_HEIGHT = 55;

    /** Current game difficulty settings. */
    protected GameSettings gameSettings;
    /** Current difficulty level number. */
    protected int level;
    protected IGameState.Difficult difficult;
    /** Formation of enemy ships. */
    protected EnemyShipFormation enemyShipFormation;
    /** Bonus enemy ship that appears sometimes. */
    protected EnemyShip enemyShipSpecial;
    /** Minimum time between bonus ship appearances. */
    protected Cooldown enemyShipSpecialCooldown;
    /** Time until bonus ship explosion disappears. */
    protected Cooldown enemyShipSpecialExplosionCooldown;
    /** Time from finishing the level to screen change. */
    protected Cooldown screenFinishedCooldown;
    /** Set of all bullets fired by on screen ships. */

    //모든 총알
    protected Set<Bullet> bullets;
    /** Moment the game starts. */
    protected long gameStartTime;
    /** Checks if the level is finished. */
    protected boolean levelFinished;
    /** Checks if a bonus life is received. */
    protected boolean bonusLife;
    //일시정기 키입력 간격
    protected Cooldown pauseDelay;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param fps    Frames per second, frame rate at which the game is run.
     */
    public IGameScreen(int width, int height, int fps) {
        super(width, height, fps);
    }

    public void initialize() {
        super.initialize();

        enemyShipFormation = new EnemyShipFormation(this.gameSettings);
        enemyShipFormation.attach(this);

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

        this.pauseDelay = Core.getCooldown(200);
        this.pauseDelay.reset();

    }

    protected double getDifficultScore(){
        switch (difficult){
            case EASY -> {
                return 0.7;
            }
            case NORMAL -> {
                return 1.0;
            }
            case HARD -> {
                return 1.4;
            }
        }
        return 1.0;
    }

    /**
     * Cleans bullets that go off screen.
     */
    protected void cleanBullets() {
        Set<Bullet> recyclable = new HashSet<Bullet>();
        for (Bullet bullet : this.bullets) {
            bullet.update();
            if (bullet.getPositionY() < SEPARATION_LINE_HEIGHT
                    || bullet.getPositionY() > this.height)
                recyclable.add(bullet);
        }
        this.bullets.removeAll(recyclable);
        //BulletPool.recycle(recyclable);
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
    protected boolean checkCollision(final Entity a, final Entity b) {
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
}
