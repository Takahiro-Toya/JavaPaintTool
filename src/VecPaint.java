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

    private Color widgetBgColor = Color.LIGHT_GRAY;
    private Color canvasBgColor = Color.white;

    private ArrayList<ShapeInfo> shapes = new ArrayList<>();

    private BufferedImage imagePanel;

    private Mode currentMode = Mode.PLOT;
    private Color lineColour = Color.BLACK;
    private Color fillColour = Color.WHITE;
    private boolean fill = false;
    private float lineWidth = 2f;


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
                // for square canvas
                if (pnlCanvas.getHeight() > pnlCanvas.getWidth()){
                    imagePanel = new BufferedImage((int)(getWidth() * 0.6), (int)(getWidth() * 0.6), BufferedImage.TYPE_INT_ARGB);
                } else {
                imagePanel = new BufferedImage((int)(getHeight() * 0.9), (int)(getHeight() * 0.9), BufferedImage.TYPE_INT_ARGB);
                }
                getContentPane().remove(pnlCanvas);
                imagePanelResized();
                switchMode();
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

    public void update(){

        currentMode = pnlTools.getCurrentMode();
        lineColour = pnlColours.getLineColour();
        fillColour = pnlColours.getFillColour();
        fill = pnlTools.getFillMode();
        getContentPane().remove(pnlCanvas);

        switchMode();
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
        getContentPane().add(pnlCanvas, BorderLayout.CENTER);
        setVisible(true);
    }

    public void updateShapes(ShapeInfo shape){
        shapes.add(shape);
    }

    public void imagePanelResized(){
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

        imagePanel = new BufferedImage((int)(getHeight() * 0.9), (int)(getHeight() * 0.9), BufferedImage.TYPE_INT_ARGB);
        pnlCanvas = new DrawPlot(imagePanel, lineColour, this);
        pnlCanvas.setPreferredSize(new Dimension((int)(getHeight() * 0.9), (int)(getHeight() * 0.9)));
        pnlCanvas.setBackground(canvasBgColor);
        getContentPane().add(pnlCanvas, BorderLayout.CENTER);

        pnlTools.setPreferredSize(new Dimension((int)(getWidth() * 0.2), getHeight()));
        pnlTools.attachObservers(this);
        getContentPane().add(pnlTools, BorderLayout.WEST);

        pnlColours.setPreferredSize(new Dimension((int)(getWidth() * 0.2), getHeight()));
        pnlColours.attachObservers(this);
        getContentPane().add(pnlColours, BorderLayout.EAST);

        pnlBottom.setBackground(widgetBgColor);
        getContentPane().add(pnlBottom, BorderLayout.SOUTH);
        pnlBottom.setPreferredSize(new Dimension(screenWidth, 20));

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
