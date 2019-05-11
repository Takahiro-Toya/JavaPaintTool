import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;

public class VecPaint extends JFrame implements ActionListener, ChangeListener {

    //Menu bar variables
    private int screenWidth;
    private int screenHeight;

    private File vecFile;

    private JMenuBar menuBar = new JMenuBar();

    //Panel variables
    private JPanel pnlCanvas;
    private JPanel pnlTools;
    private JPanel pnlColours;
    private JPanel pnlBottom;

    private BufferedImage imagePanel;

    private Color chosenColour;

    private VecPaint(){
        super("VecPaint tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getScreenSize();
        setBounds((screenWidth - screenWidth / 2) / 2, (screenHeight - screenHeight / 2) / 2, screenWidth / 2, screenHeight /2);
        setPreferredSize(new Dimension(screenWidth / 2, screenHeight / 2));
        setLayout(new BorderLayout());
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
// create menu bar
        VecFileManager manager = new VecFileManager(null);
        menuBar = manager.createJmenu();
        setJMenuBar(menuBar);

//        pnlCanvas = createPanel(Color.WHITE);
        pnlTools = new ToolPanel();
        pnlColours = new ColorPanel();
        pnlBottom = createPanel(Color.LIGHT_GRAY);

        imagePanel = new BufferedImage((int)(getHeight() * 0.9), (int)(getHeight() * 0.9), BufferedImage.TYPE_INT_ARGB);
//        pnlCanvas = new DrawLine(imagePanel);
//        pnlCanvas = new DrawEllip(imagePanel);
//        pnlCanvas = new DrawPoly(imagePanel);
        pnlCanvas = new DrawRect(imagePanel);
//        pnlCanvas = new DrawPlot(imagePanel);
//        pnlCanvas.setPreferredSize(new Dimension((int)(getHeight() * 0.9), (int)(getHeight() * 0.9)));

        getContentPane().add(pnlCanvas, BorderLayout.CENTER);

        pnlTools.setPreferredSize(new Dimension((int)(getWidth() * 0.18), getHeight()));
        getContentPane().add(pnlTools, BorderLayout.WEST);

        pnlColours.setPreferredSize(new Dimension((int)(getWidth() * 0.18), getHeight()));
        getContentPane().add(pnlColours, BorderLayout.EAST);

        getContentPane().add(pnlBottom, BorderLayout.SOUTH);
        pnlBottom.setPreferredSize(new Dimension(screenWidth, 20));

        pack();
        repaint();
        setVisible(true);
    }




    public static void main(String[] args){
        VecPaint vectorTool = new VecPaint();
        vectorTool.getScreenSize();
        vectorTool.createVecGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
