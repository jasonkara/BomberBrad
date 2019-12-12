/*
 * B Cutten
    Sept 2014
    A class which allows drawing, because it extends JPanel, by way of the 
    Graphics2D class
    Because this class is only going to ever be used by the Graphics2DTester, it could be 
    included in the same file (Graphics2DTester.java)
 */

package bomberbrad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class DrawingSurface extends JPanel{
    
    //does the actual drawing
     private void doDrawing(Graphics g) {        
        //the Graphics2D class is the class that handles all the drawing
        //must be casted from older Graphics class in order to have access to some newer methods
        Graphics2D g2d = (Graphics2D) g;
        //draw a string on the panel        
        g2d.drawString("Java 2D", 50, 50); //(text, x, y)
        g2d.drawOval(0, 0, 100, 100);  //(x,y,width,height)
        g2d.setColor(Color.red);//change color
        int x, y;
        for(int i = 0; i < 1000; i++){
            //get random x,y location
            x =(int)(Math.random()*getWidth());
            y =(int)(Math.random()*getHeight());
            //draw a really short line (point)
            g2d.drawLine(x,y,x,y);
        }
    }
     
    //overrides paintComponent in JPanel class
    //performs custom painting
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);//does the necessary work to prepare the panel for drawing
        doDrawing(g);
    }
}
