import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * Class to draw a rectangle
 * This class provides JPanel to draw a temporary rectangle,
 * and when the user finished drawing (released mouse) the rectangle is drawn on the Buffered Image
 */
public class DrawRect extends JPanel implements DrawShape, CanvasSubject {

    private BufferedImage imagePanel; // used to display drawn images (shapes)

    private int sx;
    private int sy;
    private int ex;
    private int ey;
    private boolean drawTempRect = false;
    private Color LINE_COLOR;

    /**
     * constructor
     * @param imagePanel to display drawn image
     */
    public DrawRect(BufferedImage imagePanel){
        RectMouseListener mouse = new RectMouseListener();
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

        if (drawTempRect) {
            int width = ex - sx;
            int height = ey - sy;
            g.setColor(LINE_COLOR);
            if (width >= 0 && height >= 0){
                g.drawRect(sx, sy, width, height);
            } else if (width >= 0 && height < 0){
                g.drawRect(sx, ey, width, Math.abs(height));
            } else if (width  < 0 && height >= 0){
                g.drawRect(ex, sy, Math.abs(width), height);
            } else {
                g.drawRect(ex, ey, Math.abs(width), Math.abs(height));
            }
        }
    }

    private class RectMouseListener extends MouseAdapter {

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
            drawTempRect = true;
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
            Graphics2D g2d = imagePanel.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(5f));
            if (width >= 0 && height >= 0) {
                g2d.drawRect(sx, sy, width, height);
            } else if (width >= 0 && height < 0) {
                g2d.drawRect(sx, ey, width, Math.abs(height));
            } else if (width < 0 && height >= 0) {
                g2d.drawRect(ex, sy, Math.abs(width), height);
            } else {
                g2d.drawRect(ex, ey, Math.abs(width), Math.abs(height));
            }
            g2d.dispose();
            drawTempRect = false;
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
    public void setColour(){}
    public Point getStartPoint(){
        return new Point(sx, sy);
    }
    public Point getEndPoint(){
        return new Point(ex, ey);
    }

}
