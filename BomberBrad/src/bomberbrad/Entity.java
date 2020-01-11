/**
 * DKP Industries (Reegal)
 * Entity Class that extends to both enemies and players
 * December 13th
 */


package bomberbrad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class Entity {
    
    protected ArrayList<Class <? extends tileObject>> walkable = new ArrayList<>();
    protected int xPos;
    protected int yPos;
    protected int health;
    protected int direction;
    protected int speed;

    
    public Entity(int xPos, int yPos, int health, int direction, int speed){
        this.xPos = xPos;
        this.yPos = yPos;
        this.health = health;
        this.direction = direction;
        this.speed = speed;
        walkable.add(PowerUp.class);
        walkable.add(Exit.class);
        
    }
    
    /**
     * Getter for the xPositon
     * @return the x position
     */
    public int getXPos(){
        return xPos;
    }   
    
    /**
     * Getter for the yPositon
     * @return the y position
     */
    public int getYPos(){
        return yPos;
    }   
    
    /**
     * Getter for the health
     * @return the health
     */
    public int getHealth(){
        return health;
    } 
    
    /**
     * Getter for the direction
     * @return the direction
     */
    public int getDirection(){
        return direction;
    } 
    
    /**
     * Getter for the speed
     * @return the speed
     */
    public int getSpeed(){
        return speed;
    }   
    
    /**
     * setter for the xPos
     * @param xPos the xPos
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * setter for the xPos
     * @param yPos the yPos
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * setter for the health
     * @param health the health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * setter for the direction
     * @param direction the direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * setter for the speed
     * @param speed the speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * movement for a characters
     * @param x
     * @param y 
     */
    abstract public void move();
    
    abstract public void draw(Graphics2D g2d);

    public ArrayList<Class<? extends tileObject>> getWalkable() {
        return walkable;
    }

    public void addWalkable(Class<? extends tileObject> c) {
        walkable.add(c);
    }

    protected boolean isWalkable(tileObject t){
        boolean walk = false;
        if(t == null){
            return true;
        }
        for (Class<? extends tileObject> c : walkable){
            
            if(t.getClass() == c){
                if(c == Block.class){
                    walk = ((Block)(t)).isBreakable();
                }else{
                    walk = true;
                }
                
            }
        }
        return walk;
    }
    
}
