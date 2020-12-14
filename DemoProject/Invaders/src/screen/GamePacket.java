package screen;

import entity.Bullet;
import entity.EnemyShip;
import entity.EnemyShipFormation;
import entity.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class GamePacket implements Serializable {
    private static final long serialVersionUID = 2L;

    private ArrayList<Player> players;
    private EnemyShip es;
    private EnemyShipFormation esf;

    private Set<Bullet> bullets;

    GMSG msgs[];


    public GamePacket(ArrayList<Player> players, EnemyShip es, EnemyShipFormation esf, Set<Bullet> bullets,GMSG [] msgs) {
        this.players = players;
        this.es = es;
        this.esf = esf;
        this.bullets = bullets;
        this.msgs = msgs;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public EnemyShip getEs() {
        return es;
    }
    public EnemyShipFormation getEsf() {
        return esf;
    }
    public Set<Bullet> getBullets() {
        return bullets;
    }
    public boolean hasMsg(String str) {
        for (int i = 0 ; i < msgs.length ; i++){
            if( msgs[i].getMsg().compareTo(str) == 0)
                return true;
        }
        return false;
    }

}
class GMSG implements Serializable {
    String msg;
    public GMSG(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return msg;
    }
}
