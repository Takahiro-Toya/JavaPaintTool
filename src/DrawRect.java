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
public class DrawRect extends JPanel implements DrawShape, FillShape, WriteFile{

    private BufferedImage imagePanel; // used to display drawn images (shapes)

    private double sx = 0;
    private double sy = 0;
    private double ex = 0;
    private double ey = 0;
    private boolean drawTempRect = false;
    private Color lineColor;
    private Color fillColor;
    private float lineWidth = 2f;
    private boolean fill = false;

    /**
     * constructor
     * @param imagePanel to display drawn image
     */
    public DrawRect(BufferedImage imagePanel, Color lc, Color fc){
        RectMouseListener mouse = new RectMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.imagePanel = imagePanel;
        lineColor = lc;
        fillColor = fc;
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

    private void drawRect(Graphics2D g2d){
        Rectangle2D rect;
        double width = ex - sx;
        double height = ey - sy;
        g2d.setColor(fillColor);
        if (width >= 0 && height >= 0) {
            rect = new Rectangle2D.Double(sx, sy, width, height);

        } else if (width >= 0 && height < 0) {
            rect = new Rectangle2D.Double(sx, ey, width, Math.abs(height));

        } else if (width < 0 && height >= 0) {
            rect = new Rectangle2D.Double(ex, sy, Math.abs(width), height);

        } else {
            rect = new Rectangle2D.Double(ex, ey, Math.abs(width), Math.abs(height));

        }
        if (fill) {g2d.fill(rect);}
        g2d.setColor(lineColor);
        g2d.draw(rect);
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

        if (drawTempRect) {
            drawRect(g2d);
        }
    }

    /**
     * Write the information into the content in main class
     * @param str the vairable of content
     * @return return the updated content
     */
    @Override
    public String writeIn(String str) {
        VecPaint vec = new VecPaint();
        str = str + "RECTANGLE " + sx + " " + sy + " " + ex + " " + ey +  "\n";
        vec.setContent(str);
        return str;
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
            Graphics2D g2d = imagePanel.createGraphics();
            VecPaint vecPaint = new VecPaint();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(lineWidth));
            drawRect(g2d);
            g2d.dispose();
            drawTempRect = false;
            repaint();
            vecPaint.setContent(writeIn(vecPaint.getContent()));
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
