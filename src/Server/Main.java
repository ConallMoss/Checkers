package Server;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static Scanner reader;


    public static void main(String[] args) {

            gameLoop();

        }

    public static void gameLoop(){
        Game game = makeGame();
        setUpScanner();
        boolean run = true;
        while(run) {
            System.out.println(game.getTurn() ? "White's Turn:" : "Black's Turn");
            game.printBoard();
            int[] pos;
            do{
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
            do{
                System.out.println("Input path number:");
                //TODO: UNCHECKED INPUT
                pathNum = reader.nextInt();
            } while (!(pathNum > 0 && pathNum <= numPaths));
            game.makeMove(pathNum-1);
            if(game.endOfTurn()){
                run = false;
                System.out.println((game.getTurn()?"White":"Black")+ " has won!");
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
    }

    public static void scannerthing() {
        boolean run = true;
        setUpScanner();
        while (run) {
            System.out.println("test");
            String input = reader.nextLine();
            String[] pos = input.split(" ");

            System.out.println(pos[0]);
            System.out.println(pos[1]);
            if (input == "") {
                run = false;
            }
        }
    }

    public static Game makeGame(){
        Game game = new Game(8);
        return game;
    }

    public static void setUpScanner(){
        reader = new Scanner(System.in);
    }

    public static void testBoard(){
        Board board = new Board(8);
        board.state[3][1].setPiece(new Piece(false));
        board.state[6][2].setPiece(null);
        board.pprintBoard();
        System.out.println(board.tileToChar(board.getTile(new int[]{2,2})));
        List<ArrayList<int[]>> allPaths = board.findAllPaths(new int[]{2, 2});
        System.out.println(Arrays.toString(((allPaths.get(0).get(0)))));
        System.out.println(Arrays.toString(((allPaths.get(1).get(0)))));
        System.out.println(Arrays.toString(((allPaths.get(2).get(1)))));
        //System.out.println(Arrays.toString(((allPaths.get(3).get(0)))));
        board.movePiece(new int[]{2,2}, allPaths.get(1));
        List<ArrayList<int[]>> allPaths2 = board.findAllPaths(new int[]{0, 4});
        board.movePiece(new int[]{0,4}, allPaths2.get(0));
        System.out.println(Arrays.deepToString(allPaths.toArray()));
        board.pprintBoard();

    }

    public static void test3(){
        Piece[][] thing = new Piece[4][4];
        System.out.println(thing[1][1]);

        ArrayList<int[]> thing2 = new ArrayList<>();
        ArrayList thing3 = (ArrayList) thing2.clone();
        //thing3 = thing2.clone();
    }

    public static void test2(){
        System.out.println(BoardStates.pieceToSymDict.get(-1));
        Board board = new Board(8);
        board.pprintBoard();
        Board x = null;
    }

    public static void test(){
        System.out.println("hi");
        System.out.println((new int[3])[0]);
        int[][] array = new int[8][8];
        System.out.println(Arrays.deepToString(array));

        for (int[] row : array){
            System.out.println(Arrays.toString(row));
        }
    }
}
