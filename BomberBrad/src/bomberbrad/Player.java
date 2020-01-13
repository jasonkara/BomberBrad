package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Player extends Entity {

    private boolean moving;
    private int lives;
    private static BufferedImage[] sprites = new BufferedImage[19];
    private int frameCounter;
    private boolean dying;

    Player(int xPos, int yPos, int direction) {
        super(xPos, yPos, 1, direction, 2);
        lives = 3;
        frameCounter = 0;
        dying = false;
    }

    public void action(DrawingSurface ds, Graphics2D g2d) {
        Tile[][] map = ds.getBoard();
        ArrayList<Enemy> EL = ds.getEnemiesList();
        
        boolean onBomb = map[(xPos + 8) / 16][(yPos + 8) / 16].getOnTile() instanceof Bomb;
        
        if(onBomb){
            walkable.add(Bomb.class);
        }
        
        if (moving) {
            switch (direction) {
                case 1:
                    if (isWalkable(map[xPos / 16][(yPos - speed) / 16].getOnTile())//top left of the player + speed
                            && isWalkable(map[(xPos + 15) / 16][(yPos - speed) / 16].getOnTile())) {//top right
                        
                        move();
                        
                    } else {
                        
                        if(isWalkable(map[(xPos) / 16][(yPos - speed) / 16].getOnTile())){
                            shift(-1,0);
                        }else if(isWalkable(map[(xPos + 15) / 16][(yPos - speed) / 16].getOnTile())){
                            shift(1,0);
                        }
                    }

                    break;
                case 2:
                    if (isWalkable(map[(xPos + 15 + speed) / 16][(yPos) / 16].getOnTile())
                            && isWalkable(map[(xPos + 15 + speed) / 16][(yPos + 15) / 16].getOnTile())) {
                        
                        move();
                    } else {
                        
                        if(isWalkable(map[(xPos + 15 + speed) / 16][(yPos) / 16].getOnTile())){
                            shift(0,-1);
                        }else if(isWalkable(map[(xPos + 15 + speed) / 16][(yPos + 15) / 16].getOnTile())){
                            shift(0,1);
                        }
                    }

                    break;
                case 3:
                    if (isWalkable(map[xPos / 16][(yPos + 15 + speed) / 16].getOnTile())
                            && isWalkable(map[(xPos + 15) / 16][(yPos + 15 + speed) / 16].getOnTile())) {
                        
                        move();
                    } else {
                        
                        if(isWalkable(map[(xPos)  / 16][(yPos + 15 + speed) / 16].getOnTile())){
                            shift(-1,0);
                        }else if(isWalkable(map[(xPos + 15) / 16][(yPos + 15 + speed) / 16].getOnTile())){
                            shift(1,0);
                        }
                    }
                    break;
                default:
                    if (isWalkable(map[(xPos - speed) / 16][(yPos) / 16].getOnTile())
                            && isWalkable(map[(xPos - speed) / 16][(yPos + 15) / 16].getOnTile())) {

                        move();
                    } else {
                        
                        if(isWalkable(map[(xPos - speed) / 16][(yPos) / 16].getOnTile())){
                            shift(0,-1);
                        }else if(isWalkable(map[(xPos - speed) / 16][(yPos + 15) / 16].getOnTile())){
                            shift(0,1);
                        }
                    }

            }
        }
        
        if(onBomb){
            walkable.remove(Bomb.class);
        }
        
        for (Enemy e : EL) {
            if (ds.intersecting(xPos, yPos, e.getXPos(), e.getYPos()) && ! dying) {
                ds.clip.stop();
                ds.playAudio("die");
                dying = true;
                frameCounter = 0;
            }
        }
        
        if (dying && ds.clip.getMicrosecondLength() == ds.clip.getMicrosecondPosition()) {
            dying = false;
            ds.death();
        }
        
        if (ds.intersecting(xPos,yPos,ds.getExitX() * 16,ds.getExitY() * 16) && EL.size() == 0) {
            ds.clip.stop();
            ds.playAudio("stagecomplete");
            while (ds.clip.getMicrosecondLength() != ds.clip.getMicrosecondPosition()) {}
            ds.setLevel(ds.getLevel() + 1);
            ds.restartLevel();
            ds.setScore(ds.getScore() + ds.getTimeBonus());
        }
        
        if (! dying && moving && frameCounter % 4 == 0) {
            ds.playSE("step");
        }
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    
    public void setLives(int l) {
        lives = l;
    }
    
    public int getLives() {
        return lives;
    }

    public boolean isDying() {
        return dying;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }
    
    public void setFrameCounter(int fc) {
        this.frameCounter = fc;
    }
    
    public int getFrameCounter() {
        return frameCounter;
    }

    public void move() {
        if (! dying) {
            switch (direction) {
                case 1:
                    yPos -= speed;
                    break;
                case 2:
                    xPos += speed;
                    break;
                case 3:
                    yPos += speed;
                    break;
                default:
                    xPos -= speed;
            }
        }
    }
    
    

    public void draw(Graphics2D g2d){
        BufferedImage shown = null;
        if (dying) {
            if (frameCounter < 14) {
                shown = sprites[12 + frameCounter / 2];
            }
            frameCounter ++;
        } else {
            int add = (direction - 1) * 3;
            if (frameCounter < 2) {
                shown = sprites[0 + add];
            } else if (frameCounter < 6 && frameCounter > 3) {
                shown = sprites[2 + add];
            } else {
                shown = sprites[1 + add];
            }
            if (moving) frameCounter ++;
            if (frameCounter >= 8) {
                frameCounter = 0;
            }
        }
        g2d.drawImage(shown,xPos*4,yPos*4,xPos*4+64,yPos*4+64,0,0,16,16,null);
    }
    
    public void loadImages() {
        try {    
            sprites[0] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/u1.png"));
            sprites[1] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/u2.png"));
            sprites[2] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/u3.png"));
            sprites[3] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/r1.png"));
            sprites[4] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/r2.png"));
            sprites[5] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/r3.png"));
            sprites[6] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/d1.png"));
            sprites[7] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/d2.png"));
            sprites[8] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/d3.png"));
            sprites[9] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/l1.png"));
            sprites[10] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/l2.png"));
            sprites[11] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/l3.png"));
            sprites[12] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die1.png"));
            sprites[13] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die2.png"));
            sprites[14] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die3.png"));
            sprites[15] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die4.png"));
            sprites[16] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die5.png"));
            sprites[17] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die6.png"));
            sprites[18] = ImageIO.read(getClass().getResource("/bomberbrad/sprites/entity/player/die7.png"));
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }
    

}
