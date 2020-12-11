package engine;

public abstract class IGameState {

    public enum Difficult{EASY, NORMAL, HARD};

    /** Current game level. */
    protected int level;
    /** Current game difficult. */
    Difficult difficult;

    public IGameState(int level, Difficult difficult){
        this.level = level;
        this.difficult = difficult;
    }

    /**
     * @return the level
     */
    public final int getLevel() {
        return level;
    }

    public Difficult getDifficult() {
        return difficult;
    }

    public void setDifficult(Difficult difficult) {
        this.difficult = difficult;
    }

}
