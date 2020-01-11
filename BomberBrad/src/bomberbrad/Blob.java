
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Reegal
 */
public class Blob extends Enemy {
    
    private static BufferedImage[] sprites = new BufferedImage[11];
    private int frameCounter;
    
    public Blob(int xPos, int yPos, int direction){
        super( xPos,  yPos,  1,  direction, 2);
        frameCounter = 0;
        walkable.add(Block.class);
    }
    
    
        
    public void draw(Graphics2D g2d){
        BufferedImage shown;
        if (direction <= 2) {
            if (frameCounter < 2) {
                shown = sprites[3];
            } else if (frameCounter < 6 && frameCounter > 3) {
                shown = sprites[5];
            } else {
                shown = sprites[4];
            }
        } else {
            if (frameCounter < 2) {
                shown = sprites[0];
            } else if (frameCounter < 6 && frameCounter > 3) {
                shown = sprites[2];
            } else {
                shown = sprites[1];
            }
        }
        g2d.drawImage(shown,xPos*4,yPos*4,xPos*4+64,yPos*4+64,0,0,16,16,null);
        frameCounter ++;
        if (frameCounter == 8) {
            frameCounter = 0;
        }
    }
    
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
            sprites[10] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/blob/die5.png"));
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    
    
    
}
