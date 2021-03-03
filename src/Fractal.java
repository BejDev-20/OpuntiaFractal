import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a shape (fractal) to draw with given radius, color and
 * center point of the shape
 *
 * @author Iulia Bejsovec
 * @version 02/2020
 */
public class Fractal{
    /** center point of the fractal*/
    private Point centerPoint;
    /** diameter of the fractal*/
    private int diameter;
    /** radius of the fractal */
    private int radius;
    /** fractal's color */
    private Color color;
    
    /**
     * Creates a fractal with the given radius, center point and color
     * @param radius radius of the fractal
     * @param centerPoint center point of the fractal
     * @param color color of the fractal
     * @throws IllegalArgumentException if radius is negative or equals to 0
     * @throws IllegalArgumentException if center point is null
     * @throws IllegalArgumentException if color is null
     */
    public Fractal(int radius, Point centerPoint, Color color){
        if (radius <= 0){
            throw new IllegalArgumentException("Radius must be positive");
        }
        if (centerPoint == null){
            throw new IllegalArgumentException("Center point must not be null");
        }
        if (color == null){
            throw new IllegalArgumentException("Color must not be null");
        }
        diameter = (int)(radius * 2);
        this.radius = (int)(radius);
        this.color = color;
        this.centerPoint = centerPoint;
    }
    
    /**
     * Draws the fractal on the given graphics object
     * @param g graphics object to draw on
     */
    public void draw(Graphics g){
        g.setColor(color);
        g.drawOval((int)(centerPoint.getX() - radius), (int)(centerPoint.getY() - radius), 
                   diameter, diameter);
        g.fillOval((int)(centerPoint.getX() - radius), (int)(centerPoint.getY() - radius), 
                   diameter, diameter);
    }
    
    /**
     * Retrieves the center point of the fractal
     * @return the center point of the fractal
     */
    public Point getCenterPoint(){
        return centerPoint;
    }
    
    /**
     * Retrieves the diameter of the fractal
     * @return the diameter of the fractal
     */
    public int getDiameter(){
        return diameter;
    }
    
    /**
     * Retrieves the raidus of the fractal
     * @return the raidus of the fractal
     */
    public int getRadius(){
        return radius;
    }
    
    /**
     * Retrieves the color of the fractal
     * @return the color of the fractal
     */
    public Color getColor(){
        return color;
    }
}


