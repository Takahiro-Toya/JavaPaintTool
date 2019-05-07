import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;

public class VecPaint extends JFrame{

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem fileMenuItemSave = new JMenuItem("Save");
    private JMenuItem fileMenuItemNew = new JMenuItem("New");
    private JMenuItem fileMenuItemOpen = new JMenuItem("Open");

    private int screenWidth;
    private int screenHeight;

    private VecPaint(){
        super("VecPaint tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * get screen size of the laptop or desktop
     */
    private void getScreenSize(){
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    /**
     * create GUI components
     */
    private void createVecGUI(){

        // menu bar items
        fileMenu.add(fileMenuItemSave);
        fileMenu.add(fileMenuItemNew);
        fileMenu.add(fileMenuItemOpen);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // dummy panel
        JPanel panel = new JPanel();
        super.getContentPane().add(panel);

        // pack up
        super.setPreferredSize(new Dimension(screenWidth / 2, screenHeight / 2));
        super.pack();
        super.setVisible(true);
    }



    public static void main(String[] args){
        VecPaint vectorTool = new VecPaint();
        vectorTool.getScreenSize();
        vectorTool.createVecGUI();

    }

}
