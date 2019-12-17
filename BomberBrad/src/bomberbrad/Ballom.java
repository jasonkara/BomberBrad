
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Reegal
 */
public class Ballom extends Enemy {
    
    public Ballom(int xPos, int yPos, int direction, ArrayList<BufferedImage> sprites){
        super( xPos,  yPos,  1,  direction, 2, sprites);
    }
    
    
        
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.ORANGE);
        g2d.drawRect(xPos*4,yPos*4,(xPos+12)*4,(yPos+12)*4);
    }
    
    
    
}
