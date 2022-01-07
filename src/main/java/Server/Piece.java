package main.java.Server;

public class Piece {

    private boolean isWhite;
    private boolean isKing;
    private boolean isInUse; //Deprecated
    private int[] canMoveDir;


    public Piece(boolean isWhite){
        //this.isInUse = true;
        this.isWhite = isWhite;
        this.isKing = false;
        if(isWhite){
            canMoveDir = new int[]{2, 3};
        } else {
            canMoveDir = new int[]{0, 1};
        }

    }

    public Piece(){
        this.isInUse = false;
    }

    public Piece(Piece piece) {
        //this.isInUse = piece.isInUse;
        this.isKing = piece.isKing;
        this.isWhite = piece.isWhite;
    }


    public void pieceTaken() {
        isInUse = false;
    }

    public void kingPiece() {
        isKing = true;
        canMoveDir = new int[]{0,1,2,3};
    }

    public boolean isInUse() {
        return isInUse;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isKing() {
        return isKing;
    }

    public int[] getMoveDir(){
        return canMoveDir;
    }

    public char toChar() {
        if (isWhite) {
            if (isKing) {
                return BoardStates.nameToSym("WK");
            } else {
                return BoardStates.nameToSym("W");
            }
        } else {
            if (isKing) {
                return BoardStates.nameToSym("BK");
            } else {
                return BoardStates.nameToSym("B");
            }
        }
    }

    public char toBasic() {
        if (isWhite) {
            if (isKing) {
                return BoardStates.nameToBasic("WK");
            } else {
                return BoardStates.nameToBasic("W");
            }
        } else {
            if (isKing) {
                return BoardStates.nameToBasic("BK");
            } else {
                return BoardStates.nameToBasic("B");
            }
        }
    }
}
