package screen;

import engine.IGameState;

import java.io.Serializable;

public class RoomPacket implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String password;

    private IGameState.Difficult difficult;
    public IGameState.Difficult getDifficult() {
        return difficult;
    }


    public boolean isConn() {
        return isConn;
    }

    private boolean isConn = false;
    public RoomPacket(String name, String password){
        this(name,password, IGameState.Difficult.NORMAL);
    }
    public RoomPacket(String name, String password, IGameState.Difficult difficult){
        this.name = name;
        this.password = password;
        this.difficult = difficult;
    }
    public void setRoom( RoomPacket room){
        name = room.getName();
        password = room.getPassword();
        difficult = room.getDifficult();
        isConn = true;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
