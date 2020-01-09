/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *  
 * @author Reegal
 */
public class Dahl extends Enemy {
    
    private static ArrayList<BufferedImage> sprites;
    
    public Dahl(int xPos, int yPos, int direction){
       super( xPos,  yPos,  1,  direction, 4);
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
        g2d.drawImage(sprites.get(0),xPos*4,yPos*4,xPos*4+64,yPos*4+64,0,0,16,16,null);
    }
    
    public void loadImages() {
        try {    
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/l1.png")));
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/l2.png")));
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/l3.png")));
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/r1.png")));
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/r2.png")));
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/r3.png")));
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/die1.png")));
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/die2.png")));
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/die3.png")));
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/die4.png")));
            sprites.add(ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/dahl/die5.png")));
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    
    
}