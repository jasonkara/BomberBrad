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
    protected int rndNum(int low, int high){
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
        int futureX = xPos;
        int futureY = yPos;
        
        switch(direction){
            case 1:
                futureY-=speed;
                break;
            case 2:
                futureX+=speed;
                break;
            case 3:
                futureY+=speed;
                break;
            default:
                futureX-=speed;
        }
        if(map[futureX / 16][futureY / 16].getOnTile() != null){
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
