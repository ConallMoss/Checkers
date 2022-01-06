package Server;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BoardStates {

    /*
    public static HashMap<Integer, Character> pieceDict = new HashMap<>();
    {
        pieceDict.put(-1, '□');
        pieceDict.put(0, '■');
        pieceDict.put(1, 'X');
        pieceDict.put(2, 'O');
        pieceDict.put(3, '✘');
        pieceDict.put(4, '◎');
    }

    public static Map<Integer, Character> pieceDict = Map.of(
            -1, '□',
            0, '■',
            1, 'X',
            2, 'O',
            3, '✘',
            4, '◎'
    );
    */
    //TODO add getter for these
    public static Map<Integer, Character> pieceToSymDict;
    static {
        pieceToSymDict = new LinkedHashMap<>();
        pieceToSymDict.put(-1, '□');
        pieceToSymDict.put(0, '■');
        pieceToSymDict.put(1, 'X');
        pieceToSymDict.put(2, 'O');
        pieceToSymDict.put(3, '✘');
        pieceToSymDict.put(4, '◎');
    }

    private static Map<String, Character> nameToSymDict;
    /*
    static {
        nameToSymDict = new HashMap<>();
        nameToSymDict.put("N", '□');  //Not in use
        nameToSymDict.put("E", '■');  //Empty
        nameToSymDict.put("W", 'X');  //White
        nameToSymDict.put("B", 'O');  //Black
        nameToSymDict.put("WK", '✘'); //White King
        nameToSymDict.put("BK", '◎'); //Black King
    }
     */

    static {
        nameToSymDict = new HashMap<>();
        nameToSymDict.put("N", '∙');  //Not in use
        nameToSymDict.put("E", '•');  //Empty
        nameToSymDict.put("W", '■');  //White
        nameToSymDict.put("B", '□');  //Black
        nameToSymDict.put("WK", '●'); //White King
        nameToSymDict.put("BK", '○'); //Black King
    }

    public static char nameToSym(String name){
        return nameToSymDict.get(name);
    }

    private static Map<String, Character> nameToBasicDict;
    static {
        nameToBasicDict = new HashMap<>();
        nameToBasicDict.put("N", 'N');  //Not in use
        nameToBasicDict.put("E", 'E');  //Empty
        nameToBasicDict.put("W", 'W');  //White
        nameToBasicDict.put("B", 'B');  //Black
        nameToBasicDict.put("WK", 'K'); //White King
        nameToBasicDict.put("BK", 'Q'); //Black King
    }
    public static char nameToBasic(String name){
        return nameToBasicDict.get(name);
    }


    public static int[][] start = new int[][]
            {{ 2,-1, 2,-1, 2,-1, 2,-1},
             {-1, 2,-1, 2,-1, 2,-1, 2},
             { 2,-1, 2,-1, 2,-1, 2,-1},
             {-1, 0,-1, 0,-1, 0,-1, 0},
             { 0,-1, 0,-1, 0,-1, 0,-1},
             {-1, 1,-1, 1,-1, 1,-1, 1},
             { 1,-1, 1,-1, 1,-1, 1,-1},
             {-1, 1,-1, 1,-1, 1,-1, 1}};

    public static int[][] posNum = new int[][] //to complete
            {{56, 57, 58, 59, 60, 61, 62, 63},
             {48, 0, 0, 0, 0, 0, 0, 0},
             {40, 0, 0, 0, 0, 0, 0, 0},
             {32, 0, 0, 0, 0, 0, 0, 0},
             {24, 0, 0, 0, 0, 0, 0, 0},
             {16, 0, 0, 0, 0, 0, 0, 0},
             {8, 9, 10, 11, 12, 13, 14, 15},
             {0, 1, 2, 3, 4, 5, 6, 7}};

    /*
    public static int[][] start = new int[][]
            {{0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0}};

     */
}
