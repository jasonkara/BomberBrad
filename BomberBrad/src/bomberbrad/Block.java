//Riley DeConkey
//12/13/2019
//Class to represent the breakable blocks
package bomberbrad;

public class Block {
    private int xPos;
    private int yPos;
    private String powerType;
    private boolean breakable;
    /**
     * Constructor
     * @param xPos x position of the breakable block
     * @param yPos y position of the breakable block
     * @param powerType type of the power beneath the breakable block
     */
    public Block(int xPos, int yPos, String powerType, boolean breakable) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.powerType = powerType;
        this.breakable = breakable;
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
    
    
    
}
