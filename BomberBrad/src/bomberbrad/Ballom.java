/**
 * DKP Studios 
 * 2020-01-14
 * Class that represents the Ballom
 */
package bomberbrad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ballom extends Enemy {

    private static BufferedImage[] sprites = new BufferedImage[11];
    private int frameCounter;

    /**
     * Constructor, accepts x and y position and direction
     * @param xPos x position
     * @param yPos y position
     * @param direction direction the ballom is facing
     */
    public Ballom(int xPos, int yPos, int direction) {
        //call super class constructor, all balloms have 1 health and a speed of 2
        super(xPos, yPos, 1, direction, 2);
        //set frame counter to 0
        frameCounter = 0;
    }

    /**
     * Draw method, draws the ballom
     *
     * @param g2d graphics 2d object to draw with
     */
    public void draw(Graphics2D g2d) {
        BufferedImage shown;
        //if facing up or right
        if (direction <= 2) {
            //if frame counter is less than 2
            if (frameCounter < 2) {
                //show first sprite in animation cycle
                shown = sprites[3];
                //else if frame counter is between 6 and an 3
            } else if (frameCounter < 6 && frameCounter > 3) {
               //display third sprite in animation cycle
                shown = sprites[5];
            } else {
                //else show second/fourth sprite in animation cycle
                shown = sprites[4];
            }
            //else direction must be down or left
        } else {
            //if first two frames
            if (frameCounter < 2) {
                //show first sprite in animation cycle
                shown = sprites[0];
                //else if frame counter between 3 and 6
            } else if (frameCounter < 6 && frameCounter > 3) {
                //display third sprite in animation cycle
                shown = sprites[2];
                //else if frame count is more than 6
            } else {
                //else show second/fourth sprite in animation cycle
                shown = sprites[1];
            }
        }
        //display ballom
        g2d.drawImage(shown, xPos * 4, yPos * 4, xPos * 4 + 64, yPos * 4 + 64, 0, 0, 16, 16, null);
        //add to frame counter
        frameCounter++;
        //if frame counter is 8, set it to zero to restart animation
        if (frameCounter == 8) {
            frameCounter = 0;
        }

    }

    /**
     * method to load all sprites
     */
    public void loadImages() {
        try {
            //load sprites into array
            sprites[0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/l1.png"));
            sprites[1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/l2.png"));
            sprites[2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/l3.png"));
            sprites[3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/r1.png"));
            sprites[4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/r2.png"));
            sprites[5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/r3.png"));
            sprites[6] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/die1.png"));
            sprites[7] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/die2.png"));
            sprites[8] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/die3.png"));
            sprites[9] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/die4.png"));
            sprites[10] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ballom/die5.png"));
            //if IOException is thrown, print it.
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }

}
