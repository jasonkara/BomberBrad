package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Player extends Entity {

    private boolean moving;

    Player(int xPos, int yPos, int direction) {
        super(xPos, yPos, 1, direction, 2, null);
    }

    public void action(DrawingSurface ds, Graphics2D g2d) {
        Tile[][] map = ds.getBoard();
        ArrayList<Enemy> EL = ds.getEnemiesList();
        if (moving) {
            switch (direction) {
                case 1:
                    if (map[xPos / 16][(yPos - speed) / 16].getOnTile() != null
                            || map[(xPos + 15) / 16][(yPos - speed) / 16].getOnTile() != null) {

                    } else {
                        move();
                    }

                    break;
                case 2:
                    if (map[(xPos + 15 + speed) / 16][(yPos) / 16].getOnTile() != null
                            || map[(xPos + 15 + speed) / 16][(yPos + 15) / 16].getOnTile() != null) {

                    } else {
                        move();
                    }

                    break;
                case 3:
                    if (map[xPos / 16][(yPos + 15 + speed) / 16].getOnTile() != null
                            || map[(xPos + 15) / 16][(yPos + 15 + speed) / 16].getOnTile() != null) {
                    } else {
                        move();
                    }

                    break;
                default:
                    if (map[(xPos - speed) / 16][(yPos) / 16].getOnTile() != null
                            || map[(xPos - speed) / 16][(yPos + 15) / 16].getOnTile() != null) {

                    } else {
                        move();
                    }

            }
        }
        for (Enemy e : EL) {
            if (ds.intersecting(xPos, yPos, e.getXPos(), e.getYPos())) {
                ds.restartLevel();
                ds.setBombs(1);
            }
        }
        
        if (ds.intersecting(xPos,yPos,ds.getExitX() * 16,ds.getExitY() * 16) && EL.size() == 0) {
            ds.setLevel(ds.getLevel() + 1);
            ds.restartLevel();
        }
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void move() {
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

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(xPos * 4, yPos * 4, 64, 64);
    }

}
