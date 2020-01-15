/**
 * DKP Studios
 * 2020-01-15
 * Bomb class that extends tile object
 */
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Bomb extends tileObject{
    private int counter;
    private int frame;
    private static BufferedImage[] sprites = new BufferedImage[3];
    
    /**
     * Load images method that is run at the beginning of the program to open
     * connections to all of the images
     */
    public void loadImages() {
        try {    
            sprites[0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/bomb/1.png"));
            sprites[1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/bomb/2.png"));
            sprites[2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/tile/bomb/3.png"));
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    
    /**
     * Constructor
     * @param xPos x position of the bomb
     * @param yPos y position of the bomb
     */
    public Bomb(int xPos, int yPos) {
        super(xPos,yPos);
        counter = 50;
        frame = 11;
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
    
    /**
     * Explosion method for the bomb to run when the counter reaches 0 or an explosion overlaps the bomb
     * @param length the amount of times it loops for the length
     * @param map a reference to the map
     */
    public void explode(int length, Tile[][] map) {
        //sets a center explosion at location of the bomb
        map[xPos][yPos].setEx(new Explosion(xPos, yPos, 0));
        
        for (int i = 1; i <= length; i++) {//upwards //loops for the length
            if(map[xPos][yPos - i].getOnTile() != null){//checks if there is something on the tile in the direction
                if(map[xPos][yPos - i].getOnTile() instanceof Bomb){//if it is a bomb
                    map[xPos][yPos - i].setEx(new Explosion(xPos, yPos - i, 1));//set an explosion there
                }else{//if it's anything else
                    map[xPos][yPos - i].destroy(map);//destroy it
                }
                break;//and break out of the loop
            }else{//if there is nothing there
                map[xPos][yPos - i].setEx(new Explosion(xPos, yPos - i, 1));//place an explosion and continue the loop
            }
        }
        //repeat with all of the other directions
        for (int i = 1; i <= length; i++) {//rightly
            if(map[xPos + i][yPos].getOnTile() != null){
                if(map[xPos + i][yPos].getOnTile() instanceof Bomb){
                    map[xPos + i][yPos].setEx(new Explosion(xPos + i, yPos, 2));
                }else{
                    map[xPos + i][yPos].destroy(map);
                }
                break;
            }else{
                map[xPos + i][yPos].setEx(new Explosion(xPos + i, yPos, 2));
            }
        }
        
        for (int i = 1; i <= length; i++) {//downward
            if(map[xPos][yPos + i].getOnTile() != null){
                if(map[xPos][yPos + i].getOnTile() instanceof Bomb){
                    map[xPos][yPos + i].setEx(new Explosion(xPos, yPos + i, 3));
                }else{
                    map[xPos][yPos + i].destroy(map);
                }
                break;
            }else{
                map[xPos][yPos + i].setEx(new Explosion(xPos, yPos + i, 3));
            }
        }
        
        for (int i = 1; i <= length; i++) {//leftly
            if(map[xPos - i][yPos].getOnTile() != null){
                if(map[xPos - i][yPos].getOnTile() instanceof Bomb){
                    map[xPos - i][yPos].setEx(new Explosion(xPos - i, yPos, 4));
                }else{
                    map[xPos - i][yPos].destroy(map);
                }
                break;
            }else{
                map[xPos - i][yPos].setEx(new Explosion(xPos - i, yPos, 4));
            }
        }
        
        
    }
    
    /**
     * A draw method for the bomb
     * @param g2d the g2d being used to draw
     */
    public void draw(Graphics2D g2d) {
        //draw the tile behind it
        g2d.setColor(new Color(62, 120, 19));
        g2d.fillRect(xPos*64,yPos*64,64,64);
        //initialize the image
        BufferedImage shown;
        //set the image
        shown = sprites[frame / 4];
        
        //increment the frame
        frame--;
        if(frame < 0){//loop the frame to 11 if it drops below 0
            frame = 11;
        }
        
        //draw the image
        g2d.drawImage(shown,xPos*64,yPos*64,(xPos + 1)*64,(yPos + 1)*64,0,0,16,16,null);
        
        

    }
    
}
