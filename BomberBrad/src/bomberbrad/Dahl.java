/**
 * DKP Studios
 * 2020-01-14
 * Class that represents the Dahl
 */
package bomberbrad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Dahl extends Enemy {

    private static BufferedImage[] sprites = new BufferedImage[11];
    private int frameCounter;

    /**
     * Constructor, same as enemy but with frame counter set to 0
     *
     * @param xPos x position
     * @param yPos y position
     * @param direction direction the enemy is facing
     */
    public Dahl(int xPos, int yPos, int direction) {
        //super constructor call, all dahls have 1 health and 4 speed
        super(xPos, yPos, 1, direction, 4);
        //set frame counter to 0
        frameCounter = 0;
    }

    /**
     * change direction method, changes direction, but makes sure dahl only
     * moves vertically or horizontally
     */
    public void changeDirection() {
        //random number, either 0 or 1
        int j = rndNum(0, 1);
        //if direction is even
        if (direction % 2 == 0) {
            //set direction to 2 or 4 (right or left)
            direction = (j * 2) + 2;
        } else {
            //set direction to 1 or 3 (up or down)
            direction = (j * 2) + 1;
        }
    }

    /**
     * Draw method, draws the dahl
     *
     * @param g2d graphics 2d object to draw with
     */
    public void draw(Graphics2D g2d) {
        BufferedImage shown;
        //if direction is up or right
        if (direction <= 2) {
            //if frame counter is less than 2
            if (frameCounter < 2) {
                //show first sprite in animation cycle
                shown = sprites[3];
                //else if frame counter is between 6 and 3
            } else if (frameCounter < 6 && frameCounter > 3) {
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
        g2d.drawImage(shown, xPos * 4, yPos * 4, xPos * 4 + 64, yPos * 4 + 64, 0, 0, 16, 16, null); //display dahl
        frameCounter++; //add to frame counter
        if (frameCounter == 8) { //if frame counter is 8, set it to zero to restart animation
            frameCounter = 0;
        }
    }

    /**
     * method to load all sprites
     */
    public void loadImages() {
        try {
            sprites[0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/l1.png"));
            sprites[1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/l2.png"));
            sprites[2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/l3.png"));
            sprites[3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/r1.png"));
            sprites[4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/r2.png"));
            sprites[5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/r3.png"));
            sprites[6] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/die1.png"));
            sprites[7] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/die2.png"));
            sprites[8] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/die3.png"));
            sprites[9] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/die4.png"));
            sprites[10] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/die5.png")); //load sprites into array
        } catch (IOException e) {
            System.out.println("error: " + e); //if IOException is thrown, print it.
        }
    }

}
