package Server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Stream;

public class GameInstance {
    private Socket[] sockets;
    private OutputStream[] outputStreams;
    private ObjectOutputStream[] objectOutputStreams;
    private InputStream[] inputStreams;
    private ObjectInputStream[] objectInputStreams;

    private int currentTurn; //1 = white
    private Game game;

    public GameInstance(Socket[] sockets) throws IOException {
        this.sockets = sockets;
        setUpStreams();
        gameLoop();
    }

    private void setUpStreams() throws IOException {
        for(int i : new int[]{0,1}){
            outputStreams[i] = sockets[i].getOutputStream();
            objectOutputStreams[i] = new ObjectOutputStream(outputStreams[i]);
            inputStreams[i] = sockets[i].getInputStream();
            objectInputStreams[i] = new ObjectInputStream(inputStreams[i]);
        }
    }

    private void gameLoop() {
        makeGame();
        currentTurn = 1;
        //setUpScanner();



        boolean run = true;
        while (run) {
            System.out.println(game.getTurn() ? "White's Turn:" : "Black's Turn");
            game.printBoard();
            int[] pos;
            do {
                System.out.println("Input piece co-ordinates:");
                //try { TODO: UNCHECKED INPUT
                String[] posString = reader.next().split(",");
                pos = Stream.of(posString).mapToInt(Integer::parseInt).toArray();
                System.out.println(Arrays.toString(pos));
                System.out.println(game.verifyPos(pos));
                //} catch (ArrayIndexOutOfBoundsException) {}
            } while (!game.verifyPos(pos));
            int numPaths = game.getMoves(pos);
            //TODO: Check if piece has moves to make
            //TODO: Change display symbol for selected piece
            game.printWithPath();
            int pathNum;
            do {
                System.out.println("Input path number:");
                //TODO: UNCHECKED INPUT
                pathNum = reader.nextInt();
            } while (!(pathNum > 0 && pathNum <= numPaths));
            game.makeMove(pathNum - 1);
            if (game.endOfTurn()) {
                run = false;
                System.out.println((game.getTurn() ? "White" : "Black") + " has won!");
            }
            currentTurn = ~currentTurn;

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

    private void sendStateToAll() throws IOException {
        char[][] basicGame = game.getBasicState();
        for (ObjectOutputStream oos: objectOutputStreams) {
            oos.writeObject(basicGame);
            oos.flush();
        }
    }

    private void initGame(){

    }

    private void updatePlayers(){

    }
}
