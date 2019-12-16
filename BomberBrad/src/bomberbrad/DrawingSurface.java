/*
 * B Cutten
    Sept 2014
    A class which allows drawing, because it extends JPanel, by way of the 
    Graphics2D class
    Because this class is only going to ever be used by the Graphics2DTester, it could be 
    included in the same file (Graphics2DTester.java)
 */

package bomberbrad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class DrawingSurface extends JPanel{
    
    //does the actual drawing
     private void doDrawing(Graphics g) {        
        //the Graphics2D class is the class that handles all the drawing
        //must be casted from older Graphics class in order to have access to some newer methods
        Graphics2D g2d = (Graphics2D) g;
        //draw a string on the panel        
        g2d.drawString("Java 2D", 50, 50); //(text, x, y)
        g2d.drawOval(0, 0, 100, 100);  //(x,y,width,height)
        g2d.setColor(Color.red);//change color
        int x, y;
        for(int i = 0; i < 1000; i++){
            //get random x,y location
            x =(int)(Math.random()*getWidth());
            y =(int)(Math.random()*getHeight());
            //draw a really short line (point)
            g2d.drawLine(x,y,x,y);
        }
        Tile[][] board = levelRandomizer(2);
        printBoard(board);
    }
     
    //overrides paintComponent in JPanel class
    //performs custom painting
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);//does the necessary work to prepare the panel for drawing
        doDrawing(g);
    }
    private Tile[][] levelRandomizer(int difficulty) {
        int enemies = 5;
        int breakableBlocks = 25;
        int powerups = 5;
        Tile[][] board = new Tile[15][11];
        for (int i = 0; i < 15; i ++) {
             for (int o = 0; o < 11; o ++) {
                 board[i][o] = new Tile(i,o,null);
             }
        }
        for (int i = 0; i < 15; i ++) {
            board[i][0] = new Tile(i,0,new Block(i,0,null,false));
            board[i][10] = new Tile(i,10,new Block(i,10,null,false));
        }
        for (int i = 0; i < 11; i ++) {
            board[0][i] = new Tile(0,i,new Block(0,i,null,false));
            board[14][i] = new Tile(14,i,new Block(14,i,null,false));
        }
        for (int i = 2; i < 15; i += 2) {
             for (int o = 2; o < 11; o += 2) {
                 board[i][o] = new Tile(i,o,new Block(i,o,null,false));
             }
        }
        int randomX;
        int randomY;
        int isPowerUp;
        while (breakableBlocks > 0) {
        randomX = (int)(Math.random() * 14) + 1;
        if (randomX < 3) {
            randomY = (int)(Math.random() * 8) + 3;
        } else {
        randomY = (int)(Math.random() * 10) + 1;
        }
        if (board[randomX][randomY].getOnTile() == null) {
            board[randomX][randomY].setOnTile(new Block(randomX,randomY,null,true));   
            if (powerups > 0) {
                isPowerUp = (int)(Math.random() * 5) + 1;
                if (isPowerUp < 3) {
                    ((Block)board[randomX][randomY].getOnTile()).setPowerType("powerUp");
                    powerups --;
                }
            } 
         breakableBlocks --;
        }
        }
        while (enemies > 0) {
            enemies --;
        }
        return board;
    }
    private void printBoard(Tile[][] board) {
        String print = "";
        Block unbreak = new Block(0,0,null,false);
        Block breaka = new Block(0,0,null,true);
        for (int i = 0; i < 11; i ++) {
             for (int o = 0; o < 15; o ++) {
                 if (board[o][i].getOnTile() == null) {
                     print += "T\t";
                 }
                 else if (((Block)(board[o][i].getOnTile())).equals(unbreak)) {
                     if (((Block)(board[o][i].getOnTile())).getPowerType() == null) {
                     print += "UB\t";
                     }
                 }
                 else if (((Block)(board[o][i].getOnTile())).equals(breaka)) {
                     if (((Block)(board[o][i].getOnTile())).getPowerType() == null) {
                     print += "BB\t";
                     } else {
                     print += "PU\t";
                     }
                 }
                 
             }
             print += "\n";
        }
        System.out.println(print);
    }
    
}
