//Riley DeConkey
//12/13/2019
//Class to represent a bombs explosion
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author ridec8459
 */
public class Explosion {
    private int x,y;
    private int time;
    private int direction;

    public Explosion(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        time = 10;
        this.direction = direction;
        
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
        g2d.setColor(Color.orange);
        g2d.fillRect(x * 64,y*64,64,64);
        if (direction == 0) {
            //Image of center
        } else if (direction == 1) {
            if (board[x][y - 1].getEx() != null) {
                
                //image of vertical connector
            } else {
                //image of upward tipped explosion
            }
        } else if (direction == 2) {
            if (board[x + 1][y].getEx() != null) {
                //image of horizontal connector
            } else {
                //image of rightward tipped explosion
            }
        } else if (direction == 3) {
            if (board[x][y + 1].getEx() != null) {
                //image of vertical connector
            } else {
                //image of downward tipped explosion
            }
        } else if (direction == 4) {
            if (board[x - 1][y].getEx() != null) {
                //image of horiontal connector
            } else {
                //image of leftward tipped explosion
            }
        }
    }
    
}
