/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *  
 * @author Reegal
 */
public class Dahl extends Enemy {
    
    public Dahl(int xPos, int yPos, int direction, ArrayList<BufferedImage> sprites){
       super( xPos,  yPos,  1,  direction, 4, sprites);
    }
    
    @Override
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
            int j = rndNum(0,1);
            
            if(direction % 2 == 0){
                direction = (j * 2) + 2;
            }else{
                direction = (j * 2) + 1;
            }
        }else{
            move();
        }
        
    }
    
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.yellow);
        g2d.drawRect(xPos*4,yPos*4,(xPos+12)*4,(yPos+12)*4);
    }
    
    
    
    
}