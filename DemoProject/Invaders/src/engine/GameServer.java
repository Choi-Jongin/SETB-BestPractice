package engine;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {

    private static GameServer instance;

    /*** server ***/
    ServerSocket lister = null;  //서버 생성을 위한 ServerSocket
    Socket server_socket = null;

    public GameServer(){

    }

    void conn(InetAddress ip, int port){
        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try {
                    lister = new ServerSocket(port);
                    server_socket = lister.accept();
                    BufferedReader in
                            = new BufferedReader(new InputStreamReader(server_socket.getInputStream()));
                    BufferedWriter out
                            = new BufferedWriter(new OutputStreamWriter(server_socket.getOutputStream()));

                } catch (IOException e) {
                    System.out.println("해당 포트가 열려있습니다.");
                }

            }
        };
        // 스레드풀에서 처리
        executorService.submit(runnable);
    }





    public static GameServer getInstance() {
        if (instance == null)
            instance = new GameServer();
        return instance;
    }
}
