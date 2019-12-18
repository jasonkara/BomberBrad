//Riley DeConkey
//12/13/2019
//Class to represent one tile of the game
package bomberbrad;
public class Tile {
    //Class variables
    private int xPos;
    private int yPos;
    private Object onTile;
    /**
     * Constructor
     * @param xPos xPosition of the tile
     * @param yPos yPosition of the tile
     * @param onTile Object that is on the tile
     */
    public Tile(int xPos, int yPos, Object onTile) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.onTile = onTile;
    }
    /**
     * Accessor method for x position
     * @return X position of the tile
     */
    public int getxPos() {
        return xPos;
    }
    /**
     * Mutator method for x position
     * @param xPos new x position of the tile
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }
    /**
     * Accessor method for y position
     * @return the y position of the tile
     */
    public int getyPos() {
        return yPos;
    }
    /**
     * Mutator method for y position
     * @param yPos new y position of the tile
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    /**
     * Accessor method for the object on the tile
     * @return the object on the tile
     */
    public Object getOnTile() {
        return onTile;
    }
    /**
     * Mutator method for the object on the tile
     * @param onTile the new object to be placed on the tile
     */
    public void setOnTile(Object onTile) {
        this.onTile = onTile;
    }
    public String toString() {
        return "x: " + xPos + "y: " + yPos + "Object on tile" + onTile.toString();
    }

    
}
