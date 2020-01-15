/**
 * DKP Studios
 * 2020-01-15
 * An abstract class that extends to blocks powerups and the exits
 */
package bomberbrad;

import java.awt.Graphics2D;

public abstract class tileObject {
    protected int xPos;
    protected int yPos;
    
    /**
     * Constructor, accepts x and y locations
     * @param xLoc x location
     * @param yLoc y location
     */
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
    /**
     * Abstract draw method, all tile objects need a draw method
     * @param g2d 2d graphics object to draw with
     */
    abstract public void draw(Graphics2D g2d);
}
