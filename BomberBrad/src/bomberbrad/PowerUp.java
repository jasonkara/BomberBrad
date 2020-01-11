//Riley DeConkey
//01//06//2020
//Class to represent one of 6 types of powerups
/*

1 extra bomb 
2 bigger bomb explosion
3 speed
4 go through walls
5 detonator
6 walk through bombs
*/
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ridec8459
 */
public class PowerUp extends tileObject {
    private int type;
    private static BufferedImage[] sprites = new BufferedImage[6];
    
    public void loadImages() {
        try {    
            sprites[0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/1.png"));
            sprites[1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/2.png"));
            sprites[2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/3.png"));
            sprites[3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/4.png"));
            sprites[4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/5.png"));
            sprites[5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/power/6.png"));
            
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    
    public PowerUp(int xPos, int yPos, int type) {
        super(xPos,yPos);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public void draw(Graphics2D g2d) {
        BufferedImage shown = sprites[type - 1];
        
        g2d.drawImage(shown,xPos*64,yPos*64,(xPos + 1)*64,(yPos + 1)*64,0,0,16,16,null);
    }
}
