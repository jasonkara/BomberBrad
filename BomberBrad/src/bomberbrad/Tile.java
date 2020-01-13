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

    public void update(DrawingSurface ds) {
        Tile[][] board = ds.getBoard();
        ArrayList<Enemy> EL = ds.getEnemiesList();
        if (ex != null) {
            ex.setTime(ex.getTime() - 1);

            if (ds.intersecting(xPos * 16, yPos * 16, ds.getPlayer().getXPos(),ds.getPlayer().getYPos()) && ! ds.getPlayer().isDying()) {
                ds.clip.stop();
                ds.playAudio("die");
                ds.getPlayer().setFrameCounter(0);
                ds.getPlayer().setDying(true);
            }
            for (Enemy e: EL) {
                if (ds.intersecting(xPos * 16, yPos * 16, e.getXPos(),e.getYPos())) {
                    //death animation of enemy
                    e.setDying(true);
                    ds.setScore(ds.getScore() + 200);
                }
                if (e.getDeathFrame() == 8) {
                    e.setDeathFrame(-1);
                    EL.remove(e);
                }
                if (EL.size() == 0) {
                    ds.playSE("enemiesclear");
                    ds.clip.stop();
                    ds.playAudio("exit");
                    ds.clip.loop(ds.clip.LOOP_CONTINUOUSLY);
                }
            }
            if (ex.getTime() == 0) {
                ex = null;
            }
            
            
            if (onTile instanceof Bomb){
                ((Bomb)(onTile)).explode(ds.getLength(),board);
            }
        }
        
        
        
        if (onTile instanceof Bomb) {
            ((Bomb)(onTile)).setCounter(((Bomb)(onTile)).getCounter() - 1);
            
            if (((Bomb)(onTile)).getCounter() == 0) {
                ((Bomb)(onTile)).explode(ds.getLength(),board);
                ds.playSE("explosion");
                onTile = null;
                ds.setBombs(ds.getBombs() + 1);
            } 
        }
        

        if (onTile instanceof Block) {
            if(((Block)(onTile)).isBreaking()){
                ((Block)(onTile)).setTime(((Block)(onTile)).getTime() - 1);
            }
            
            if(((Block)(onTile)).getTime() < 0){
                if (((Block)(onTile)).getPU() != null) {
                    onTile = ((Block)(onTile)).getPU();
                } else {
                    onTile = null;
                } 
            }
            
        }
        
        if(onTile instanceof PowerUp){
            if (ds.intersecting(xPos * 16, yPos * 16, ds.getPlayer().getXPos(),ds.getPlayer().getYPos())) {
                ds.playSE("powerup");
                ds.setScore(ds.getScore() + 200);
                switch(((PowerUp)(onTile)).getType()){
            case 1:
                ds.setBombs(ds.getBombs() + 1);
                //more bombs
                
                break;
            case 2:
                ds.setLength(ds.getLength() + 1);
                //bigger bomb explosion
                break;
            case 3:
                ds.getPlayer().setSpeed(4);
                //speed
                break;
            case 4:
                ds.getPlayer().addWalkable(Block.class);
                //go through walls
                break;
            case 5:
                ds.setDetonator(true);
                //detonator
            case 6:
                ds.getPlayer().addWalkable(Bomb.class);
                //walk through bombs
        }
                onTile = null;
            }
        }

    }
    
}
