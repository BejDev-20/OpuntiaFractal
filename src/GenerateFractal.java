import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Point;

/**
 * Class holds the settings information about the fractals, generates them and draws when asked to by its observers.
 * Model part of the MVC
 *
 * @author Iulia Bejsovec
 * @version 02/2020
 */
public class GenerateFractal implements Subject{
    /** list of observers */
    private ArrayList<Observer> observers;
    /** list of all fractals */
    private ArrayList<Fractal> allFractals;
    /** recursion depth */
    private int recursionDepth;
    /** ratio of child radius to parent's radius */
    private double radiusRatio;
    /** color of the body of the cactus */
    private Color cactusColor;
    /** color of the pear (last level fractals) */
    private Color pearColor;
    /** degree of the position of the child to the parent*/
    private double degree;
    /** default recursion depth */
    private int DEFAULT_DEPTH = 5;
    /** default ratio of child radius to parent radius */
    private double DEFAULT_RATIO = 0.45;
    /** default degree of the position of the child to the parent */
    private double DEFAULT_DEGREE = Math.toRadians(45);
    /** default color of the body of the cactus */
    private Color DEFAULT_CACTUS_COLOR = new Color(34, 139, 34);
    /** default color of the pear (last level fractals) */
    private Color DEFAULT_PEAR_COLOR = new Color(139, 34, 139);
    /** main parent's starting radius */
    private int STARTING_RADIUS = 100;
    
    /**
     * Creates the object while setting all the values to default
     */
    public GenerateFractal(){
        recursionDepth = DEFAULT_DEPTH;
        radiusRatio = DEFAULT_RATIO;
        cactusColor = DEFAULT_CACTUS_COLOR;
        pearColor = DEFAULT_PEAR_COLOR;
        degree = DEFAULT_DEGREE;
        allFractals = new ArrayList<>();
        observers = new ArrayList<>();
    }
    
    /**
     * Generates fractals with the start in the bottom middle of the given parameters
     * @param width     width of the panel
     * @param height    height of the panel
     * @return current object
     */
    public GenerateFractal getData(int width, int height){
        if (width <= 0 || height <= 0){
            throw new IllegalArgumentException("Width and height must be bigger than 0");
        }
        allFractals.clear();
        Point startingPoint = new Point((width/2), height - STARTING_RADIUS );
        generateFractals(recursionDepth, STARTING_RADIUS, startingPoint, degree);
        return this;
    }
    
    /**
     * Recursively generates the fractals with the given parameters
     * @param radius        radius of the current fractal
     * @param centerPoint   center point of the fractal
     * @param currentDegree degree of the position of the child fractal
     * @throws IllegalArgumentException when level passed is less than 1
     * @throws IllegalArgumentException if radius passed is negative or equals 0
     */
    private void generateFractals(int recursionDepth, double radius, Point centerPoint, 
                                  double currentDegree){
        if (recursionDepth < 1){
            throw new IllegalArgumentException("Recursion depth must be positive");
        } 
        if (radius <=0){
            throw new IllegalArgumentException("Radius must be positive");
        }
        if (recursionDepth == 1){
            allFractals.add(new Fractal((int)radius, centerPoint, pearColor));
        } else {
            if (radius < 5){
                recursionDepth = 2;
            }
            double childRadius = radius * radiusRatio;
            double leftDegree = currentDegree - degree*2;  
            
            Point tangentPointR = 
                new Point((int)(radius * Math.sin(currentDegree) + centerPoint.getX()), 
                (int)(-radius * Math.cos(currentDegree) + centerPoint.getY()));
            Point centerPointR = 
                new Point((int)(childRadius * Math.sin(currentDegree) + 
                tangentPointR.getX()), (int)(-childRadius * Math.cos(currentDegree) + 
                tangentPointR.getY()));                    
            Point tangentPointL = 
                new Point((int)(radius * Math.sin(leftDegree) + centerPoint.getX()), 
                (int)(-radius * Math.cos(leftDegree) + centerPoint.getY()));
            Point centerPointLeft = 
                new Point((int)(childRadius * Math.sin(leftDegree) + tangentPointL.getX()),
                (int)(-childRadius * Math.cos(leftDegree) + tangentPointL.getY()));
            generateFractals(recursionDepth - 1, childRadius, centerPointR, currentDegree + degree);
            allFractals.add(new Fractal((int)radius, centerPoint, cactusColor));
            generateFractals(recursionDepth - 1, childRadius, centerPointLeft, 
                             currentDegree - degree); 
        }
    }
                                                                                                    
    /**
     * Sets the fractal data to the passed values
     * @param recursionDepth    depth of recursion of fractals
     * @param radiusRatio       radius ratio of the child to the parent
     * @param cactusColor       color of the cactus
     * @param pearColor         color of the pear of the cactus
     * @throws IllegalArgumentException if recursion depth less than 2 or bigger than 10
     * @throws IllegalArgumentException if radius ratio is less than 40 or bigger than 70
     * @throws IllegalArgumentException if cactus color is null
     * @throws IllegalArgumentException if pear color is null
     */
    
    public void setData(int recursionDepth, double radiusRatio, int degree, 
                        Color cactusColor, Color pearColor ){
        if (recursionDepth < 2 || recursionDepth > 10){
            throw new IllegalArgumentException("Recursion depth must be between " +
                                                "2 and 10");
        }
        if (radiusRatio < 40 && radiusRatio > 70){
            throw new IllegalArgumentException("Radius ratio must be between 40 and " +
                                                "70");
        }
        if (pearColor == null || cactusColor == null){
            throw new IllegalArgumentException("Color cannot be null");
        }
        this.recursionDepth = recursionDepth;
        this.radiusRatio = (double)(radiusRatio / 100);
        this.degree = Math.toRadians(degree);
        this.cactusColor = cactusColor;
        this.pearColor = pearColor;
        notifyAllObservers();
    }
    
    /**
     * Draws every fractal on the given graphics object
     * @param g     graphic object to be drawn on
     */
    public void draw(Graphics g){
        for (Fractal oneFractal : allFractals){
            oneFractal.draw(g);
        }
    }
    
    /**
     * Retrieves the recursion depth
     * @return the recursion depth (levels) of the fractals
     */
    public int getRecursionDepth(){
        return recursionDepth;
    }
    
    /**
     * Retrieves the radius ratio
     * @return the radius ratio of the child to the parent
     */
    public int getRadiusRatio(){
        return (int)(radiusRatio * 100);
    }
    
    /**
     * Retrieves the color of the cactus
     * @return the color of the cactus
     */
    public Color getCactusColor(){
        return cactusColor;
    }
    
    /**
     * Retrieves the color of the pear part of the cactus
     * @return the color of the pear part of the cactus
     */
    public Color getPearColor(){
        return pearColor;
    }
    
    
    /**
     * Adds an observer to the observer list
     * @param observer  observer to be added to the list
     */
    public void attach(Observer observer){
        observers.add(observer);  
    }
    
    /**
     * Deletes an observer from the observer list
     * @param observer  observer to be deleted from the list
     */
    public void detach(Observer observer){
        observers.remove(observers.indexOf(observer));
    }
    
    /**
     * Notifies all observers of a data update
     */
    public void notifyAllObservers(){
        for (Observer oneObserver : observers){
            oneObserver.update();
        }
    }
}
