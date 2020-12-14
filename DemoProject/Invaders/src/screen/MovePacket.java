package screen;

import java.io.Serializable;

public class MovePacket implements Serializable {
    private static final long serialVersionUID = 3L;

    static int LEFT  = 1;
    static int RIGHT  = 10;
    static int ATTACK  = 100;

    int dir;

    public MovePacket(int dir){
        this.dir = dir;
    }

    public boolean isLeft(){
        return (dir%10) == 1;
    }
    public boolean isRight(){
        return ((dir/10)%10) == 1;
    }
    public boolean isATTACK(){
        return ((dir/100)%10) == 1;
    }
    public String toString(){
        return dir+"";
    }

}
