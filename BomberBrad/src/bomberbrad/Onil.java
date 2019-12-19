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
public class Onil extends Enemy {
    
    public Onil(int xPos, int yPos, int direction){
        super( xPos,  yPos,  1,  direction, 4, null);
    }
    
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.BLUE);
        g2d.fillRect(xPos*4,yPos*4,48,48);
    }
    
    
}
