import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VecPaint extends JFrame implements ActionListener, ChangeListener {
    private int screenWidth;
    private int screenHeight;

    private int prevX, prevY;
    private Graphics drawingArea;
    private boolean mouseIsDragging;
    //Menu bar variables
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenu undo = new JMenu("Undo");
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

    //initialise quick colour select buttons;
    private JButton btnRed = new JButton();
    private JButton btnBlue = new JButton();
    private JButton btnGreen = new JButton();
    private JButton btnOrange = new JButton();
    private JButton btnYellow = new JButton();
    private JButton btnPink = new JButton();
    private JButton btnCyan = new JButton();
    private JButton btnGray = new JButton();
    private JButton btnBlack = new JButton();
    private JButton btnMagenta = new JButton();

    //Button variables
    private JButton btnPlot;
    private JButton btnLine;
    private JButton btnRectangle;
    private JButton btnEllipse;
    private JButton btnPolygon;
    private JButton btnColourPicker;
    private JButton btnClear;

    private Color chosenColour;

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
     * create button
     */
    private JButton createToolButton(String str) {
        JButton newButton = new JButton(str);
        newButton.addActionListener( this);
        return newButton;
    }

    /**
     * create colour chooser button
     */
    private JButton createColourChooserButton() {
        JButton newButton = new JButton("More Colours");

        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chosenColour = JColorChooser.showDialog(null, "Select Colour", chosenColour);
                if (chosenColour == null) {
                    chosenColour = Color.WHITE;
                }

                pnlCanvas.setBackground(chosenColour);
            }
        });
        return newButton;
    }

    /**
     * create clear button
     */
    private JButton createClearButton() {
        JButton newButton = new JButton("Clear");

        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pnlCanvas.setBackground(Color.WHITE);
            }
        });
        return newButton;
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

    /**
     * add grid layout to the quick colour panel
     */
    private void layoutQuickColourPanel() {
        GridLayout layout = new GridLayout(5, 2);
        pnlQuickColours.setLayout(layout);
        btnRed.setBackground(Color.red);
        btnBlue.setBackground(Color.blue);
        btnGreen.setBackground(Color.green);
        btnOrange.setBackground(Color.orange);
        btnYellow.setBackground(Color.yellow);
        btnPink.setBackground(Color.pink);
        btnCyan.setBackground(Color.cyan);
        btnGray.setBackground(Color.gray);
        btnBlack.setBackground(Color.black);
        btnMagenta.setBackground(Color.magenta);

        pnlQuickColours.add(btnRed);
        pnlQuickColours.add(btnBlue);
        pnlQuickColours.add(btnGreen);
        pnlQuickColours.add(btnOrange);
        pnlQuickColours.add(btnYellow);
        pnlQuickColours.add(btnPink);
        pnlQuickColours.add(btnCyan);
        pnlQuickColours.add(btnGray);
        pnlQuickColours.add(btnBlack);
        pnlQuickColours.add(btnMagenta);

        ActionListener colorListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 JButton button = (JButton)e.getSource();
                 chosenColour = button.getBackground();
            }
        };

        btnRed.setOpaque(true);btnRed.setBorderPainted(false);
        btnBlue.setOpaque(true);btnBlue.setBorderPainted(false);
        btnGreen.setOpaque(true);btnGreen.setBorderPainted(false);
        btnOrange.setOpaque(true);btnOrange.setBorderPainted(false);
        btnYellow.setOpaque(true);btnYellow.setBorderPainted(false);
        btnPink.setOpaque(true);btnPink.setBorderPainted(false);
        btnCyan.setOpaque(true);btnCyan.setBorderPainted(false);
        btnGray.setOpaque(true);btnGray.setBorderPainted(false);
        btnBlack.setOpaque(true);btnBlack.setBorderPainted(false);
        btnMagenta.setOpaque(true);btnMagenta.setBorderPainted(false);

        btnRed.addActionListener(colorListener);
        btnBlue.addActionListener(colorListener);
        btnGreen.addActionListener(colorListener);
        btnOrange.addActionListener(colorListener);
        btnYellow.addActionListener(colorListener);
        btnPink.addActionListener(colorListener);
        btnCyan.addActionListener(colorListener);
        btnGray.addActionListener(colorListener);
        btnBlack.addActionListener(colorListener);
        btnMagenta.addActionListener(colorListener);


    }

    /**
     * add flow layout to the colour picker panel
     */
    private void layoutColourPickerPanel() {
        pnlColourPicker.add(btnColourPicker);
        pnlColourPicker.add(btnClear);
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
        menuBar.add(undo);
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
        pnlColours.setPreferredSize(new Dimension(screenWidth / 14, screenHeight));
        pnlQuickColours.setPreferredSize(new Dimension(screenWidth / 16, 250));
        pnlColourPicker.setPreferredSize(new Dimension(screenWidth / 16, 100));
        pnlBottom.setPreferredSize(new Dimension(screenWidth, 20));

        // buttons
        btnPlot = createToolButton("Plot");
        btnLine = createToolButton("Line");
        btnRectangle = createToolButton("Rectangle");
        btnEllipse = createToolButton("Ellipse");
        btnPolygon = createToolButton("Polygon");

        // clear button
        btnClear = createClearButton();

        // colour chooser button
        btnColourPicker = createColourChooserButton();

        layoutToolsPanel();
        layoutColourPanel();
        layoutQuickColourPanel();
        layoutColourPickerPanel();

        // pack up
        setPreferredSize(new Dimension(screenWidth / 2, screenHeight / 2));

        pack();
        repaint();
        setVisible(true);
    }

    private void setDrawingArea(){
        drawingArea = getGraphics();
    }

    public void mousePressed(MouseEvent evt) {
        int mouseX = evt.getX();
        int mouseY = evt.getY();
        if (mouseIsDragging){
            return;
        }

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
