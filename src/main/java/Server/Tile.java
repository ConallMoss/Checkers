package main.java.Server;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private Piece piece;
    private final int x;
    private final int y;

    /*
    private Tile upLeft; //Up = increase in y
    private Tile upRight; // Right = increase in x
    private Tile downLeft;
    private Tile downRight;
    */
    private Tile[] directions; //upLeft, upRight, downLeft, downRight

    public Tile(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public int[] getPos(){
        return new int[]{x, y};
    }
    public int getY(){
        return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public void setDirs(Tile upLeft, Tile upRight, Tile downLeft, Tile downRight){
        /*
        this.upLeft = upLeft;
        this.upRight = upRight;
        this.downLeft = downLeft;
        this.downRight = downRight;
        */
        this.directions = new Tile[]{upLeft, upRight, downLeft, downRight};
    }

    public Tile[] getDirs(){
        return directions;
    }

    public boolean canJump(int dirIndex, boolean pieceIsWhite){
        Tile dirTile = directions[dirIndex];
        return (dirTile != null &&
                dirTile.getPiece() != null &&
                dirTile.getPiece().isWhite() != pieceIsWhite &&
                (dirTile.getDirs()[dirIndex] != null &&
                dirTile.getDirs()[dirIndex].getPiece() == null));
    }

    public Tile twiceDir(int dirIndex){
        //UNCHECKED - must validate before use
        return directions[dirIndex].getDirs()[dirIndex];
    }



}
