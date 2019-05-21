import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Defines tool picker GUI components
 */
public class ToolPanel extends JPanel implements Subject {

    private String[] toolBtnTexts = {"Plot", "Line", "Rectangle", "Ellipse", "Polygon"};

    private VecShape.Mode currentMode = VecShape.Mode.PLOT;

    private boolean fill = false;

    private ArrayList<Observer> observers = new ArrayList<>();
    private ArrayList<JButton> toolBtns =  new ArrayList<>();

    private JButton btnFill = new JButton("Fill on");

    /**
     * Constructor
     */
    public ToolPanel(){
        setBorder(BorderFactory.createTitledBorder("Tools"));
        setLayout(new GridLayout(VecShape.Mode.values().length + 1, 1));
        setBackground(Color.LIGHT_GRAY);
        createToolBtns();
        for (int i = 0; i < toolBtns.size(); i++){
            add(toolBtns.get(i));
        }
        createFillButton();
        add(btnFill);
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
}

