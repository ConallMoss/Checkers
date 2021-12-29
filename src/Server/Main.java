package Server;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

    }

    public static void test3(){
        Piece[][] thing = new Piece[4][4];
        System.out.println(thing[1][1]);
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
