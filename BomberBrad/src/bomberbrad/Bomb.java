//Riley DeConkey
//12/13/2019
//Class to represent the bombs that the player places
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
    public void explode(int length, Tile[][] map) {
        
        map[xPos][yPos].setEx(new Explosion(xPos, yPos, 0));
        
        for (int i = 1; i <= length; i++) {//upwards
            if(map[xPos][yPos - i].getOnTile() != null){
                if(map[xPos][yPos - i].getOnTile() instanceof Bomb){
                    map[xPos][yPos - i].setEx(new Explosion(xPos, yPos - i, 1));
                }else{
                    map[xPos][yPos - i].destroy(map);
                }
                break;
            }else{
                map[xPos][yPos - i].setEx(new Explosion(xPos, yPos - i, 1));
            }
        }
        
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
    public void draw(Graphics2D g2d) {

        g2d.setColor(new Color(62, 120, 19));
        g2d.fillRect(xPos*64,yPos*64,64,64);
        
        BufferedImage shown;
        
        shown = sprites[frame / 4];
        frame--;
        if(frame < 0){
            frame = 11;
        }
        
        g2d.drawImage(shown,xPos*64,yPos*64,(xPos + 1)*64,(yPos + 1)*64,0,0,16,16,null);
        
        

    }
    
}
