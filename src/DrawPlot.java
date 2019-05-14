import javax.swing.*;
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
     * @param imagePanel to display drawn image
     */
    public DrawPlot(BufferedImage imagePanel, Color c, Observer o){
        super(imagePanel, c, o);
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
     * Mouse Listener
     */
    private class PlotMouseListener extends MouseAdapter {
        /**
         * When mouse is pressed, start drawing a rectangle, so set the start location
         */
        @Override
        public void mousePressed(MouseEvent e) {
            sx = e.getPoint().getX();
            sy = e.getPoint().getY();
        }

        public void mouseReleased(MouseEvent e) {
            Graphics2D g2 = getImagePanel().createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getLineColour());
            g2.setStroke(new BasicStroke(getLineWidth()));
            Line2D plot = new Line2D.Double(sx, sy, sx, sy);
            Plot plotVec = new Plot(sx / getImagePanel().getWidth(), sy / getImagePanel().getHeight(), getLineColour());
            g2.draw(plot);
            g2.dispose();
            repaint();
            paintUpdated(plotVec);
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
