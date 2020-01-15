/**
 * DKP Studios
 * 2020-01-15
 * Powerup Class that extends tile object


1 extra bomb 
2 bigger bomb explosion
3 speed
4 go through walls
5 fire pass
6 walk through bombs
*/
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class PowerUp extends tileObject {
    private int type;
    private static BufferedImage[] sprites = new BufferedImage[6];
    
    /**
     * Load images method, loads all sprites into the sprites array
     */
    public void loadImages() {
        //try to load all images
        try {    
            sprites[0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/1.png"));
            sprites[1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/2.png"));
            sprites[2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/3.png"));
            sprites[3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/4.png"));
            sprites[4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/5.png"));
            sprites[5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/6.png"));
            //if io is thrown, print it
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    /**
     * Constructor, accepts x and y positions and a type
     * @param xPos x position of the powerup
     * @param yPos y position of the powerup
     * @param type integer representing the type of powerup
     */
    public PowerUp(int xPos, int yPos, int type) {
        super(xPos,yPos);
        this.type = type;
    }
    /**
     * Accessor method for type of powerup
     * @return integer representing the type of power up
     */
    public int getType() {
        return type;
    }
    /**
     * mutator method for type of powerup
     * @param type the new integer to represent the type of powerup
     */
    public void setType(int type) {
        this.type = type;
    }
    /**
     * Draw method, draws the powerup
     * @param g2d the graphics 2d window to draw with
     */
    public void draw(Graphics2D g2d) {
        //the type of the power up is always 1 greater than the reference to array
        BufferedImage shown = sprites[type - 1];
        //draw the image
        g2d.drawImage(shown,xPos*64,yPos*64,(xPos + 1)*64,(yPos + 1)*64,0,0,16,16,null);
    }
}
