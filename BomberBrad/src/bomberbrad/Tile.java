/**
 * DKP Studios
 * 2020-01-14
 * The Tile Class that holds a tileObject and has an X and Y Position
 */
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

    /**
     * Getter for the explosion
     *
     * @return the explosion
     */
    public Explosion getEx() {
        return ex;
    }

    /**
     * Setter for the explosion
     *
     * @param ex the explosion to set
     */
    public void setEx(Explosion ex) {
        this.ex = ex;
    }

    /**
     * To string for the tile
     *
     * @return the tile as a string
     */
    public String toString() {
        return "x: " + xPos + "y: " + yPos + "Object on tile" + onTile.toString();
    }

    /**
     * A method to destroy the tile object on the tile
     *
     * @param board psasing the board
     */
    public void destroy(Tile[][] board) {
        if (onTile instanceof Block) {//if on the tile is a block
            if (((Block) (onTile)).isBreakable()) {// and the block is breakable
                ((Block) onTile).startBreak();//being the break animation
            }
        } else if (onTile instanceof PowerUp) {//if ton the tile is a power up
            onTile = null;//set the on tile to null
        }
    }

    /**
     * Draw method for the tile
     *
     * @param board passing the board
     * @param g2d passing the g2d
     */
    public void draw(Tile[][] board, Graphics2D g2d) {
        if (onTile != null) {//if there is something on the tile
            onTile.draw(g2d);//call the draw method for whatever is there

        } else {//otherwise
            g2d.setColor(new Color(62, 120, 19));//set the colour to green

            g2d.fillRect(xPos * 64, yPos * 64, 64, 64);//and draw a rectangle
        }
        if (ex != null) {//if there is an explosion
            ex.draw(board, g2d);//draw the explosion
        }
    }

    /**
     * Updates the tile, tile object and explosions on the tile
     *
     * @param ds a reference to the drawing surface
     */
    public void update(DrawingSurface ds) {
        Tile[][] board = ds.getBoard();//getting the board and enemy lists
        ArrayList<Enemy> EL = ds.getEnemiesList();
        if (ex != null) {//if there is an explosion
            ex.setTime(ex.getTime() - 1);//lower the explosion time 

            //if the player is intersecting the explosion, is not dying and does not have the fire pass ability
            if (ds.intersecting(xPos * 16, yPos * 16, ds.getPlayer().getXPos(), ds.getPlayer().getYPos()) && !ds.getPlayer().isDying() && !ds.hasFirePass()) {
                ds.clip.stop();//stop the current clip
                ds.playAudio("die");// play the death audio
                ds.getPlayer().setFrameCounter(0);//begin the death animation
                ds.getPlayer().setDying(true);//set the players state to fying
            }
            for (Enemy e : EL) {//for each enemy
                //if the enemy is intersecting the explosion and the player is not dead
                if (ds.intersecting(xPos * 16, yPos * 16, e.getXPos(), e.getYPos()) && !ds.getPlayer().isDying()) {
                    EL.remove(e);//remove the enemy from the list
                    ds.setScore(ds.getScore() + 200);//add the score
                    if (EL.size() == 0) {//check is there are no enemies left
                        ds.playSE("enemiesclear");//play the enemy clear audio
                        ds.clip.stop();//and play the exit sound theme
                        ds.playAudio("exit");
                        ds.clip.loop(ds.clip.LOOP_CONTINUOUSLY);
                        ds.setScore(ds.getScore() + 200);//increase the score
                    }
                }

            }
            if (ex.getTime() == 0) {//if the time has reached 0
                ex = null;//remove the explosion
            }

            if (onTile instanceof Bomb) {//if there is a bomb overlapping an explosion
                ((Bomb) (onTile)).explode(ds.getLength(), board);//explode the bomb
            }
        }

        if (onTile instanceof Bomb) {//if there is a bomb on the tile
            ((Bomb) (onTile)).setCounter(((Bomb) (onTile)).getCounter() - 1);//lower the counter by 1

            if (((Bomb) (onTile)).getCounter() == 0) {//if the counter is at 0
                ((Bomb) (onTile)).explode(ds.getLength(), board);//explode it
                ds.playSE("explosion");//and play the explosion
                onTile = null;//set the on tile to null
                ds.setBombs(ds.getBombs() + 1);//replenish a bomb 
            }
        }

        if (onTile instanceof Block) {//if there is a block on the tile
            if (((Block) (onTile)).isBreaking()) {//if the block is breaking
                ((Block) (onTile)).setTime(((Block) (onTile)).getTime() - 1);//decrease the timer
            }

            if (((Block) (onTile)).getTime() < 0) {//if the conunter is lower than 0
                if (((Block) (onTile)).getPU() != null) {//if there is a power up within the block
                    onTile = ((Block) (onTile)).getPU();//place the power up on the tile
                } else {//otherwise
                    onTile = null;//set it to null
                }
            }

        }

        if (onTile instanceof PowerUp) {//if there is a power up on the tile
            if (ds.intersecting(xPos * 16, yPos * 16, ds.getPlayer().getXPos(), ds.getPlayer().getYPos())) {//and the player is intersecting it
                ds.playSE("powerup");//play the audio
                ds.setScore(ds.getScore() + 200);//add the powerup score
                ds.getPowerUpList().add(((PowerUp) (onTile)).getType());
                switch (((PowerUp) (onTile)).getType()) {//switch based on the type
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
                        ds.setWallPass(true);
                        break;
                    case 5:
                        ds.setFirePass(true);
                    //firePass
                    case 6:
                        ds.getPlayer().addWalkable(Bomb.class);
                        //walk through bombs
                        ds.setBombPass(true);

                }

            }

        }
    }
}
