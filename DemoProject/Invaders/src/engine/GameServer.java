package engine;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {

    /*** server ***/
    private ExecutorService executorService; // 스레드풀
    private ServerSocket serverSocket;
    private ArrayList<Socket> connections = new ArrayList<Socket>();

    BufferedReader in = null;
    BufferedWriter out = null;

    private boolean waiting = false;

    public void startServer(int port) { // 서버 시작 시 호출
        // 스레드풀 생성
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        // 서버 소켓 생성 및 바인딩
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
        } catch (Exception e) {
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
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    // 클라이언트 접속 요청 시 객체 하나씩 생성해서 저장
                    connections.add(socket);
                    waiting = false;
                    System.out.println("[연결 개수: " + connections.size() + "]");
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!serverSocket.isClosed()) {
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

    public String read(){

        if( hasConn() ){
            String re = "";
            try {
                return in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private static GameServer instance;
    public static GameServer getInstance() {
        if (instance == null)
            instance = new GameServer();
        return instance;
    }
}
