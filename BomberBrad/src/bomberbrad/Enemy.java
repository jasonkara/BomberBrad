/**
 * DKP Industries (Reegal)
 * Enemy class that is extended from entity and will extend to all the enemy types
 * December 13th
 */

package bomberbrad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



public abstract class Enemy extends Entity {
    
    
    
    /**
     * Random number generator
     * @param low 
     * @param high
     * @return 
     */
    
    public static int rndNum(int low, int high){
        int r;
        r= (int)(Math.random() * (high - low + 1) + low);
        
        return r;
    }
    
    Enemy(int xPos, int yPos, int health, int direction, int speed){
        super( xPos,  yPos,  health,  direction,  speed);
        
    }

    

    //abstract action that will made
    //the decision making for enemies
    public void action(Tile[][] map){
        
        
        switch (direction) {
                case 1:
                    if (isWalkable(map[xPos / 16][(yPos - speed) / 16].getOnTile())//top left of the player + speed
                            && isWalkable(map[(xPos + 15) / 16][(yPos - speed) / 16].getOnTile())) {//top right
                        
                        move();
                        
                    } else {
                        
                        changeDirection();
                    }

                    break;
                case 2:
                    if (isWalkable(map[(xPos + 15 + speed) / 16][(yPos) / 16].getOnTile())
                            && isWalkable(map[(xPos + 15 + speed) / 16][(yPos + 15) / 16].getOnTile())) {
                        
                        move();
                    } else {
                        
                        changeDirection();
                    }

                    break;
                case 3:
                    if (isWalkable(map[xPos / 16][(yPos + 15 + speed) / 16].getOnTile())
                            && isWalkable(map[(xPos + 15) / 16][(yPos + 15 + speed) / 16].getOnTile())) {
                        
                        move();
                    } else {
                        
                        changeDirection();
                    }
                    break;
                default:
                    if (isWalkable(map[(xPos - speed) / 16][(yPos) / 16].getOnTile())
                            && isWalkable(map[(xPos - speed) / 16][(yPos + 15) / 16].getOnTile())) {

                        move();
                    } else {
                        
                        changeDirection();
                    }

            }
        
        
    }

    
    
    public void changeDirection(){
        direction = rndNum(1,4);
    }
    
    public void move(){
        switch(direction){
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
    
    abstract public void draw(Graphics2D g2d);
    
    
}
