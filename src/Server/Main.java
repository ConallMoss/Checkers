package Server;

import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static Scanner reader;


    public static void main(String[] args) {
        makeBoard();
        boolean run = true;
        setUpScanner();
        while(run) {
            System.out.println("test");
            int input = reader.nextInt();
            System.out.println(input);
            if(input==1){run=false;}
        }

    }

    public static void makeBoard(){

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
