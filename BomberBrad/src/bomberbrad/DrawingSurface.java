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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingSurface extends JPanel{
    
    int windowState;
    int selectedYPos;
    
    public DrawingSurface() {
        
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                repaint();
            }
        };
        windowState = 0;
        selectedYPos = 381;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent k) {
                if (k.getID() == KeyEvent.KEY_PRESSED) {
                    if (windowState == 0) { // keyboard inputs for main menu
                        if (k.getKeyCode() == KeyEvent.VK_W) {
                            updateMenuSelectedYPos(-1);
                        } else if (k.getKeyCode() == KeyEvent.VK_S) {
                            updateMenuSelectedYPos(1);
                        } else if (k.getKeyCode() == KeyEvent.VK_ENTER) {
                            getSelected();
                        }
                    } else if (windowState == 1) { // main game
                        
                    } else if (windowState == 2) { // high scores
                        
                    } else if (windowState == 3) { // credits
                        
                    }
                    if (k.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        windowState = 0;
                    }
                }
                return false;
            }
        });
        Timer timer = new Timer(30, al);
        timer.start();
    }

    private void doDrawing(Graphics g) {        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,896,712);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        BufferedImage menuImg = null;
        try {
            menuImg = ImageIO.read(getClass().getResource("/bomberbrad/menulogo.png"));
        } catch (IOException e) {
            g2d.drawString("Error: " + e, 10, 10);
        }
        if (windowState == 0) { // main menu
            g2d.drawString("Start Game", 340, 400);
            g2d.drawString("High Scores", 340, 450);
            g2d.drawString("Credits", 340, 500);
            g2d.drawString("Exit", 340, 550);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("© 2019 DKP Studios", 340, 650);
            g2d.fillPolygon(new int[] {320, 320, 330}, new int[] {selectedYPos, selectedYPos + 20, selectedYPos + 10}, 3);
            g2d.drawImage(menuImg,80,100,816,252,0,0,368,76,null);
        } else if (windowState == 1) { // main game
            g2d.drawString("Main Game", 10, 50);
        } else if (windowState == 2) { // high scores
            g2d.setFont(new Font("Arial", Font.BOLD, 32));
            g2d.drawString("High Scores", 350, 100);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 24));
            g2d.drawString("No.  Name   Score", 300, 200);
        } else if (windowState == 3) { // credits
            g2d.drawImage(menuImg,120,75,488,151,0,0,368,76,null);
            g2d.drawString("was created by:", 520, 122);
            g2d.drawString("RILEY DECONKEY - Systems Anaylst", 200, 300);
            g2d.drawString("JASON KARAPOSTOLAKIS - Technical Writer", 200, 400);
            g2d.drawString("REEGAL PANCHAL - Project Manager", 200, 500);
            g2d.drawString("Special thanks to TOMMY JOHNSTON", 200, 600);
        }
        Tile[][] board = levelRandomizer(2);
        printBoard(board);
    }
     
     private void updateMenuSelectedYPos(int i) {
         if (i == -1 && selectedYPos >= 431) {
             selectedYPos -= 50;
         } else if (i == 1 && selectedYPos <= 481) {
             selectedYPos += 50;
         }
     }
     
     private void getSelected() {
         if (selectedYPos == 381) {
             windowState = 1; // go to main game
         } else if (selectedYPos == 431) {
             windowState = 2; // go to high scores
         } else if (selectedYPos == 481) {
             windowState = 3; // go to credits
         } else {
             System.exit(0);
         }
     }
     
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
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
         breakableBlocks --;
        }
        
        }
        while (powerups > 0) {
            randomX = (int)(Math.random() * 14) + 1;
        if (randomX < 3) {
            randomY = (int)(Math.random() * 8) + 3;
        } else {
        randomY = (int)(Math.random() * 10) + 1;
        }
        if (board[randomX][randomY].getOnTile() == null) {
            board[randomX][randomY].setOnTile(new Block(randomX,randomY,"powerUp",true));  
         powerups --;
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
