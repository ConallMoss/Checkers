package Client;

import java.util.HashMap;
import java.util.Map;

public class Player {
    public char[][] playerState;
    private char[][] playerPathState;
    private int SIZE;

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

    public Player(){
        playerState = new char[8][8];
        SIZE = 8;
    }

    public void initPlayer(char[][] playerState, int size){
        this.playerState = playerState;
        this.SIZE = size;
    }

    public void updatePlayerState(char[][] newPlayerState){
        playerState = newPlayerState;
    }

    public void updatePlayerPathState(char[][] newPlayerPathState){
        playerPathState = newPlayerPathState;
    }

    public void printPlayerState(){
        StringBuilder toPrint = new StringBuilder();
        for (int i = SIZE-1; i >= 0; i--) {
            for (int j = 0; j < SIZE; j++) {
                toPrint.append(basicToChar(playerState[i][j])).append("|");
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
                toPrint = toPrint + playerState[i][j]  + ("|");
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
