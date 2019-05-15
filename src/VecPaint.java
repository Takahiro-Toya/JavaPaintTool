import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.*;

public class VecPaint extends JFrame implements Observer {

    enum Mode{PLOT, LINE, RECTANGLE, ELLIPSE, POLYGON};

    //Menu bar variables
    private int screenWidth;
    private int screenHeight;

    private static String content = "";

    private JMenuBar menuBar = new JMenuBar();

    //Panel variables
    private JPanel pnlCanvas;
    private ToolPanel pnlTools;
    private ColorPanel pnlColours;
    private JPanel pnlBottom;
    private JLayeredPane layer;

    private Color widgetBgColor = Color.LIGHT_GRAY;
    private Color layerBgColor = Color.DARK_GRAY;
    private Color canvasBgColor = Color.WHITE;

    private ArrayList<ShapeInfo> shapes = new ArrayList<>();

    private BufferedImage imagePanel;

    private Mode currentMode = Mode.PLOT;
    private Color lineColour = Color.BLACK;
    private Color fillColour = Color.WHITE;
    private boolean fill = false;
    private float lineWidth = 2f;

    private double sideToolTabArea = 0.2;
    private int bottomHeight = 20;
    private double canvasArea = 0.8;


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
                int edge = keepSquare();
                imagePanel = new BufferedImage((int)(edge * canvasArea), (int)(edge * canvasArea), BufferedImage.TYPE_INT_ARGB);
                layer.removeAll();
                switchMode();
                pnlCanvas.setBounds((int)((layer.getWidth() - edge * canvasArea) / 2), (int)((layer.getHeight() - edge * canvasArea) / 2),
                        (int)(edge * canvasArea), (int)(edge * canvasArea));

                imagePanelResized();
                layer.add(pnlCanvas);

            }
        });

    }

    /**
     * get screen size of the laptop or desktop
     */
    private void getScreenSize(){
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    private int keepSquare(){
        int width = getWidth() - (int)(getWidth() * sideToolTabArea * 2);
        int height = getHeight() - menuBar.getHeight() - bottomHeight;
        int edge;
        // for square canvas
        if (height > width){
            edge = width;
        } else {
            edge = height;
        }

        return edge;
    }

    public void update(){

        currentMode = pnlTools.getCurrentMode();
        lineColour = pnlColours.getLineColour();
        fillColour = pnlColours.getFillColour();
        fill = pnlTools.getFillMode();

        int edge = keepSquare();

        layer.removeAll();
        switchMode();
        pnlCanvas.setBounds((int)((layer.getWidth() - edge * canvasArea) / 2), (int)((layer.getHeight() - edge * canvasArea) / 2),
                (int)(edge * canvasArea), (int)(edge * canvasArea));
        layer.add(pnlCanvas);
    }

    private void switchMode(){

        switch (currentMode) {
            case LINE:
                pnlCanvas = new DrawLine(imagePanel, lineColour, this);
                break;
            case RECTANGLE:
                pnlCanvas = new DrawRect(imagePanel, lineColour, fillColour, fill, this);
                break;
            case ELLIPSE:
                pnlCanvas = new DrawEllip(imagePanel, lineColour, fillColour, fill, this);
                break;
            case POLYGON:
                pnlCanvas = new DrawPoly(imagePanel, lineColour, fillColour, fill, this);
                break;
            default:
                pnlCanvas = new DrawPlot(imagePanel, lineColour, this);
                break;
        }
        pnlCanvas.setBackground(canvasBgColor);
        layer.setOpaque(true);
        setVisible(true);
    }

    public void updateShapes(ShapeInfo shape){
        shapes.add(shape);
    }

    private void imagePanelResized(){
        Graphics2D g2d = imagePanel.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(lineWidth));
        for(int i = 0; i < shapes.size(); i++) {
            ShapeInfo shape = shapes.get(i);

            if (shape.getFill()) {
                g2d.setColor(shape.getFillColour());
                g2d.fill(shape.getShape(imagePanel.getWidth()));
            }
            g2d.setColor(shape.getLineColour());
            g2d.draw(shape.getShape(imagePanel.getWidth()));

        }
        g2d.dispose();
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
        layer = new JLayeredPane();
        layer.setBackground(Color.darkGray);
        int width = getWidth() - (int)(getWidth() * sideToolTabArea * 2);
        int height = getHeight() - menuBar.getHeight() - bottomHeight;
        imagePanel = new BufferedImage((int)(width * canvasArea), (int)(height * canvasArea), BufferedImage.TYPE_INT_ARGB);

        pnlCanvas = new DrawPlot(imagePanel, lineColour, this);
        pnlCanvas.setBackground(canvasBgColor);
        layer.setBackground(layerBgColor);
        layer.setOpaque(true);
        getContentPane().add(layer, BorderLayout.CENTER);

        pnlTools.setPreferredSize(new Dimension((int)(getWidth() * sideToolTabArea), getHeight()));
        pnlTools.attachObservers(this);
        getContentPane().add(pnlTools, BorderLayout.WEST);

        pnlColours.setPreferredSize(new Dimension((int)(getWidth() * sideToolTabArea), getHeight()));
        pnlColours.attachObservers(this);
        getContentPane().add(pnlColours, BorderLayout.EAST);

        pnlBottom.setBackground(widgetBgColor);
        getContentPane().add(pnlBottom, BorderLayout.SOUTH);
        pnlBottom.setPreferredSize(new Dimension(screenWidth, bottomHeight));

        pack();
        repaint();
        setVisible(true);
    }

    public String getContent(){
        return content;
    }

    public void setContent(String str){
        content = str;
    }




    public static void main(String[] args){
        VecPaint vectorTool = new VecPaint();
        vectorTool.getScreenSize();
        vectorTool.createVecGUI();
    }

}
