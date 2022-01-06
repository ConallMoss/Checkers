package Client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;

    private Timer timer;
    private Screen screen;
    private JFrame window;
    public Scanner reader;

    private boolean run;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();

    }

    private Client() throws IOException, ClassNotFoundException {
        String host = "192.168.1.80";
        host = "2a00:23c7:5b81:fb01:1ce8:7f70:8888:28b9";
        //host = "localhost";
        socket = new Socket(host, 7777);
        System.out.println("Connection");

        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        reader = new Scanner(System.in);

        /*
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });

         */

        initWindow();

        waitForStart(); //blocks for gameLoop
        gameLoop();
    }

    private void gameLoop() throws IOException, ClassNotFoundException {
        timer.start();
        screen.gameState.directPrintBoard();
        run = true;
        while(true){
            listen();
        }
    }
    /*
    private void receiveBoard() throws IOException, ClassNotFoundException {
        int[][] receivedState = (int[][]) objectInputStream.readObject();
        GameState.updatePlayerState(receivedState);
    }
    */
    private void waitForStart() throws IOException, ClassNotFoundException {
        char[][] state = (char[][]) objectInputStream.readObject();
        screen.gameState.updatePlayerState(state);
        boolean isWhite = (boolean) objectInputStream.readObject();
        screen.gameState.isWhite = isWhite;

    }

    private void listen() throws IOException, ClassNotFoundException {
        String inpString = (String) objectInputStream.readObject();
        System.out.println(inpString);
        switch (inpString) {
            case "basicState":
                System.out.println("Basic recieved");
                char[][] state = (char[][]) objectInputStream.readObject();
                screen.gameState.updatePlayerState(state);
                //GameState.printPlayerState();
                break;
            case "basicStateWithPaths":
                char[][] pathState = (char[][]) objectInputStream.readObject();
                screen.gameState.updatePlayerState(pathState);
                List<int[]> endPoints = (ArrayList<int[]>) objectInputStream.readObject();
                screen.gameState.updatePathEnds(endPoints);
                //GameState.directPrintPathBoard();
                break;
            case "isTurn":
                //TODO
                screen.isCurrentTurn = true;
                break;
            case "isNotTurn":
                screen.isCurrentTurn = false;
                break;
            case "reqPieceCoords":
                screen.isPieceOrPath = true;
                screen.getPiece();

                break;
            case "reqPathNo":
                screen.isPieceOrPath = true;
                screen.getPath();
                break;
            case "hasWon":
                screen.hasWon = true;
                run = false;
                System.out.println("Won");
                break;
            case "hasLost":
                screen.hasLost = true;
                run = false;
                System.out.println("Lost");


        }
    }

    private void sendToServer(Object o) throws IOException {
     objectOutputStream.writeObject(o);
     objectOutputStream.flush();
    }

    public void sendPiece(int x, int y) throws IOException {
        String send = x + "," + y;
        sendToServer(send);
    }

    public void sendPath(int p) throws IOException {
        sendToServer(p);
    }

    private void initWindow() {

        window = new JFrame("test");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen = new Screen(this);
        window.add(screen);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        timer = new Timer(25, screen);
    }
}
