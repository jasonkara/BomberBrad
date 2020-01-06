//Riley DeConkey
//01//06//2020
//Class to represent one of 5 types of powerups
package bomberbrad;

import java.awt.Graphics2D;

/**
 *
 * @author ridec8459
 */
public class PowerUp extends tileObject {
    private int type;
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
        switch(type){
            case 1:
                //extra bomb
                break;
            case 2:
                //bigger bomb explosion
                break;
            case 3:
                //speed
                break;
            case 4:
                //go through walls
                break;
            case 5:
                //detonator
            case 6:
                //walk through bombs
        }
    }
}
