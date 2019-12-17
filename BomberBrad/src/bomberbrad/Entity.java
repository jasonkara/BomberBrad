/**
 * DKP Industries (Reegal)
 * Entity Class that extends to both enemies and players
 * December 13th
 */


package bomberbrad;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class Entity {
    
    protected int xPos;
    protected int yPos;
    protected int health;
    protected int direction;
    protected int speed;
    ArrayList<BufferedImage> sprites;

    
    public Entity(int xPos, int yPos, int health, int direction, int speed, ArrayList<BufferedImage> sprites){
        this.xPos = xPos;
        this.yPos = yPos;
        this.health = health;
        this.direction = direction;
        this.speed = speed;
        this.sprites = sprites;
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
     * getter for the sprite
     * @param i the index of the sprite
     * @return the sprite
     */
    public BufferedImage getSprite(int i){
        return sprites.get(i);
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
     * setter for the sprites
     * @param sprites the arraylist of sprites
     */
    public void setSprites(ArrayList<BufferedImage> sprites) {
        this.sprites = sprites;
    }
    
    /**
     * movement for a characters
     * @param x
     * @param y 
     */
    abstract public void move();
    
    

    
    
}
