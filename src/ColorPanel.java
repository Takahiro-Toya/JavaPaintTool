import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ColorPanel extends JPanel implements Subject {

    private String className = "ColorPanel";

    private ArrayList<Observer> observers  = new ArrayList<>();

    private JPanel pnlQuickColours;
    private JPanel pnlColourPicker;
    private JButton btnColourPicker;
    private Color chosenColour = Color.black; // default

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
        pnlQuickColours = new JPanel();
        pnlColourPicker = new JPanel();
        createColourChooserButton();
        createQuickColourBtns();
        layoutQuickColourPanel();
        add(pnlQuickColours);
        add(btnColourPicker);
        add(pnlColourPicker);
        pnlColourPicker.setBackground(chosenColour);
        pnlColourPicker.setAlignmentX(0.5f);
        btnColourPicker.setAlignmentX(0.5f);
        pnlColourPicker.setAlignmentX(0.5f);
    }

    /**
     * create colour chooser button
     */
    private void createColourChooserButton() {
        btnColourPicker = new JButton("More Colours");

        btnColourPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chosenColour = JColorChooser.showDialog(null, "Select Colour", chosenColour);
                if (chosenColour == null) {
                    chosenColour = Color.black;
                }

                pnlColourPicker.setBackground(chosenColour);
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
                    chosenColour = button.getBackground();
                    pnlColourPicker.setBackground(chosenColour);
                    notifyObservers();
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

    public void notifyObservers(){
        for (int i = 0; i < observers.size(); i++){
            observers.get(i).update(className);
        }
    }

    /**
     * return current choose colour
     * @return current choosen colour
     */
    public Color getChosenColour(){
        return chosenColour;
    }

}


