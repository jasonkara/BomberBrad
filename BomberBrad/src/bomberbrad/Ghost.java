/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberbrad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Reegal
 */
public class Ghost extends Enemy {
    
    private static BufferedImage[] sprites = new BufferedImage[11];
    private int frameCounter;
    
    public Ghost(int xPos, int yPos, int direction){
        super( xPos,  yPos,  1,  direction, 4);
        frameCounter = 0;
        walkable.add(Block.class);
    }
    
    public void draw(Graphics2D g2d){
        BufferedImage shown;
        if (direction <= 2) {
            if (frameCounter < 2) {
                shown = sprites[3];
            } else if (frameCounter < 6 && frameCounter > 3) {
                shown = sprites[5];
            } else {
                shown = sprites[4];
            }
        } else {
            if (frameCounter < 2) {
                shown = sprites[0];
            } else if (frameCounter < 6 && frameCounter > 3) {
                shown = sprites[2];
            } else {
                shown = sprites[1];
            }
        }
        g2d.drawImage(shown,xPos*4,yPos*4,xPos*4+64,yPos*4+64,0,0,16,16,null);
        frameCounter ++;
        if (frameCounter == 8) {
            frameCounter = 0;
        }
    }
    
    public void loadImages() {
        try {    
            sprites[0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/l1.png"));
            sprites[1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/l2.png"));
            sprites[2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/l3.png"));
            sprites[3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/r1.png"));
            sprites[4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/r2.png"));
            sprites[5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/r3.png"));
            sprites[6] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/die1.png"));
            sprites[7] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/die2.png"));
            sprites[8] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/die3.png"));
            sprites[9] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/die4.png"));
            sprites[10] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/enemy/ghost/die5.png"));
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    
    
}
