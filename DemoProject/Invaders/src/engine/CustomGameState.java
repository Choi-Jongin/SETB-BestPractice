package engine;

import entity.Player;

import java.util.ArrayList;

/**
 * Implements an object that stores the state of the game between levels.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class CustomGameState extends GameState{

    public enum MultiMethod{local, p2p};
    private int playernum;
    private MultiMethod method;

    //플레이어
    ArrayList<Player> players;

    public CustomGameState(final int level, ArrayList<Player> players, MultiMethod method) {
        super(level,0,0,0,0 );
        this.players = players;
        this.method =  method;
    }

    public int getPlayernum() {
        return playernum;
    }

    public void setPlayernum(int playernum) {
        this.playernum = playernum;
    }

    public MultiMethod getMethod() {
        return method;
    }

    public void setMethod(MultiMethod method) {
        this.method = method;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


}
