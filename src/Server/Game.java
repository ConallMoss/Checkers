package Server;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//Inputs: processed input from user
//Interacts with board to return results
public class Game {
    public static Board board;
    private int turn;
    private int turnCounter;

    private List<ArrayList<int[]>> currentPaths

    public Game(int size){
        board = new Board(8);
    }



    //Turn:
        //Say which player to move
        //Player picks piece
        //Get possible moves for piece
        //Player picks move path
        //Make move path
        //Check for king + winners + valid state

    public int getTurn(){
        return turn;
    }

    private _ getMoves(int[] pos){
        List<ArrayList<int[]>> currentPaths = board.findAllPaths(pos);
        Set<int[]> endPoints = new LinkedHashSet<>();
        Set<int[]> dupEndPoints = new LinkedHashSet<>();

        for (ArrayList<int[]> path: currentPaths) {

        }
        //Pretty print board with paths
    }
}
