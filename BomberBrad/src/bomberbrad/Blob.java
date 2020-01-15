/**
 * DKP Studios
 * 2020-01-14
 * Class that represents the Blob
 */
package bomberbrad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Blob extends Enemy {
    
    private static BufferedImage[] sprites = new BufferedImage[11];
    private int frameCounter;
    
    /**
     * Constructor, same as enemy but with frame counter set to 0
     *
     * @param xPos x position
     * @param yPos y position
     * @param direction direction the enemy is facing
     */
    public Blob(int xPos, int yPos, int direction){
        super( xPos,  yPos,  1,  direction, 2); //super constructor call, all blobs have 1 health and 2 speed
        frameCounter = 0; //set frame counter to 0
        walkable.add(Block.class); // add block to walkable arraylist so they can walk over breakable blocks
    }
    
    /**
     * Draw method, draws the blob
     *
     * @param g2d graphics 2d object to draw with
     */
    public void draw(Graphics2D g2d){
        BufferedImage shown;
        if (direction <= 2) { //if direction is up or right
            if (frameCounter < 2) { //if frame counter is less than 2
                shown = sprites[3]; //show first sprite in animation cycle
            } else if (frameCounter < 6 && frameCounter > 3) { //else if frame counter is between 6 and 3
                shown = sprites[5]; //display third sprite in animation cycle
            } else {
                shown = sprites[4]; //else show second/fourth sprite in animation cycle
            }
        } else { //else direction must be down or left
            if (frameCounter < 2) { //if first two frames
                shown = sprites[0]; //show first sprite in animation cycle
            } else if (frameCounter < 6 && frameCounter > 3) { //else if frame counter between 3 and 6
                shown = sprites[2]; //display third sprite in animation cycle
            } else {
                shown = sprites[1]; //else show second/fourth sprite in animation cycle
            }
        }
        g2d.drawImage(shown,xPos*4,yPos*4,xPos*4+64,yPos*4+64,0,0,16,16,null); //display blob
        frameCounter ++; //add to frame counter
        if (frameCounter == 8) { //if frame counter is 8, set it to zero to restart animation
            frameCounter = 0;
        }
    }
    
    /**
     * method to load all sprites
     */
    public void loadImages() {
        try {    
            sprites[0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/l1.png"));
            sprites[1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/l2.png"));
            sprites[2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/l3.png"));
            sprites[3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/r1.png"));
            sprites[4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/r2.png"));
            sprites[5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/r3.png"));
            sprites[6] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/die1.png"));
            sprites[7] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/die2.png"));
            sprites[8] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/die3.png"));
            sprites[9] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/die4.png"));
            sprites[10] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/die5.png")); //load sprites into array
        } catch (IOException e) {
            System.out.println("error: " + e); //if IOException is thrown, print it.
        }
    }
    
    
    
}
