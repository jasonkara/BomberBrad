//Riley DeConkey
//12/19/2019
//Object on tile abstract class
package bomberbrad;

import java.awt.Graphics2D;

/**
 *
 * @author ridec8459
 */
public abstract class tileObject {
    protected int xPos;
    protected int yPos;
    
    public tileObject(int xLoc, int yLoc) {
        xPos = xLoc;
        yPos = yLoc;
    }
    
     /**
     * Accessor method for the x position of the block
     * @return the x position of the block
     */
    public int getxPos() {
        return xPos;
    }
    /**
     * Mutator method for the x position of the block
     * @param xPos the new x position of the block
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }
    /**
     * Accessor method for the y position of the block
     * @return 
     */
    public int getyPos() {
        return yPos;
    }
    /**
     * Mutator method for the y position of the block
     * @param yPos 
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    abstract public void draw(Graphics2D g2d);
}
