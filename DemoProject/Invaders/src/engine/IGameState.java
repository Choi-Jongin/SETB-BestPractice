package engine;

abstract class IGameState {

    /** Current game level. */
    protected int level;

    IGameState(int level){
        this.level = level;
    }
    /**
     * @return the level
     */
    public final int getLevel() {
        return level;
    }

}
