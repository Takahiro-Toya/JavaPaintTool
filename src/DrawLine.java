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
    private Color lineColor;

    /**
     * constructor
     * @param imagePanel to display drawn image
     */
    public DrawLine(BufferedImage imagePanel, Color c, Observer o){
        super(imagePanel, c, o);
        LineMouseListener mouse = new LineMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        lineColor = c;
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

        if (drawTempLine) {
            g2d.setColor(lineColor);
            g2d.draw(new Line2D.Double(sx, sy, ex, ey));
        }
    }


    /**
     * Mouse Listener
     */
    private class LineMouseListener extends MouseAdapter {
        /**
         * When mouse is pressed, start drawing a rectangle, so set the start location
         */
        @Override
        public void mousePressed(MouseEvent e) {
            sx = e.getPoint().getX();
            sy = e.getPoint().getY();
        }

        /**
         * While mouse is being dragged, temporary line is shown
         * every time repaint the JPanel (this object)
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            ex = e.getPoint().getX();
            ey = e.getPoint().getY();
            drawTempLine = true;
            repaint();
        }

        /**
         * When mouse click is released, the rectangle is drawn on the BufferedImage
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            ex = e.getPoint().getX();
            ey = e.getPoint().getY();
//            Graphics2D g2 = getImagePanel().createGraphics();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setColor(lineColor);
//            g2.setStroke(new BasicStroke(getLineWidth()));
            Line lineVec = new Line(sx / getImagePanel().getWidth(), sy / getImagePanel().getHeight(),
                    ex / getImagePanel().getWidth(), ey /  getImagePanel().getHeight(), lineColor);
//            g2.draw(new Line2D.Double(sx, sy, ex, ey));
//            g2.dispose();
            drawTempLine = false;
            paintUpdated(lineVec);
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
