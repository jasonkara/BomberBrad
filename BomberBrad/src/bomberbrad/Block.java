/**
 * DKP Studios
 * 2020-01-14
 * The block class extends tileObject and has the potential to carry a power up
 */
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Block extends tileObject {

    private tileObject PU;
    private boolean breakable;
    private int time = 11;
    private boolean breaking = false;
    private static BufferedImage[] sprites = new BufferedImage[8];

    /**
     * A method to load all of the sprites
     */
    public void loadImages() {
        try {
            sprites[0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/solidblock.png"));//adds each sprite to the array
            sprites[1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/breakblock/block.png"));
            sprites[2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/breakblock/exp1.png"));
            sprites[3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/breakblock/exp2.png"));
            sprites[4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/breakblock/exp3.png"));
            sprites[5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/breakblock/exp4.png"));
            sprites[6] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/breakblock/exp5.png"));
            sprites[7] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/breakblock/exp6.png"));

        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }

    /**
     * Getter for time
     *
     * @return time
     */
    public int getTime() {
        return time;
    }

    /**
     * Setter for time
     *
     * @param time the new time
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Constructor
     *
     * @param xPos x position of the breakable block
     * @param yPos y position of the breakable block
     * @param PU powerup or exit on the block
     */
    public Block(int xPos, int yPos, tileObject PU, boolean breakable) {
        super(xPos, yPos);
        this.PU = PU;
        this.breakable = breakable;
    }

    /**
     * checks if the block is breaking
     *
     * @return breaking
     */
    public boolean isBreaking() {
        return breaking;
    }

    /**
     * Getter for the power up
     *
     * @return the powerup
     */
    public tileObject getPU() {
        return PU;
    }

    /**
     * Setter for the power up
     *
     * @param PU the new power up
     */
    public void setPU(tileObject PU) {
        this.PU = PU;
    }

    /**
     * Accessor method to tell if the block is breakable
     *
     * @return boolean representing if the block is breakable
     */
    public boolean isBreakable() {
        return breakable;
    }

    /**
     * Mutator method to set if a block is breakable or not
     *
     * @param breakable new breakable state
     */
    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    /**
     * Equals method to check if 2 blocks are the same
     * @param b the other block
     * @return boolean whether the blocks are the same
     */
    public boolean equals(Block b) {
        if (b.isBreakable() == this.isBreakable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Draw method for the blokc
     * @param g2d the g2d being used to draw them
     */
    public void draw(Graphics2D g2d) {
        //draw the tile behind it
        g2d.setColor(new Color(62, 120, 19));
        g2d.fillRect(xPos * 64, yPos * 64, 64, 64);
        BufferedImage shown;

        //if the tile is breakabkle
        if (breakable) {
            if (breaking) {//and the tile is breaking
                shown = sprites[time / 2 + 2];//show the broken tiles

            } else {//if it's not breaking
                shown = sprites[1];//show the breakable blokc
            }

        } else {//otherwise
            shown = sprites[0];//show the unbreakable block
        }

        g2d.drawImage(shown, xPos * 64, yPos * 64, (xPos + 1) * 64, (yPos + 1) * 64, 0, 0, 16, 16, null);

    }

    /**
     * A method to begin breaking the block
     */
    public void startBreak() {
        breaking = true;
    }

    /**
     * To string method for the block
     * @return 
     */
    public String toString() {
        return "Block\n"
                + "Breakable: " + breakable;
    }

}
