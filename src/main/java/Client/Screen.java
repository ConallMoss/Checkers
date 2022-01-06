package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.*;

public class Screen extends JPanel implements ActionListener {
    private int x;
    private int y;

    private final int  boardX = 200;
    private final int boardY = 200;
    private final int boardWidth = 400;
    private final int boardHeight = 400;
    private int tileWidth;
    private int tileHeight;
    private int SIZE;

    public boolean isCurrentTurn;
    public boolean isPieceOrPath; //True = piece
    private int selectedTileX;
    private int selectedTileY;

    private boolean waitForPiece;
    private boolean waitForPath;

    public boolean hasWon;
    public boolean hasLost;

    private Client client;
    GameState gameState;



    public Screen(Client client){
        setPreferredSize(new Dimension(800, 800));
        setBackground(new Color(232,232,232));

        this.client = client;

        gameState = new GameState();
        SIZE = 8;
        tileWidth = boardWidth / 8;
        tileHeight = boardHeight / 8;

        selectedTileX = -1;
        selectedTileY = -1;

        waitForPath = false;
        waitForPiece = false;

        hasWon = false;
        hasLost = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = (e.getX()-boardX) / tileWidth;
                int y = (boardY + boardHeight - e.getY()) / tileHeight;
                System.out.println(x);
                System.out.println(y);
                System.out.println(waitForPiece);
                System.out.println(waitForPath);
                if (x >= 0 && x < SIZE && y >= 0 && y < SIZE){
                    if(waitForPiece){
                        try {
                            sendPiece(x, y);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else if (waitForPath){
                        try {
                            sendPath(x, y);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        drawBoard(g);
        drawPieces(g);
        drawText(g);
        //test draw to screen
        g.setColor(new Color(255, 100, 100));
        g.fillRect(x,y++, 50, 50);


        Point mousePos = getMousePosition();
        if (mousePos != null) {
            int mouseX = mousePos.x;
            int mouseY = mousePos.y;

            g.setColor(new Color(100, 255, 100));
            g.fillRect(mouseX, mouseY, 20, 20);
        }
    }

    public void drawBoard(Graphics g){
        for (int i = SIZE-1; i >= 0; i--) {
            for (int j = 0; j < SIZE; j++) {
                g.setColor(((i+j)%2==0) ? new Color(175,175,175) : new Color(150,125,125));
                g.fillRect(boardX + j * tileWidth, boardY + i * tileHeight, tileWidth, tileHeight);
            }
        }
    }

    public void drawPieces(Graphics g){
        for (int i = SIZE-1; i >= 0; i--) {
            for (int j = 0; j < SIZE; j++) {
                drawPiece(g, i, j);
            }
        }
    }

    public void drawPiece(Graphics g, int i, int j){
        char piece = gameState.state[SIZE-i-1][j];
        if (piece == 'N' || piece == 'E'){}
        else if (Character.isDigit(piece)){
            g.setColor(new Color(241,229,172, 128));
            g.fillRoundRect((int)(boardX + (j+0.1) * tileWidth), (int)(boardY + (i+0.1) * tileHeight), (int)(tileWidth*0.8), (int)(tileHeight*0.8), 10, 10 );
        }
        else{
            if(piece == 'W' || piece == 'K'){
                g.setColor(new Color(225, 25, 25));
            } else {
                g.setColor(new Color(50,50,50));
            }
            g.fillOval((int)(boardX + (j+0.1) * tileWidth), (int)(boardY + (i+0.1) * tileHeight), (int)(tileWidth*0.8), (int)(tileHeight*0.8));
            if(piece == 'K' || piece == 'Q'){
                g.setColor((piece=='K') ? new Color(150, 0, 0,200) : new Color(75,100,100,200));
                g.fillOval((int)(boardX + (j+0.25) * tileWidth), (int)(boardY + (i+0.25) * tileHeight), (int)(tileWidth*0.5), (int)(tileHeight*0.5));
            }
        }
    }

    public void drawText(Graphics g){
        String text;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        // set the text color and font
        g2d.setFont(new Font("Lato", Font.BOLD, 25));

        if(hasWon){
            g2d.setColor(new Color(0, 255, 0));
            text = ("You have Won!");
        } else if (hasLost){
            g2d.setColor(new Color(255, 0, 0));
            text = ("You have Lost.");
        }else if (!isCurrentTurn){
            g2d.setColor(new Color(30, 120, 139));
            text = "Opponent's Turn";
        } else {
            g2d.setColor(new Color(30, 201, 139));
            if(isPieceOrPath){
                text = "Your Turn - Select piece";
            } else {
                text = "Your Turn - Select path";
            }
        }
        g2d.drawString(text, 100, 100);
        g2d.drawString(gameState.isWhite ? "Playing as: White": "Playing as: Black", 100, 150);
    }

    private int[] coordToPos(int[] coord){
        int posX = boardX + boardWidth / 16 + boardWidth * coord[0] / 8;
        int posY = boardY + boardHeight / 16 + boardHeight * coord[1] / 8;

        return new int[]{posX, posY};
    }

    public void getPiece(){
        waitForPiece = true;
    }

    public void getPath(){
        waitForPath = true;
    }

    private void sendPiece(int x, int y) throws IOException {
        client.sendPiece(x, y);
        waitForPiece = false;
    }

    private void sendPath(int x, int y) throws IOException {
        int p = -1;
        for(int i = 0; i < gameState.pathEnds.size(); i++){
            if(Arrays.equals(gameState.pathEnds.get(i), new int[]{x, y})){
                p = i+1;
            }
        }
        if (p != -1) {
            client.sendPath(p);
            waitForPath = false;
        }
    }

    /*
    public String getPiece(){
        System.out.println(this);
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getStackTrace());
            }
            return selectedTileX + "," + selectedTileY;
        }
    }

    public String getPath(){
        return "1";
    }
     */

}