import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Class to draw a ellipse
 * This class provides JPanel to draw a temporary ellipse,
 * and when the user finished drawing (released mouse) the ellipse is drawn on the Buffered Image
 */
public class DrawEllip extends JPanel implements DrawShape {

    private BufferedImage imagePanel;

    private int sx;
    private int sy;
    private int ex;
    private int ey;
    private boolean drawTempLine = false;
    private Color lineColor = Color.black;

    public DrawEllip(BufferedImage imagePanel){
        EllipMouseListener mouse = new EllipMouseListener();
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

        if (drawTempLine) {

            int width = ex - sx;
            int height = ey - sy;
            g.setColor(lineColor);
            if (width >= 0 && height >= 0){
                g.drawOval(sx, sy, width, height);
            } else if (width >= 0 && height < 0){
                g.drawOval(sx, ey, width, Math.abs(height));
            } else if (width  < 0 && height >= 0){
                g.drawOval(ex, sy, Math.abs(width), height);
            } else {
                g.drawOval(ex, ey, Math.abs(width), Math.abs(height));
            }
        }
    }

    private class EllipMouseListener extends MouseAdapter {
        /**
         * When mouse is pressed, start drawing a rectangle, so set the start location
         */
        public void mousePressed(MouseEvent e) {
            sx = e.getX();
            sy = e.getY();
        }

        /**
         * While mouse is being dragged, temporary line is shown
         * every time repaint the JPanel (this object)
         */
        public void mouseDragged(MouseEvent e) {
            ex = e.getX();
            ey = e.getY();
            drawTempLine = true;
            repaint();
        }

        /**
         * When mouse click is released, the rectangle is drawn on the BufferedImage
         */
        public void mouseReleased(MouseEvent e) {
            ex = e.getX();
            ey = e.getY();

            int width = ex - sx;
            int height = ey - sy;

            Graphics2D g2 = imagePanel.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(lineColor);
            g2.setStroke(new BasicStroke(5f));
            if (width >= 0 && height >= 0) {
                g2.drawOval(sx, sy, width, height);
            } else if (width >= 0 && height < 0) {
                g2.drawOval(sx, ey, width, Math.abs(height));
            } else if (width < 0 && height >= 0) {
                g2.drawOval(ex, sy, Math.abs(width), height);
            } else {
                g2.drawOval(ex, ey, Math.abs(width), Math.abs(height));
            }

            g2.dispose();
            drawTempLine = false;
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
