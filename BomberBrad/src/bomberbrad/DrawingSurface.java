

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
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class DrawingSurface extends JPanel{
    
    int windowState;
    int selectedYPos;
    int frameCounter;
    ArrayList<Enemy> enemiesList = new ArrayList();
    Player player = new Player(16,16,1);
    boolean moveDown = false, moveUp = false, moveLeft = false, moveRight = false;
    boolean playingLevel = false;
    Tile[][] board;
    int difficulty;
    Clip clip, clipSE;
    AudioInputStream[] audio = new AudioInputStream[3];
    Timer timer;
    int exitX,exitY;
    int level;
    int bombs;
    int score;
    private int length;
    private boolean detonator;
    ArrayList<Score> scores = new ArrayList();
    boolean addedScore;
    int timeBonus;
    int bonusCounter;
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
                            if (bombs >= 1 && ! player.isDying()) {
                                bombs --;
                                playSE("placebomb");
                                board[(player.getXPos() + 8) / 16][(player.getYPos() + 8) / 16].setOnTile(new Bomb((player.getXPos() + 8) / 16, (player.getYPos() + 8) / 16));
                            }
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
                } else if (windowState == 2) {
                     if (k.getKeyCode() == KeyEvent.VK_SPACE) {
                         JOptionPane.showInputDialog("Enter a username to search for.");
                     }
                }
                if (k.getKeyCode() == KeyEvent.VK_ESCAPE && windowState != 0) { // following input can happen at any time except main menu
                    if (windowState == 1) {
                        clip.stop();
                        playAudio("title"); // plays the title music again only if coming from main game
                    }
                    windowState = 0; // ESC returns to main menu
                }
                return false;
            }
        });
        
        //printBoard(board);
        score();
        timer = new Timer(50, al);
        timer.start();
        playAudio("title");
        loadSprites();
    }

    private void doDrawing(Graphics g) {        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,960,776);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        BufferedImage menuImg = null, lifeSprite = null;
        try {
            menuImg = ImageIO.read(getClass().getResource("/bomberbrad/menulogo.png"));
            lifeSprite = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/r1.png"));
        } catch (IOException e) {
            g2d.drawString("Error: " + e, 10, 10);
        }
        if (windowState == 0) { // main menu
            g2d.drawString("Start Game", 370, 400);
            g2d.drawString("High Scores", 370, 450);
            g2d.drawString("Credits", 370, 500);
            g2d.drawString("Exit", 370, 550);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("© 2020 DKP Studios", 370, 650);
            g2d.fillPolygon(new int[] {350, 350, 360}, new int[] {selectedYPos, selectedYPos + 20, selectedYPos + 10}, 3);
            g2d.drawImage(menuImg,112,100,848,252,0,0,368,76,null);
        } else if (windowState == 1) { // main game
         if (player.getLives() < 1) {
                g2d.drawString("Press ESC to return to the menu", 270, 500);
                g2d.setFont(new Font("Arial", Font.BOLD, 64));
                g2d.setColor(Color.RED);
                g2d.drawString("Game Over!", 270, 300);
                if (addedScore == false) {
                    addedScore = true;
                    addScores();
                    
                }
                if (frameCounter == 0) {
                    clip.stop();
                    playAudio("gameover");
                    frameCounter = 1;
                }
            } else {
                if (playingLevel) {
                  mainGame(g2d);
                } else {
                    if (frameCounter == 0) {
                        g2d.drawString("Level " + level, 400, 340);
                        g2d.drawString("Lives: " + player.getLives(), 420, 410);
                        g2d.drawImage(lifeSprite,370,385,402,417,0,0,16,16,null);
                        frameCounter = 1;
                        clip.stop();
                        playAudio("stagestart");
                    } else {
                        while (clip.getMicrosecondLength() != clip.getMicrosecondPosition()) {}
                        clip.stop();
                        playAudio("stage");
                        clip.loop(clip.LOOP_CONTINUOUSLY);
                        playingLevel = true;
                    }
                }
            }
        } else if (windowState == 2) { // high scores
            
            g2d.setFont(new Font("Arial", Font.BOLD, 32));
            g2d.drawString("High Scores", 380, 100);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 24));
            g2d.drawString("No.  Name   Score", 330, 200);
            drawScores(g2d);
        } else if (windowState == 3) { // credits
            g2d.drawImage(menuImg,150,75,518,151,0,0,368,76,null);
            g2d.drawString("was created by:", 550, 122);
            g2d.drawString("RILEY DECONKEY - Systems Analyst", 200, 300);
            g2d.drawString("JASON KARAPOSTOLAKIS - Technical Writer", 200, 400);
            g2d.drawString("REEGAL PANCHAL - Project Manager", 200, 500);
            g2d.drawString("Special thanks to TOMMY JOHNSTON", 200, 600);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("© 2020 DKP Studios", 370, 650);
        }
        
    }
    
    public void playAudio(String sound) {
        try {
            AudioInputStream instream = AudioSystem.getAudioInputStream(new File("src\\bomberbrad\\audio\\" + sound + ".wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(instream);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        clip.start();
    }
    
    public void playSE(String sound) {
        try {
            AudioInputStream instream = AudioSystem.getAudioInputStream(new File("src\\bomberbrad\\audio\\" + sound + ".wav").getAbsoluteFile());
            clipSE = AudioSystem.getClip();
            clipSE.open(instream);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        clipSE.start();
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
             difficulty = 1;
             level = 1;
             restartLevel();
             playingLevel = false;
             bombs = 1;
             length = 1;
             detonator = false;
             addedScore = false;
             player.setLives(3);
         } else if (selectedYPos == 431) {
             windowState = 2; // go to high scores
         } else if (selectedYPos == 481) {
             windowState = 3; // go to credits
         } else {
             System.exit(0);
         }
     }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
     
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        doDrawing(g);
    }
    
    public boolean intersecting(int x1,int y1,int x2,int y2) {
        
        boolean xOverlap, yOverlap;
        xOverlap = (Math.abs(x1 - x2) < 16);
        yOverlap = (Math.abs(y1 - y2) < 16);
        return (xOverlap && yOverlap);
    }

    public ArrayList<Enemy> getEnemiesList() {
        return enemiesList;
    }

    public void setEnemiesList(ArrayList<Enemy> enemiesList) {
        this.enemiesList = enemiesList;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public boolean hasDetonator() {
        return detonator;
    }

    public void setDetonator(boolean detonator) {
        this.detonator = detonator;
    }
    
  
  private void loadSprites(){
        Ballom b = new Ballom(0,0,0);
        b.loadImages();
        b = null;
        Dahl d = new Dahl(0,0,0);
        d.loadImages();
        d = null;
        Onil o = new Onil(0,0,0);
        o.loadImages();
        o = null;
        Block bl = new Block(0, 0, null, false);
        bl.loadImages();
        bl = null;
        Explosion ex = new Explosion(0,0,0);
        ex.loadImages();
        ex = null;
        Bomb bo = new Bomb(0,0);
        bo.loadImages();
        bo = null;
        player.loadImages();
        PowerUp pu = new PowerUp(0,0,0);
        pu.loadImages();
        pu = null;
        Exit e = new Exit(0,0);
        e.loadImages();
        e = null;
        Blob bb = new Blob(0,0,0);
        bb.loadImages();
        bb = null;
        Ghost g = new Ghost(0,0,0);
        g.loadImages();
        g = null;
    }
  
    private void mainGame(Graphics2D g2d) {
        g2d.drawString("Main Game", 10, 50);
        player.setMoving(false);
            if (moveUp || moveDown || moveLeft || moveRight) {
                if (moveDown) {
                    player.setDirection(3);
                } else if (moveRight) {
                    player.setDirection(2);
                } else if (moveLeft) {
                    player.setDirection(4);
                } else {
                    player.setDirection(1);
                }
                player.setMoving(true);
            }
            player.action(this,g2d);
            for (int i = 0; i < 11; i ++) {
             for (int o = 0; o < 15; o ++) {
                 board[o][i].update(this);
                 board[o][i].draw(board, g2d);
                 g2d.setColor(Color.WHITE);
                 g2d.drawString("SCORE: " + score, 40, 730);
                 
             }
             }
            player.draw(g2d);
           
            for (Enemy e: enemiesList) {
                e.action(board);
                e.draw(g2d);
            }
            bonusCounter ++;
            if (bonusCounter == 20) {
                timeBonus --;
            }
            
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
        int powerups = 2;
        board = new Tile[15][11];
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
        //creating random variable to choose random positions
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
        int puType;
        switch (difficulty){
            case 1:
                puType = (int)(Math.random() * 2) + 1;
                break;
            case 2:
                puType = (int)(Math.random() * 4) + 1;
                break;
            default:
                puType = (int)(Math.random() * 6) + 1;
        }
        (possible.get(random)).setOnTile(new Block(possible.get(random).getxPos(),possible.get(random).getyPos(),new PowerUp(possible.get(random).getxPos(),possible.get(random).getyPos(),puType),true));
        powerups --;
        possible.remove(random);
        }
        random = (int)(Math.random() * possible.size());
        (possible.get(random)).setOnTile(new Block(possible.get(random).getxPos(),possible.get(random).getyPos(),new Exit(possible.get(random).getxPos(),possible.get(random).getyPos()),true));
        exitX = possible.get(random).getxPos();
        exitY = possible.get(random).getyPos();
        //exitX = 1;
        //exitY = 2;
        possible.remove(random);
        int enemyType;
        while (enemies > 0) {  
            random = (int)(Math.random() * possible.size());
            switch (difficulty) {
            case 1:
                enemiesList.add(new Ballom(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                enemies --;
                break;
            case 2:
                enemyType = (int)(Math.random() * 3) + 1;
                switch (enemyType) {
                    case 1:
                        enemiesList.add(new Ballom(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                        break;
                    case 2: 
                        enemiesList.add(new Onil(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                        break;
                    case 3:
                        enemiesList.add(new Dahl(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                        break;
                }
                
                enemies --;
            default: 
                enemyType = (int)(Math.random() * 5) + 1;
                switch (enemyType) {
                    case 1:
                        enemiesList.add(new Ballom(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                        break;
                    case 2: 
                        enemiesList.add(new Onil(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                        break;
                    case 3:
                        enemiesList.add(new Dahl(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                        break;
                    case 4:
                        enemiesList.add(new Blob(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                        break;
                    case 5:
                        enemiesList.add(new Ghost(possible.get(random).getxPos() * 16,possible.get(random).getyPos() * 16,Enemy.rndNum(1,4)));
                        break;
                }
                enemies --;
                
            }
            
        }
        System.out.println(enemiesList);
        System.out.println(enemiesList.size());
        return board;
    }
    
    public void death(){
        detonator = false;
        player.setLives(player.getLives() - 1);
        restartLevel();
        
    }
    
    public void restartLevel(){
        
        enemiesList = new ArrayList();
        //windowState = 0;
        timeBonus = 240;
        bonusCounter = 0;
        playingLevel = false;
        player.setxPos(16);
        player.setyPos(16);
        frameCounter = 0;
        if (level < 4) {
            difficulty = 1;
        } else if (level < 7) {
            difficulty = 2;
        } else if (level < 9) {
            difficulty = 3;
        } else {
            difficulty = 4;
        }
         board = levelRandomizer(difficulty);
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
                     if (((Block)(board[o][i].getOnTile())).getPU() == null) {
                     print += "UB\t";
                     }
                 }
                 else if (((Block)(board[o][i].getOnTile())).equals(breaka)) {
                     if (((Block)(board[o][i].getOnTile())).getPU() == null) {
                     print += "BB\t";
                     } else if ((board[o][i].getOnTile()) instanceof Exit){
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
    public void updateGameScreen(Tile[][] board) {
        
    }
    public void score() {
        scores = new ArrayList();
        try {
            InputStream in = DrawingSurface.class.getResourceAsStream("scores.txt");
            Scanner s = new Scanner(in);
            int value;
            String name;
            Score score;
            while (s.hasNextLine()) {
                name = s.nextLine();
                value = Integer.parseInt(s.nextLine());
                score = new Score(value,name);
                scores.add(score);
            }
            
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        
    }
    public void drawScores(Graphics2D g2d) {
        g2d.setColor(Color.white);
        
        for (int i = 0; i < scores.size(); i ++) {
            g2d.drawString((i + 1) + "     " + scores.get(i).getName()+ "      " + scores.get(i).getAmount(),330,250 + (50 * i));
        }
    }
    public void addScores() {
        String userName;
        userName = JOptionPane.showInputDialog("Enter 3 characters to represent you.");
        while (userName.length() > 3) {
            userName = JOptionPane.showInputDialog("Invalid entry, please enter a maximum of 3 characters.\nEnter 3 characters to represent you.");
        }
        scores.add(new Score(score,userName));
        
        
    }

    public int getExitX() {
        return exitX;
    }

    public void setExitX(int exitX) {
        this.exitX = exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public void setExitY(int exitY) {
        this.exitY = exitY;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimeBonus() {
        return timeBonus;
    }

    public void setTimeBonus(int timeBonus) {
        this.timeBonus = timeBonus;
    }
    
}

