import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Class to draw a line
 * This class provides JPanel to draw a temporary line,
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
     * constructor
     * @param imagePanel to display drawn image
     */
    public DrawPoly (BufferedImage imagePanel, Color lineColour, Color fillColour, boolean fill, Observer o){
        super(imagePanel, lineColour, o);
        PolyMouseListener mouse = new PolyMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.fillColour = fillColour;
        this.fill = fill;
    }

    /**
     * Draw image on the image Panel
     */
    private void drawOnImagePanel(double x1, double y1, double x2, double y2){
        Graphics2D g2 = getImagePanel().createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getLineColour());
        g2.setStroke(new BasicStroke(getLineWidth()));
        g2.draw(new Line2D.Double(x1, y1, x2, y2));
        g2.dispose();
    }

    private void drawPolygon(){
        Graphics2D g2d = getImagePanel().createGraphics();
        g2d.setStroke(new BasicStroke((getLineWidth())));
        int[] xp = new int[xVertices.size()];
        int[] yp = new int[yVertices.size()];
        for(int i = 0; i < xVertices.size(); i++){
            xp[i] = (xVertices.get(i)).intValue();
            yp[i] = (yVertices.get(i)).intValue();
        }
        if(fill){
            g2d.setColor(fillColour);
            g2d.fillPolygon(xp, yp, xp.length);
        }
        g2d.setColor(getLineColour());
        g2d.drawPolygon(xp, yp, xp.length);
        xVertices.clear();
        yVertices.clear();
    }

    public void paintUpdated(ShapeInfo shape){
        ((VecPaint)getObserver()).updateShapes(shape);
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
            g2d.draw(new Line2D.Double(tpx, tpy, ex, ey));
        }
    }



    private class PolyMouseListener extends MouseAdapter {
        /**
         * When mouse is pressed, start drawing a rectangle, so set the start location
         */
        public void mousePressed(MouseEvent e) {

            if (edges == 0) {
                sx = e.getPoint().getX();
                sy = e.getPoint().getY();
                xVertices.add(sx);
                yVertices.add(sy);
                tpx = sx;
                tpy = sy;
                edges++;
                System.out.println(sx + " : " + sy);
            } else {
                if(e.getClickCount() != 2) {
                    ex = e.getPoint().getX();
                    ey = e.getPoint().getY();
                    System.out.println(ex + " : " + ey + "HERE");
                    xVertices.add(ex);
                    yVertices.add(ey);
                    System.out.println("List: " + xVertices);
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
        public void mouseReleased(MouseEvent e) {
            drawTempLine = false;
            repaint();
        }


        /**
         * Double clicking finishes drawing polygon, and connects end to start point
         */
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                Polygon polygon = new Polygon(xVertices, yVertices, getLineColour(), fillColour, fill, getImagePanel().getWidth());
                polygon.drawPoly(getImagePanel());
                paintUpdated(polygon);
                drawPolygon();
                drawTempLine = false;
                edges = 0;
            }
        }

        /**
         * When mouse is being dragged, continuously updates tpx, tpy and ex, ey.
         * So, it produces like a free shape
         * This is still a polygon : A polygon with lots of very short straight lines
         */
        public void mouseDragged(MouseEvent e) {
            ex = e.getPoint().getX();
            ey = e.getPoint().getY();
            xVertices.add(ex);
            yVertices.add(ey);
            drawOnImagePanel(tpx, tpy, ex, ey);
            repaint();
            tpx = ex;
            tpy = ey;
        }

        // those methods are not used, just need to implements here
        public void mouseEntered(MouseEvent evt) {
        }

        public void mouseExited(MouseEvent evt) {
        }
    }
}
