import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * The view part of the MVC. Representsthe window to draw the fractals on. Implements the Observer interface with a
 * pull model, retrieves data from the subjects when ready to draw
 *
 * @author Iulia Bejsovec
 * @version 02/2020
 */
public class Display extends JFrame implements Observer{
    /** all fractals to be drawn (subject) */
    private GenerateFractal allFractals;
    
    /**
     * Creates the display to be drawn on
     * @param fractals all fractals to be drawn
     * @throws IllegalArgumentException if fractals is null
     */
    public Display(GenerateFractal fractals){
        if (fractals == null){
            throw new IllegalArgumentException("Fractals cannot be null");
        }
        allFractals = fractals;
        Toolkit toolkit = Toolkit.getDefaultToolkit(); 
        Dimension screenSize = toolkit.getScreenSize();
        setPreferredSize(new Dimension((int)(screenSize.getWidth() * 0.75), 
                                       (int)(screenSize.getHeight() * 0.65)));
        setTitle("Display");
        JPanel panel = new GPanel();
        getContentPane().add(panel);
        panel.setLayout(null);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Gets up to date data for the fractals and set the frame visible
     */
    public void update(){
        allFractals.getData((int)getContentPane().getSize().getWidth(), (int)getContentPane().getSize().getHeight());
        this.setVisible(true);
    }
    
    /**
     * Custom JPanel responsible for drawing the fractals
     */
    private class GPanel extends JPanel{
        
        /**
         * Paints the fractals on the given graphics object
         * @param g graphics object to be drawn on
         */
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            allFractals.draw(g);
        }
    }
}
