package VecGUI;

import VecInterface.Observer;
import VecInterface.Subject;
import VecShape.VecShape;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Defines tool picker VecGUI components
 */
public class ToolPanel extends JPanel implements Subject {

    private String[] toolBtnTexts = {"Plot", "Line", "Rectangle", "Ellipse", "Polygon"};

    private VecShape.Mode currentMode = VecShape.Mode.PLOT;

    private boolean fill = false;
    private boolean grid = false;
    private int gridSize = 0;
    private boolean isGridTop = false;

    private ArrayList<Observer> observers = new ArrayList<>();
    private ArrayList<JButton> toolBtns =  new ArrayList<>();

    private JButton btnFill = new JButton("Fill on");

    private JCheckBox gridCheckBox;

    private JSlider gridSlider;

    private JLabel gridSizeText;

    private JCheckBox gridTopCheckBox;

    /**
     * Constructor
     */
    public ToolPanel(){
        setBorder(BorderFactory.createTitledBorder("Tools"));
        setLayout(new GridLayout(VecShape.Mode.values().length + 5, 1)); // +4 for btnFill, gridCheckBox and gridSlider
        setBackground(Color.LIGHT_GRAY);
        createToolBtns();
        for (int i = 0; i < toolBtns.size(); i++){
            add(toolBtns.get(i));
        }
        createFillButton();
        createGridTool();
        add(btnFill);
        add(gridCheckBox);
        add(gridSlider);
        add(gridTopCheckBox);
        add(gridSizeText);
    }

    /**
     * create a check box that is used to enable grid
     * while this check box is selected, grid is visible
     */
    private void createGridCheckBox(){
        gridCheckBox = new JCheckBox("Grid", false);
        gridCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gridCheckBox.isSelected()){
                    grid = true;
                    gridSlider.setEnabled(true);
                    gridSlider.setValue(gridSize);
                    gridTopCheckBox.setEnabled(true);
                    gridSizeText.setVisible(true);
                } else {
                    grid = false;
                    gridSlider.setEnabled(false);
                    gridTopCheckBox.setEnabled(false);
                    gridSizeText.setVisible(false);
                }
                notifyObservers("GridEnabler");
            }
        });
    }

    /**
     * create a JSlider to customise grid size
     * the value is range between 2 to 50 where 2 divides the canvas into two
     */
    private void createGridSlider(){
        gridSlider = new JSlider(2, 50);
        gridSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gridSize = gridSlider.getValue();
                gridSizeText.setText("Grid Size: " + gridSize);
                notifyObservers("GridEnabler");
            }
        });
        gridSlider.setEnabled(false);
    }

    /**
     * create a check box to determine where to draw grid
     * While this check box is selected, grid is visible on top of the image
     */
    private void createGridTopCheckBox(){
        gridTopCheckBox = new JCheckBox("Grid top", false);
        gridTopCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gridTopCheckBox.isSelected()){
                    isGridTop = true;
                } else {
                    isGridTop = false;
                }
                notifyObservers("GridTop");
            }
        });
    }

    /**
     * Create a check box to enable grid, slider bar to customise grid size, and a label that displays current grid size
     * Grid size can be customised range between 2 ~ 50: size represents how many the canvas is divided into.
     * E.g. if grid size is 2, then canvas is divided in to 2 at both vertical and horizontal direction. So there will be two grid lines
     * vertically and horizontally.
     */
    private void createGridTool(){

        createGridCheckBox();
        createGridSlider();
        createGridTopCheckBox();
        gridTopCheckBox.setEnabled(false);
        gridSizeText = new JLabel();
        gridSizeText.setVisible(false);
    }

    /**
     * Initialise each tool buttons
     */
    private void createToolBtns(){
        for (int i = 0; i < VecShape.Mode.values().length; i++){
            JButton btn = new JButton(toolBtnTexts[i]);
            btn.setOpaque(true);
            btn.setBackground(Color.lightGray);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedBtn = (JButton)e.getSource();
                    switch (clickedBtn.getText()){
                        case "Plot":
                            currentMode = VecShape.Mode.PLOT;
                            break;
                        case "Line":
                            currentMode = VecShape.Mode.LINE;
                            break;
                        case "Rectangle":
                            currentMode = VecShape.Mode.RECTANGLE;

                            break;
                        case "Ellipse":
                            currentMode = VecShape.Mode.ELLIPSE;
                            break;
                        case "Polygon":
                            currentMode = VecShape.Mode.POLYGON;
                            break;
                        default:
                            currentMode = VecShape.Mode.PLOT;
                    }
                    notifyObservers("ToolPanel");
                }
            });
            toolBtns.add(btn);
        }
    }

    /**
     * create fill button
     */
    private void createFillButton(){
        btnFill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fill = !fill;
                if (fill){
                    btnFill.setText("Fill Off");
                } else {
                    btnFill.setText("Fill On");
                }
                notifyObservers("ToolPanel");
            }
        });
    }

    /**
     * attach observer that wants to know the change of drawing tool
     * @param observer new observer to be registered
     */
    public void attachObserver(Observer observer){
        observers.add(observer);
    }

    /**
     * notify changes to observers
     * @param location -need to specify location as this application have multiple subject and one observer
     */
    public void notifyObservers(String location){
        for (int i = 0; i < observers.size(); i++){
            observers.get(i).update(location);
        }
    }

    /**
     * Return current drawing mode
     * @return current drawing mode
     */
    public VecShape.Mode getCurrentMode(){
        return currentMode;
    }

    /**
     * return current fill mode
     * @return true if fill mode is on
     */
    public boolean getFillMode(){return fill;}

    /**
     * return current grid mode
     * @return true if grid is enabled
     */
    public boolean getGridMode(){return grid;}

    /**
     * return current grid size
     * @return grid size that divides screen into.
     */
    public int getGridSize(){return gridSize;}

    /**
     * return is gridTop check box is selected or not
     * @return true if grid needs to appear top of the shapes
     */
    public boolean getIsGridTop(){ return isGridTop;}
}

