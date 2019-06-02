package DrawVecShape;

import VecShape.VecPolygon;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Class to draw a line
 * This class provides JPanel to display a temporary line when user is dragging mouse
 * and when the user finished drawing (released mouse) the line is drawn on the Buffered Image
 */
public class DrawPoly extends DrawShape {

    private double sx;
    private double sy;
    private double tpx;
    private double tpy;
    private double ex;
    private double ey;
    private int edges = 0;

    private ArrayList<Double> xVertices = new ArrayList<>();
    private ArrayList<Double> yVertices = new ArrayList<>();

    private boolean drawTempLine = false;
    private boolean fill;
    private Color fillColour;


    /**
     * Constructor
     * @param imagePanel BufferedImage -on which the image is drawn
     * @param lineWidth line width
     * @param penColour Color -colour of line
     * @param fillColour Color -fill colour
     * @param fill boolean -true if image needs to be filled
     * @param grid - set true if grid is on
     * @param gridSize - grid size that divides the canvas: e.g gridSize = 2 means the grid divides canvas into two horizontally and vertically
     * @param canvasObserver VecInterface.Observer -class that wants to receive a drawn object information.
     *                    Usually, a class that has a canvas to draw this object (polygon)
     */
    public DrawPoly (BufferedImage imagePanel, float lineWidth, Color penColour, Color fillColour, boolean fill, boolean grid, int gridSize, VecCanvas canvasObserver){
        super(imagePanel, lineWidth, penColour, canvasObserver, grid, gridSize);
        PolyMouseListener mouse = new PolyMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.fillColour = fillColour;
        this.fill = fill;
    }

    /**
     * draw polygon components on image panel
     * This method is used to draw polygon's edges on image panel.
     * @param x1 -start x coordinate of an edge
     * @param y1 -start y coordinate of an edge
     * @param x2 -end x coordinate of an edge
     * @param y2 -end x coordinate of an edge
     */
    private void drawOnImagePanel(double x1, double y1, double x2, double y2){
        Graphics2D g2 = getImagePanel().createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getLineColour());
        g2.setStroke(new BasicStroke(getLineWidth()));
        g2.draw(new Line2D.Double(x1, y1, x2, y2));
        g2.dispose();
    }


    /**
     * Override paintComponent so that when user made changes on the application window,
     * i.e. minimise/ maximise the screen, the drawn images are not discarded
     * This function is automatically called.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(getImagePanel(), 0, 0, this);
        if (drawTempLine) {
            g2d.setColor(getLineColour());
            g2d.drawLine((int)tpx, (int)tpy, (int)ex, (int)ey);
        }
        g2d.dispose();
    }


    /**
     * private class that defines behaviour when mouse movement is made
     */
    private class PolyMouseListener extends MouseAdapter {
        /**
         * When mouse is pressed, start drawing a rectangle, so set the start location
         */
        @Override
        public void mousePressed(MouseEvent e) {
            // first edge
            if (edges == 0) {
                double x = e.getPoint().getX();
                double y = e.getPoint().getY();
                if (!getIsGridOn()){
                    sx = x;
                    sy = y;
                } else {
                    sx = adjustPoint(x);
                    sy = adjustPoint(y);
                }
                xVertices.add(sx);
                yVertices.add(sy);
                tpx = sx;
                tpy = sy;
                edges++;
            // after second edge
            } else {
                if(e.getClickCount() != 2) { // avoid producing two points (another produced by mouseClicked())
                    double x = e.getPoint().getX();
                    double y = e.getPoint().getY();
                    if (!getIsGridOn()){
                        ex = x;
                        ey = y;
                    } else {
                        ex = adjustPoint(x);
                        ey = adjustPoint(y);
                    }
                    xVertices.add(ex);
                    yVertices.add(ey);
                    drawOnImagePanel(tpx, tpy, ex, ey);
                    drawTempLine = false;
                    repaint();
                    tpx = ex;
                    tpy = ey;
                    edges++;
                }

            }

        }

        /**
         * When mouse is being moved (not dragged!), a temporary line is drawn
         * between previously clicked location to current mouse location
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            if (!(edges == 0)) {
                ex = e.getPoint().getX();
                ey = e.getPoint().getY();
                drawTempLine = true;
                repaint();
            }
        }

        /**
         * When mouse click is released, a line is drawn on the BufferedImage
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            drawTempLine = false;
            repaint();
        }


        /**
         * Double clicking finishes drawing polygon, and connects end to start point
         * send a polygon object to draw on image panel
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                // convert ArrayList to Array
                double[] xScaled = new double[xVertices.size()];
                double[] yScaled = new double[yVertices.size()];
                for(int i = 0; i < xVertices.size(); i++){
                    xScaled[i] = xVertices.get(i) / getImagePanel().getWidth();
                    yScaled[i] = yVertices.get(i) / getImagePanel().getHeight();
                }
                VecPolygon polygon = new VecPolygon(xScaled, yScaled, getLineColour(), fillColour, fill);
                paintUpdated(polygon);
                drawTempLine = false;
                edges = 0;
            }
        }

        /**
         * When mouse is being dragged, continuously updates tpx, tpy and ex, ey.
         * So, it produces like a free shape. This mode is only enabled while grid is not visible
         * This is still a polygon : A polygon with lots of very short straight edges
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            if (!getIsGridOn()){
                ex = e.getPoint().getX();
                ey = e.getPoint().getY();
                xVertices.add(ex);
                yVertices.add(ey);
                drawOnImagePanel(tpx, tpy, ex, ey);
                repaint();
                tpx = ex;
                tpy = ey;
            }
        }

        // those methods are not used, just need to implements here
        public void mouseEntered(MouseEvent evt) {
        }

        public void mouseExited(MouseEvent evt) {
        }
    }
}
