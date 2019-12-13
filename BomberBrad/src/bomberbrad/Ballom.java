
package bomberbrad;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Reegal
 */
public class Ballom extends Enemy {
    
    public Ballom(int xPos, int yPos, int health, int direction, double speed, ArrayList<BufferedImage> sprites){
        super( xPos,  yPos,  health,  direction,  speed, sprites);
    }
    public void action(Tile[][] map){
        //Implement the AI
    }
    
    
    
}
