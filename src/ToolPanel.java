import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToolPanel extends JPanel implements Subject {

    private String className = "ToolPanel"
;
    private String currentMode;

    private ArrayList<Observer> observers = new ArrayList<>();

    private ArrayList<JButton> toolBtns =  new ArrayList<>();

    private String[] toolBtnTexts = {"Plot", "Line", "Rectangle", "Ellipse", "Polygon"};

    /**
     * Constructor
     */
    public ToolPanel(){
        setBorder(BorderFactory.createTitledBorder("Tools"));
        setLayout(new GridLayout(5, 1));
        setBackground(Color.LIGHT_GRAY);
        createToolBtns();
        for (int i = 0; i < toolBtns.size(); i++){
            add(toolBtns.get(i));
        }
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
                    currentMode = clickedBtn.getText();
                    notifyObservers();
                }
            });
            toolBtns.add(btn);
        }
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
     * Return current drawing mode
     * @return current drawing mode
     */
    public String getCurrentMode(){
        return currentMode;
    }
}

