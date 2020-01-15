/**
 * DKP Studios
 * 2020-01-14
 * Class that represents the player
 */
package bomberbrad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Player extends Entity {

    private boolean moving;
    private int lives;
    private static BufferedImage[] sprites = new BufferedImage[19];
    private int frameCounter;
    private boolean dying;

    /**
     * player constructor
     *
     * @param xPos x position of the player
     * @param yPos y position of the player
     * @param direction the direction the player is facing
     */
    Player(int xPos, int yPos, int direction) {
        //call entity constructor
        super(xPos, yPos, 1, direction, 2);
        //set lives to 3
        lives = 3;
        //begin on first animation frame
        frameCounter = 0;
        //player is not dying when its created
        dying = false;
    }

    /**
     * Action function, updates the player object
     *
     * @param ds drawing surface, used for its getters and setters
     * @param g2d graphics 2d object to draw with
     */
    public void action(DrawingSurface ds, Graphics2D g2d) {
        //get enemies and board
        Tile[][] map = ds.getBoard();
        ArrayList<Enemy> EL = ds.getEnemiesList();
        //check boolean checking if player is on a bomb
        boolean onBomb = map[(xPos + 8) / 16][(yPos + 8) / 16].getOnTile() instanceof Bomb;
        //if the player is on a bomb, allow them to walk through the bomb
        if (onBomb) {
            walkable.add(Bomb.class);
        }
        //if moving
        if (moving) {
            //check which direction
            switch (direction) {
                //if direction is up
                case 1:
                    //check if either top corner will be put into an unwalkable tile if player moves
                    if (isWalkable(map[xPos / 16][(yPos - speed) / 16].getOnTile())//top left of the player + speed
                            && isWalkable(map[(xPos + 15) / 16][(yPos - speed) / 16].getOnTile())) {//top right
                        //if not, move.
                        move();

                    } else {
                        //otherwise shift player left or right depending on which side they are closer too
                        //this helps the player not have to be perfectly lined up with the gap to move
                        if (isWalkable(map[(xPos) / 16][(yPos - speed) / 16].getOnTile())) {
                            shift(-1, 0);
                        } else if (isWalkable(map[(xPos + 15) / 16][(yPos - speed) / 16].getOnTile())) {
                            shift(1, 0);
                        }
                    }

                    break;
                //if facing right
                case 2:
                    //check if either right corner will be put into an unwalkable tile if player moves
                    if (isWalkable(map[(xPos + 15 + speed) / 16][(yPos) / 16].getOnTile())
                            && isWalkable(map[(xPos + 15 + speed) / 16][(yPos + 15) / 16].getOnTile())) {
                        //if not, move
                        move();
                    } else {
                        //otherwise shift player up or down depending on which is closer
                        //this helps the player not have to be perfectly lined up
                        if (isWalkable(map[(xPos + 15 + speed) / 16][(yPos) / 16].getOnTile())) {
                            shift(0, -1);
                        } else if (isWalkable(map[(xPos + 15 + speed) / 16][(yPos + 15) / 16].getOnTile())) {
                            shift(0, 1);
                        }
                    }

                    break;
                //if facing down
                case 3:
                    //check if either bottom corner will be put into an unwalkable tile if player moves
                    if (isWalkable(map[xPos / 16][(yPos + 15 + speed) / 16].getOnTile())
                            && isWalkable(map[(xPos + 15) / 16][(yPos + 15 + speed) / 16].getOnTile())) {
                        //if not, move
                        move();
                    } else {
                        //otherwise shift player left or right depending on which side they are closer too
                        //this helps the player not have to be perfectly lined up with the gap to move
                        if (isWalkable(map[(xPos) / 16][(yPos + 15 + speed) / 16].getOnTile())) {
                            shift(-1, 0);
                        } else if (isWalkable(map[(xPos + 15) / 16][(yPos + 15 + speed) / 16].getOnTile())) {
                            shift(1, 0);
                        }
                    }
                    break;
                //otherwise direction must be left
                default:
                    //check if either left corner will be put into an unwalkable tile if player moves
                    if (isWalkable(map[(xPos - speed) / 16][(yPos) / 16].getOnTile())
                            && isWalkable(map[(xPos - speed) / 16][(yPos + 15) / 16].getOnTile())) {
                        //if not, move
                        move();
                    } else {
                        //otherwise shift player left or right depending on which side they are closer too
                        //this helps the player not have to be perfectly lined up with the gap to move
                        if (isWalkable(map[(xPos - speed) / 16][(yPos) / 16].getOnTile())) {
                            shift(0, -1);
                        } else if (isWalkable(map[(xPos - speed) / 16][(yPos + 15) / 16].getOnTile())) {
                            shift(0, 1);
                        }
                    }

            }
        }
        //remove ability to walk through bombs so that once player leaves the bomb they can no longer walk through
        if (onBomb) {
            walkable.remove(Bomb.class);
        }
        //check if player is intersecting each enemy
        for (Enemy e : EL) {
            //if so, begin player dying animation
            if (ds.intersecting(xPos, yPos, e.getXPos(), e.getYPos()) && !dying) {
                ds.clip.stop();
                ds.playAudio("die");
                dying = true;
                frameCounter = 0;
            }
        }
        //dying animation lasts until dying sound clip is over
        if (dying && ds.clip.getMicrosecondLength() == ds.clip.getMicrosecondPosition()) {
            //call death method
            dying = false;
            ds.death();
        }
        //check if player is on exit and all enemies are dead
        if (ds.intersecting(xPos, yPos, ds.getExitX() * 16, ds.getExitY() * 16) && EL.size() == 0) {
            //play level complete sound
            ds.clip.stop();
            ds.playAudio("stagecomplete");
            while (ds.clip.getMicrosecondLength() != ds.clip.getMicrosecondPosition()) {
            }
            //increase level counter
            ds.setLevel(ds.getLevel() + 1);
            //add time bonus to score
            ds.setScore(ds.getScore() + ds.getTimeBonus());
            //generate new level
            ds.restartLevel();

        }
        //if player moves play walking sound effect
        if (!dying && moving && frameCounter % 4 == 0) {
            ds.playSE("step");
        }
    }

    /**
     * Accessor method for if the player is moving
     *
     * @return if the player is moving
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * Mutator method for moving
     *
     * @param moving new state of moving
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * Mutator method for lives
     *
     * @param l new amount of lives
     */
    public void setLives(int l) {
        lives = l;
    }

    /**
     * Accessor method for lives
     *
     * @return the amount of lives the player has
     */
    public int getLives() {
        return lives;
    }

    /**
     * Accessor method for if the player is dying
     *
     * @return if the player is dying
     */
    public boolean isDying() {
        return dying;
    }

    /**
     * Mutator method for dying
     *
     * @param dying new state of dying
     */
    public void setDying(boolean dying) {
        this.dying = dying;
    }

    /**
     * Mutator method for frame counter
     *
     * @param fc
     */
    public void setFrameCounter(int fc) {
        this.frameCounter = fc;
    }

    /**
     * Accessor method for frame counter
     *
     * @return the current frame counter
     */
    public int getFrameCounter() {
        return frameCounter;
    }

    /**
     * Move method, moves player in whichever direction it is facing
     */
    public void move() {
        //if player isnt dying
        if (!dying) {
            switch (direction) {
                //if player is facing up
                case 1:
                    //move up
                    yPos -= speed;
                    break;
                //if player is facing right
                case 2:
                    //move right
                    xPos += speed;
                    break;
                //if player is facing down
                case 3:
                    //move down
                    yPos += speed;
                    break;
                //if player is facing left
                default:
                    //move left
                    xPos -= speed;
            }
        }
    }

    /**
     * method to draw the player
     *
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        BufferedImage shown = null;
        if (dying) { // if the player is dying...
            if (frameCounter < 14) { // if less than 14 frames have passed...
                shown = sprites[12 + frameCounter / 2]; // play each dying sprite for two frames
            }
            frameCounter++; // increment frame counter
        } else { // if the player is not dying...
            int add = (direction - 1) * 3; // determine amount to add to sprite no. to get right direction
            if (frameCounter < 2) { // if in the first two frames of the animation...
                shown = sprites[0 + add]; // show the first sprite of the animation cycle
            } else if (frameCounter < 6 && frameCounter > 3) { // if in the 5th or 6th frame of animation...
                shown = sprites[2 + add]; // show the third sprite of the animation cycle
            } else { // if in the 3rd, 4th, 7th, or 8th frame of animation
                shown = sprites[1 + add]; // show the second/fourht sprite of the animation cycle (same sprite)
            }
            if (moving) {
                frameCounter++; // if the player is moving, increment frame counter
            }
            if (frameCounter >= 8) { // if the framecounter reaches 8 (end of animation)
                frameCounter = 0; // return to 0 (restart animation)
            }
        }
        g2d.drawImage(shown, xPos * 4, yPos * 4, xPos * 4 + 64, yPos * 4 + 64, 0, 0, 16, 16, null); // draw the player
    }

    public void loadImages() {
        try {
            sprites[0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/u1.png"));
            sprites[1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/u2.png"));
            sprites[2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/u3.png"));
            sprites[3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/r1.png"));
            sprites[4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/r2.png"));
            sprites[5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/r3.png"));
            sprites[6] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/d1.png"));
            sprites[7] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/d2.png"));
            sprites[8] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/d3.png"));
            sprites[9] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/l1.png"));
            sprites[10] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/l2.png"));
            sprites[11] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/l3.png"));
            sprites[12] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die1.png"));
            sprites[13] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die2.png"));
            sprites[14] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die3.png"));
            sprites[15] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die4.png"));
            sprites[16] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die5.png"));
            sprites[17] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die6.png"));
            sprites[18] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die7.png")); // add images to arraylist
        } catch (IOException e) {
            System.out.println("error: " + e); // if IOexception, print error message
        }
    }

}
