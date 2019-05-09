import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.tools.Tool;

public class VecPaint extends JFrame implements ActionListener, ChangeListener {
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
    private JPanel pnlQuickColours;
    private JPanel pnlColourPicker;
    private JPanel pnlBottom;

    //Colour panel variables;
    private JPanel red;
    private JPanel blue;
    private JPanel green;
    private JPanel orange;
    private JPanel yellow;
    private JPanel pink;
    private JPanel cyan;
    private JPanel gray;
    private JPanel black;
    private JPanel magenta;


    //Button variables
    private JButton btnPlot;
    private JButton btnLine;
    private JButton btnRectangle;
    private JButton btnEllipse;
    private JButton btnPolygon;
    private JButton btnColourPicker;

    //Colour chooser variable
    private JColorChooser colourChooser;

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
     * create square colour panel
     */
    private JPanel createSquarePanel(Color c, int size) {
        JPanel newPanel = new JPanel();
        newPanel.setBackground(c);
        newPanel.setPreferredSize(new Dimension(size, size));
        return newPanel;
    }

    /**
     * create button
     */
    private JButton createButton(String str) {
        JButton newButton = new JButton(str);
        newButton.addActionListener( this);
        return newButton;
    }

    /**
     *  create colour chooser
     */
    private JColorChooser createColourChooser() {
        JColorChooser newColourChooser = new JColorChooser();
        newColourChooser.setPreviewPanel(new JPanel());
        newColourChooser.getSelectionModel().addChangeListener(this);
        newColourChooser.setBorder(BorderFactory.createTitledBorder("Colour"));
        return newColourChooser;
    }

    /**
     * add grid layout to the tools panel
     */
    private void layoutToolsPanel() {
        GridLayout layout = new GridLayout(5, 1);
        pnlTools.setLayout(layout);
        pnlTools.setBorder(BorderFactory.createTitledBorder("Tools"));

        pnlTools.add(btnPlot);
        pnlTools.add(btnLine);
        pnlTools.add(btnRectangle);
        pnlTools.add(btnEllipse);
        pnlTools.add(btnPolygon);
    }

    /**
     * add grid layout to the colour panel
     */
    private void layoutColourPanel() {
        pnlColours.setBorder(BorderFactory.createTitledBorder("Colours"));
        pnlColours.add(pnlQuickColours);
        pnlColours.add(pnlColourPicker);
    }

    private void layoutQuickColourPanel() {
        GridLayout layout = new GridLayout(5, 2);
        pnlQuickColours.setLayout(layout);

        red = createSquarePanel(Color.RED, 10);
        blue = createSquarePanel(Color.BLUE, 10);
        green = createSquarePanel(Color.GREEN, 10);
        orange = createSquarePanel(Color.ORANGE, 10);
        yellow = createSquarePanel(Color.YELLOW, 10);
        pink = createSquarePanel(Color.PINK, 10);
        cyan = createSquarePanel(Color.CYAN, 10);
        gray = createSquarePanel(Color.GRAY, 10);
        black = createSquarePanel(Color.BLACK, 10);
        magenta = createSquarePanel(Color.MAGENTA, 10);

        pnlQuickColours.add(red);
        pnlQuickColours.add(blue);
        pnlQuickColours.add(green);
        pnlQuickColours.add(orange);
        pnlQuickColours.add(yellow);
        pnlQuickColours.add(pink);
        pnlQuickColours.add(cyan);
        pnlQuickColours.add(gray);
        pnlQuickColours.add(black);
        pnlQuickColours.add(magenta);
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

        pnlQuickColours = createPanel(Color.WHITE);
        pnlColourPicker = createPanel(Color.PINK);

        getContentPane().add(pnlCanvas, BorderLayout.CENTER);
        getContentPane().add(pnlTools, BorderLayout.WEST);
        getContentPane().add(pnlColours, BorderLayout.EAST);
        getContentPane().add(pnlBottom, BorderLayout.SOUTH);

        pnlTools.setPreferredSize(new Dimension(screenWidth / 16, screenHeight));
        pnlColours.setPreferredSize(new Dimension(screenWidth / 16, screenHeight));
        pnlQuickColours.setPreferredSize(new Dimension(screenWidth / 18, 250));
        pnlColourPicker.setPreferredSize(new Dimension(screenWidth / 18, 100));
        pnlBottom.setPreferredSize(new Dimension(screenWidth, 20));

        // buttons
        btnPlot = createButton("Plot");
        btnLine = createButton("Line");
        btnRectangle = createButton("Rectangle");
        btnEllipse = createButton("Ellipse");
        btnPolygon = createButton("Polygon");

        // colour chooser
        colourChooser = createColourChooser();

        layoutToolsPanel();
        layoutColourPanel();
        layoutQuickColourPanel();

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

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
