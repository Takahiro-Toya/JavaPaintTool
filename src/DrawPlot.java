import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * Class to draw a line
 * This class provides JPanel to draw a temporary line,
 * and when the user finished drawing (released mouse) the line is drawn on the Buffered Image
 */
public class DrawPlot extends DrawShape {

    private double sx = 0;
    private double sy = 0;

    /**
     * constructor
     * @param imagePanel BufferedImage -on which the image is drawn
     * @param penColour Color -colour of plot
     * @param o Observer -class that wants to receive a drawn object information.
     *                    Usually, a class that has a canvas to draw this object (rectangle)
     */
    public DrawPlot(BufferedImage imagePanel, Color penColour, boolean grid, double gridSize, Observer o){
        super(imagePanel, penColour, o, grid, gridSize);
        PlotMouseListener mouse = new PlotMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
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
        g2d.setColor(getLineColour());
        g2d.draw(new Line2D.Double(sx, sy, sx, sy));

    }

    /**
     * private class that defines behaviour when mouse movement is made
     */
    private class PlotMouseListener extends MouseAdapter {
        /**
         * When mouse is pressed, start drawing a rectangle, so set the start location
         * @param e -mouse event
         */
        @Override
        public void mousePressed(MouseEvent e) {
            double x = e.getPoint().getX();
            double y = e.getPoint().getY();
            if (!grid){
                sx = x;
                sy = y;
            } else {
                sx = adjustPoint(x);
                sy = adjustPoint(y);
            }
        }

        /**
         * Once the mouse is released create plot object, and send this data to main class to draw plot as an image
         * @param e -mouse event
         */
        public void mouseReleased(MouseEvent e) {
            VecPlot vecPlot = new VecPlot(sx / getImagePanel().getWidth(), sy / getImagePanel().getHeight(), getLineColour());
            repaint();
            paintUpdated(vecPlot);
        }

        // those methods are not used, just need to implements here
        public void mouseDragged(MouseEvent e) {
        }

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
