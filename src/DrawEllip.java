import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

/**
 * Class to draw a ellipse
 * This class provides JPanel to draw a temporary ellipse,
 * and when the user finished drawing (released mouse) the ellipse is drawn on the Buffered Image
 */
public class DrawEllip extends JPanel implements DrawShape, FillShape{

    private BufferedImage imagePanel;

    private double sx;
    private double sy;
    private double ex;
    private double ey;

    private boolean drawTemp = false;
    private boolean fill = false;

    private Color lineColor;
    private Color fillColor;

    private float lineWidth = 2f;


    public DrawEllip(BufferedImage imagePanel, Color lineColor, Color fillColor){
        EllipMouseListener mouse = new EllipMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.imagePanel = imagePanel;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }

    private void drawEllipse(Graphics2D g2d){
        Ellipse2D ellipse;
        double width = ex - sx;
        double height = ey - sy;
        g2d.setColor(fillColor);
        if (width >= 0 && height >= 0) {
            ellipse = new Ellipse2D.Double(sx, sy, width, height);

        } else if (width >= 0 && height < 0) {
            ellipse = new Ellipse2D.Double(sx, ey, width, Math.abs(height));

        } else if (width < 0 && height >= 0) {
            ellipse = new Ellipse2D.Double(ex, sy, Math.abs(width), height);

        } else {
            ellipse = new Ellipse2D.Double(ex, ey, Math.abs(width), Math.abs(height));

        }
        if (fill) {g2d.fill(ellipse);}
        g2d.setColor(lineColor);
        g2d.draw(ellipse);
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
        g2d.drawImage(imagePanel, 0, 0, this);

        if (drawTemp) {
            drawEllipse(g2d);
        }
    }


    public void setLineColour(Color c){
        lineColor = c;
    }

    public void setFillColour(Color c){
        fillColor = c;
    }

    public void setFill(boolean bool){
        fill = bool;
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
            drawTemp = true;
            repaint();
        }

        /**
         * When mouse click is released, the rectangle is drawn on the BufferedImage
         */
        public void mouseReleased(MouseEvent e) {
            ex = e.getX();
            ey = e.getY();

            Graphics2D g2d = imagePanel.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(lineWidth));
            drawEllipse(g2d);
            g2d.dispose();
            drawTemp = false;
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



}
