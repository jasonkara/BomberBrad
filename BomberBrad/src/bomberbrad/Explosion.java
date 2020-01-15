/**
 * DKP Studios
 * 2020-01-14
 * An independent class that lies on a tile but not as a tile object as an explosion and a tile object can both be on the same tile
 */
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ridec8459
 */
public class Explosion {

    private int x, y;
    private int time;
    private int direction;
    private static BufferedImage[][] sprites = new BufferedImage[4][7];

    /**
     * Constructor for the explosion class
     *
     * @param x the x position
     * @param y the y position
     * @param direction the direction the explosion is facing
     */
    public Explosion(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        time = 11;
        this.direction = direction;

    }

    /**
     * Load images method that is run at the beginning of the program to open
     * connections to all of the images
     */
    public void loadImages() {
        //try to load all of the images
        try {
            for (int i = 0; i < 4; i++) {//loops 4 times for all levels of strength the explosion can be
                sprites[i][0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/" + (i + 1) + "/mid.png"));
                sprites[i][1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/" + (i + 1) + "/top.png"));
                sprites[i][2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/" + (i + 1) + "/right.png"));
                sprites[i][3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/" + (i + 1) + "/bottom.png"));
                sprites[i][4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/" + (i + 1) + "/left.png"));
                sprites[i][5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/" + (i + 1) + "/ver.png"));
                sprites[i][6] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/" + (i + 1) + "/hor.png"));
            }

            // print the error if an io exception is caught
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }

    /**
     * Accessor method for the xPosition of the explosion
     *
     * @return the x position of the explosion
     */
    public int getX() {
        return x;
    }

    /**
     * Mutator method for the xPosition of the explosion
     *
     * @param x the new x position of the explosion
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Accessor method for the yPosition of the explosion
     *
     * @return the y position of the explosion
     */
    public int getY() {
        return y;
    }

    /**
     * Mutator method for the yPosition of the explosion
     *
     * @param y the new y position of the explosion
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Accessor method for the time remaining until the explosion fades
     * @return the time remaining
     */
    public int getTime() {
        return time;
    }

    /**
     * Mutator method for the time remaining until the explosion fades
     * @param time the new amount of time remaining
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Accessor method for the direction of the explosion
     * @return the direction of the explosion
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Mutator method for the direction of the explosion
     * @param direction the new direction of the explosion
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Draw method of the explosion
     * @param board a reference to the board of tiles
     * @param g2d the g2d being used to draw
     */
    public void draw(Tile[][] board, Graphics2D g2d) {
        BufferedImage shown = null;//initialize the shown variable

        //[time / 3] dictates which strength level is shown for the ecplosion
        
        
        if (direction == 0) {//if the direction is 0 (center) 
            shown = sprites[time / 3][0];//display the center explosion
        } else if (direction == 1) {//if the explosion is travelling upwards
            if (board[x][y - 1].getEx() != null) {//if there is an explosion directly upwards of it

                shown = sprites[time / 3][5];//draw a connecter vertical explosion
            } else {//otherwise
                shown = sprites[time / 3][1];//draw the peak explosion
            }
            //repeat for all other directions
        } else if (direction == 2) {//right
            if (board[x + 1][y].getEx() != null) {
                shown = sprites[time / 3][6];
            } else {
                shown = sprites[time / 3][2];
            }
        } else if (direction == 3) {//down
            if (board[x][y + 1].getEx() != null) {
                shown = sprites[time / 3][5];
            } else {
                shown = sprites[time / 3][3];
            }
        } else if (direction == 4) {//left
            if (board[x - 1][y].getEx() != null) {
                shown = sprites[time / 3][6];
            } else {
                shown = sprites[time / 3][4];
            }
        }
        g2d.drawImage(shown, x * 64, y * 64, (x + 1) * 64, (y + 1) * 64, 0, 0, 16, 16, null);
    }

}
