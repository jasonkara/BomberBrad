/**
 * DKP Studios
 * 2020-01-15
 * The exit class extends tileObject
 */
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Exit extends tileObject {
    private static BufferedImage sprite;
    
    /**
     * Load images method that is run at the beginning of the program to open
     * connections to all of the images
     */
    public void loadImages() {
        try {    
            sprite = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/door.png"));
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    
    /**
     * Constructor
     * @param xPos the xPos
     * @param yPos the yPos
     */
    public Exit(int xPos,int yPos) {
        super(xPos,yPos);
    }
    
    /**
     * The draw method
     * @param g2d the g2d being used to draw
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprite,xPos*64,yPos*64,(xPos + 1)*64,(yPos + 1)*64,0,0,16,16,null);
    }
}
