package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;

/**
 * Implements the title screen.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class TitleScreen extends Screen {

	/** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 200;
	private static final int MAX_SELECTNUM = 5;

	/** Time between changes in user selection. */
	private Cooldown selectionCooldown;


	private int selectItem = 0;
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
	public TitleScreen(final int width, final int height, final int fps) {
		super(width, height, fps);

		// Defaults to play.
		selectItem = 0;
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
			if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
				previousMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
				nextMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
				switch (selectItem){
					case 0: this.returnCode = 0; break;
					case 1: this.returnCode = 1; break;
					case 2: this.returnCode = 5; break;
					case 3: this.returnCode = 2; break;
					case 4: this.returnCode = 3; break;
					case 5: this.returnCode = 4; break;
				}
				this.isRunning = false;
			}
		}
	}

	/**
	 * Shifts the focus to the next menu item.
	 */
	private void nextMenuItem() {
		if (this.selectItem == MAX_SELECTNUM)
			this.selectItem = 0;
		else
			this.selectItem++;
	}

	/**
	 * Shifts the focus to the previous menu item.
	 */
	private void previousMenuItem() {
		if (this.selectItem == 0)
			this.selectItem = MAX_SELECTNUM;
		else
			this.selectItem--;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawTitle(this);
		drawManager.drawMenu(this, selectItem);
		drawManager.completeDrawing(this);
	}

	public int getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}
}
