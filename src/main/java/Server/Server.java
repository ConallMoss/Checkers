package main.java.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    public static Scanner reader;
    public static ServerSocket serverSocket;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(InetAddress.getLocalHost());

        String PORT = System.getenv("PORT");
        if (PORT == null){
            PORT = "7777";
        }
        serverSocket = new ServerSocket(Integer.parseInt(PORT));

            //Socket[] sockets = waitForSockets();
            while(true) {
                Socket[] sockets = waitForSockets();
                try {
                    ConnectedGame connectedGame = new ConnectedGame(sockets);
                } catch (IOException e) {
                }
            }
    }

    private static Socket[] waitForSockets() throws IOException {


        //serverSocket.bind()
        Socket[] sockets = new Socket[2];
        System.out.println("Awaiting connections...");
        sockets[0] = serverSocket.accept();
        System.out.println("Connection from: " + sockets[0]);
        sockets[1] = serverSocket.accept();
        System.out.println("Connection from: " + sockets[1]);
        return sockets;
    }






    public static void setUpScanner(){
        reader = new Scanner(System.in);
    }


}
