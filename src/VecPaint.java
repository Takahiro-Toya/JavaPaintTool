import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.tools.Tool;

public class VecPaint extends JFrame{
    private int screenWidth;
    private int screenHeight;

    //Menu bar variables
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem fileMenuItemSave = new JMenuItem("Save");
    private JMenuItem fileMenuItemNew = new JMenuItem("New");
    private JMenuItem fileMenuItemOpen = new JMenuItem("Open");

    //Panel variables
    private JPanel pnlCanvas;
    private JPanel pnlTools;
    private JPanel pnlColours;
    private JPanel pnlBottom;

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
     * create panel
     */
    private JPanel createPanel(Color c) {
        JPanel newPanel = new JPanel();
        newPanel.setBackground(c);
        return newPanel;
    }

    /**
     * create GUI components
     */
    private void createVecGUI(){
        setLayout(new BorderLayout());

        // menu bar items
        fileMenu.add(fileMenuItemSave);
        fileMenu.add(fileMenuItemNew);
        fileMenu.add(fileMenuItemOpen);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // panels
        pnlCanvas = createPanel(Color.WHITE);
        pnlTools = createPanel(Color.LIGHT_GRAY);
        pnlColours = createPanel(Color.LIGHT_GRAY);
        pnlBottom = createPanel(Color.LIGHT_GRAY);

        getContentPane().add(pnlCanvas, BorderLayout.CENTER);
        getContentPane().add(pnlTools, BorderLayout.WEST);
        getContentPane().add(pnlColours, BorderLayout.EAST);
        getContentPane().add(pnlBottom, BorderLayout.SOUTH);

        pnlTools.setPreferredSize(new Dimension(screenWidth / 24, screenHeight));
        pnlColours.setPreferredSize(new Dimension(screenWidth / 24, screenHeight));
        pnlBottom.setPreferredSize(new Dimension(screenWidth, screenHeight / 26));

        // pack up
        setPreferredSize(new Dimension(screenWidth / 2, screenHeight / 2));

        pack();
        repaint();
        setVisible(true);
    }

    public static void main(String[] args){
        VecPaint vectorTool = new VecPaint();
        vectorTool.getScreenSize();
        vectorTool.createVecGUI();
    }

}
