/**
 * DKP Industries
 * 2020-01-14
 * Entity Class that extends to both enemies and players
 */
package bomberbrad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Entity {

    protected ArrayList<Class<? extends tileObject>> walkable = new ArrayList<>();
    protected int xPos;
    protected int yPos;
    protected int health;
    protected int direction;
    protected int speed;

    /**
     * Constructor, accepts things all entitys have
     *
     * @param xPos x position of entity
     * @param yPos y position of entity
     * @param health health of entity
     * @param direction direction entity is facing
     * @param speed speed of entity
     */
    public Entity(int xPos, int yPos, int health, int direction, int speed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.health = health;
        this.direction = direction;
        this.speed = speed;
        //all entitys can walk over powerups and exits, so add both to walkable arraylist
        walkable.add(PowerUp.class);
        walkable.add(Exit.class);

    }

    /**
     * Getter for the xPositon
     *
     * @return the x position
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Getter for the yPositon
     *
     * @return the y position
     */
    public int getYPos() {
        return yPos;
    }

    /**
     * Getter for the health
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Getter for the direction
     *
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Getter for the speed
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * setter for the xPos
     *
     * @param xPos the xPos
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * setter for the xPos
     *
     * @param yPos the yPos
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * setter for the health
     *
     * @param health the health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * setter for the direction
     *
     * @param direction the direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * setter for the speed
     *
     * @param speed the speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * movement for a characters
     *
     * @param x
     * @param y
     */
    abstract public void move();

    /**
     * Draw method
     *
     * @param g2d the graphics 2d object to draw with
     */
    abstract public void draw(Graphics2D g2d);

    /**
     * Accessor for walkable arraylist
     *
     * @return arraylist containing the classes the entity can walk over
     */
    public ArrayList<Class<? extends tileObject>> getWalkable() {
        return walkable;
    }

    /**
     * Method to add to the classes the entity can walk over
     *
     * @param c a class that the entity can now walk over
     */
    public void addWalkable(Class<? extends tileObject> c) {
        walkable.add(c);
    }

    /**
     * shift method, moves entity a certain amount in the y direction and x
     * direction
     *
     * @param xShift change in x
     * @param yShift change in y
     */
    protected void shift(int xShift, int yShift) {
        xPos += xShift;
        yPos += yShift;
    }

    /**
     * method to check if a tile is walkable for the entity
     *
     * @param t the object on the tile that its trying to walk on
     * @return if its walkable
     */
    protected boolean isWalkable(tileObject t) {
        boolean walk = false;
        //if no object on tile, all entitys can walk over it
        if (t == null) {
            return true;
        }
        //loop through the walkable arraylist
        for (Class<? extends tileObject> c : walkable) {
            //compare the tile object to the arraylist
            if (t.getClass() == c) {
                /**
                 * The only time an entity will have block in its walkable list
                 * is if it can walk through breakable blocks, so return if the
                 * block is breakable.
                 */
                if (c == Block.class) {
                    walk = ((Block) (t)).isBreakable();
                    //otherwise if the class matches a class in the list, return true
                } else {
                    walk = true;
                }

            }
        }
        return walk;
    }

}
