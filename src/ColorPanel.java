import VecInterface.Observer;
import VecInterface.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Defines Color picker GUI component
 */
public class ColorPanel extends JPanel implements Subject {

    enum SelectMode{PEN, FILL};

    private String className = "ColourPanel";

    private ArrayList<Observer> observers  = new ArrayList<>();

    private JPanel pnlQuickColours = new JPanel();
    private JLayeredPane pnlColourPicked = new JLayeredPane();
    private JPanel lineColourPicked = new JPanel();
    private JPanel fillColourPicked = new JPanel();
    private JButton btnColourPicker = new JButton("More Colours");
    private JButton btnChooseLineColour = new JButton("Pen");
    private JButton btnChooseFillColour = new JButton("Fill");
    private Color penColour = Color.black; // default
    private Color fillColour = Color.white; // default

    private SelectMode colourChooseMode = SelectMode.PEN;

    private Color[] quickColours = {Color.red, Color.blue, Color.green, Color.orange, Color.yellow,
            Color.pink, Color.cyan, Color.gray, Color.black, Color.magenta};

    private ArrayList<JButton> quickColourBtns =  new ArrayList<>();

    /**
     * Constructor
     */
    public ColorPanel(){
        setBorder(BorderFactory.createTitledBorder("Colours"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);

        createColourChooserButton();
        createQuickColourBtns();
        layoutQuickColourPanel();

        add(pnlQuickColours);
        add(btnColourPicker);
        add(pnlColourPicked);

        setUpPenColourPanel();
        setUpFillColourPanel();

        pnlColourPicked.setLayout(new GridLayout(1, 2));
        pnlColourPicked.add(lineColourPicked);
        pnlColourPicked.add(fillColourPicked);

        pnlQuickColours.setAlignmentX(0.5f);
        btnColourPicker.setAlignmentX(0.5f);
        pnlColourPicked.setAlignmentX(0.5f);
    }

    /**
     * Sets up line (pen) colour panel
     */
    private void setUpPenColourPanel(){
        lineColourPicked.setBackground(penColour);
        btnChooseLineColour.setForeground(Color.orange);
        btnChooseLineColour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnChooseLineColour.setForeground(Color.orange);
                btnChooseFillColour.setForeground(Color.black);
                colourChooseMode = SelectMode.PEN;
            }
        });
        lineColourPicked.add(btnChooseLineColour, TOP_ALIGNMENT);
    }

    /**
     * Sets up fill colour panel
     */
    private void setUpFillColourPanel(){
        fillColourPicked.setBackground(fillColour);
        btnChooseFillColour.setForeground(Color.black);
        btnChooseFillColour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnChooseLineColour.setForeground(Color.black);
                btnChooseFillColour.setForeground(Color.orange);
                colourChooseMode = SelectMode.FILL;
            }
        });
        fillColourPicked.add(btnChooseFillColour, TOP_ALIGNMENT);
    }



    /**
     * create colour chooser button
     */
    private void createColourChooserButton() {

        btnColourPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (colourChooseMode == SelectMode.PEN) {
                    penColour = JColorChooser.showDialog(null, "Select VecShape.VecLine Colour", penColour);
                    if (penColour == null) {
                        penColour = Color.black;
                    }
                    lineColourPicked.setBackground(penColour);
                } else {
                    fillColour = JColorChooser.showDialog(null, "Select Fill Colour", fillColour);
                    if (fillColour == null) {
                        fillColour = Color.white;
                    }
                    fillColourPicked.setBackground(fillColour);
                }
                notifyObservers(className);
            }
        });
    }

    /**
     * Initialise quick colour buttons
     */
    private void createQuickColourBtns(){
        for (int i = 0; i < 10; i++){
            JButton btn = new JButton();
            btn.setBackground(quickColours[i]);
            pnlQuickColours.add(btn);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton)e.getSource();
                    if (colourChooseMode == SelectMode.PEN) {
                        penColour = button.getBackground();
                        lineColourPicked.setBackground(penColour);
                    } else {
                        fillColour = button.getBackground();
                        fillColourPicked.setBackground(fillColour);
                    }
                    notifyObservers(className);
                }
            });
            quickColourBtns.add(btn);
        }
    }

    /**
     * add grid layout to the quick colour panel
     */
    private void layoutQuickColourPanel() {
        GridLayout layout = new GridLayout(5, 2);
        pnlQuickColours.setLayout(layout);
    }


    /**
     * attach observer
     * @param observer new observer to be registered
     */
    public void attachObserver(Observer observer){
        observers.add(observer);
    }

    /**
     * Tell changes in this class (changes of colour) to observers
     * @param location -need to specify location as this application have multiple subject and one observer
     */
    public void notifyObservers(String location){
        for (int i = 0; i < observers.size(); i++){
            observers.get(i).update(location);
        }
    }

    /**
     * return current pen colour
     * @return current pen colour
     */
    public Color getPenColour(){
        return penColour;
    }

    /**
     * Returns current fill colour
     * @return current fill colour
     */
    public Color getFillColour() { return fillColour;}

}


