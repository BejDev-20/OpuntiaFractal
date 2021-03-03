import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.plaf.SliderUI;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.JColorChooser;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * GUI with components to set the settings for the fractal drawing
 * with the parameters:
 * -recursion depth of the fractals (2 to 10)
 * -ratio of child's radius to parent's radius (40 to 70)
 * -degree of the position of the child to the parent (25 to 60)
 * -cactus color chooser
 * -pear color chooser
 * Controller part of the MVC
 *
 * @author Iulia Bejsovec with help of GuiGenie - Copyright (c) 2004 Mario Awad.
 * @version 02/2020
 */
public class GUI{
    /** all fractals */
    private GenerateFractal allFractals;
    /** width of the window */
    int windowWidth = 300;
    /** height of the window */
    int windowHeight = 630;
    
    /** label for the recursion depth*/
    private JLabel recursionDepthLabel;
    /** label for the child radius ratio */
    private JLabel childRadiusLabel;
    /** label for the degree ofthe position of the child */
    private JLabel childPositionToParent;
    /** label for cactus color */
    private JLabel cactusColorLabel;
    /** label for pear color */
    private JLabel pearColorLabel;
    
    /** drop down for recursion depth */
    private JComboBox<Integer> recursionDepthComboBox;
    /** slider for the childs' radius ratio to parent's radius */
    private JSlider childRadiusSlider;
    /** slider fot the position (degree) of the child to parent */
    private JSlider childPositionSlider;
    /** color chooser for the cactus color */
    private JButton chooseCactusColor;
    /** color chooser for the pear color */
    private JButton choosePearColor;
    /** button to draw the fractals*/
    private JButton drawButton;
    
    /**
     * Creates the GUI with the settings (controls)
     * @param fractals fractal object that has all the default and updated
     * data for the fractals to be drawn
     */
    public GUI(GenerateFractal fractals){
        this.allFractals = fractals;
        JFrame frame = new JFrame("Prickley Pear Cactus Fractal");
        frame.setSize(windowHeight, windowWidth);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        frame.add(panel);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
        
        Integer[] recursionDepthComboBoxItems = {2, 3, 4, 5, 6, 7, 8, 9, 10};

        recursionDepthComboBox = new JComboBox<>(recursionDepthComboBoxItems);
        childRadiusSlider = new JSlider(40, 70);
        childPositionSlider = new JSlider(25, 60);
        
        recursionDepthLabel = new JLabel("Recursion Depth");
        childRadiusLabel = new JLabel("Child to Parent Ratio");
        childPositionToParent = new JLabel("Child Position (degree)");
        cactusColorLabel = new JLabel("Cactus Color");
        pearColorLabel = new JLabel("Pear Color");
        
        chooseCactusColor = new JButton();
        choosePearColor = new JButton();
        drawButton = new JButton("Draw");

        //set components properties
        childRadiusSlider.setOrientation(JSlider.HORIZONTAL);
        childRadiusSlider.setMinorTickSpacing(1);
        childRadiusSlider.setMajorTickSpacing(5);
        childRadiusSlider.setPaintTicks(true);
        childRadiusSlider.setPaintLabels(true);
        
        childPositionSlider.setOrientation(JSlider.HORIZONTAL);
        childPositionSlider.setMinorTickSpacing(1);
        childPositionSlider.setMajorTickSpacing(5);
        childPositionSlider.setPaintTicks(true);
        childPositionSlider.setPaintLabels(true);

        childRadiusSlider.setValue(fractals.getRadiusRatio());
        recursionDepthComboBox.setSelectedIndex(fractals.getRecursionDepth()-2);
        
        panel.setPreferredSize(new Dimension(windowHeight, windowWidth));
        panel.setLayout(null);

        //add components
        panel.add(recursionDepthComboBox);
        panel.add(recursionDepthLabel);
        panel.add(childRadiusLabel);
        panel.add(childPositionToParent);
        panel.add(childRadiusSlider);
        panel.add(childPositionSlider);
        panel.add(cactusColorLabel);
        panel.add(pearColorLabel);
        panel.add(chooseCactusColor);
        panel.add(choosePearColor);
        panel.add(drawButton);

        //set component bounds
        recursionDepthComboBox.setBounds(35, 60, 100, 25);
        recursionDepthLabel.setBounds(35, 20, 100, 25);
        childRadiusLabel.setBounds(180, 20, 120, 25);
        childPositionToParent.setBounds(395, 20, 145, 25);
        childRadiusSlider.setBounds(180, 60, 185, 55);
        childPositionSlider.setBounds(390, 60, 200, 55);
        cactusColorLabel.setBounds(35, 160, 100, 25);
        pearColorLabel.setBounds(35, 210, 100, 25);
        chooseCactusColor.setBounds(135, 160, 25, 25);
        choosePearColor.setBounds(135, 210, 25, 25);
        drawButton.setBounds(425, 165, 120, 50);
        
        //adding action listeners
        chooseCactusColor.setBackground(allFractals.getCactusColor());
        chooseCactusColor.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    Color color = JColorChooser.showDialog(panel, "Color chooser", 
                                                           allFractals.getCactusColor());
                    if (color != null){
                        chooseCactusColor.setBackground(color);                                       
                    }
                }
            }
        );
        
        choosePearColor.setBackground(allFractals.getPearColor());
        choosePearColor.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    Color color = JColorChooser.showDialog(panel, "Color chooser", 
                                                           allFractals.getPearColor());
                    if (color != null){
                        choosePearColor.setBackground(color);                                       
                    }
                }
            }
        );
        
        drawButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    fractals.setData((Integer)(recursionDepthComboBox.getSelectedItem()), 
                                    childRadiusSlider.getValue(), childPositionSlider.getValue(),
                                    chooseCactusColor.getBackground() , choosePearColor.getBackground());
                }
            }
        );
    }
}

