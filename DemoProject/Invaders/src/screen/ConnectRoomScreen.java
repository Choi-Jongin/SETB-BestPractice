package screen;

import engine.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.nio.Buffer;

public class ConnectRoomScreen extends Screen {

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

    String menuString[] = { "HOST IP : ", "PORT : ", "PASSWORD : ", "MY NAME : ", "CONNECT", "EXIT"};
    String ip = "";
    String port = "3000";
    String password = "1234";
    private int conntime = 0;

    public String getMyname() {
        return myname;
    }

    String myname = "Player2";

    IGameState.Difficult difficult;

    boolean isWait = false;
    int hlinemoveper = 0;
    int hlinestart = 250;
    int hlinesign = 1;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param fps    Frames per second, frame rate at which the game is run.
     */
    public ConnectRoomScreen(int width, int height, int fps) {
        super(width, height, fps);
        ////
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.messageCooldown = Core.getCooldown(3000);
        this.messageCooldown.reset();
        this.inputCooldown = Core.getCooldown(1000/fps);
        this.inputCooldown.reset();

        this.port = "3000";
        this.password = "1234";
        this.myname = "Player2";

        this.isWait = false;
        this.hlinemoveper = 0;
        this.hlinestart = this.getHeight()/5*3;
        this.hlinesign = 1;
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

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

        hlinemoveper += 1*hlinesign;
        if( hlinemoveper> 100 ) {
            hlinemoveper = 100;
            hlinesign = -1;
        }else if( hlinemoveper < 0  ) {
            hlinemoveper = 0;
            hlinesign = 1;
        }
        if(nowinput && inputCooldown.checkFinished()){
            String in = inputManager.alphanumDown();
            if(in != "")
                switch ( selectitem){
                    case 0: ip += in; break;
                    case 1: port += in; break;
                    case 2: password += in; break;
                    case 3: myname += in; break;
                }
            inputCooldown.reset();
            inputManager.clearUp();
        }
        if( GameServerClient.getInstance().isConnect() )
        {
            if(++conntime >= fps*2)
            {
                this.returnCode = 1;
                this.isRunning = false;
            }
        }
        draw();
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

            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                Select();
                selectionCooldown.reset();
            }
        }
    }

    private void Select() {

        if (selectitem == 0) {
            ip = "";
            startInput();
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
            Connect();
        }else if (selectitem == MAX_SELECTNUM) {

            this.returnCode = 5;
            this.isRunning = false;
        }
    }
    private void Connect(){
        if( Integer.parseInt(port) < 3000 || Integer.parseInt(port) >= 10000 ){
            showMessage("port is only allowed from 3000 to 9999.");
            return;
        }else if( myname.length() < 1 ) {
            showMessage("please fill your name");
            return;
        }

        GameServerClient.getInstance().startClient(ip,Integer.parseInt(port), myname, password);

    }
    private void nextMenuItem() {
        if (selectitem == MAX_SELECTNUM)
            selectitem = 0;
        else
            selectitem++;
        nowinput = false;
    }

    private void previousMenuItem() {
        if (selectitem == 0)
            selectitem = MAX_SELECTNUM;
        else
            selectitem--;
        nowinput = false;
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        String optionString[] = new String[menuString.length];
        optionString[0] = ip;
        optionString[1] = port;
        optionString[2] = password;
        optionString[3] = myname;
        optionString[4] = "";
        optionString[5] = "";
        drawManager.drawConnectSetting(this, selectitem, menuString, optionString);

        drawManager.drawHorizontalLine(this,  hlinestart + (int)(100*hlinemoveper/100));

        if(!messageCooldown.checkFinished()){
            drawManager.drawCenterText(this, message, this.getHeight()/13*12, Color.GREEN);
        }
        drawManager.completeDrawing(this);
    }

    private void showMessage(String msg){
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
