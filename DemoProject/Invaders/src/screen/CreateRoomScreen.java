package screen;

import engine.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateRoomScreen extends Screen {

    /**
     * Milliseconds between changes in user selection.
     */
    private static final int SELECTION_TIME = 200;
    // SIZE - 1
    private static final int MAX_SELECTNUM = 5;

    /**
     * Time between changes in user selection.
     */
    private Cooldown selectionCooldown;
    private Cooldown messageCooldown;
    private Cooldown inputCooldown;

    boolean nowinput = false;

    String message = "";

    int diffcultindex;
    int selectitem = 0;
    int conntime = 0;

    String menuString[] = {"", "PORT : ", "PASSWORD : ", "MY NAME : ", "CREATE", "EXIT"};
    String port = "3000";
    String password = "1234";
    String myname = "Player1";
    RoomPacket roomPacket = new RoomPacket("","");
    public String getMyname() {
        return myname;
    }
    public RoomPacket getRoomPacket() {
        return roomPacket;
    }

    IGameState.Difficult difficult;

    boolean isWait = false;
    int hlinemoveper = 0;
    int hlinestart = 250;
    int hlinesign = 1;
    boolean islocal = true;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param fps    Frames per second, frame rate at which the game is run.
     */
    public CreateRoomScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.messageCooldown = Core.getCooldown(3000);
        this.messageCooldown.reset();
        this.inputCooldown = Core.getCooldown(1000/fps);
        this.inputCooldown.reset();

        this.port = "3000";
        this.password = "1234";
        this.myname = "Player1";
        this.islocal = true;
        this.nowinput = false;
        this.isWait = false;
        this.roomPacket = new RoomPacket("","");

        this.hlinemoveper = 0;
        this.hlinesign = 1;
        this.hlinestart = this.getHeight() / 6 * 4;

        this.returnCode = 5;

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

//        String data = GameServer.getInstance().read();
//        if (data != null) {
//            showMessage(data);
//        }

        if (GameServer.getInstance().isWaiting() || roomPacket.isConn() ) {
            hlinemoveper += (roomPacket.isConn()?1:2) * hlinesign;
            if (hlinemoveper > 100) {
                hlinemoveper = 100;
                hlinesign = -1;
            } else if (hlinemoveper < 0) {
                hlinemoveper = 0;
                hlinesign = 1;
            }
        } else{
            hlinemoveper = 0;
        }
        draw();

        if( roomPacket.isConn() )
        {
            if(++conntime >= fps*2)
            {
                this.returnCode = 1;
                this.isRunning = false;
               // GameServer.getInstance().sendObject(roomPacket);
            }
        }

        if(nowinput && inputCooldown.checkFinished()){
            String in = inputManager.alphanumDown();
            if(in != "")
            switch ( selectitem){
                case 1: port += in; break;
                case 2: password += in; break;
                case 3: myname += in; break;
            }
            inputCooldown.reset();
            inputManager.clearUp();
        }

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
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE) || inputManager.isKeyDown(KeyEvent.VK_ENTER)) {
                Select();
                selectionCooldown.reset();
            }
        }
    }

    private void Select() {
        if (selectitem == 0) {
            islocal = !islocal;
        }  else if (selectitem == 1) {
            port= "";
            startInput();
        } else if (selectitem == 2) {
            password = "";
            startInput();
        } else if (selectitem == 3) {
            myname ="";
            startInput();
        }else if (selectitem == 4) {
            Create();
        } else if (selectitem == MAX_SELECTNUM) {

            GameServer.getInstance().stopServer();
            this.returnCode = 5;
            this.isRunning = false;
        }

    }

    private void Create() {
        if (Integer.parseInt(port) < 3000 || Integer.parseInt(port) >= 10000) {
            showMessage("port is only allowed from 3000 to 9999.");
            return;
        } else if (myname.length() < 1) {
            showMessage("please fill your name");
        }
        ////////////////////////

        GameServer.getInstance().startServer(Integer.parseInt(port), password,roomPacket);
    }

    private void nextMenuItem() {
        endInput();
        if (selectitem == MAX_SELECTNUM)
            selectitem = 0;
        else
            selectitem++;
    }

    private void previousMenuItem() {
        endInput();
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
        String optionString[] = new String[menuString.length];
        optionString[0] = islocal?"local":"external";
        optionString[1] = port;
        optionString[2] = password;
        optionString[3] = myname;
        optionString[4] = "";
        optionString[5] = "";
        String ipstr = islocal ? GameServer.getInstance().getLocalip() : GameServer.getInstance().getMyip();
        drawManager.drawCreateSetting(this, selectitem, ipstr, menuString, optionString);

        if (GameServer.getInstance().isWaiting()) {
            String waiting = "wait";
            for (int i = 0; i < ((50 - (50 - hlinemoveper) * hlinesign) * 3 / 101) + 1; ++i)
                waiting += ".";
            drawManager.drawCenterText(this, waiting, hlinestart - 5, Color.GREEN);
        }

        drawManager.drawHorizontalLine(this, hlinestart + (int) (100 * hlinemoveper / 100));

        if( roomPacket.isConn() ){
            drawManager.drawCenterText(this, roomPacket.getName(), hlinestart + 50, Color.GREEN);
        }
        if (!messageCooldown.checkFinished()) {
            drawManager.drawCenterText(this, message, this.getHeight() / 13 * 12, Color.GREEN);
        }
        drawManager.completeDrawing(this);
    }

    private void showMessage(String msg) {
        message = msg;
        messageCooldown.reset();
    }
    private void startInput(){
        inputManager.clearUp();
        nowinput = true;
    }
    private void endInput(){
        nowinput = false;
    }

}
