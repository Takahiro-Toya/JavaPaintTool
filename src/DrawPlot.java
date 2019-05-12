import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Class to draw a line
 * This class provides JPanel to draw a temporary line,
 * and when the user finished drawing (released mouse) the line is drawn on the Buffered Image
 */
public class DrawPlot extends JPanel implements DrawShape {

    private BufferedImage imagePanel;

    private int sx = 0;
    private int sy = 0;
    private int ex = 0;
    private int ey = 0;
    private Color lineColor = Color.black;

    /**
     * constructor
     * @param imagePanel to display drawn image
     */
    public DrawPlot(BufferedImage imagePanel){
        PlotMouseListener mouse = new PlotMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.imagePanel = imagePanel;
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
        g.setColor(lineColor);
        g.drawLine(sx, sy, sx, sy);

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
            sx = e.getX();
            sy = e.getY();
        }

        public void mouseReleased(MouseEvent e) {
            Graphics2D g2 = imagePanel.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(lineColor);
            g2.setStroke(new BasicStroke(5f));
            g2.drawLine(sx, sy, sx, sy);
            g2.dispose();
            repaint();
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
    public void writeVecFile(){}
    public void setColour(Color color){
        lineColor = color;
    }
    public Point getStartPoint(){ return new Point(sx, sy); }
    public Point getEndPoint(){ return new Point(ex, ey); }


}
