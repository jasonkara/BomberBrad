/**
 * DKP Studios 
 * 2020-01-14
 * JPanel that displays game graphics, contains most game logic
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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class DrawingSurface extends JPanel {

    int windowState;
    int selectedYPos;
    int frameCounter;
    ArrayList<Enemy> enemiesList = new ArrayList();
    ArrayList<Integer> powerUpList = new ArrayList();
    Player player = new Player(16, 16, 1);
    boolean moveDown = false, moveUp = false, moveLeft = false, moveRight = false;
    boolean playingLevel = false;
    Tile[][] board;
    int difficulty;
    Clip clip, clipSE; // two seperate clips: one for background music (BGM), one for sound effects (SE)
    AudioInputStream[] audio = new AudioInputStream[3];
    Timer timer;
    int exitX, exitY;
    int level;
    int bombs, maxBombs;
    int score;
    private int length;
    private boolean firePass;
    ArrayList<Score> scores = new ArrayList();
    boolean addedScore;
    int timeBonus;
    int bonusCounter;
    boolean bombPass, wallPass;
    UIManager ui = new UIManager();
    BufferedImage bombHud = null, lengthHud = null, speedHud = null, bombpassHud = null, wallHud = null, fireHud = null;

    /**
     * constructor for DrawingSurface - things that happen when program is started
     */
    public DrawingSurface() {

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) { 
                repaint(); // sets up ActionListener to redraw the screen along with the timer
            }
        };
        windowState = 0; // sets default window state (main menu)
        selectedYPos = 381; // sets default position for cursor on main menu (next to "Start Game")
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() { // manager for keyboard input
            @Override
            public boolean dispatchKeyEvent(KeyEvent k) {
                if (windowState == 0) { // keyboard inputs for main menu
                    if (k.getID() == KeyEvent.KEY_PRESSED) { // if a key is pressed...
                        if (k.getKeyCode() == KeyEvent.VK_W) { // if the key pressed is W...
                            updateMenuSelectedYPos(-1); // move the cursor up one selection
                        } else if (k.getKeyCode() == KeyEvent.VK_S) { // if the key pressed is S...
                            updateMenuSelectedYPos(1); // move the cursor down one selection
                        } else if (k.getKeyCode() == KeyEvent.VK_ENTER) { // if the key pressed is enter...
                            getSelected(); // select the chosen option
                        }
                    }
                } else if (windowState == 1) { // keyboard inputs for main game
                    if (k.getID() == KeyEvent.KEY_PRESSED) { // if a key is pressed...
                        if (k.getKeyCode() == KeyEvent.VK_W) { // if the key is W...
                            moveUp = true; // start moving the player up
                        } else if (k.getKeyCode() == KeyEvent.VK_S) { // if the key is S...
                            moveDown = true; // start moving the player down
                        } else if (k.getKeyCode() == KeyEvent.VK_A) { // if the key is A...
                            moveLeft = true; // start moving the player left
                        } else if (k.getKeyCode() == KeyEvent.VK_D) { // if the key is D...
                            moveRight = true; // start moving the player right
                        } else if (k.getKeyCode() == KeyEvent.VK_SPACE) { // if the key is space...
                            // if the player has bombs left to place, is not dying, and is on an empty space...
                            if (bombs >= 1 && !player.isDying() && board[(player.getXPos() + 8) / 16][(player.getYPos() + 8) / 16].getOnTile() == null) {
                                bombs--; // decrement no. of bombs left to place
                                playSE("placebomb"); // plays sound effect for placing a bomb
                                // place a bomb on the tile where the player is standing
                                board[(player.getXPos() + 8) / 16][(player.getYPos() + 8) / 16].setOnTile(new Bomb((player.getXPos() + 8) / 16, (player.getYPos() + 8) / 16));
                            }
                        }
                    } else if (k.getID() == KeyEvent.KEY_RELEASED) { // if a key is released...
                        if (k.getKeyCode() == KeyEvent.VK_W) { // if the key is W...
                            moveUp = false; // stop moving the player up
                        } else if (k.getKeyCode() == KeyEvent.VK_S) { // if the key is A...
                            moveDown = false; // stop moving the player down
                        } else if (k.getKeyCode() == KeyEvent.VK_A) { // if the key is S...
                            moveLeft = false; // stop moving the player left
                        } else if (k.getKeyCode() == KeyEvent.VK_D) { // if the key is D...
                            moveRight = false; // stop moving the player right
                        }
                    }
                } else if (windowState == 2) { // keyboard inputs for high scores menu
                    if (k.getKeyCode() == KeyEvent.VK_SPACE && k.getID() == KeyEvent.KEY_RELEASED) { // if the space key is released...
                        // prompt user for username to search for
                        String user = JOptionPane.showInputDialog(null, "Enter a username to search for.", "Search", JOptionPane.PLAIN_MESSAGE);
                        searchScores(user); // search the scores for that user and display the result
                    }
                }
                if (k.getKeyCode() == KeyEvent.VK_ESCAPE && windowState != 0 && !player.isDying()) { // following input can happen at any time except main menu
                    if (windowState == 1) { // if the user is in the main game...
                        clip.stop(); // stops other background music
                        playAudio("title"); // plays the title music again only if coming from main game
                    }
                    windowState = 0; // ESC returns to main menu
                }
                return false;
            }
        });

        ui.put("OptionPane.background", Color.BLACK);
        ui.put("Panel.background", Color.BLACK);
        ui.put("OptionPane.messageForeground", Color.white); // adjusts visuals of JOptionPane popups to better match the program
        score(); // sets up/accesses data file containing high scores
        timer = new Timer(50, al); 
        timer.start(); // instantiates and starts a timer that ticks every 50 milliseconds (goes along with above ActionListener that redraws game screen)
        playAudio("title"); // plays title music for main menu
        loadSprites(); // loads all sprites
    }

    /**
     * method that is called every frame to update what is on screen
     * @param g graphics object used to draw the screen
     */
    private void doDrawing(Graphics g) {
        // following things are drawn regardless of which part of the program you're in (main menu, game, high scores, credits)
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 960, 776); // black rectangle covering entire screen to serve as background
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24)); // standard white font to be used for text throughout the program
        BufferedImage menuImg = null, lifeSprite = null;
        try { // tries to load images to use on main menu and level introduction
            menuImg = ImageIO.read(getClass().getResource("/bomberbrad/menulogo.png"));
            lifeSprite = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/r1.png"));
        } catch (IOException e) { // if the files for the images aren't found...
            g2d.drawString("Error: " + e, 10, 10); // display error message
        }
        if (windowState == 0) { // main menu
            g2d.drawString("Start Game", 370, 400);
            g2d.drawString("High Scores", 370, 450);
            g2d.drawString("Credits", 370, 500);
            g2d.drawString("Exit", 370, 550); // draws selectable options
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("© 2020 DKP Studios", 370, 650); // draws copyright information in smaller text
            // draws triangle to represent cursor next to currently selected options
            g2d.fillPolygon(new int[]{350, 350, 360}, new int[]{selectedYPos, selectedYPos + 20, selectedYPos + 10}, 3);
            g2d.drawImage(menuImg, 112, 100, 848, 252, 0, 0, 368, 76, null); // draws game logo at top of screen
        } else if (windowState == 1) { // main game
            if (player.getLives() < 1) { // if the player has run out of lives...
                g2d.drawString("Press ESC to return to the menu", 300, 500); // draws prompt to return to main menu
                g2d.setFont(new Font("Arial", Font.BOLD, 64));
                g2d.setColor(Color.RED);
                g2d.drawString("Game Over!", 300, 300); // draws "game over" text in larger red font
                if (addedScore == false) { // if the player's score has not yet been added to the scores data file...
                    addedScore = true; // prevent this input from reappearing
                    addScores(); // display popup to input user's username and save high score
                }
                if (frameCounter == 0) { // if this is the first frame that the game over screen has appeared in...
                    clip.stop(); // stops other background music
                    playAudio("gameover"); // plays game over music
                    frameCounter = 1; // stops music from replaying every frame
                }
            } else { // if the player still has lives left...
                if (level == 11) { // if the player has beat all 10 levels...
                    if (frameCounter == 0) { // if this is the first frame that the ending screen has appeared...
                        clip.stop(); // stops other background music
                        playAudio("ending"); // plays game over music
                        frameCounter = 1; // prevents this section from replaying every frame
                        addScores(); // display popup to input user's username and save high score
                    }
                    g2d.drawString("You have recovered the tests from the evil Schaeffer", 180, 100);
                    g2d.drawString("Report cards can go out on time", 290, 150);
                    g2d.drawString("Your job is saved", 375, 200);
                    g2d.drawString("Press ESC to return to the menu", 300, 650);
                    g2d.setFont(new Font("Arial", Font.BOLD, 64));
                    g2d.drawString("You Win!", 340, 400); // display congratulatory message
                } else { // if the player has not yet beat all 10 levels...
                    if (playingLevel) { // if the players is currently playing a level...
                        mainGame(g2d); // draw the main game
                    } else { // if the player is watching the introduction to a level...
                        if (frameCounter == 0) { // if this is the first frame that the introduction has been shown...
                            g2d.drawString("Level " + level, 400, 340);
                            g2d.drawString("Lives: " + player.getLives(), 420, 410); // display level and no. of lives remaining
                            g2d.drawImage(lifeSprite, 370, 385, 402, 417, 0, 0, 16, 16, null); // draws a sprite of the character next to life counter
                            frameCounter = 1; // stops music clip from replaying every frame
                            clip.stop(); // stops other background music
                            playAudio("stagestart"); // plays stage starting music
                        } else {
                            while (clip.getMicrosecondLength() != clip.getMicrosecondPosition()) { // freezes program until end of stage starting music
                            }
                            clip.stop(); // stops other background music
                            playAudio("stage"); // plays main stage music
                            clip.loop(clip.LOOP_CONTINUOUSLY); // loops main stage music continuously
                            playingLevel = true; // begins playing the elvel
                        }
                    }
                }
            }
        } else if (windowState == 2) { // high scores
            g2d.drawString("Press SPACE to search for a high score", 260, 650);
            g2d.setFont(new Font("Arial", Font.BOLD, 32));
            g2d.drawString("High Scores", 380, 100); // draws title of section and prompt to search for scores
            g2d.setFont(new Font("Monospaced", Font.BOLD, 24));
            g2d.drawString("No.  Name   Score", 330, 200); // draws header for high scores table
            drawScores(g2d);
        } else if (windowState == 3) { // credits
            g2d.drawImage(menuImg, 150, 75, 518, 151, 0, 0, 368, 76, null); // draws the game logo
            g2d.drawString("was created by:", 550, 122);
            g2d.drawString("RILEY DECONKEY - Systems Analyst", 200, 300);
            g2d.drawString("JASON KARAPOSTOLAKIS - Technical Writer", 200, 400);
            g2d.drawString("REEGAL PANCHAL - Project Manager", 200, 500);
            g2d.drawString("Special thanks to TOMMY JOHNSTON", 200, 600);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("© 2020 DKP Studios", 370, 650); // draws credits and copyright info
        }

    }

    /**
     * plays background music
     * @param sound name of music track to be played
     */
    public void playAudio(String sound) {
        try {
            AudioInputStream instream = AudioSystem.getAudioInputStream(getClass().getResource("/bomberbrad/audio/" + sound + ".wav")); // retrieves audio file
            clip = AudioSystem.getClip(); // assigns audio file to bgm clip
            clip.open(instream); // opens audio file
        } catch (Exception e) {
        }
        clip.start(); // starts playing file
    }

    /**
     * plays sound effect
     * @param sound name of music track to be played
     */
    public void playSE(String sound) {
        try {
            AudioInputStream instream = AudioSystem.getAudioInputStream(getClass().getResource("/bomberbrad/audio/" + sound + ".wav")); // retrieves audio file
            clipSE = AudioSystem.getClip(); // assigns audio file to se clip
            clipSE.open(instream); // opens audio file
        } catch (Exception e) {
        }
        clipSE.start(); // starts playing file
    }

    /**
     * method to update selected option on main menu
     * @param i integer that hold which direction to move (-1 for up, 1 for down)
     */
    private void updateMenuSelectedYPos(int i) {
        if (i == -1 && selectedYPos >= 431) { // if the cursor is to move up and is not already at the topmost selection...
            selectedYPos -= 50; // move cursor to next selection upward
        } else if (i == 1 && selectedYPos <= 481) { // if the cursor is to move down and is not already at the bottommost selection...
            selectedYPos += 50; // move cursor to next selection downward
        }
    }

    /**
     * method called when selecting an option on main menu
     */
    private void getSelected() {
        if (selectedYPos == 381) { // if the cursor is next to "start game"...
            windowState = 1; // go to main game
            difficulty = 1;
            level = 1; // reset difficulty and level variables to default values
            restartLevel(); // creates new level
            playingLevel = false; // plays level intro screen
            powerUpList = new ArrayList(); // resets power-ups
            bombs = 1;
            maxBombs = 1;
            length = 1; 
            player.setSpeed(2);
            firePass = false;
            addedScore = false;
            bombPass = false;
            wallPass = false; // removes all player's upgrades
            player.setLives(3);
            score = 0; // reset no. of lives and score to default values
        } else if (selectedYPos == 431) { // if the cursor is next to "high scores"...
            windowState = 2; // go to high scores
        } else if (selectedYPos == 481) { // if the cursor is next to "credits"...
            windowState = 3; // go to credits
        } else { // if the cursor is next to "exit"...
            System.exit(0); // exit the program
        }
    }

    /**
     * accessor for bomb length
     * @return bomb length (in tiles)
     */
    public int getLength() {
        return length;
    }

    /**
     * mutator for bomb length
     * @param length new bomb length (in tiles)
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * accessor for ArrayList of powerups
     * @return ArrayList of powerups
     */
    public ArrayList<Integer> getPowerUpList() {
        return powerUpList;
    }

    /**
     * method called every frame to redraw screen
     * @param g graphics object used to draw
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g); // calls doDrawing to redraw screen
    }

    /**
     * method to determine if two things are intersecting
     * @param x1 x coordinate of top left corner of first object
     * @param y1 y coordinate of top left corner of first object
     * @param x2 x coordinate of top left corner of second object
     * @param y2 y coordinate of top left corner of second object
     * @return whether or not they are intersecting
     */
    public boolean intersecting(int x1, int y1, int x2, int y2) {

        boolean xOverlap, yOverlap;
        xOverlap = (Math.abs(x1 - x2) < 16); // the two objects are overlapping in the x axis if the difference in their coordinates is less than 16
        yOverlap = (Math.abs(y1 - y2) < 16); // the two objects are overlapping in the y axis if the difference in their coordinates is less than 16
        return (xOverlap && yOverlap); // return true only if the objects are overlapping in both the x and y axes
    }

    /**
     * accessor for list of enemies
     * @return ArrayList of enemies
     */
    public ArrayList<Enemy> getEnemiesList() {
        return enemiesList;
    }

    /**
     * mutator for list of enemies
     * @param enemiesList new list of enemies
     */
    public void setEnemiesList(ArrayList<Enemy> enemiesList) {
        this.enemiesList = enemiesList;
    }

    /**
     * accessor for game board
     * @return game board (two-dimensional array of Tiles)
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * mutator for game board
     * @param board new game board
     */
    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    /**
     * accessor for whether or not the player has fire pass powerup
     * @return whether or not the player has fire pass powerup
     */
    public boolean hasFirePass() {
        return firePass;
    }

    /**
     * mutator for whether or not the player has fire pass powerup
     * @param firePass new value for whether or not the player has fire pass powerup
     */
    public void setFirePass(boolean firePass) {
        this.firePass = firePass;
    }

    /**
     * loads sprites for all entities and tiles
     */
    private void loadSprites() {
        Ballom b = new Ballom(0, 0, 0); // creates new ballom enemy in corner of screen
        b.loadImages(); // loads images for ballom class (loadImages is a static method in Ballom)
        b = null; // deletes loaded enemy
        Dahl d = new Dahl(0, 0, 0); // repeats that process for each enemy and tile
        d.loadImages();
        d = null;
        Onil o = new Onil(0, 0, 0);
        o.loadImages();
        o = null;
        Block bl = new Block(0, 0, null, false);
        bl.loadImages();
        bl = null;
        Explosion ex = new Explosion(0, 0, 0);
        ex.loadImages();
        ex = null;
        Bomb bo = new Bomb(0, 0);
        bo.loadImages();
        bo = null;
        player.loadImages();
        PowerUp pu = new PowerUp(0, 0, 0);
        pu.loadImages();
        pu = null;
        Exit e = new Exit(0, 0);
        e.loadImages();
        e = null;
        Blob bb = new Blob(0, 0, 0);
        bb.loadImages();
        bb = null;
        Ghost g = new Ghost(0, 0, 0);
        g.loadImages();
        g = null;
        try { // tries to load images to represent powerups on hud
            bombHud = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/1.png"));
            lengthHud = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/2.png"));
            speedHud = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/3.png"));
            wallHud = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/4.png"));
            fireHud = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/5.png"));
            bombpassHud = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/6.png"));
        } catch (IOException error) {
        }
    }

    private void mainGame(Graphics2D g2d) {
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
        player.action(this, g2d);
        for (int i = 0; i < 11; i++) {
            for (int o = 0; o < 15; o++) {
                board[o][i].update(this);
                board[o][i].draw(board, g2d);
                g2d.setColor(Color.WHITE);
            }
        }
        g2d.drawString("LEVEL: " + level + "   LIVES: " + player.getLives() + "   SCORE: " + score, 40, 745);
        if (firePass) {
            g2d.drawImage(fireHud, 496, 720, 528, 752, 0, 0, 16, 16, null);
        }
        if (player.getSpeed() == 4) {
            g2d.drawImage(speedHud, 536, 720, 568, 752, 0, 0, 16, 16, null);
        }
        if (bombPass) {
            g2d.drawImage(bombpassHud, 576, 720, 608, 752, 0, 0, 16, 16, null);
        }
        if (wallPass) {
            g2d.drawImage(wallHud, 616, 720, 648, 752, 0, 0, 16, 16, null);
        }
        g2d.drawImage(bombHud, 696, 720, 728, 752, 0, 0, 16, 16, null);
        g2d.drawImage(lengthHud, 816, 720, 848, 752, 0, 0, 16, 16, null);
        g2d.drawString("x " + maxBombs + "             x " + length, 737, 745);
        player.draw(g2d);

        for (Enemy e : enemiesList) {
            e.action(board);
            e.draw(g2d);
        }
        bonusCounter++;
        if (bonusCounter >= 20) {
            if (timeBonus > 0) {
                timeBonus--;
            }
            bonusCounter = 0;
        }

    }

    private Tile[][] levelRandomizer(int difficulty) {
        int enemies = 5;
        int breakableBlocks = 25;
        int powerups = 2;
        board = new Tile[15][11];
        //Creating blank tiles
        for (int i = 0; i < 15; i++) {
            for (int o = 0; o < 11; o++) {
                board[i][o] = new Tile(i, o, null);
            }
        }
        //Creating top and bottom borders
        for (int i = 0; i < 15; i++) {
            board[i][0] = new Tile(i, 0, new Block(i, 0, null, false));
            board[i][10] = new Tile(i, 10, new Block(i, 10, null, false));
        }
        //Creating left and right borders
        for (int i = 0; i < 11; i++) {
            board[0][i] = new Tile(0, i, new Block(0, i, null, false));
            board[14][i] = new Tile(14, i, new Block(14, i, null, false));
        }
        //creating spaced unbreakable blocks
        for (int i = 2; i < 15; i += 2) {
            for (int o = 2; o < 11; o += 2) {
                board[i][o] = new Tile(i, o, new Block(i, o, null, false));
            }
        }
        board[1][3] = new Tile(1, 3, new Block(1, 3, null, true));
        board[3][1] = new Tile(3, 1, new Block(3, 1, null, true));
        //creating random variable to choose random positions
        int random;
        //creating arraylist of possible positions for breakable blocks, 
        ArrayList<Tile> possible = new ArrayList();
        //filling arraylist with possible tiles
        for (int i = 0; i < 11; i++) {
            for (int o = 0; o < 15; o++) {
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
            random = (int) (Math.random() * possible.size());
            //placing breakable block at chosen position
            (possible.get(random)).setOnTile(new Block(possible.get(random).getxPos(), possible.get(random).getyPos(), null, true));
            //removing place from list
            possible.remove(random);
            //subtracting from number of remaining blocks to be placed
            breakableBlocks--;
        }
        while (powerups > 0) {
            random = (int) (Math.random() * possible.size());
            int puType;
            switch (difficulty) {
                case 1:
                    puType = (int) (Math.random() * 2) + 1;
                    break;
                case 2:
                    puType = (int) (Math.random() * 4) + 1;
                    break;
                default:
                    puType = (int) (Math.random() * 6) + 1;
            }
            (possible.get(random)).setOnTile(new Block(possible.get(random).getxPos(), possible.get(random).getyPos(), new PowerUp(possible.get(random).getxPos(), possible.get(random).getyPos(), puType), true));
            powerups--;
            possible.remove(random);
        }
        random = (int) (Math.random() * possible.size());
        (possible.get(random)).setOnTile(new Block(possible.get(random).getxPos(), possible.get(random).getyPos(), new Exit(possible.get(random).getxPos(), possible.get(random).getyPos()), true));
        exitX = possible.get(random).getxPos();
        exitY = possible.get(random).getyPos();
        //exitX = 1;
        //exitY = 2;
        possible.remove(random);
        int enemyType;
        while (enemies > 0) {
            random = (int) (Math.random() * possible.size());
            switch (difficulty) {
                case 1:
                    enemiesList.add(new Ballom(possible.get(random).getxPos() * 16, possible.get(random).getyPos() * 16, Enemy.rndNum(1, 4)));
                    enemies--;
                    break;
                case 2:
                    enemyType = (int) (Math.random() * 3) + 1;
                    switch (enemyType) {
                        case 1:
                            enemiesList.add(new Ballom(possible.get(random).getxPos() * 16, possible.get(random).getyPos() * 16, Enemy.rndNum(1, 4)));
                            break;
                        case 2:
                            enemiesList.add(new Onil(possible.get(random).getxPos() * 16, possible.get(random).getyPos() * 16, Enemy.rndNum(1, 4)));
                            break;
                        case 3:
                            enemiesList.add(new Dahl(possible.get(random).getxPos() * 16, possible.get(random).getyPos() * 16, Enemy.rndNum(1, 4)));
                            break;
                    }

                    enemies--;
                default:
                    enemyType = (int) (Math.random() * 5) + 1;
                    switch (enemyType) {
                        case 1:
                            enemiesList.add(new Ballom(possible.get(random).getxPos() * 16, possible.get(random).getyPos() * 16, Enemy.rndNum(1, 4)));
                            break;
                        case 2:
                            enemiesList.add(new Onil(possible.get(random).getxPos() * 16, possible.get(random).getyPos() * 16, Enemy.rndNum(1, 4)));
                            break;
                        case 3:
                            enemiesList.add(new Dahl(possible.get(random).getxPos() * 16, possible.get(random).getyPos() * 16, Enemy.rndNum(1, 4)));
                            break;
                        case 4:
                            enemiesList.add(new Blob(possible.get(random).getxPos() * 16, possible.get(random).getyPos() * 16, Enemy.rndNum(1, 4)));
                            break;
                        case 5:
                            enemiesList.add(new Ghost(possible.get(random).getxPos() * 16, possible.get(random).getyPos() * 16, Enemy.rndNum(1, 4)));
                            break;
                    }
                    enemies--;

            }

        }
        System.out.println(enemiesList);
        System.out.println(enemiesList.size());
        return board;
    }

    public void death() {
        player.setLives(player.getLives() - 1);
        restartLevel();

    }

    public void restartLevel() {

        bombs = maxBombs;
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

    public void updateGameScreen(Tile[][] board) {

    }

    public void score() {
        File f = new File(System.getProperty("user.home") + "/Documents/BomberBrad");
        File file = new File(System.getProperty("user.home") + "/Documents/BomberBrad/scores.txt");
        String userName;
        int userAmount;
        try {
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                userName = s.nextLine();
                userAmount = Integer.parseInt(s.nextLine());
                scores.add(new Score(userAmount, userName));
            }

        } catch (FileNotFoundException e) {
            try {
                f.mkdirs();
                file.createNewFile();
                PrintWriter writer = new PrintWriter(file);
                scores.add(new Score(3000, "jsk"));
                scores.add(new Score(2000, "ree"));
                scores.add(new Score(1000, "rwd"));
                for (Score s : scores) {
                    writer.println(s.getName());
                    writer.println(s.getAmount());

                }
                writer.close();
            } catch (FileNotFoundException u) {
                System.out.println(u);

            } catch (IOException o) {
                System.out.println(o);
            }
        }

    }

    public void drawScores(Graphics2D g2d) {
        g2d.setColor(Color.white);
        if (scores.size() < 5) {
            for (int i = 0; i < scores.size(); i++) {
                g2d.drawString((i + 1) + "     " + scores.get(i).getName() + "      " + scores.get(i).getAmount(), 330, 250 + (50 * i));
            }
        } else {
            for (int i = 0; i < 5; i++) {
                g2d.drawString((i + 1) + "     " + scores.get(i).getName() + "      " + scores.get(i).getAmount(), 330, 250 + (50 * i));
            }
        }

    }

    public void addScores() {
        String userName;
        userName = JOptionPane.showInputDialog(null, "Enter 3 characters to represent you.", "Submit Score", JOptionPane.PLAIN_MESSAGE);
        while (userName.length() > 3) {
            userName = JOptionPane.showInputDialog(null, "Invalid entry, please enter a maximum of 3 characters.\nEnter 3 characters to represent you.", "ERROR", JOptionPane.PLAIN_MESSAGE);
        }
        scores.add(new Score(score, userName));
        File f = new File(System.getProperty("user.home") + "/Documents/BomberBrad");
        File file = new File(System.getProperty("user.home") + "/Documents/BomberBrad/scores.txt");
        Score[] sorting = new Score[scores.size()];
        for (int i = 0; i < scores.size(); i++) {
            sorting[i] = new Score(scores.get(i).getAmount(), scores.get(i).getName());
        }
        mergeSort(sorting, 0, sorting.length - 1);
        scores.clear();
        for (int i = 0; i < sorting.length; i++) {
            scores.add(sorting[i]);
        }

        try {

            PrintWriter writer = new PrintWriter(file);

            for (Score s : scores) {

                writer.println(s.getName());
                writer.println(s.getAmount());
            }
            writer.close();

        } catch (FileNotFoundException e) {
            try {
                f.mkdirs();
                file.createNewFile();
                PrintWriter writer = new PrintWriter(file);
                for (Score s : scores) {
                    writer.println(s.getName());
                    writer.println(s.getAmount());

                }
                writer.close();
            } catch (FileNotFoundException u) {
                System.out.println(u);

            } catch (IOException o) {
                System.out.println(o);
            }

        }

    }

    public void searchScores(String name) {
        boolean found = false;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).getName().equals(name)) {
                JOptionPane.showMessageDialog(null, name + " is first found in position " + (i + 1) + " with a score of " + scores.get(i).getAmount() + ".", "Search Results", JOptionPane.PLAIN_MESSAGE);
                i = scores.size() + 1;
                found = true;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "Name not found.", "Search Results", JOptionPane.PLAIN_MESSAGE);
        }
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
        maxBombs = bombs;
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

    public boolean isBombPass() {
        return bombPass;
    }

    public void setBombPass(boolean bombPass) {
        this.bombPass = bombPass;
    }

    public boolean isWallPass() {
        return wallPass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public void setTimeBonus(int timeBonus) {
        this.timeBonus = timeBonus;
    }

    public static void merge(Score nums[], int l, int m, int r) {
        int i, j, k;
        int n1 = m - l + 1;
        int n2 = r - m;
        Score L[] = new Score[n1];
        Score R[] = new Score[n2];
        for (i = 0; i < n1; i++) {
            L[i] = nums[l + i];
        }
        for (j = 0; j < n2; j++) {
            R[j] = nums[m + 1 + j];
        }
        i = 0;
        j = 0;
        k = l;
        while (i < n1 && j < n2) {
            if (L[i].getAmount() >= R[j].getAmount()) {
                nums[k] = L[i];
                i++;
            } else {
                nums[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            nums[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            nums[k] = R[j];
            j++;
            k++;
        }
    }

    public static void mergeSort(Score nums[], int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(nums, l, m);
            mergeSort(nums, m + 1, r);
            merge(nums, l, m, r);
        }
    }

}
