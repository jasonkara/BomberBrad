//Riley DeConkey
//12/13/2019
//Class to represent a bombs explosion
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
public class Explosion {
    private int x,y;
    private int time;
    private int direction;
    private static BufferedImage[][] sprites = new BufferedImage[4][7];
    
    
    
    public Explosion(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        time = 11;
        this.direction = direction;
        
        
    }
    
    public void loadImages() {
        try {    
            for (int i = 0; i < 4; i++) {
                sprites[i][0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/"+ (i + 1) +"/mid.png"));
                sprites[i][1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/"+ (i + 1) +"/top.png"));
                sprites[i][2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/"+ (i + 1) +"/right.png"));
                sprites[i][3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/"+ (i + 1) +"/bottom.png"));
                sprites[i][4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/"+ (i + 1) +"/left.png"));
                sprites[i][5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/"+ (i + 1) +"/ver.png"));
                sprites[i][6] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/explosion/"+ (i + 1) +"/hor.png"));
            }
            
            

        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void draw(Tile[][] board, Graphics2D g2d) {
        BufferedImage shown = null;
        
        if (direction == 0) {
            shown = sprites[time /3 ][0];
        } else if (direction == 1) {
            if (board[x][y - 1].getEx() != null) {
                
                shown = sprites[time / 3][5];
            } else {
                shown = sprites[time / 3][1];
            }
        } else if (direction == 2) {
            if (board[x + 1][y].getEx() != null) {
                shown = sprites[time / 3][6];
            } else {
                shown = sprites[time / 3][2];
            }
        } else if (direction == 3) {
            if (board[x][y + 1].getEx() != null) {
                shown = sprites[time / 3][5];
            } else {
                shown = sprites[time / 3][3];
            }
        } else if (direction == 4) {
            if (board[x - 1][y].getEx() != null) {
                shown = sprites[time / 3][6];
            } else {
                shown = sprites[time / 3][4];
            }
        }
        g2d.drawImage(shown,x*64,y*64,(x + 1)*64,(y + 1)*64,0,0,16,16,null);
    }
    
}
