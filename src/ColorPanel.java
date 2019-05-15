import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ColorPanel extends JPanel implements Subject {

    private String className = "ColorPanel";

    private ArrayList<Observer> observers  = new ArrayList<>();

    private JPanel pnlQuickColours = new JPanel();
    private JLayeredPane pnlColourPicked = new JLayeredPane();
    private JPanel lineColourPicked = new JPanel();
    private JPanel fillColourPicked = new JPanel();
    private JButton btnColourPicker = new JButton("More Colours");
    private JButton btnChooseLineColour = new JButton("Line");
    private JButton btnChooseFillColour = new JButton("Fill");
    private Color lineColour = Color.black; // default
    private Color fillColour = Color.white; // default

    private String colourChooseMode = "Line";

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

        setUpLineColourPanel();

        setUpFillColourPanel();
        add(lineColourPicked);
        add(fillColourPicked);

        pnlColourPicked.setLayout(new GridLayout(1, 2));
        pnlColourPicked.add(lineColourPicked);
        pnlColourPicked.add(fillColourPicked);

        pnlQuickColours.setAlignmentX(0.5f);
        btnColourPicker.setAlignmentX(0.5f);
        pnlColourPicked.setAlignmentX(0.5f);
    }

    private void setUpLineColourPanel(){
        lineColourPicked.setBackground(lineColour);
        btnChooseLineColour.setForeground(Color.orange);
        btnChooseLineColour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnChooseLineColour.setForeground(Color.orange);
                btnChooseFillColour.setForeground(Color.black);
                colourChooseMode = "Line";
            }
        });
        lineColourPicked.add(btnChooseLineColour, TOP_ALIGNMENT);
    }

    private void setUpFillColourPanel(){
        fillColourPicked.setBackground(fillColour);
        btnChooseFillColour.setForeground(Color.black);
        btnChooseFillColour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnChooseLineColour.setForeground(Color.black);
                btnChooseFillColour.setForeground(Color.orange);
                colourChooseMode = "Fill";
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
                if (colourChooseMode == "Line") {
                    lineColour = JColorChooser.showDialog(null, "Select Line Colour", lineColour);
                    if (lineColour == null) {
                        lineColour = Color.black;
                    }
                    lineColourPicked.setBackground(lineColour);
                } else {
                    fillColour = JColorChooser.showDialog(null, "Select Fill Colour", fillColour);
                    if (fillColour == null) {
                        fillColour = Color.white;
                    }
                    fillColourPicked.setBackground(fillColour);
                }
                notifyObservers("ColourPanel");
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
                    if (colourChooseMode == "Line") {
                        lineColour = button.getBackground();
                        lineColourPicked.setBackground(lineColour);
                    } else {
                        fillColour = button.getBackground();
                        fillColourPicked.setBackground(fillColour);
                    }
                    notifyObservers("ColourPanel");
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


    public void attachObservers(Observer observer){
        observers.add(observer);
    }

    public void notifyObservers(String location){
        for (int i = 0; i < observers.size(); i++){
            observers.get(i).update(location);
        }
    }

    /**
     * return current choose colour
     * @return current choosen colour
     */
    public Color getLineColour(){
        return lineColour;
    }

    public Color getFillColour() { return fillColour;}

}


