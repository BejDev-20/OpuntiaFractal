import java.awt.Color;

/**
 * Interface for the subject in the observer pattern
 * Includes methods to attach, remove observers, notify all observers about
 * any changes in the subject, get data whenever needed (pull model) and set
 * data to update the state whenever necessary
 *
 * @author Iulia Bejsovec
 * @version 02-01-2020
 */
public interface Subject {
    /** 
     * Attaches the given observer to the subject
     * @param obs observer to be added
     */
    public void attach(Observer obs);
    
    /** 
     * Removes the given observer from the subject
     * @param obs observer to be removed
     */
    public void detach(Observer obs);
    
    /** 
     * Notify all observers
     */
    public void notifyAllObservers();
    
    /**
     * Retrives the current state of the subject
     * @param width     width of the window of the observer
     * @param height    height of the window of the observer
     * @return the current state of the Subject
     */
    public Object  getData(int width, int height);
    
    /**
     * Updating subject's state with the given values
     * @param recurstionDepth       recursion depth of the subject
     * @param childRadiusToParent   ratio of the child's radius to parent's
     * @param degree                degree of the position of the child to 
     *                              the parent
     * @param cactusColor           color of the cactus part
     * @param pearColor             color of the pear part (last level/leaves)
     */
    public void  setData(int recurstionDepth, double childRadiusToParent, 
                         int degree, Color cactusColor, Color pearColor);
}