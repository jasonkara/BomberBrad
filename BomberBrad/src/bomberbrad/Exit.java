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
import javax.imageio.ImageIO;

/**
 *
 * @author ridec8459
 */
public class Exit extends tileObject {
    private static BufferedImage sprite;
    
    public void loadImages() {
        try {    
            sprite = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/door.png"));
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    
    public Exit(int xPos,int yPos) {
        super(xPos,yPos);
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprite,xPos*64,yPos*64,(xPos + 1)*64,(yPos + 1)*64,0,0,16,16,null);
    }
}
