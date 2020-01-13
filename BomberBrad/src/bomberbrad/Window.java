/**
 * DKP Studios (Jason)
 * JFrame window that contains DrawingSurface JPanel
 */

package bomberbrad;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Window extends JFrame {

    //constructor
    public Window() {
        //create the User interface
        initUI();
    }

    //create the custom JFrame
    private void initUI() {        
        //set title of the JFrame
        setTitle("BomberBrad - © 2020 DKP Studios");
        //add a custom JPanel to draw on
        add(new DrawingSurface());
        //set the size of the window
        setSize(960,812);
        //tell the JFrame what to do when closed
        //this is important if our application has multiple windows
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    }

    public static void main(String[] args) {
        //make sure that all UI updates are concurrency safe (related to multi threading)
        //much more detailed http://www.javamex.com/tutorials/threads/invokelater.shtml
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                //instantiate the main window
                Window windowFrame = new Window();
                //make sure it can be seen
                windowFrame.setVisible(true);
                windowFrame.setResizable(false);
            }
        });
    }
}
