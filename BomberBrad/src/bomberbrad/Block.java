//Riley DeConkey
//12/13/2019
//Class to represent the breakable blocks
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;

public class Block extends tileObject{
    private String powerType;
    private boolean breakable;
    /**
     * Constructor
     * @param xPos x position of the breakable block
     * @param yPos y position of the breakable block
     * @param powerType type of the power beneath the breakable block
     */
    public Block(int xPos, int yPos, String powerType, boolean breakable) {
        super(xPos,yPos);
        this.powerType = powerType;
        this.breakable = breakable;
    }
   
    /**
     * Accessor method for the power up type
     * @return the type of power up beneath the block
     */
    public String getPowerType() {
        return powerType;
    }
    /**
     * Mutator method for the power up beneath the block
     * @param powerType the new power up to be placed beneath th block
     */
    public void setPowerType(String powerType) {
        this.powerType = powerType;
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
    
    public void startBreak(){
        
    }
    
    public String toString(){
        return "Block\n"
                + "Breakable: " + breakable;
    }
    
    
    
    
}
