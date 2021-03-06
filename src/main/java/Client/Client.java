package main.java.Client;

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

        initWindow();

        System.out.println(1);
        outputStream = socket.getOutputStream();
        System.out.println(2);
        objectOutputStream = new ObjectOutputStream(outputStream);
        System.out.println(3);
        inputStream = socket.getInputStream();
        System.out.println(4);
        objectInputStream = new ObjectInputStream(inputStream);
        System.out.println("1");

        /*
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });

         */

        //timer.start();
        waitForStart(); //blocks for gameLoop
        gameLoop();
    }

    private void gameLoop() throws IOException, ClassNotFoundException {
        screen.gameState.directPrintBoard();
        screen.gameStarted = true;
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
        System.out.println("Waiting");
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
                screen.repaint();
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

        window = new JFrame("Checker Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen = new Screen(this);
        window.add(screen);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        timer = new Timer(25, screen);
        timer.start();

    }
}
