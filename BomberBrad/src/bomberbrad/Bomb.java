//Riley DeConkey
//12/13/2019
//Class to represent the bombs that the player places
package bomberbrad;

public class Bomb {
    private int xPos;
    private int yPos;
    private int counter;
    /**
     * Constructor
     * @param xPos x position of the bomb
     * @param yPos y position of the bomb
     */
    public Bomb(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        counter = 0;
    }
    /**
     * Accessor method for x position
     * @return the x position of the bomb
     */
    public int getxPos() {
        return xPos;
    }
    /**
     * Mutator method for x position
     * @param xPos the new x position of the bomb
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }
    /**
     * Accessor method for y position
     * @return y position of the bomb
     */
    public int getyPos() {
        return yPos;
    }
    /**
     * Mutator method for y position
     * @param yPos the new y position of the bomb
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    /**
     * Accessor method for the counter keeping track of when the bomb will explode
     * @return the counter keeping track of when the bomb should explode
     */
    public int getCounter() {
        return counter;
    }
    /**
     * Accessor method for the counter keeping track of when the bomb will explode
     * @param counter the new value for the counter keeping track of when the bomb should explode
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }
    public void explode() {
        
    }
    
}
