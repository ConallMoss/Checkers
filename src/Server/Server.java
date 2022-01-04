package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    public static Scanner reader;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(InetAddress.getLocalHost());
            String PORT = "7777";

            Socket[] sockets = waitForSockets();

            ConnectedGame connectedGame = new ConnectedGame(sockets);

        }

    private static Socket[] waitForSockets() throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);
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
