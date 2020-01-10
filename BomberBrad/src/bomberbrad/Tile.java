//Riley DeConkey
//12/13/2019
//Class to represent one tile of the game
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Tile {

    //Class variables
    private int xPos;
    private int yPos;
    private tileObject onTile;
    private Explosion ex;

    /**
     * Constructor
     *
     * @param xPos xPosition of the tile
     * @param yPos yPosition of the tile
     * @param onTile Object that is on the tile
     */
    public Tile(int xPos, int yPos, tileObject onTile) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.onTile = onTile;
        ex = null;
    }

    /**
     * Accessor method for x position
     *
     * @return X position of the tile
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Mutator method for x position
     *
     * @param xPos new x position of the tile
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Accessor method for y position
     *
     * @return the y position of the tile
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Mutator method for y position
     *
     * @param yPos new y position of the tile
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Accessor method for the object on the tile
     *
     * @return the object on the tile
     */
    public tileObject getOnTile() {
        return onTile;
    }

    /**
     * Mutator method for the object on the tile
     *
     * @param onTile the new object to be placed on the tile
     */
    public void setOnTile(tileObject onTile) {
        this.onTile = onTile;
    }

    public Explosion getEx() {
        return ex;
    }

    public void setEx(Explosion ex) {
        this.ex = ex;
    }

    public String toString() {
        return "x: " + xPos + "y: " + yPos + "Object on tile" + onTile.toString();
    }

    public void destroy(Tile[][] board) {
        if (onTile instanceof Block) {
            if (((Block)(onTile)).isBreakable()) {
                ((Block)onTile).startBreak();
                
            }
        } else if (onTile instanceof PowerUp) {
            onTile = null;
        } else {
            //DrawingSurface.restartLevel();
        }
    }

    public void draw(Tile[][] board, Graphics2D g2d) {
        if (ex != null) {
            ex.draw(board, g2d);
        }
        if (onTile != null) {
            onTile.draw(g2d);
        } else {
            g2d.setColor(new Color(62, 120, 19));
            g2d.fillRect(xPos * 64, yPos * 64, 64, 64);
        }
        if (ex != null) {
            ex.draw(board, g2d);
        }
    }

    public void update(Tile[][] board,DrawingSurface ds) {
        if (ex != null) {
            ex.setTime(ex.getTime() - 1);
            if (ex.getTime() < 0) {
                ex = null;
            }
        }
        
        
        
        if (onTile instanceof Bomb) {
            ((Bomb)(onTile)).setCounter(((Bomb)(onTile)).getCounter() - 1);
            
            if (((Bomb)(onTile)).getCounter() == 0) {
                ((Bomb)(onTile)).explode(1,board);
                onTile = null;
                ds.setBombs(ds.getBombs() + 1);
            } 
        }
        
        if (onTile instanceof Block) {
            if(((Block)(onTile)).isBreaking()){
                ((Block)(onTile)).setTime(((Block)(onTile)).getTime() - 1);
            }
            
            
            if (((Block)(onTile)).getTime() < 0) {
                onTile = null;
            } 
        }
    }
    
}
