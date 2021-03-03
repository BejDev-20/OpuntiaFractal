/**
 * Observer interface
 * 
 * @author Iulia Bejsovec
 * @version 01-04-2020
 */
public interface Observer {
    /** 
     * Updates the observer whenever necessary, usually about changes in the state of 
     * the Subject
     */
    public void update();
}
