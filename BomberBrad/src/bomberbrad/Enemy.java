/**
 * DKP Industries (Reegal)
 * Enemy class that is extended from entity and will extend to all the enemy types
 * December 13th
 */

package bomberbrad;

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
        r= (int)(Math.random() * (high - low + 1) + 1);
        
        return r;
    }
    
    Enemy(int xPos, int yPos, int health, int direction, int speed, ArrayList<BufferedImage> sprites){
        super( xPos,  yPos,  health,  direction,  speed, sprites);
    }

    //abstract action that will made
    //the decision making for enemies
    public void action(Tile[][] map){
        int xMod = 0;
        int yMod = 0;
        
        switch(direction){
            case 1:
                yMod-=speed;
                break;
            case 2:
                xMod+=speed;
                break;
            case 3:
                yMod+=speed;
                break;
            default:
                xMod-=speed;
        }
        if(map[(xPos + xMod) / 16][(yPos + yMod) / 16].getOnTile() != null//top left
                && map[(xPos + 12+ xMod) / 16][(yPos + yMod) / 16].getOnTile() != null//top right
                && map[(xPos + xMod) / 16][(yPos + 12 + yMod) / 16].getOnTile() != null//bottom left
                && map[(xPos + 12 + xMod) / 16][(yPos + 12 + yMod) / 16].getOnTile() != null){//bottom right
            direction = rndNum(1,4);
        }else{
            move();
        }
        
    }
    
    public void move(){
        switch(direction){
            case 1:
                yPos += speed;
                break;
            case 2:
                xPos += speed;
                break;
            case 3:
                yPos -= speed;
                break;
            default:
                xPos -= speed;
        }
    }
    
    
}
