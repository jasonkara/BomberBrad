//Riley DeConkey
//12/13/2019
//Class to represent the bombs that the player places
package bomberbrad;

import static com.sun.org.apache.bcel.internal.Repository.instanceOf;
import java.util.ArrayList;

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
    public ArrayList explode(int l, Tile[][] map, int direction) {
        ArrayList<Tile> toKill = new ArrayList();
        
        if(direction != 1){
            for (int i = 0; i < l; i++) {//downwards
            if (yPos >= map[0].length){
                break; 
            }else if(map[xPos][yPos + l].getOnTile() != null){
                if(map[xPos][yPos + l].getOnTile() instanceof Block){
                    if(((Block)map[xPos][yPos + l].getOnTile()).isBreakable()){
                        map[xPos][yPos + l].destroy(3);
                    }
                    break;
                }else if(map[xPos][yPos + l].getOnTile() instanceof Bomb){
                    toKill.addAll(map[xPos][yPos + l].destroy(3));
                }
                toKill.add(map[xPos][yPos + l]);
            }
            
        }
        }
        
        if(direction != 2){
            for (int i = 0; i < l; i++) {//to the left
            if (xPos - l < 0){
                break; 
            }else if(map[xPos - l][yPos].getOnTile() != null){
                if(map[xPos - l][yPos].getOnTile() instanceof Block){
                    if(((Block)map[xPos - l][yPos].getOnTile()).isBreakable()){
                        map[xPos - l][yPos].destroy(4);
                    }
                    break;
                }else if(map[xPos - l][yPos].getOnTile() instanceof Bomb){
                    toKill.addAll(map[xPos - l][yPos].destroy(4));
                }
                toKill.add(map[xPos - l][yPos]);
            }
            
            }
        }
        
        if(direction != 3){
            for (int i = 0; i < l; i++) {//upwards
            if (yPos < 0){
                break; 
            }else if(map[xPos][yPos - l].getOnTile() != null){
                if(map[xPos][yPos - l].getOnTile() instanceof Block){
                    if(((Block)map[xPos][yPos - l].getOnTile()).isBreakable()){
                        map[xPos][yPos - l].destroy(1);
                    }
                    break;
                }else if(map[xPos][yPos - l].getOnTile() instanceof Bomb){
                    toKill.addAll(map[xPos][yPos - l].destroy(1));
                }
                toKill.add(map[xPos][yPos - l]);
            }
            
        }
        }
        
        if(direction != 4){
            for (int i = 0; i < l; i++) {//to the right
            if (xPos + l >= map.length){
                break; 
            }else if(map[xPos + l][yPos].getOnTile() != null){
                if(map[xPos + l][yPos].getOnTile() instanceof Block){
                    if(((Block)map[xPos + l][yPos].getOnTile()).isBreakable()){
                        map[xPos + l][yPos].destroy(2);
                    }
                    break;
                }else if(map[xPos + l][yPos].getOnTile() instanceof Bomb){
                    toKill.addAll(map[xPos + l][yPos].destroy(2));
                }
                toKill.add(map[xPos + l][yPos]);
            }
            
            }
        }
        
        return toKill;
    }
    
}
