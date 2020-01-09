
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
public class Ballom extends Enemy {
    
    private static BufferedImage[] sprites = new BufferedImage[11];
    
    public Ballom(int xPos, int yPos, int direction){
        super( xPos,  yPos,  1,  direction, 2);
    }
    
    
        
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[0],xPos*4,yPos*4,xPos*4+64,yPos*4+64,0,0,16,16,null);
    }
    
    public void loadImages() {
        try {    
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
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    
    
    
}
