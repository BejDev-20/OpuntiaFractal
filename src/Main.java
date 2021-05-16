/**
 * Entry to the program, creates the GUI, a Display to draw the fractals and generates all fractals to be drawn with
 * the data from GUI. Created using MVC and Observer patterns
 *
 * @author Iulia Bejsovec
 * @version 02/2020
 */
public class Main{
    /**
     * Creates the GUI, Display to draw the fractals and generates all fractals to be drawn with the data from GUI.
     * Attaches the observer(s) to the subject
     * @param args  possible arguments
     */
    public static void main(String[] args){
        GenerateFractal fractalData = new GenerateFractal();
        GUI gui = new GUI(fractalData);
        Display display = new Display(fractalData);
        fractalData.attach(display);
    }
}
