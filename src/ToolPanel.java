import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToolPanel extends JPanel {

    private String currentMode;

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
                }
            });
            toolBtns.add(btn);
        }
    }


    /**
     * Return current drawing mode
     * @return current drawing mode
     */
    private String getCurrentMode(){
        return currentMode;
    }
}

