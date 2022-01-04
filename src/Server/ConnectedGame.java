package Server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Stream;

public class ConnectedGame {
    private Socket[] sockets;
    private OutputStream[] outputStreams;
    private ObjectOutputStream[] objectOutputStreams;
    private InputStream[] inputStreams;
    private ObjectInputStream[] objectInputStreams;

    private int currentTurn; //1 = white
    private Game game;

    public ConnectedGame(Socket[] sockets) throws IOException, ClassNotFoundException {
        this.sockets = sockets;
        setUpStreams();
        gameLoop();
    }

    private void setUpStreams() throws IOException {
        outputStreams = new OutputStream[2];
        objectOutputStreams = new ObjectOutputStream[2];
        inputStreams = new InputStream[2];
        objectInputStreams = new ObjectInputStream[2];

        for(int i : new int[]{0,1}){
            outputStreams[i] = sockets[i].getOutputStream();
            objectOutputStreams[i] = new ObjectOutputStream(outputStreams[i]);
            inputStreams[i] = sockets[i].getInputStream();
            objectInputStreams[i] = new ObjectInputStream(inputStreams[i]);
        }
    }

    private void gameLoop() throws IOException, ClassNotFoundException {
        makeGame();
        currentTurn = 1;
        initPlayers();
        //setUpScanner();



        boolean run = true;
        while (run) {
            //System.out.println(game.getTurn() ? "White's Turn:" : "Black's Turn");
            //game.printBoard();
            sendTo(currentTurn, "isTurn");
            sendTo(1-currentTurn, "isNotTurn");
            int[] pos;
            do {
                //System.out.println("Input piece co-ordinates:");
                sendTo(currentTurn, "reqPieceCoords");
                //try { TODO: UNCHECKED INPUT
                String[] posString = ((String)listen(currentTurn)).split(",");
                pos = Stream.of(posString).mapToInt(Integer::parseInt).toArray();
                //System.out.println(Arrays.toString(pos));
                //System.out.println(game.verifyPos(pos));
                //} catch (ArrayIndexOutOfBoundsException) {}
            } while (!game.verifyPos(pos));
            int numPaths = game.getMoves(pos);
            //TODO: Check if piece has moves to make
            //TODO: Change display symbol for selected piece
            //game.printWithPath();
            sendTo(currentTurn, "basicStateWithPaths");
            sendTo(currentTurn, game.getBasicStateWithPaths());
            int pathNum;
            do {
                //System.out.println("Input path number:");
                sendTo(currentTurn, "reqPathNo");
                //TODO: UNCHECKED INPUT
                pathNum = Integer.parseInt((String)listen(currentTurn));
            } while (!(pathNum > 0 && pathNum <= numPaths));
            game.makeMove(pathNum - 1);
            updatePlayers();
            if (game.endOfTurn()) {
                run = false;
                //System.out.println((game.getTurn() ? "White" : "Black") + " has won!");
                sendTo(currentTurn, "hasWon");
                sendTo(1-currentTurn, "hasLost");
            }
            currentTurn = 1-currentTurn;

        }
    }

    //Turn:
    //Say which player to move
    //(Not yet) Check for forced moves
    //Player picks piece
    //Get possible moves for piece
    //Player picks move path
    //Make move path
    //Check for king + winners + valid state

    private void makeGame(){
        game = new Game(8);
    }

    private void sendToAll(Object o) throws IOException {
        //char[][] basicGame = game.getBasicState();
        for (ObjectOutputStream oos: objectOutputStreams) {
            oos.writeObject(o);
            oos.flush();
        }
    }

    private void sendTo(int ind, Object o) throws IOException {
        objectOutputStreams[ind].writeObject(o);
        objectOutputStreams[ind].flush();
    }

    private Object listen(int ind) throws IOException, ClassNotFoundException {
        return objectInputStreams[ind].readObject();
    }

    private void initPlayers() throws IOException {
        sendToAll(game.getBasicState());
    }

    private void updatePlayers() throws IOException {
        sendToAll(new String("basicState"));
        sendToAll(game.getBasicState());
    }
}
