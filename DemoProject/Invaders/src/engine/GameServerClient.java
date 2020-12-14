package engine;

import screen.GamePacket;
import screen.RoomPacket;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class GameServerClient {

    Socket socket;
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;
    InputStream input = null;
    OutputStream output = null;


    private boolean isConnect;

    public GameServerClient() {

    }

    public void startClient(String ip, int port, String name, String password) {
        // 스레드 생성
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    // 소켓 생성 및 연결 요청
                    socket = new Socket(ip, port);
                    //socket.connect(new InetSocketAddress(ip, port));
                    System.out.println("[서버연결]");

                    output = socket.getOutputStream();
                    input = socket.getInputStream();
                    oos = new ObjectOutputStream(output);
                    ois = new ObjectInputStream(input);
                    System.out.println("연결정보"+name + password);
                    oos.writeObject(new RoomPacket(name, password));
                    oos.flush();
                    RoomPacket room = (RoomPacket) ois.readObject();
                    if( room.isConn() ) {
                        setConnect(true);
                    }else{
                        socket.close();
                    }
                } catch (Exception e) {
                    System.out.println("[서버 통신 안됨]");
                    if (socket!=null && !socket.isClosed()) {
                        stopClient();
                    }
                    return;
                }
            }
        };
        // 스레드 시작
        thread.start();
    }

    public void stopClient() {
        try {
            System.out.println("[연결 끊음]");
            // 연결 끊기
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            setConnect(false);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.println(e.getMessage());}
    }

    public void sendObject( Object o ){
        try {
            oos.reset();
            oos.writeObject(o);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            System.out.println("아웃풋 미생성");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Object readObject() {
        try {
            Object o = ois.readObject();
            return o;
        }catch (WriteAbortedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    private static GameServerClient instance;

    public static GameServerClient getInstance() {
        if (instance == null)
            instance = new GameServerClient();
        return instance;
    }

}