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
public class DrawEllip extends DrawShape {


    private double sx;
    private double sy;
    private double ex;
    private double ey;

    private boolean drawTemp = false;
    private boolean fill;
    private Color fillColor;

    public DrawEllip(BufferedImage imagePanel, Color lineColor, Color fillColor, boolean fill, Observer o){
        super(imagePanel, lineColor, o);
        EllipMouseListener mouse = new EllipMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.fillColor = fillColor;
        this.fill = fill;
    }


    private void drawEllipse(Graphics2D g2d){
        Ellipse2D ellipse;
        Ellipse ellipseVec;
        int imageWidth = getImagePanel().getWidth();
        int imageHeight = getImagePanel().getHeight();
        double width = ex - sx;
        double height = ey - sy;
        g2d.setColor(fillColor);
        if (width >= 0 && height >= 0) {
            ellipse = new Ellipse2D.Double(sx, sy, width, height);
            ellipseVec = new Ellipse(sx / imageWidth, sy / imageHeight,
                    ex / imageWidth, ey / imageHeight, getLineColour(), fillColor, fill);
        } else if (width >= 0 && height < 0) {
            ellipse = new Ellipse2D.Double(sx, ey, width, Math.abs(height));
            ellipseVec = new Ellipse(sx / imageWidth, ey / imageHeight,
                    ex / imageWidth, sy / imageHeight, getLineColour(), fillColor, fill);
        } else if (width < 0 && height >= 0) {
            ellipse = new Ellipse2D.Double(ex , sy, Math.abs(width), height);
            ellipseVec = new Ellipse(ex / imageWidth, sy / imageHeight,
                    sx / imageWidth, ey /imageHeight, getLineColour(), fillColor, fill);
        } else {
            ellipse = new Ellipse2D.Double(ex, ey, Math.abs(width), Math.abs(height));
            ellipseVec = new Ellipse(ex / imageWidth, ey / imageHeight,
                    sx/ imageWidth, sy / imageHeight, getLineColour(), fillColor, fill);
        }
        if (fill) {g2d.fill(ellipse);}
        g2d.setColor(getLineColour());
        g2d.draw(ellipse);
        if (!drawTemp) {
            paintUpdated(ellipseVec);
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

        if (drawTemp) {
            drawEllipse(g2d);
        }
    }

    private class EllipMouseListener extends MouseAdapter {
        /**
         * When mouse is pressed, start drawing a rectangle, so set the start location
         */
        public void mousePressed(MouseEvent e) {
            sx = e.getPoint().getX();
            sy = e.getPoint().getY();
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
         * When mouse click is released, the rectangle is drawn on the BufferedImage
         */
        public void mouseReleased(MouseEvent e) {
            ex = e.getPoint().getX();
            ey = e.getPoint().getY();
            drawTemp = false;
            Graphics2D g2d = getImagePanel().createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(getLineWidth()));
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
