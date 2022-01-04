package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Player player;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;

    public Scanner reader;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();

    }

    private Client() throws IOException, ClassNotFoundException {
        String host = "192.168.1.80";
        host = "2a00:23c7:5b81:fb01:1031:94e1:1918:cce";
        socket = new Socket(host, 7777);
        System.out.println("Connection");

        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        reader = new Scanner(System.in);

        player = new Player();

        waitForStart(); //blocks for gameLoop
        gameLoop();
    }

    private void gameLoop() throws IOException, ClassNotFoundException {
        player.printPlayerState();
        //player.directPrintBoard();

        while(true){
            listen();
        }
    }
    /*
    private void receiveBoard() throws IOException, ClassNotFoundException {
        int[][] receivedState = (int[][]) objectInputStream.readObject();
        player.updatePlayerState(receivedState);
    }
    */
    private void waitForStart() throws IOException, ClassNotFoundException {
        char[][] state = (char[][]) objectInputStream.readObject();
        player.updatePlayerState(state);

    }

    private void listen() throws IOException, ClassNotFoundException {
        String inpString = (String) objectInputStream.readObject();
        switch (inpString) {
            case "basicState":
                System.out.println("Basic recieved");
                char[][] state = (char[][]) objectInputStream.readObject();
                player.updatePlayerState(state);
                //player.printPlayerState();
                break;
            case "basicStateWithPaths":
                char[][] pathState = (char[][]) objectInputStream.readObject();
                player.updatePlayerPathState(pathState);
                //player.directPrintPathBoard();
                player.printPlayerPathState();
                break;
            case "isTurn":
                //TODO
                System.out.println("Your Turn");
                break;
            case "isNotTurn":
                System.out.println("Opponenets Turn");
                break;
            case "reqPieceCoords":
                System.out.println("Input Piece Co-ordinates: ");
                String inpCoords = reader.nextLine();
                sendToServer(inpCoords);
                break;
            case "reqPathNo":
                System.out.println("Input Path No: ");
                String inpPath = reader.nextLine();
                sendToServer(inpPath);
                break;
            case "hasWon":
                System.out.println("Won");
            case "hasLost":
                System.out.println("Lost");


        }
    }
    private void sendToServer(Object o) throws IOException {
     objectOutputStream.writeObject(o);
     objectOutputStream.flush();

    }
}
