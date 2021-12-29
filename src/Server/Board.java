package Server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Board {
    //0,0 is botleft corner
    //[y][x]
    private Piece[][] state;
    private int turn;
    private int size;

    /*
    0 = Empty Square
    1 = White
    2 = Black
    3 = White King
    4 = Black King;
    */

    //Initialisations
    public Board(int sizeI){
        size = sizeI;
        state = new Piece[size][size];
        turn = 0;
        initBoard();

    }

    private void initBoard(){
        if(size==8){
            initBoard8();
        } else {
            System.out.println("not implemented");;
        }
    }

    private void initBoard8(){
        //state = BoardStates.start;
    }

    //Conversions


    //Checks for stuff

    //Checks validity of board
    private boolean validateBoard(){
        boolean isValid = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(state[i][j] == null && i+j % 2 == 0){
                    isValid = false;
                }
            }
        }

        return isValid; //Returns if board in valid state
    }

    //Check if either player has won - 0: No, 1: Player 1, 2: Player 2
    private int checkForWinner(){
        return 0;
    }

    //Check if any piece should King
    private void checkForKings(){
        //To implement
    }

    //Check possible moves of a given piece
    //Note - needs to know previously taken pieces, type of piece moving, check for kings
    private int[] checkMove(int[] pos){

        return new int[0]; //Returns array of possible moves
    }

    //Update state

    //Moves piece pos to posTo
    private boolean movePiece(int pos, int posTo){
        return true; //Returns true if done, false if was not done i.e. error
    }


    // For testing purposes - should be moved to DisplayGame

    public void printBoard(){
        //redo for correct print direction
        for (Piece[] row : state){
            System.out.println(Arrays.toString(row));
        }
    }

    public void pprintBoard(){
        //redo for correct print direction
        String toPrint = "";
        for (int i = 7;i>-1;i--){
            toPrint = toPrint + Arrays.toString(state[i]) + '\n';
        }
        for (Map.Entry<Integer, Character> entry : BoardStates.pieceToSymDict.entrySet()) {
            toPrint = toPrint.replace(entry.getKey().toString(), entry.getValue().toString());
            System.out.println(1);
        }
        System.out.println(toPrint);
    }
}
