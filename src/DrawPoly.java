import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Class to draw a line
 * This class provides JPanel to draw a temporary line,
 * and when the user finished drawing (released mouse) the line is drawn on the Buffered Image
 */
public class DrawPoly extends JPanel implements DrawShape {

    private BufferedImage imagePanel;

    private int sx;
    private int sy;
    private int tpx;
    private int tpy;
    private int ex;
    private int ey;

    private int edges = 0;
    private boolean drawTempLine = false;
    private Color lineColor = Color.black;

    /**
     * constructor
     * @param imagePanel to display drawn image
     */
    public DrawPoly (BufferedImage imagePanel){
        PolyMouseListener mouse = new PolyMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.imagePanel = imagePanel;
    }

    /**
     * Draw image on the image Panel
     */
    private void drawOnImagePanel(int x1, int y1, int x2, int y2){
        Graphics2D g2 = imagePanel.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(lineColor);
        g2.setStroke(new BasicStroke(5f));
        g2.drawLine(x1, y1, x2, y2);
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

        g.drawImage(imagePanel, 0, 0, this);

        if (drawTempLine) {
            g.setColor(lineColor);
            g.drawLine(tpx, tpy, ex, ey);
        }
    }

    private class PolyMouseListener extends MouseAdapter {
        /**
         * When mouse is pressed, start drawing a rectangle, so set the start location
         */
        public void mousePressed(MouseEvent e) {
            if (edges == 0) {
                sx = e.getX();
                sy = e.getY();
                tpx = sx;
                tpy = sy;
                edges++;
            } else {
                ex = e.getX();
                ey = e.getY();
                drawOnImagePanel(tpx, tpy, ex, ey);
                drawTempLine = false;
                repaint();
                tpx = ex;
                tpy = ey;
                edges++;
            }
        }

        /**
         * When mouse is being moved (not dragged!), a temporary line is drawn
         * between preveously clicked location to current mouse location
         */
        public void mouseMoved(MouseEvent e) {
            if (!(edges == 0)) {
                ex = e.getX();
                ey = e.getY();
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
         * Only when double click is made, then finish drawing polygon, and connects start to end point
         */
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                ex = e.getX();
                ey = e.getY();
                drawTempLine = false;
                edges = 0;
                drawOnImagePanel(ex, ey, sx, sy);

            }
        }

        /**
         * When mouse is being dragged, continuously updates tpx, tpy and ex, ey.
         * So, it produces like a free shape
         * This is still a polygon : A polygon with lots of very short straight lines
         */
        public void mouseDragged(MouseEvent e) {
            ex = e.getX();
            ey = e.getY();
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
    public void writeVecFile(){}
    public void setColour(Color color){
        lineColor = color;
    }
    public Point getStartPoint(){
        return new Point(sx, sy);
    }
    public Point getEndPoint(){
        return new Point(ex, ey);
    }




}
