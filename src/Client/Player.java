package Client;

public class Player {
    private int[][] playerState;
    private int SIZE;

    public Player(){
        playerState = new int[8][8];

    }

    public void initPlayer(int[][] playerState, int size){
        this.playerState = playerState;
        this.SIZE = size;
    }

    public void updatePlayerState(int[][] newPlayerState){
        playerState = newPlayerState;
    }

    public void printPlayerState(){
        StringBuilder toPrint = new StringBuilder();
        for (int i = SIZE-1; i >= 0; i--) {
            for (int j = 0; j < SIZE; j++) {
                toPrint.append(numToChar(playerState[i][j])).append("|");
            }
            toPrint.append("\n");
        }
    }

    public char numToChar(int num){
        return 'a'; //TODO
    }
}
