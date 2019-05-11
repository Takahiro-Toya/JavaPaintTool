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
    public enum MenuNames{
         File("File"), Undo("Undo"), Save("Save"), New("New"), Open("Open");
         private final String name;
         private MenuNames(String s){
            name = s;
         }
    }

    private int screenWidth;
    private int screenHeight;

    private File vecFile;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu(MenuNames.File.name);
    private JMenu undo = new JMenu(MenuNames.Undo.name);
    private JMenuItem fileMenuItemSave = new JMenuItem(MenuNames.Save.name);
    private JMenuItem fileMenuItemNew = new JMenuItem(MenuNames.New.name);
    private JMenuItem fileMenuItemOpen = new JMenuItem(MenuNames.Open.name);

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
     * add actions to file menu
     */
    private void addFileMenuFunc(){
        fileMenuItemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
                    vecFile = chooser.getSelectedFile();

                }
            }
        });

        fileMenuItemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
                    vecFile = chooser.getSelectedFile();
                }
            }
        });

        fileMenuItemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    /**
     * create GUI components
     */
    private void createVecGUI(){

        // menu bar items
        fileMenu.add(fileMenuItemSave);
        fileMenu.add(fileMenuItemNew);
        fileMenu.add(fileMenuItemOpen);
        addFileMenuFunc();
        menuBar.add(fileMenu);
        menuBar.add(undo);
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
