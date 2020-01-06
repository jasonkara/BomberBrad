//Riley DeConkey
//12/13/2019
//Class to represent the breakable blocks
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;

public class Block extends tileObject{
    private tileObject PU;
    private boolean breakable;
    /**
     * Constructor
     * @param xPos x position of the breakable block
     * @param yPos y position of the breakable block
     * @param PU powerup or exit on the block
     */
    public Block(int xPos, int yPos, tileObject PU, boolean breakable) {
        super(xPos,yPos);
        this.PU = PU;
        this.breakable = breakable;
    }

    public tileObject getPU() {
        return PU;
    }

    public void setPU(tileObject PU) {
        this.PU = PU;
    }
   
    /**
     * Accessor method to tell if the block is breakable
     * @return boolean representing if the block is breakable
     */
    public boolean isBreakable() {
        return breakable;
    }
    /**
     * Mutator method to set if a block is breakable or not
     * @param breakable new breakable state
     */
    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }
    public boolean equals(Block b) {
        if (b.isBreakable() == this.isBreakable()) {
            return true;
        } else {
            return false;
        }
    }
    public void draw(Graphics2D g2d) {
        if (breakable) {
            g2d.setColor(Color.DARK_GRAY);
        }
        else {
            g2d.setColor(Color.LIGHT_GRAY);
        }
        g2d.fillRect(xPos*64,yPos*64,64,64);
    }
    
    public String toString(){
        return "Block\n"
                + "Breakable: " + breakable;
    }
    
    
    
    
}
