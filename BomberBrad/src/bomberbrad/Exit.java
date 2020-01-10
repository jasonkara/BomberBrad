/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberbrad;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author ridec8459
 */
public class Exit extends tileObject {
    public Exit(int xPos,int yPos) {
        super(xPos,yPos);
    }
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.blue);
        g2d.fillRect(xPos*64,yPos*64,64,64);
    }
}
