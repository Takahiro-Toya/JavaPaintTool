import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToolPanel extends JPanel implements Subject {

    private String className = "ToolPanel";

    private VecPaint.Mode currentMode = VecPaint.Mode.PLOT;

    private boolean fill = false;

    private ArrayList<Observer> observers = new ArrayList<>();

    private ArrayList<JButton> toolBtns =  new ArrayList<>();

    private String[] toolBtnTexts = {"Plot", "Line", "Rectangle", "Ellipse", "Polygon"};

    private JButton btnFill = new JButton("Fill on");

    /**
     * Constructor
     */
    public ToolPanel(){
        setBorder(BorderFactory.createTitledBorder("Tools"));
        setLayout(new GridLayout(toolBtnTexts.length + 1, 1));
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
        for (int i = 0; i < toolBtnTexts.length; i++){
            JButton btn = new JButton(toolBtnTexts[i]);
            btn.setOpaque(true);
            btn.setBackground(Color.lightGray);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedBtn = (JButton)e.getSource();
                    switch (clickedBtn.getText()){
                        case "Plot":
                            currentMode = VecPaint.Mode.PLOT;
                            break;
                        case "Line":
                            currentMode = VecPaint.Mode.LINE;
                            break;
                        case "Rectangle":
                            currentMode = VecPaint.Mode.RECTANGLE;

                            break;
                        case "Ellipse":
                            currentMode = VecPaint.Mode.ELLIPSE;
                            break;
                        case "Polygon":
                            currentMode = VecPaint.Mode.POLYGON;
                            break;
                        default:
                            currentMode = VecPaint.Mode.PLOT;
                    }
                    notifyObservers();
                }
            });
            toolBtns.add(btn);
        }
    }

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
                notifyObservers();
            }
        });
    }

    public void attachObservers(Observer observer){
        observers.add(observer);
    }

    public void notifyObservers(){
        for (int i = 0; i < observers.size(); i++){
            observers.get(i).update();
        }
    }

    /**
     * Return current drawing mode
     * @return current drawing mode
     */
    public VecPaint.Mode getCurrentMode(){
        return currentMode;
    }

    public boolean getFillMode(){return fill;}
}

