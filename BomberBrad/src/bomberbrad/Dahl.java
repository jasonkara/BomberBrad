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
    
    public void changeDirection(){
        int j = rndNum(0,1);
            
        if(direction % 2 == 0){
            direction = (j * 2) + 2;
        }else{
            direction = (j * 2) + 1;
        }
    }
    
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.yellow);
        g2d.fillRect(xPos*4,yPos*4,64,64);
    }
    
    
    
    
}