/**
 * DKP Studios (Jason)
 * JPanel that displays game graphics
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
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingSurface extends JPanel{
    
    int windowState;
    int selectedYPos;
    ArrayList<Enemy> enemiesList = new ArrayList();
    Player player = new Player(16,16,1);
    boolean moveDown = false, moveUp = false, moveLeft = false, moveRight = false;
    Tile[][] board;
    
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
                if (windowState == 0) { // keyboard inputs for main menu
                    if (k.getID() == KeyEvent.KEY_PRESSED) { // stuff that happens when a key is pressed
                        if (k.getKeyCode() == KeyEvent.VK_W) {
                            updateMenuSelectedYPos(-1);
                        } else if (k.getKeyCode() == KeyEvent.VK_S) {
                            updateMenuSelectedYPos(1);
                        } else if (k.getKeyCode() == KeyEvent.VK_ENTER) {
                            getSelected();
                        }
                    }
                } else if (windowState == 1) { // main game
                    if (k.getID() == KeyEvent.KEY_PRESSED) { // stuff that happens when a key is pressed
                        if (k.getKeyCode() == KeyEvent.VK_W) {
                            moveUp = true;
                        } else if (k.getKeyCode() == KeyEvent.VK_S) {
                            moveDown = true;
                        } else if (k.getKeyCode() == KeyEvent.VK_A) {
                            moveLeft = true;
                        } else if (k.getKeyCode() == KeyEvent.VK_D) {
                            moveRight = true;
                        } else if (k.getKeyCode() == KeyEvent.VK_SPACE) {
                            // place bomb;
                        }
                    } else if (k.getID() == KeyEvent.KEY_RELEASED) { // stuff that happens when a key is released
                        if (k.getKeyCode() == KeyEvent.VK_W) {
                            moveUp = false;
                        } else if (k.getKeyCode() == KeyEvent.VK_S) {
                            moveDown = false;
                        } else if (k.getKeyCode() == KeyEvent.VK_A) {
                            moveLeft = false;
                        } else if (k.getKeyCode() == KeyEvent.VK_D) {
                            moveRight = false;
                        }
                    }
                }
                if (k.getKeyCode() == KeyEvent.VK_ESCAPE) { // key inputs that can happen at any time -
                    windowState = 0; // ESC returns to main menu
                }
                return false;
            }
        });
        board = levelRandomizer(1);
        printBoard(board);
        Timer timer = new Timer(250, al);
        timer.start();
    }

    private void doDrawing(Graphics g) {        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,960,776);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        BufferedImage menuImg = null;
        try {
            menuImg = ImageIO.read(getClass().getResource("/bomberbrad/menulogo.png"));
        } catch (IOException e) {
            g2d.drawString("Error: " + e, 10, 10);
        }
        if (windowState == 0) { // main menu
            g2d.drawString("Start Game", 370, 400);
            g2d.drawString("High Scores", 370, 450);
            g2d.drawString("Credits", 370, 500);
            g2d.drawString("Exit", 370, 550);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("© 2019 DKP Studios", 370, 650);
            g2d.fillPolygon(new int[] {350, 350, 360}, new int[] {selectedYPos, selectedYPos + 20, selectedYPos + 10}, 3);
            g2d.drawImage(menuImg,112,100,848,252,0,0,368,76,null);
        } else if (windowState == 1) { // main game
            g2d.drawString("Main Game", 10, 50);
            if (moveUp || moveDown || moveLeft || moveRight) {
                if (moveDown) {
                    player.setDirection(1);
                } else if (moveRight) {
                    player.setDirection(2);
                } else if (moveLeft) {
                    player.setDirection(4);
                } else {
                    player.setDirection(3);
                }
                player.move();
            }
            for (int i = 0; i < 11; i ++) {
             for (int o = 0; o < 15; o ++) {
                 board[o][i].draw(g2d);
             }
             }
            for (Enemy e: enemiesList) {
                e.action(board);
                e.draw(g2d);
            }
        } else if (windowState == 2) { // high scores
            g2d.setFont(new Font("Arial", Font.BOLD, 32));
            g2d.drawString("High Scores", 380, 100);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 24));
            g2d.drawString("No.  Name   Score", 330, 200);
            g2d.drawString("1.   JSK    900000", 330, 250);
        } else if (windowState == 3) { // credits
            g2d.drawImage(menuImg,150,75,518,151,0,0,368,76,null);
            g2d.drawString("was created by:", 550, 122);
            g2d.drawString("RILEY DECONKEY - Systems Analyst", 200, 300);
            g2d.drawString("JASON KARAPOSTOLAKIS - Technical Writer", 200, 400);
            g2d.drawString("REEGAL PANCHAL - Project Manager", 200, 500);
            g2d.drawString("Special thanks to TOMMY JOHNSTON", 200, 600);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("© 2019 DKP Studios", 370, 650);
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
    
    private boolean intersecting(Entity e1, Entity e2) {
        int x1 = Math.min(e1.getXPos(), e2.getXPos()), y1 = Math.min(e1.getYPos(), e2.getYPos()), x2 = Math.max(e1.getXPos(), e2.getXPos()), y2 = Math.max(e1.getYPos(), e2.getYPos());
        boolean xOverlap, yOverlap;
        xOverlap = (Math.abs(x1 - x2) < 12);
        yOverlap = (Math.abs(y1 - y2) < 12);
        return (xOverlap && yOverlap);
    }
    
    private boolean overlapping(Tile t, Entity e) {
        int tX = t.getxPos() * 16, tY = t.getyPos() * 16, eX = e.getXPos(), eY = e.getYPos();
        boolean xOverlap = false, yOverlap = false;
        xOverlap = ((tX <= eX && tX + 16 > eX) || (tX > eX && eX + 12 > tX));
        yOverlap = ((tY <= eY && tY + 16 > eY) || (tY > eY && eY + 12 > tY));
        return (xOverlap && yOverlap);
    }
    
    private Tile[][] levelRandomizer(int difficulty) {
        int enemies = 5;
        int breakableBlocks = 25;
        int powerups = 5;
        Tile[][] board = new Tile[15][11];
        //Creating blank tiles
        for (int i = 0; i < 15; i ++) {
             for (int o = 0; o < 11; o ++) {
                 board[i][o] = new Tile(i,o,null);
             }
        }
        //Creating top and bottom borders
        for (int i = 0; i < 15; i ++) {
            board[i][0] = new Tile(i,0,new Block(i,0,null,false));
            board[i][10] = new Tile(i,10,new Block(i,10,null,false));
        }
        //Creating left and right borders
        for (int i = 0; i < 11; i ++) {
            board[0][i] = new Tile(0,i,new Block(0,i,null,false));
            board[14][i] = new Tile(14,i,new Block(14,i,null,false));
        }
        //creating spaced unbreakable blocks
        for (int i = 2; i < 15; i += 2) {
             for (int o = 2; o < 11; o += 2) {
                 board[i][o] = new Tile(i,o,new Block(i,o,null,false));
             }
        }
        //creating random vairble to choose random positions
        int random;
        //creating arraylist of possible positions for breakable blocks, 
        ArrayList<Tile> possible = new ArrayList();
        //filling arraylist with possible tiles
        for (int i = 0; i < 11; i ++) {
             for (int o = 0; o < 15; o ++) {
                 if (board[o][i].getOnTile() == null) {
                     if (!((i == 1 && o == 1) || (i == 2 && o == 1) || (i == 1 && o == 2))) {
                     possible.add(board[o][i]);
                     }
                 }
             }
        }
        //generating breakable blocks
        while (breakableBlocks > 0) {
         //Choosing random position in the arraylist  
        random = (int)(Math.random() * possible.size());
        //placing breakable block at chosen position
        (possible.get(random)).setOnTile(new Block(possible.get(random).getxPos(),possible.get(random).getyPos(),null,true));
        //removing place from list
        possible.remove(random);
        //subtracting from number of remaining blocks to be placed
        breakableBlocks --;
        }
        while (powerups > 0) {
        random = (int)(Math.random() * possible.size());
        (possible.get(random)).setOnTile(new Block(possible.get(random).getxPos(),possible.get(random).getyPos(),"powerUP",true));
        powerups --;
        possible.remove(random);
        }
        random = (int)(Math.random() * possible.size());
        (possible.get(random)).setOnTile(new Block(possible.get(random).getxPos(),possible.get(random).getyPos(),"Exit",true));
        possible.remove(random);
        
        while (enemies > 0) {
            
            random = (int)(Math.random() * possible.size());
            if (difficulty == 1) {
                enemiesList.add(new Ballom(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                enemies --;
            } else if (difficulty == 2) {
                enemiesList.add(new Onil(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                enemies --;
            } else if (difficulty == 3) {
                enemiesList.add(new Dahl(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                enemies --;
            }
            
        }
        System.out.println(enemiesList);
        System.out.println(enemiesList.size());
        return board;
    }
    private void printBoard(Tile[][] board) {
        String print = "";
        Block unbreak = new Block(0,0,null,false);
        Block breaka = new Block(0,0,null,true);
        boolean foundGuy = false;
        for (int i = 0; i < 11; i ++) {
             for (int o = 0; o < 15; o ++) {
                 for (int p = 0; p < enemiesList.size(); p ++) {
                     if (o == enemiesList.get(p).getXPos() / 16 && i == enemiesList.get(p).getYPos() / 16) {
                         foundGuy = true;
                         }
                 }
                 if (foundGuy) {
                     print += "E\t";
                 }
                 else if (board[o][i].getOnTile() == null) {
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
                     } else if (((Block)(board[o][i].getOnTile())).getPowerType().equals("Exit")){
                         print  += "EX\t";
                     } else {
                     print += "PU\t";
                     }
                 }
                 foundGuy = false;
             }
             print += "\n";
        }
        System.out.println(print);
    }
    
}
