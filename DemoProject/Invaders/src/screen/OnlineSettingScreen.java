package screen;

import engine.Cooldown;
import engine.Core;
import engine.IGameState;

import java.awt.event.KeyEvent;

public class OnlineSettingScreen extends Screen {

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;
    // SIZE - 1
    private static final int MAX_SELECTNUM = 2;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

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
    public OnlineSettingScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();


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

    private void Select() {
        if( selectitem == MAX_SELECTNUM) {

        }

        this.returnCode = -1;
        this.isRunning = false;
    }

    private void nextMenuItem() {
        if (selectitem == MAX_SELECTNUM)
            selectitem = 0;
        else
            selectitem++;
    }

    private void previousMenuItem() {
        if (selectitem == 0)
            selectitem = MAX_SELECTNUM;
        else
            selectitem--;
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawOnlineSettingTitle(this);
        drawManager.drawSettingMenu(this, selectitem,new String[]{
                "Create Room", "Connect Room","Exit",
        } );
        drawManager.completeDrawing(this);
    }


    public int getSelectitem() {
        return selectitem;
    }

}
