package DrawVecShape;

import VecShape.VecEllipse;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Class to draw a ellipse
 * This class provides JPanel to draw a temporary ellipse,
 * and when the user finished drawing (mouse released) an ellipse object is sent to main class
 */
public class DrawEllip extends DrawShape {

    private double sx;
    private double sy;
    private double ex;
    private double ey;

    private boolean drawTemp = false;
    private boolean fill;
    private Color fillColor;

    /**
     * constructor
     * @param imagePanel BufferedImage -on which the image is drawn
     * @param lineWidth line width
     * @param penColour Color -colour of line
     * @param fillColour Color -fill colour
     * @param fill boolean -true if image needs to be filled
     * @param grid - set true if grid is on
     * @param gridSize - grid size that divides the canvas: e.g gridSize = 2 means the grid divides canvas into two horizontally and vertically
     * @param canvasObserver VecInterface.Observer -class that wants to receive a drawn object information.
     *                    Usually, a class that has a canvas to draw this object (ellipse)
     */
    public DrawEllip(BufferedImage imagePanel, float lineWidth, Color penColour, Color fillColour, boolean fill, boolean grid, int gridSize, VecCanvas canvasObserver){
        super(imagePanel, lineWidth, penColour, canvasObserver, grid, gridSize);
        EllipMouseListener mouse = new EllipMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.fillColor = fillColour;
        this.fill = fill;
    }


    /**
     * Calculate ellipse width and height from sx, sy, ex and ey.
     * while mouse is dragging, temporary ellipse is drawn.
     * once mouse is released, ellipse object is passed to main class to draw ellipse as image
     * @param g2d graphic component
     */
    private void drawEllipse(Graphics2D g2d){
        VecEllipse vEllipse;
        int imageWidth = getImagePanel().getWidth();
        int imageHeight = getImagePanel().getHeight();
        double width = ex - sx;
        double height = ey - sy;
        g2d.setColor(fillColor);
        if (width >= 0 && height >= 0) {
            vEllipse = new VecEllipse(sx / imageWidth, sy / imageHeight,
                    ex / imageWidth, ey / imageHeight, getLineColour(), fillColor, fill);
        } else if (width >= 0 && height < 0) {
            vEllipse = new VecEllipse(sx / imageWidth, ey / imageHeight,
                    ex / imageWidth, sy / imageHeight, getLineColour(), fillColor, fill);
        } else if (width < 0 && height >= 0) {
            vEllipse = new VecEllipse(ex / imageWidth, sy / imageHeight,
                    sx / imageWidth, ey /imageHeight, getLineColour(), fillColor, fill);
        } else {
            vEllipse = new VecEllipse(ex / imageWidth, ey / imageHeight,
                    sx/ imageWidth, sy / imageHeight, getLineColour(), fillColor, fill);
        }
        if (drawTemp) {
            if (fill) { g2d.fill(vEllipse.getShape(imageWidth)); }
            g2d.setColor(getLineColour());
            g2d.draw(vEllipse.getShape(imageWidth));
        } else {
            paintUpdated(vEllipse);
        }
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
        if (drawTemp) { drawEllipse(g2d); }
        g2d.dispose();
    }

    /**
     * private class that defines behaviour when mouse movement is made
     */
    private class EllipMouseListener extends MouseAdapter {
        /**
         * When mouse is pressed, start drawing a rectangle, so set the start location
         */
        public void mousePressed(MouseEvent e) {
            double x = e.getPoint().getX();
            double y = e.getPoint().getY();
            if (!getIsGridOn()){
                sx = x;
                sy = y;
            } else {
                sx = adjustPoint(x);
                sy = adjustPoint(y);
            }
        }

        /**
         * While mouse is being dragged, temporary line is shown
         * every time repaint the JPanel (this object)
         */
        public void mouseDragged(MouseEvent e) {
            ex = e.getPoint().getX();
            ey = e.getPoint().getY();
            drawTemp = true;
            repaint();
        }

        /**
         * Once the mouse is released create plot object, and send this data to main class to draw plot as an image
         */
        public void mouseReleased(MouseEvent e) {
            double x = e.getPoint().getX();
            double y = e.getPoint().getY();
            if (!getIsGridOn()){
                ex = x;
                ey = y;
            } else {
                ex = adjustPoint(x);
                ey = adjustPoint(y);
            }
            drawTemp = false;
            Graphics2D g2d = (Graphics2D)getGraphics(); // just to pass graphics to call drawRect(g2d);
            drawEllipse(g2d);
            g2d.dispose();
            repaint();

        }

        // those methods are not used, just need to implements here
        public void mouseEntered(MouseEvent evt) {
        }

        public void mouseExited(MouseEvent evt) {
        }

        public void mouseClicked(MouseEvent evt) {
        }

        public void mouseMoved(MouseEvent evt) {
        }

    }



}
