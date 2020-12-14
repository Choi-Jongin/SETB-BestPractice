package screen;

abstract public class IScoreScreen extends Screen{


    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param fps    Frames per second, frame rate at which the game is run.
     */
    public IScoreScreen(int width, int height, int fps) {
        super(width, height, fps);
    }
}
