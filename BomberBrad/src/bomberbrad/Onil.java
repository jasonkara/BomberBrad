/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberbrad;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Reegal
 */
public class Onil extends Enemy {
    
    public Onil(int xPos, int yPos, int health, int direction, double speed, ArrayList<BufferedImage> sprites){
        super( xPos,  yPos,  health,  direction,  speed, sprites);
    }
    public void action(Tile[][] map){
        //Implement the AI
    }
    
    
    
}
