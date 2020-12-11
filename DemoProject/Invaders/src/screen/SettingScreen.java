package screen;

import engine.Cooldown;
import engine.Core;
import engine.IGameState;

import java.awt.event.KeyEvent;

public class SettingScreen extends Screen {

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    String  difficultstr[] = {"< EAST >", "< NORMAR >", "< HARD >"};
    int     diffcultindex;
    int     selectitem = 0;


    IGameState.Difficult difficult;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public SettingScreen(final int width, final int height, final int fps,  IGameState.Difficult difficult) {
        super(width, height, fps);

        // Defaults to play.
        this.returnCode = 0;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();

        this.difficult = difficult;
        switch (difficult){
            case EASY -> {
                diffcultindex = 0;
            }
            case NORMAL -> {
                diffcultindex = 1;
            }
            case HARD -> {
                diffcultindex = 2;
            }
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
        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()) {
            //상하
            if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
                previousMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
                nextMenuItem();
                this.selectionCooldown.reset();
            }

            //좌우
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) {
                switch (selectitem){
                    case 0:
                        if( --diffcultindex < 0 ) diffcultindex = 2;
                        break;
                }

                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) {
                switch (selectitem){
                    case 0:
                        if( ++diffcultindex > 2 ) diffcultindex = 0;
                        break;
                }
                this.selectionCooldown.reset();
            }


            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                Select();
            }
        }
    }

    /**
     * Shifts the focus to the next menu item.
     */
    private void nextMenuItem() {
        if (selectitem == 1)
            selectitem = 0;
        else
            selectitem++;
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void previousMenuItem() {
        if (selectitem == 0)
            selectitem = 1;
        else
            selectitem--;
    }

    private void Select(){
        if( selectitem == 1) {
            switch (diffcultindex){
                case 0: difficult = IGameState.Difficult.EASY; break;
                case 1: difficult = IGameState.Difficult.NORMAL; break;
                case 2: difficult = IGameState.Difficult.HARD; break;
            }

            this.returnCode = -1;
            this.isRunning = false;
        }
    }
    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawSettingTitle(this);
        drawManager.drawSettingMenu(this, selectitem,new String[]{
                difficultstr[diffcultindex],
                        "Exit"
        } );
        drawManager.completeDrawing(this);
    }

    public IGameState.Difficult getDifficult() {
        return difficult;
    }

}
