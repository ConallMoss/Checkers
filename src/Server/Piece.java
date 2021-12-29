package Server;

public class Piece {

    private boolean isWhite;
    private boolean isKing;
    private boolean isInUse;


    public Piece(boolean isInUse, boolean isWhite, boolean isKing){
        this.isInUse = isInUse;
        if(isInUse) {
            this.isWhite = isWhite;
            this.isKing = isKing;
        }
    }

    public Piece(Piece piece){
        this.isInUse = piece.isInUse;
        this.isKing = piece.isKing;
        this.isWhite = piece.isWhite;
    }

    protected void pieceTaken(){
        isInUse = false;
    }

    protected void kingPiece(){
        isKing = true;
    }

    protected boolean isInUse(){
        return isInUse;
    }

    protected boolean isWhite(){
        return isWhite;
    }

    protected  boolean isKing(){
        return isKing;
    }

    protected char toChar(){
        if(isInUse) {
            if (isWhite) {
                if (isKing) {
                    return BoardStates.nameToSymDict.get("WK");
                } else {
                    return BoardStates.nameToSymDict.get("W");
                }
            } else {
                if (isKing) {
                    return BoardStates.nameToSymDict.get("BK");
                } else {
                    return BoardStates.nameToSymDict.get("B");
                }
            }
        } else {
            return BoardStates.nameToSymDict.get("E");
        }

    }
}
