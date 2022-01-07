package main.java.Server;

import java.util.*;

public class Board {
    //0,0 is botleft corner
    //[y][x]
    public Tile[][] state;
    private final int SIZE;

    /*
    0 = Empty Square
    1 = White
    2 = Black
    3 = White King
    4 = Black King;
    */

    //Initialisations
    public Board(int sizeI){
        SIZE = sizeI;
        state = new Tile[SIZE][SIZE];
        initBoard();
    }

    private void initBoard(){
        if(SIZE==8){
            initBoard8();
        } else {
            System.out.println("not implemented");;
        }
    }

    private void initBoard8(){
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if((y+x) % 2 == 0){
                    //Init Piece
                    if(y < 3){
                        state[y][x] = new Tile(x, y, new Piece(true));
                    } else if (y > 4) {
                        state[y][x] = new Tile(x, y, new Piece(false));
                    } else {
                        state[y][x] = new Tile(x, y, null);
                    }
                } else {
                    //idk do something ???
                }
            }
        }

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if((y+x) % 2 == 0){
                    state[y][x].setDirs(
                            //Swapped in y-axis because board displayed flipped
                        (isValidLoc(x-1, y-1) ? state[y-1][x-1] : null),
                        (isValidLoc(x+1, y-1) ? state[y-1][x+1] : null),
                        (isValidLoc(x-1, y+1) ? state[y+1][x-1] : null),
                        (isValidLoc(x+1, y+1) ? state[y+1][x+1] : null)
                    );
                }
            }
        }

    }

    //Conversions
    private boolean isValidLoc(int x, int y){
        return (x >= 0 && y >= 0 && x < SIZE && y < SIZE);
    }

    //Checks for stuff

    //Checks validity of board TODO: assert(validateBoard)

    public boolean validateBoard(){
        boolean isValid = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] == null && (i+j) % 2 == 0){
                    isValid = false;
                }
            }
        }

        return isValid; //Returns if board in valid state
    }

    //Check if either player has won - 0: No, 1: GameState 1, 2: GameState 2
    public boolean checkForWinner(boolean playerWon){
        boolean hasWon = true;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Tile t = state[y][x];
                if(t != null &&
                t.getPiece() != null &&
                t.getPiece().isWhite() != playerWon){
                    hasWon = false;
                }
            }
        }
        return hasWon;
    }

    //Check if any piece should King
    public void checkForKings(){
        int[] kingRows = new int[]{0, SIZE-1};
        boolean[] kingType = new boolean[]{false, true};
        for (int i : new int[]{0,1}) {
            for (int x = 0; x<SIZE; x++) {
                Tile t = state[kingRows[i]][x];
                if(t != null &&
                        t.getPiece() != null &&
                        t.getPiece().isWhite() == kingType[i]){
                    t.getPiece().kingPiece();
                }
            }
        }
    }

    private void checkKingPiece(Tile tile){
        if((tile.getY()==0 && tile.getPiece().isWhite() == false)
                || (tile.getY() == SIZE-1 && tile.getPiece().isWhite() == true)){
            tile.getPiece().kingPiece();
        }
    }

    //Check possible moves of a given piece
    //Note - needs to know previously taken pieces, type of piece moving, check for kings
    public List<ArrayList<int[]>> findAllPaths(int[] pos) {
        //TODO: Need to verify with king, multipaths, others?
        Tile curTile = state[pos[1]][pos[0]];
        if (curTile == null || curTile.getPiece() == null) {
            return null; //null or empty list?
        } else {
            List<ArrayList<int[]>> allPaths = new ArrayList<>();
            ArrayList<int[]> dead = new ArrayList<>();
            findShorts(allPaths, curTile);
            findJumps(allPaths, new ArrayList<int[]>(), dead, curTile, curTile.getPiece());
            return allPaths;
        }
    }

    private void findShorts(List<ArrayList<int[]>> allPaths, Tile curTile) {
        Tile[] tileDirs = curTile.getDirs();
        for (int i : curTile.getPiece().getMoveDir()) {
            Tile tileDir = tileDirs[i];
            if(tileDir != null && tileDir.getPiece() == null){
                allPaths.add(new ArrayList<int[]>(Collections.singleton(tileDir.getPos())));
                System.out.println("Added short");
            }
        }
    }

    private void findJumps(List<ArrayList<int[]>> allPaths, ArrayList<int[]> curPath, List<int[]> dead, Tile curTile, Piece piece) {
        System.out.println(tileToChar(curTile));
        for (int i : piece.getMoveDir()) {
            if(curTile.canJump(i, piece.isWhite()) && !dead.contains(curTile.getDirs()[i].getPos())){
                dead.add(curTile.getDirs()[i].getPos());
                curPath.add(curTile.twiceDir(i).getPos());
                allPaths.add((ArrayList<int[]>) curPath.clone());
                System.out.println(curPath);
                System.out.println(allPaths);
                System.out.println("Added jump");
                System.out.println(tileToChar(curTile.twiceDir(i)));
                findJumps(allPaths, curPath, dead, curTile.twiceDir(i), piece);
                //Remove from end
                dead.remove(dead.size()-1);
                curPath.remove(curPath.size()-1);
            }
        }
    }

            /*
            List<int[]> moves = new ArrayList<>();
            Tile[] tileDirs = curTile.getDirs();
            for (int i : curTile.getPiece().getMoveDir()) {
                System.out.println(i);
                Tile tileDir = tileDirs[i];
                if(tileDir != null){
                    //System.out.println("Tile not null");
                    //System.out.println(tileDir.getPiece().toChar());
                    if(tileDir.getPiece() == null) {
                        moves.add(tileDir.getPos());
                        System.out.println("Added move");

                    } else if (tileDir.canJump(tileDir.getCol(), i)) {
                    }




                    } else if(tileDir.getPiece().isWhite() != curTile.getPiece().isWhite() &&
                            (tileDir.getDirs()[i] != null && tileDir.getDirs()[i].getPiece() == null)) {
                        //Implement existsIsEmpty?
                        moves.add(tileDir.getDirs()[i].getPos());
                        System.out.println("Added jump");
                    }
                }
            }
            int[][] movesArray = new int[moves.size()][2];
            movesArray = moves.toArray(movesArray);
            return movesArray;
        }
        //Returns array of possible moves

             */


    //Update state

    //Moves piece pos to posTo

    //Should be private
    public boolean movePiece(int[] pos, ArrayList<int[]> curPath){
        //UNCHECKED - assumes moves are valid and removes jumped pieces
        int[] moveTo = curPath.remove(0);
        if(Math.abs(moveTo[0]-pos[0])==1){
            state[moveTo[1]][moveTo[0]].setPiece(state[pos[1]][pos[0]].getPiece());
            state[pos[1]][pos[0]].setPiece(null);
        } else {
            state[moveTo[1]][moveTo[0]].setPiece(state[pos[1]][pos[0]].getPiece());
            state[pos[1]][pos[0]].setPiece(null);
            state[(moveTo[1] + pos[1])/2][(moveTo[0] + pos[0])/2].setPiece(null);
            //Tile taken
            if(curPath.size()!=0){
                movePiece(moveTo, curPath);
            }
        }
        return true;
    }




    // For testing purposes - should be moved to DisplayGame
    /*
    public void printBoard(){
        //redo for correct print direction
        for (Piece[] row : state){
            System.out.println(Arrays.toString(row));
        }
    }
    */
    public Tile getTile(int[] pos){
        return state[pos[1]][pos[0]];
    }

    public void pprintBoard(){
        //redo for correct print direction
        String toPrint = "";
        for (int i = 7;i>-1;i--){
            for (int j = 0; j < 8; j++) {
                toPrint = toPrint + tileToChar(state[i][j]) + "|";
            }
            toPrint = toPrint + '\n';
        }
        /*
        for (Map.Entry<Integer, Character> entry : BoardStates.pieceToSymDict.entrySet()) {
            toPrint = toPrint.replace(entry.getKey().toString(), entry.getValue().toString());
            System.out.println(1);
        }
        */
        System.out.println(toPrint);
    }

    public char tileToChar(Tile t){
        if(t==null){
            return BoardStates.nameToSym("N");
        } else if (t.getPiece()==null) {
            return BoardStates.nameToSym("E");
        } else {
            return t.getPiece().toChar();
        }
    }

    public char tileToBasic(Tile t){
        if(t==null){
            return BoardStates.nameToBasic("N");
        } else if (t.getPiece()==null) {
            return BoardStates.nameToBasic("E");
        } else {
            return t.getPiece().toBasic();
        }
    }
}
