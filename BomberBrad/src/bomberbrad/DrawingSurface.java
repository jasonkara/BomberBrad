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
}
