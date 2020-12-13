package engine;

import screen.RoomPacket;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;

public class GameServer {

    /*** server ***/
    private ExecutorService executorService; // 스레드풀
    private ServerSocket serverSocket;
    private ArrayList<Socket> connections = new ArrayList<Socket>();

//    BufferedReader in = null;
//    BufferedWriter out = null;

    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;

    InputStream input = null;
    OutputStream output = null;

    RoomPacket roomPacket;

    String myip="172.0.0.1";


    String localip="172.0.0.1";
    private boolean waiting = false;

    public void startServer(int port, String password, RoomPacket room) { // 서버 시작 시 호출
        // 스레드풀 생성
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        // 서버 소켓 생성 및 바인딩
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
            if (!serverSocket.isClosed()) {
                stopServer();
            }
            return;
        }

        // 수락 작업 생성
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("[서버 시작]");

                try {
                    // 연결 수락
                    waiting = true;
                    Socket socket = serverSocket.accept();
                    System.out.println("[연결 수락: " + socket.getRemoteSocketAddress() + ": " + Thread.currentThread().getName() + "]");
                    output = socket.getOutputStream();
                    input = socket.getInputStream();
                    oos = new ObjectOutputStream(output);
                    ois = new ObjectInputStream(input);
//                    in = new BufferedReader(new InputStreamReader(input));
//                    out = new BufferedWriter(new OutputStreamWriter(output));
                    roomPacket = (RoomPacket) ois.readObject();
                    if( roomPacket.getPassword().compareTo(password) != 0) {
                        System.out.println("비밀번호 미일치");
                        socket.close();
                        return;
                    }
                    System.out.println("비밀번호 일치");
                    room.setRoom(roomPacket);
                    oos.writeObject(room);
                    oos.flush();
                    // 클라이언트 접속 요청 시 객체 하나씩 생성해서 저장
                    connections.add(socket);
                    waiting = false;
                    System.out.println("[연결 개수: " + connections.size() + "]");
                } catch (Exception e) {
                    e.printStackTrace();
                    if ( serverSocket != null && !serverSocket.isClosed()) {
                        stopServer();
                    }
                } finally {
                    waiting = false;
                }

            }
        };
        // 스레드풀에서 처리
        executorService.submit(runnable);
    }
    public boolean hasConn(){
        return connections.size() != 0;
    }

    public void sendObject( Object o ){
        try {
            oos.writeObject(o);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
        System.out.println(e.getMessage());}
    }

    public Object readObject() {
        try {
            Object o = ois.readObject();
            if( o.toString().compareTo("0") != 0)
            System.out.println(o.toString() + "클라이언트로 부터 옴");
            return o;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.println(e.getMessage());}
        return null;
    }



    public void stopServer() { // 서버 종료 시 호출
        try {
            // 모든 소켓 닫기
            Iterator<Socket> iterator = connections.iterator();
            while (iterator.hasNext()) {
                Socket client = iterator.next();
                client.close();
                iterator.remove();
            }
            // 서버 소켓 닫기
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            // 스레드풀 종료
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
            System.out.println("[서버 멈춤]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public GameServer(){
        try {
            myip = getip();
            localip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static GameServer instance;
    public static GameServer getInstance() {
        if (instance == null)
            instance = new GameServer();
        return instance;
    }

    private static String getip() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getMyip() {
        return myip;
    }
    public String getLocalip() {
        return localip;
    }
}
