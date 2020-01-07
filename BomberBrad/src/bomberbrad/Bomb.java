//Riley DeConkey
//12/13/2019
//Class to represent the bombs that the player places
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Bomb extends tileObject{
    private int counter;
    /**
     * Constructor
     * @param xPos x position of the bomb
     * @param yPos y position of the bomb
     */
    public Bomb(int xPos, int yPos) {
        super(xPos,yPos);
        counter = 0;
    }
    /**
     * Accessor method for the counter keeping track of when the bomb will explode
     * @return the counter keeping track of when the bomb should explode
     */
    public int getCounter() {
        return counter;
    }
    /**
     * Mutator method for the counter keeping track of when the bomb will explode
     * @param counter the new value for the counter keeping track of when the bomb should explode
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }
    public void explode(int l, Tile[][] map) {
        for (int i = 0; i < l; i++) {//upwards
            if(map[xPos][yPos - l].getOnTile() != null){
                if(map[xPos][yPos - l].getOnTile() instanceof Bomb){
                    map[xPos][yPos - l].setEx(new Explosion(xPos, yPos - l, 1));
                }else{
                    map[xPos][yPos - l].destroy(map);
                }
                break;
            }else{
                map[xPos][yPos - l].setEx(new Explosion(xPos, yPos - l, 1));
            }
        }
        
        for (int i = 0; i < l; i++) {//rightly
            if(map[xPos + l][yPos].getOnTile() != null){
                if(map[xPos + l][yPos].getOnTile() instanceof Bomb){
                    map[xPos + l][yPos].setEx(new Explosion(xPos + l, yPos, 2));
                }else{
                    map[xPos + 1][yPos].destroy(map);
                }
                break;
            }else{
                map[xPos + 1][yPos].setEx(new Explosion(xPos + l, yPos, 2));
            }
        }
        
        for (int i = 0; i < l; i++) {//downward
            if(map[xPos][yPos + l].getOnTile() != null){
                if(map[xPos][yPos + l].getOnTile() instanceof Bomb){
                    map[xPos][yPos + l].setEx(new Explosion(xPos, yPos + l, 3));
                }else{
                    map[xPos][yPos + l].destroy(map);
                }
                break;
            }else{
                map[xPos][yPos + l].setEx(new Explosion(xPos, yPos + l, 3));
            }
        }
        
        for (int i = 0; i < l; i++) {//leftly
            if(map[xPos - l][yPos].getOnTile() != null){
                if(map[xPos - l][yPos].getOnTile() instanceof Bomb){
                    map[xPos - l][yPos].setEx(new Explosion(xPos - l, yPos, 4));
                }else{
                    map[xPos - 1][yPos].destroy(map);
                }
                break;
            }else{
                map[xPos - 1][yPos].setEx(new Explosion(xPos - l, yPos, 4));
            }
        }
        
        
    }
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(xPos*64,yPos*64,64,64);
    }
    
}
