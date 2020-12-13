package screen;

import engine.Cooldown;
import engine.Core;
import engine.IGameState;

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

    String message = "";

    int diffcultindex;
    int selectitem = 0;

    String menuString[] = { "HOST IP : ", "PORT : ", "PASSWORD : ", "MY NAME : ", "CONNECT", "EXIT"};
    InetAddress ip = null;
    int port = 3000;
    String password = "1234";
    String myname = "Player2";

    IGameState.Difficult difficult;

    /*** server ***/
    Socket clinetsocket = new Socket();
    BufferedReader in = null;
    BufferedWriter out = null;

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

        this.port = 3000;
        this.password = "1234";
        this.myname = "Player1";

        this.isWait = false;
        this.hlinemoveper = 0;
        this.hlinestart = this.getHeight()/5*3;
        this.hlinesign = 1;
        /////////

        try {
            ip = InetAddress.getLocalHost();
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
            }
        }
    }

    private void Select() {

        if (selectitem == 4) {
            Connect();
        }
        if (selectitem == MAX_SELECTNUM) {

            this.returnCode = 5;
            this.isRunning = false;
        }
    }
    private void Connect(){
        if( port < 3000 || port >= 10000 ){
            showMessage("port is only allowed from 3000 to 9999.");
            return;
        }else if( myname.length() < 1 ) {
            showMessage("please fill your name");
            return;
        }
        try {
//            clinetsocket.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),port));
//            clinetsocket.connect( new InetSocketAddress(ip.getHostAddress(),port));

            clinetsocket = new Socket(ip.getHostAddress(),port);

            BufferedReader in
                    = new BufferedReader( new InputStreamReader(clinetsocket.getInputStream()));
            BufferedWriter out
                    = new BufferedWriter( new OutputStreamWriter(clinetsocket.getOutputStream()));
            out.write(myname + password);
            out.flush();

        } catch (ConnectException e) {
            System.out.println(e.getMessage());
            showMessage(e.getMessage());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void nextMenuItem() {
        if (selectitem == MAX_SELECTNUM)
            selectitem = 0;
        else
            selectitem++;
    }

    private void previousMenuItem() {
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
        optionString[0] = ip.getHostAddress();
        optionString[1] = port +"";
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

}
