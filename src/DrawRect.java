import javax.swing.*;
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
     * @param imagePanel to display drawn image
     */
    public DrawRect(BufferedImage imagePanel, Color lc, Color fc, boolean fill, Observer o){
        super(imagePanel, lc, o);
        RectMouseListener mouse = new RectMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        fillColor = fc;
        this.fill = fill;
    }


    private void drawRect(Graphics2D g2d){
        Rectangle2D rect;
        Rectangle rectVec;
        int imageWidth = getImagePanel().getWidth();
        int imageHeight = getImagePanel().getHeight();
        double width = ex - sx;
        double height = ey - sy;
        g2d.setColor(fillColor);
        if (width >= 0 && height >= 0) {
            rect = new Rectangle2D.Double(sx, sy, width, height);
            rectVec = new Rectangle(sx / imageWidth, sy / imageHeight, width / imageWidth,
                    height / imageHeight, getLineColour(), fillColor, fill);
        } else if (width >= 0 && height < 0) {
            rect = new Rectangle2D.Double(sx, ey, width, Math.abs(height));
            rectVec = new Rectangle(sx / imageWidth, ey / imageHeight,
                    width / imageWidth, Math.abs(height) / imageHeight, getLineColour(), fillColor, fill);
        } else if (width < 0 && height >= 0) {
            rect = new Rectangle2D.Double(ex, sy, Math.abs(width), height);
            rectVec = new Rectangle(ex / imageWidth, sy / imageHeight,
                    Math.abs(width) / imageWidth, height /imageHeight, getLineColour(), fillColor, fill);
        } else {
            rect = new Rectangle2D.Double(ex, ey, Math.abs(width), Math.abs(height));
            rectVec = new Rectangle(ex / imageWidth, ey / imageHeight,
                    Math.abs(width)/ imageWidth, Math.abs(height) / imageHeight, getLineColour(), fillColor, fill);
        }
        if (fill) {g2d.fill(rect);}
        g2d.setColor(getLineColour());
        g2d.draw(rect);
        if (!drawTempRect) {
            paintUpdated(rectVec);
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

        if (drawTempRect) {
            drawRect(g2d);
        }
    }

    private class RectMouseListener extends MouseAdapter {

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
            drawTempRect = true;
            repaint();
        }

        /**
         * When mouse click is released, the rectangle is drawn on the BufferedImage
         */
        public void mouseReleased(MouseEvent e) {
            ex = e.getPoint().getX();
            ey = e.getPoint().getY();
            drawTempRect = false;
            Graphics2D g2d = getImagePanel().createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(getLineWidth()));
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
