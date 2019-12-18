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
    
    public Dahl(int xPos, int yPos, int direction){
       super( xPos,  yPos,  1,  direction, 4, null);
    }
    
    @Override
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