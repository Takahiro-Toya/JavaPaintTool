import DrawVecShape.*;
import DrawVecShape.VecCanvas;
import VecInterface.Observer;
import VecShape.VecShape;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Vec Paint software's main class
 * This class is basically a canvas that draws a drawn or opened image
 */
public class VecPaint extends JFrame implements Observer, VecCanvas {

    //Menu bar variables
    private int screenWidth;
    private int screenHeight;

    private VecFileManager manager;
    //Panel variables
    private JLayeredPane layer;
    private JPanel pnlCanvas;
    private ToolPanel pnlTools;
    private ColorPanel pnlColours;
    private JPanel pnlBottom;
    private HelpPanel helpLabel;

    // GUI colours: DEFAULT setting
    private Color widgetBgColor = Color.LIGHT_GRAY;
    private Color layerBgColor = Color.DARK_GRAY;
    private Color canvasBgColor = Color.WHITE;

    // store all shapes drawn on the image panel
    private ArrayList<VecShape> shapes = new ArrayList<>();
    private ArrayList<VecShape> undoneShapes = new ArrayList<>();

    // image panel that is responsible to show any drawn objects at all time, and updated
    private BufferedImage imagePanel;

    // default painting colour/ fill mode
    private VecShape.Mode currentMode = VecShape.Mode.PLOT;
    private Color lineColour = Color.BLACK;
    private Color fillColour = Color.WHITE;
    private boolean fill = false;
    private float lineWidth = 2f;

    // Define GUI properties' area
    private double sideToolTabArea = 0.2;
    private int bottomHeight = 20;
    private double canvasArea = 0.8;


    /**
     * Constructor
     * application size is set to half of screen width x half of screen height, and displayed at the center of screen
     * This is minimum size, user cannot make it smaller than this size
     */
    public VecPaint(){
        super("VecPaint tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getScreenSize();
        setBounds((screenWidth - screenWidth / 2) / 2, (screenHeight - screenHeight / 2) / 2, screenWidth / 2, screenHeight /2);
        setPreferredSize(new Dimension(screenWidth / 2, screenHeight / 2));
        setMinimumSize(new Dimension(screenWidth / 2, screenHeight / 2));
        setLayout(new BorderLayout());
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                    refreshCanvas();
            }
        });
    }

    public BufferedImage getImagePanel(){
        return imagePanel;
    }

    /**
     * get screen size of the laptop or desktop
     */
    private void getScreenSize(){
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    /**
     * Vec paint canvas is always square, this method determines canvas edge based on application width and height
     * @return edge that can be used as one edge of canvas
     */
    private int keepSquare(){
        int width = getWidth() - (int)(getWidth() * sideToolTabArea * 2);
        int height = getHeight() - manager.getHeight() - bottomHeight;
        int edge;
        // for square canvas
        if (height > width){
            edge = width;
        } else {
            edge = height;
        }

        return edge;
    }

    /**
     * This method reflects any changes on canvas, such as:
     * drawing mode change, undo/ clear button click, resized application size
     * so that drawn image is always visible
     */
    private void refreshCanvas(){
        int edge = keepSquare();
        imagePanel = new BufferedImage((int)(edge * canvasArea), (int)(edge * canvasArea), BufferedImage.TYPE_INT_ARGB);
        layer.removeAll();
        switchMode();
        pnlCanvas.setBounds((int)((layer.getWidth() - edge * canvasArea) / 2), (int)((layer.getHeight() - edge * canvasArea) / 2),
                (int)(edge * canvasArea), (int)(edge * canvasArea));
        refreshImage();
        layer.add(pnlCanvas);
    }

    /**
     * Deletes last element of shape array if shapes array size is greater than zero
     * Add deleted shape to the undone shapes list to perform redo
     */
    private void undo(){
        if (shapes.size() > 0) {
            undoneShapes.add(shapes.get(shapes.size() - 1));
            shapes.remove(shapes.size() - 1);
        }
    }

    /**
     * Deletes last element of undonShapes array if undoneShapes array size is greater than zero
     * Add deleted shape to the shapes list
     */
    private void redo(){
        if (undoneShapes.size() > 0){
            shapes.add(undoneShapes.get(undoneShapes.size() - 1));
            undoneShapes.remove(undoneShapes.size() - 1);
        }
    }

    /**
     * One of VecInterface.Observer interface components
     * reflects changes of drawing mode, colour, undo/ clear, save / open
     * @param location -class or component name in which a change is made
     */
    @Override
    public void update(String location){
        if (location == "ToolPanel" || location == "ColourPanel") {
            currentMode = pnlTools.getCurrentMode();
            lineColour = pnlColours.getPenColour();
            fillColour = pnlColours.getFillColour();
            fill = pnlTools.getFillMode();
            refreshCanvas();
        } else if (location == "UndoBtn") {
            undo();
            refreshCanvas();
        } else if (location == "RedoBtn"){
            redo();
            refreshCanvas();
        } else if (location == "ClearBtn"){
            shapes.clear();
            refreshCanvas();
        } else if (location == "SaveBtn"){
            manager.saveShape(shapes);
        } else if (location == "OpenBtn"){
            shapes = manager.getOpenedShapes();
            refreshCanvas();
        } else if (location == "GridEnabler"){
            refreshCanvas();
        } else if (location == "GridTop"){
            refreshCanvas();
        }else if (location == "Export"){
            //
        }
    }

    /**
     * Used when drawing mode has changed
     */
    private void switchMode() {
        if (currentMode == VecShape.Mode.PLOT){
            pnlCanvas = new DrawPlot(imagePanel, lineColour, pnlTools.getGridMode(), pnlTools.getGridSize(), this);
        } else if (currentMode == VecShape.Mode.LINE){
            pnlCanvas = new DrawLine(imagePanel, lineColour, pnlTools.getGridMode(), pnlTools.getGridSize(),this);
        } else if (currentMode == VecShape.Mode.RECTANGLE){
            pnlCanvas = new DrawRect(imagePanel, lineColour, fillColour, fill, pnlTools.getGridMode(), pnlTools.getGridSize(),this);
        } else if (currentMode == VecShape.Mode.ELLIPSE){
            pnlCanvas = new DrawEllip(imagePanel, lineColour, fillColour, fill, pnlTools.getGridMode(), pnlTools.getGridSize(),this);
        } else if (currentMode == VecShape.Mode.POLYGON){
            pnlCanvas = new DrawPoly(imagePanel, lineColour, fillColour, fill, pnlTools.getGridMode(), pnlTools.getGridSize(),this);
        }
        helpLabel.changeText(currentMode);
        pnlCanvas.setBackground(canvasBgColor);
        layer.setOpaque(true);
        setVisible(true);
    }

    /**
     * update shapes array after new drawing object is drawn
     * @param shape -new shape to be drawn
     */
    @Override
    public void updateShapes(VecShape shape){
        shapes.add(shape);
        undoneShapes.clear();
        refreshCanvas();
    }

    /**
     * draw grid based on the grid size that user specifies
     * @param g2d graphic object of canvas on which the image is drawn
     */
    private void drawGrid(Graphics2D g2d){
        double interval = (double)imagePanel.getHeight() / (double)pnlTools.getGridSize();
        g2d.setStroke(new BasicStroke(0.5f));
        g2d.setColor(Color.lightGray);
        for (int i = 1; i < pnlTools.getGridSize(); i++ ){
            g2d.draw(new Line2D.Double(0, interval * i, imagePanel.getWidth(), interval * i));
        }
        for (int i = 1; i < pnlTools.getGridSize(); i++ ) {
            g2d.draw(new Line2D.Double(interval * i, 0, interval * i, imagePanel.getHeight()));
        }
    }

    /**
     * draw all the stored vec shape on canvas
     * @param g2d graphic object of canvas on which the image is drawn
     */
    private void drawShapes(Graphics2D g2d){
        g2d.setStroke(new BasicStroke(lineWidth));

        for(int i = 0; i < shapes.size(); i++) {
            VecShape shape = shapes.get(i);
            if (shape.getFill()) {
                g2d.setColor(shape.getFillColour());
                g2d.fill(shape.getShape(imagePanel.getWidth()));
            }
            g2d.setColor(shape.getLineColour());
            g2d.draw(shape.getShape(imagePanel.getWidth()));
        }
    }
    /**
     * clear image on imagePanel, and draw them again
     */
    private void refreshImage(){
        Graphics2D g2d = imagePanel.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (pnlTools.getGridMode() && !pnlTools.getIsGridTop()){
            drawGrid(g2d);
            drawShapes(g2d);
        } else if (pnlTools.getGridMode() && pnlTools.getIsGridTop()){
            drawShapes(g2d);
            drawGrid(g2d);
        } else {
            drawShapes(g2d);
        }
        g2d.dispose();
    }


    /**
     * create GUI components
     */
    private void createVecGUI(){
        // create menu bar
        manager = new VecFileManager();
        JMenuBar bar = manager.createJmenuBar();
        setJMenuBar(bar);
        pnlTools = new ToolPanel();
        pnlColours = new ColorPanel();
        pnlBottom = new JPanel();
        helpLabel = new HelpPanel();
        helpLabel.changeText(currentMode);
        pnlBottom.add(helpLabel);
        pnlBottom.add(helpLabel);

        manager.attachObserver(this);

        layer = new JLayeredPane();
        layer.setBackground(Color.darkGray);
        int width = getWidth() - (int)(getWidth() * sideToolTabArea * 2);
        int height = getHeight() - manager.getHeight() - bottomHeight;
        imagePanel = new BufferedImage((int)(width * canvasArea), (int)(height * canvasArea), BufferedImage.TYPE_INT_ARGB);



        pnlCanvas = new DrawPlot(imagePanel, lineColour, false, 0, this);
        pnlCanvas.setBackground(canvasBgColor);
        layer.setBackground(layerBgColor);
        layer.setOpaque(true);
        getContentPane().add(layer, BorderLayout.CENTER);

        pnlTools.setPreferredSize(new Dimension((int)(getWidth() * sideToolTabArea), getHeight()));
        pnlTools.attachObserver(this);
        getContentPane().add(pnlTools, BorderLayout.WEST);

        pnlColours.setPreferredSize(new Dimension((int)(getWidth() * sideToolTabArea), getHeight()));
        pnlColours.attachObserver(this);
        getContentPane().add(pnlColours, BorderLayout.EAST);

        pnlBottom.setBackground(widgetBgColor);
        getContentPane().add(pnlBottom, BorderLayout.SOUTH);
        pnlBottom.setPreferredSize(new Dimension(screenWidth, bottomHeight));

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
