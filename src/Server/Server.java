package Server;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Stream;

public class Server {
    public static Scanner reader;


    public static void main(String[] args) throws IOException {
            String PORT = "7777";

            Socket[] sockets = waitForSockets();

            GameInstance gameInstance = new GameInstance(sockets);

        }

    private static Socket[] waitForSockets() throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);
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
