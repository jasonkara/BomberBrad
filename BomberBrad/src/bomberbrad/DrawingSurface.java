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
                    if (k.getKeyCode() == KeyEvent.VK_W) {
                        updateMenuSelectedYPos(-1);
                    } else if (k.getKeyCode() == KeyEvent.VK_S) {
                        updateMenuSelectedYPos(1);
                    } else if (k.getKeyCode() == KeyEvent.VK_ENTER) {
                        getSelected();
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
        g2d.drawString("Start Game", 340, 400);
        g2d.drawString("High Scores", 340, 450);
        g2d.drawString("Credits", 340, 500);
        g2d.drawString("Exit", 340, 550);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("© 2019 DKP Studios", 340, 650);
        g2d.fillPolygon(new int[] {320, 320, 330}, new int[] {selectedYPos, selectedYPos + 20, selectedYPos + 10}, 3);
        try {
            BufferedImage menuImg = ImageIO.read(getClass().getResource("/bomberbrad/menulogo.png"));
            g2d.drawImage(menuImg,100,50,796,478,0,0,227,139,null);
        } catch (IOException e) {
            g2d.drawString("Error: " + e, 10, 10);
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
             // exit
         }
     }
     
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        doDrawing(g);
    }
}
