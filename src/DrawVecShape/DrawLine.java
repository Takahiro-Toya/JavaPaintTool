package DrawVecShape;

import VecShape.VecLine;
import VecInterface.Observer;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * Class to draw a line
 * Defines behaviour when mouse is clicked, dragged etc... when drawing mode is LINE
 */
public class DrawLine extends DrawShape {

    private double sx = 0;
    private double sy = 0;
    private double ex = 0;
    private double ey = 0;
    private boolean drawTempLine = false;

    /**
     * constructor
     * @param imagePanel BufferedImage -on which the image is drawn
     * @param penColour Color -colour of line
     * @param grid - set true if grid is on
     * @param gridSize - grid size that divides the canvas: e.g gridSize = 2 means the grid divides canvas into two horizontally and vertically
     * @param observer -VecInterface.Observer -class that wants to receive a drawn object information.
     *                    Usually, a class that has a canvas to draw this object (rectangle)
     */
    public DrawLine(BufferedImage imagePanel, Color penColour, boolean grid, int gridSize, Observer observer){
        super(imagePanel, penColour, observer, grid, gridSize);
        LineMouseListener mouse = new LineMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
    }

    /**
     * Override paintComponent() so, when user made changes on the application window,
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
            g2d.draw(new Line2D.Double(sx, sy, ex, ey));
        }
        g2d.dispose();
    }


    /**
     * private class that defines behaviour when mouse movement is made
     */
    private class LineMouseListener extends MouseAdapter {
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
         * While mouse is being dragged, temporary line is show every time repaint the JPanel (this object)
         * @param e -mouse event
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            ex = e.getPoint().getX();
            ey = e.getPoint().getY();
            drawTempLine = true;
            repaint();
        }

        /**
         * Once the mouse is released create line object, and send this data to main class to draw plot as an image
         * @param e -mouse event
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
            VecLine vecLine = new VecLine(sx / getImagePanel().getWidth(), sy / getImagePanel().getHeight(),
                    ex / getImagePanel().getWidth(), ey /  getImagePanel().getHeight(), getLineColour());
            drawTempLine = false;
            paintUpdated(vecLine);
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
