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
    private static final int MAX_SELECTNUM = 4;

    /**
     * Time between changes in user selection.
     */
    private Cooldown selectionCooldown;
    private Cooldown messageCooldown;

    String message = "";

    int diffcultindex;
    int selectitem = 0;

    String menuString[] = { "PORT : ", "PASSWORD : ", "MY NAME : ", "CREATE","EXIT"};
    int port = 3000;
    String password = "1234";
    String myname = "Player1";

    IGameState.Difficult difficult;

    /*** server ***/
    ServerSocket lister = null;  //서버 생성을 위한 ServerSocket
    Socket server_socket = null;

    boolean isWait = false;
    boolean isConnected = false;
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
    public CreateRoomScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.messageCooldown = Core.getCooldown(3000);
        this.messageCooldown.reset();

        this.port = 3000;
        this.password = "1234";
        this.myname = "Player1";

        this.isWait = false;
        this.isConnected = false;
        this.hlinemoveper = 0;
        this.hlinesign = 1;
        this.hlinestart = this.getHeight()/6*4;

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

        String data = GameServer.getInstance().read();
        if( data != null){
            showMessage(data);
        }

        if( GameServer.getInstance().isWaiting()) {
            hlinemoveper += 1 * hlinesign;
            if (hlinemoveper > 100) {
                hlinemoveper = 100;
                hlinesign = -1;
            } else if (hlinemoveper < 0) {
                hlinemoveper = 0;
                hlinesign = 1;
            }
        }else hlinemoveper = 0;
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

            if (inputManager.isKeyDown(KeyEvent.VK_SPACE) || inputManager.isKeyDown(KeyEvent.VK_ENTER)) {
                Select();
                selectionCooldown.reset();
            }
        }
    }

    private void Select() {
        if (selectitem == 0) {

        }else if (selectitem == 3) {
            Create();
        }else if (selectitem == MAX_SELECTNUM) {

            this.returnCode = 5;
            this.isRunning = false;
        }

    }

    private void Create() {
        if (port < 3000 || port >= 10000) {
            showMessage("port is only allowed from 3000 to 9999.");
            return;
        } else if (myname.length() < 1) {
            showMessage("please fill your name");
        }
        ////////////////////////

        GameServer.getInstance().startServer(port);


//        ExecutorService executorService = Executors.newFixedThreadPool(
//                Runtime.getRuntime().availableProcessors()
//        );
//
//        isWait = true;
//        this.hlinemoveper = 0;
//        this.hlinesign = 1;
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    lister = new ServerSocket(port);
//
//                    server_socket = lister.accept();
//                    BufferedReader in
//                            = new BufferedReader(new InputStreamReader(server_socket.getInputStream()));
//                    BufferedWriter out
//                            = new BufferedWriter(new OutputStreamWriter(server_socket.getOutputStream()));
//
//                    showMessage(in.readLine());
//                    isWait = false;
//                } catch (IOException e) {
//                    System.out.println("해당 포트가 열려있습니다.");
//                }
//
//            }
//        };
//        // 스레드풀에서 처리
//        executorService.submit(runnable);
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
        optionString[0] = port +"";
        optionString[1] = password;
        optionString[2] = myname;
        optionString[3] = "";
        optionString[4] = "";
        drawManager.drawCreateSetting(this, selectitem, getMyIP(), menuString, optionString);

        if( GameServer.getInstance().isWaiting() ) {
            String waiting = "wait";
            for( int i = 0 ; i < ((50-(50-hlinemoveper)*hlinesign) *3/101)+1 ; ++i)
                waiting += ".";
            drawManager.drawCenterText(this, waiting, hlinestart - 5, Color.GREEN);
         }
        drawManager.drawHorizontalLine(this, hlinestart + (int) (100 * hlinemoveper / 100));

        if(!messageCooldown.checkFinished()){
            drawManager.drawCenterText(this, message, this.getHeight()/13*12, Color.GREEN);
        }
        drawManager.completeDrawing(this);
    }

    public String getMyIP() {
        String ipstr = "unknown";
        try {

            InetAddress ip = InetAddress.getLocalHost();
            ipstr = ip.getHostAddress();
        } catch (Exception e) {
            System.out.println(e);
        }

        return ipstr;
    }

    private void showMessage(String msg){
        message = msg;
        messageCooldown.reset();
    }

}
