package engine;

import entity.Player;

import java.util.ArrayList;

/**
 * Implements an object that stores the state of the game between levels.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class CustomGameState extends IGameState{

    public enum MultiMethod{LOCAL, P2P};

    private int level;
    private MultiMethod method;

    //플레이어
    ArrayList<Player> players;

    public CustomGameState(final int level, ArrayList<Player> players, MultiMethod method) {
        super(level);
        this.players = players;
        this.method =  method;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxLives(){
        int lives = 0;
        for( Player p : players) {
            if (p.getLives() > lives)
                lives = p.getLives();
        }
        return lives;
    }
    public int getMinLives(){
        int lives = 999;
        for( Player p : players) {
            if (p.getLives() < lives)
                lives = p.getLives();
        }
        return lives;
    }

    public int getPlayernum() {
        return players.size();
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

    @Override
    public String toString(){
        String str = "";
        for( Player p : players){
            str += "["+p.getName() +": ";
            str += ""+p.getLives() +" lives remaining, ";
            str += ""+p.getBulletsShot() +" bullets shot and ";
            str += ""+p.getShipsDestroyed() +" ships destroyed.]\n";
        }

        return str;
    }


}
