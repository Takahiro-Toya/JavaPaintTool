package DrawVecShape;

import VecShape.VecRectangle;
import VecInterface.Observer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

/**
 * Class to draw a rectangle
 * This class provides JPanel to draw a temporary rectangle,
 * and when the user finished drawing (released mouse) the rectangle is drawn on the Buffered Image
 */
public class DrawRect extends DrawShape {

    private double sx;
    private double sy;
    private double ex;
    private double ey;
    private boolean drawTempRect = false;
    private Color fillColor;
    private boolean fill;

    /**
     * constructor
     * @param imagePanel BufferedImage -on which the image is drawn
     * @param penColour Color -colour of line
     * @param fillColour Color -fill colour
     * @param fill boolean -true if image needs to be filled
     * @param grid - set true if grid is on
     * @param gridSize - grid size that divides the canvas: e.g gridSize = 2 means the grid divides canvas into two horizontally and vertically
     * @param observer VecInterface.Observer -class that wants to receive a drawn object information.
     *                    Usually, a class that has a canvas to draw this object (rectangle)
     */
    public DrawRect(BufferedImage imagePanel, Color penColour, Color fillColour, boolean fill, boolean grid, int gridSize, Observer observer){
        super(imagePanel, penColour, observer, grid, gridSize);
        RectMouseListener mouse = new RectMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        fillColor = fillColour;
        this.fill = fill;
    }

    /**
     * Calculate rectangle width and height from sx, sy, ex and ey.
     * while mouse is dragging, temporary rectangle is drawn.
     * once mouse is released, rectangle object is passed to main class to draw rectangle as image
     * @param g2d graphic component
     */
    private void drawRect(Graphics2D g2d){
        VecRectangle vRect;
        int imageWidth = getImagePanel().getWidth();
        int imageHeight = getImagePanel().getHeight();
        double width = ex - sx;
        double height = ey - sy;
        g2d.setColor(fillColor);
        if (width >= 0 && height >= 0) {
            vRect = new VecRectangle(sx / imageWidth, sy / imageHeight, ex / imageWidth,
                    ey / imageHeight, getLineColour(), fillColor, fill);
        } else if (width >= 0 && height < 0) {
            vRect = new VecRectangle(sx / imageWidth, ey / imageHeight,
                    ex / imageWidth, sy / imageHeight, getLineColour(), fillColor, fill);
        } else if (width < 0 && height >= 0) {
            vRect = new VecRectangle(ex / imageWidth, sy / imageHeight,
                    sx / imageWidth, ey /imageHeight, getLineColour(), fillColor, fill);
        } else {
            vRect = new VecRectangle(ex / imageWidth, ey / imageHeight,
                    sx / imageWidth, sy / imageHeight, getLineColour(), fillColor, fill);
        }

        if(drawTempRect) {
            if (fill) { g2d.fill(vRect.getShape(imageWidth)); }
            g2d.setColor(getLineColour());
            g2d.draw(vRect.getShape(imageWidth));
        } else {
            paintUpdated(vRect);
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
        if (drawTempRect) { drawRect(g2d); }
        g2d.dispose();

    }

    /**
     * private class that defines behaviour when mouse movement is made
     */
    private class RectMouseListener extends MouseAdapter {

        /**
         * When mouse is pressed, start drawing a rectangle, so set the start location
         */
        @Override
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
        @Override
        public void mouseDragged(MouseEvent e) {
            ex = e.getPoint().getX();
            ey = e.getPoint().getY();
            drawTempRect = true;
            repaint();
        }

        /**
         * When mouse click is released, the rectangle is drawn on the BufferedImage
         */
        @Override
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
            drawTempRect = false;
            Graphics2D g2d = (Graphics2D)getGraphics(); // just to pass graphics to call drawRect(g2d);
            drawRect(g2d);
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
