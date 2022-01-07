package main.java.Server;

import java.lang.reflect.Array;
import java.util.*;

//Inputs: processed input from user
//Interacts with board to return results
public class Game {
    public static Board board;
    private boolean turnIsWhite;
    private int turnCounter;
    private int SIZE;

    private List<ArrayList<int[]>> currentPaths;
    private int[] currentPos;

    private List<int[]> endPoints;
    private List<int[]> dupEndPoints;

    public Game(int size){
        board = new Board(size);
        SIZE = size;
        turnIsWhite = true;
    }



    //Turn:
        //Say which player to move
        //(Not yet) Check for forced moves
        //GameState picks piece
        //Verify piece pos
        //Get possible moves for piece
        //GameState picks move path
        //Make move path
        //Check for king + winners + valid state

    public boolean getTurn(){
        return turnIsWhite;
    }

    public int getMoves(int[] pos) {
        currentPos = pos;
        currentPaths = board.findAllPaths(pos);
        return currentPaths.size();
    }

    public void makeMove(int pathNum){
        ArrayList<int[]> pathToTake = currentPaths.get(pathNum);
        board.movePiece(currentPos, pathToTake);
    }

    public boolean endOfTurn(){
        assert(board.validateBoard());
        if(board.checkForWinner(turnIsWhite)){
            return true;
        }
        board.checkForKings();
        turnIsWhite = !turnIsWhite;
        turnCounter++;
        return false;
    }

    public boolean verifyPos(int[] pos){
        int y = pos[0];
        int x = pos[1];
        return ((x >= 0 && x < SIZE && y >= 0 && y < SIZE) &&
                board.getTile(pos) != null &&
                board.getTile(pos).getPiece() != null &&
                board.getTile(pos).getPiece().isWhite() == turnIsWhite);
    }

    public char[][] getBasicStateWithPaths(){

        findEndPoints();

        System.out.println(endPoints.toString());
        //TODO: implement multipaths
        char[][] basicState = new char[SIZE][SIZE];

        //int posMoveCount = 1;
        for (int i = 7;i>-1;i--){
            for (int j = 0; j < 8; j++) {
                Integer isAnEndPoint = isInList(new int[]{j, i}, endPoints);
                if(isAnEndPoint!=null) {
                    basicState[i][j] = (char)(isAnEndPoint+1 + '0');
                } else {
                    basicState[i][j] = board.tileToBasic(board.getTile(new int[]{j, i}));
                }
            }
        }
        return basicState;

    }

    private void findEndPoints(){
        endPoints = new ArrayList<>();
        dupEndPoints = new ArrayList<>();
        //Does it work because references would be different ???
        for (ArrayList<int[]> path: currentPaths) {
            int[] endPoint = path.get(path.size() - 1);
            if(endPoints.contains(endPoint)){
                if(!dupEndPoints.contains(endPoint)){
                    dupEndPoints.add(endPoint);
                }
            } else {
                endPoints.add(endPoint);
            }
        }
    }

    public List<int[]> getEndPoints(){
        return endPoints;
    }

    public void printWithPath(){

        findEndPoints();

        System.out.println(endPoints.toString());
        //TODO: implement multipaths
        StringBuilder toPrint = new StringBuilder();
        //int posMoveCount = 1;
        for (int i = 7;i>-1;i--){
            for (int j = 0; j < 8; j++) {
                Integer isAnEndPoint = isInList(new int[]{j, i}, endPoints);
                if(isAnEndPoint!=null) {
                    toPrint.append(isAnEndPoint+1).append("|");
                } else {
                    toPrint.append(board.tileToChar(board.getTile(new int[]{j, i}))).append("|");
                }
            }
            toPrint.append('\n');
        }
        System.out.println(toPrint);

    }

    private Integer isInList(int[] val, List<int[]> ls){
        for (int i = 0; i < ls.size(); i++) {
            if(Arrays.equals(ls.get(i), val)){
                return i;
            }
        }
        return null;
    }

    public void printBoard(){
        board.pprintBoard();
    }

    public char[][] getBasicState(){
        //redo for correct print direction
        char[][] basicState = new char[SIZE][SIZE];
        for (int i = 7;i>-1;i--){
            for (int j = 0; j < 8; j++) {
                basicState[i][j] = board.tileToBasic(board.getTile(new int[]{j, i}));
            }
        }

        return basicState;
    }
}
