package Client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {
    public char[][] state;
    private char[][] playerPathState; //DO NOT USE
    public List<int[]> pathEnds;
    private int SIZE;
    public boolean isWhite;

    private static Map<Character, Character> basicToCharDict;
    static {
        basicToCharDict = new HashMap<>();
        basicToCharDict.put('N', '∙');  //Not in use
        basicToCharDict.put('E', '•');  //Empty
        basicToCharDict.put('W', '■');  //White
        basicToCharDict.put('B', '□');  //Black
        basicToCharDict.put('K', '●'); //White King
        basicToCharDict.put('Q', '○'); //Black King
    }
    public static Character basicToChar(char name){
        if (Character.isDigit(name)){
            return name;
        } else {
            return basicToCharDict.get(name);
        }
    }

    public GameState(){
        state = new char[8][8];
        SIZE = 8;
    }

    public void initPlayer(char[][] playerState, int size){
        this.state = playerState;
        this.SIZE = size;
    }

    public void updatePlayerState(char[][] newPlayerState){
        state = newPlayerState;
    }

    public void updatePlayerPathState(char[][] newPlayerPathState){
        playerPathState = newPlayerPathState;
    }

    public void updatePathEnds(List<int[]> newPathEnds){ pathEnds = newPathEnds;}

    public void printPlayerState(){
        StringBuilder toPrint = new StringBuilder();
        for (int i = SIZE-1; i >= 0; i--) {
            for (int j = 0; j < SIZE; j++) {
                toPrint.append(basicToChar(state[i][j])).append("|");
            }
            toPrint.append("\n");
        }
        System.out.println(toPrint);
    }

    public void printPlayerPathState(){
        StringBuilder toPrint = new StringBuilder();
        for (int i = SIZE-1; i >= 0; i--) {
            for (int j = 0; j < SIZE; j++) {
                toPrint.append(basicToChar(playerPathState[i][j]).toString()).append("|");
            }
            toPrint.append("\n");
        }
        System.out.println(toPrint);
    }

    public void directPrintBoard(){
        String toPrint = "";
        for (int i = SIZE-1; i >= 0; i--) {
            for (int j = 0; j < SIZE; j++) {
                toPrint = toPrint + state[i][j]  + ("|");
            }
            toPrint = toPrint + ("\n");
        }
        System.out.println(toPrint);
    }

    public void directPrintPathBoard(){
        String toPrint = "";
        for (int i = SIZE-1; i >= 0; i--) {
            for (int j = 0; j < SIZE; j++) {
                toPrint = toPrint + playerPathState[i][j]  + ("|");
            }
            toPrint = toPrint + ("\n");
        }
        System.out.println(toPrint);
    }

    public char numToChar(int num){
        return 'a'; //TODO
    }
}
