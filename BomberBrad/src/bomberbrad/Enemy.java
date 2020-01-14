/**
 * DKP Industries 
 * 2020-01-14
 * Enemy class that is extended from entity and will extend to all the enemy types
 */
package bomberbrad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Enemy extends Entity {

    /**
     * Random number generator
     *
     * @param low lowest number can be
     * @param high highest number can be
     * @return
     */
    public static int rndNum(int low, int high) {
        int r;
        r = (int) (Math.random() * (high - low + 1) + low);

        return r;
    }

    /**
     * Constructor, same as entity constructor
     *
     * @param xPos x position
     * @param yPos y position
     * @param health health of enemy
     * @param direction direction enemy is facing
     * @param speed speed of enemy
     */
    Enemy(int xPos, int yPos, int health, int direction, int speed) {
        super(xPos, yPos, health, direction, speed);

    }

    /**
     * Action method for enemies
     *
     * @param map the game map
     */
    public void action(Tile[][] map) {
        switch (direction) {
            //if facing up
            case 1:
                //check using the top corners of the enemy, if moving either corner by speed would hit a non walkable tile, dont move.
                if (isWalkable(map[xPos / 16][(yPos - speed) / 16].getOnTile())//top left of the player + speed
                        && isWalkable(map[(xPos + 15) / 16][(yPos - speed) / 16].getOnTile())) {//top right
                    //call move method if parameters are met
                    move();
                    //if something is in the way
                } else {
                    //change direction
                    changeDirection();
                }

                break;
            //if facing right
            case 2:
                //check using the right corners of the enemy, if moving either corner by speed would hit a non walkable tile, dont move.
                if (isWalkable(map[(xPos + 15 + speed) / 16][(yPos) / 16].getOnTile())
                        && isWalkable(map[(xPos + 15 + speed) / 16][(yPos + 15) / 16].getOnTile())) {
                    //call move method
                    move();
                } else {
                    //otherwise change direction
                    changeDirection();
                }

                break;
            //if facing down
            case 3:
                ////check using the bottom corners of the enemy, if moving either corner by speed would hit a non walkable tile, dont move.
                if (isWalkable(map[xPos / 16][(yPos + 15 + speed) / 16].getOnTile())
                        && isWalkable(map[(xPos + 15) / 16][(yPos + 15 + speed) / 16].getOnTile())) {
                    //call move method
                    move();
                } else {
                    //otherwise change direction
                    changeDirection();
                }
                break;
            //otherwise must be facing left
            default:
                //check using the left corners of the enemy, if moving either corner by speed would hit a non walkable tile, dont move.
                if (isWalkable(map[(xPos - speed) / 16][(yPos) / 16].getOnTile())
                        && isWalkable(map[(xPos - speed) / 16][(yPos + 15) / 16].getOnTile())) {
                    //call move method
                    move();
                } else {
                    //otherwise change direction
                    changeDirection();
                }

        }

    }

    /**
     * Change direction method, sets direction to a random number 1 - 4
     */
    public void changeDirection() {
        direction = rndNum(1, 4);
    }

    /**
     * Move method, adds speed to position in whatever direction the enemy is
     * facing
     */
    public void move() {
        switch (direction) {
            case 1:
                yPos -= speed;
                break;
            case 2:
                xPos += speed;
                break;
            case 3:
                yPos += speed;
                break;
            default:
                xPos -= speed;
        }
    }

    /**
     * Abstract draw method, all enemies must have a draw method
     *
     * @param g2d graphics 2d method to draw with
     */
    abstract public void draw(Graphics2D g2d);

}
