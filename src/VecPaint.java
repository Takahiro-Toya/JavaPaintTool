import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;

public class VecPaint extends JFrame implements ActionListener, ChangeListener, Observer {

    //Menu bar variables
    private int screenWidth;
    private int screenHeight;

    private File vecFile;

    private JMenuBar menuBar = new JMenuBar();

    //Panel variables
    private JPanel pnlCanvas;
    private ToolPanel pnlTools;
    private ColorPanel pnlColours;
    private JPanel pnlBottom;

    private Color widgetBgColor = Color.LIGHT_GRAY;
    private Color canvasBgColor = Color.white;

    private BufferedImage imagePanel;

    private String currentMode;
    private Color lineColour = Color.BLACK;
    private Color fillColour = Color.WHITE;

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

    public void update(String className){

        if (className == "ToolPanel"){
            currentMode = pnlTools.getCurrentMode();
            if(currentMode == "Line"){
                getContentPane().remove(pnlCanvas);
                pnlCanvas = new DrawLine(imagePanel, lineColour);
                ((DrawLine)pnlCanvas).setLineColour(lineColour);
            } else if (currentMode == "Plot"){
                getContentPane().remove(pnlCanvas);
                pnlCanvas = new DrawPlot(imagePanel, lineColour);
                ((DrawPlot)pnlCanvas).setLineColour(lineColour);
            } else if (currentMode == "Rectangle") {
                getContentPane().remove(pnlCanvas);
                pnlCanvas = new DrawRect(imagePanel, lineColour, fillColour);
                ((DrawRect)pnlCanvas).setFill(pnlTools.getFillMode());
            } else if (currentMode == "Polygon"){
                getContentPane().remove(pnlCanvas);
                pnlCanvas = new DrawPoly(imagePanel, lineColour, fillColour);
                ((DrawPoly)pnlCanvas).setFill(pnlTools.getFillMode());
            } else if (currentMode == "Ellipse"){
                getContentPane().remove(pnlCanvas);
                pnlCanvas = new DrawEllip(imagePanel, lineColour, fillColour);
                ((DrawEllip)pnlCanvas).setFill(pnlTools.getFillMode());
            } else {
                getContentPane().remove(pnlCanvas);
                pnlCanvas = new DrawLine(imagePanel, lineColour);
            }

        } else if (className == "ColorPanel"){
            lineColour = pnlColours.getLineColour();
            fillColour = pnlColours.getFillColour();
            if(currentMode == "Line"){
                ((DrawLine)pnlCanvas).setLineColour(lineColour);
            } else if (currentMode == "Plot"){
                ((DrawPlot)pnlCanvas).setLineColour(lineColour);
            } else if (currentMode == "Rectangle") {
                ((DrawRect)pnlCanvas).setLineColour(lineColour);
                ((DrawRect)pnlCanvas).setFillColour(fillColour);
                ((DrawRect)pnlCanvas).setFill(pnlTools.getFillMode());
            } else if (currentMode == "Polygon"){
                ((DrawPoly)pnlCanvas).setLineColour(lineColour);
                ((DrawPoly)pnlCanvas).setFillColour(fillColour);
                ((DrawPoly)pnlCanvas).setFill(pnlTools.getFillMode());
            } else if (currentMode == "Ellipse"){
                ((DrawEllip)pnlCanvas).setLineColour(lineColour);
                ((DrawEllip)pnlCanvas).setFillColour(fillColour);
                ((DrawEllip)pnlCanvas).setFill(pnlTools.getFillMode());
            } else {
                ((DrawPlot)pnlCanvas).setLineColour(lineColour);
            }
        }

        canvasChanged();
    }

    private void canvasChanged(){
        pnlCanvas.setBackground(canvasBgColor);
        getContentPane().add(pnlCanvas, BorderLayout.CENTER);
        setVisible(true);
    }



    /**
     * create GUI components
     */
    private void createVecGUI(){
        // create menu bar
        VecFileManager manager = new VecFileManager(null);
        menuBar = manager.createJmenu();
        setJMenuBar(menuBar);

        pnlTools = new ToolPanel();
        pnlColours = new ColorPanel();
        pnlBottom = new JPanel();
        pnlBottom.setBackground(widgetBgColor);

        imagePanel = new BufferedImage((int)(getHeight() * 0.9), (int)(getHeight() * 0.9), BufferedImage.TYPE_INT_ARGB);
        pnlCanvas = new DrawPlot(imagePanel, lineColour);
        pnlCanvas.setPreferredSize(new Dimension((int)(getHeight() * 0.9), (int)(getHeight() * 0.9)));
        pnlCanvas.setBackground(canvasBgColor);
        getContentPane().add(pnlCanvas, BorderLayout.CENTER);

        pnlTools.setPreferredSize(new Dimension((int)(getWidth() * 0.2), getHeight()));
        pnlTools.attachObservers(this);
        getContentPane().add(pnlTools, BorderLayout.WEST);

        pnlColours.setPreferredSize(new Dimension((int)(getWidth() * 0.2), getHeight()));
        pnlColours.attachObservers(this);
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
