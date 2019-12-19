
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;


public class Player extends Entity{
    
    Player(int xPos, int yPos, int direction){
        super( xPos,  yPos,  1,  direction,  2, null);
    }
    
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
    
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.RED);
        g2d.drawRect(xPos*4,yPos*4,48,48);
    }
    
}
