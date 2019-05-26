import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Class to draw a shape
 * This class provides JPanel to draw a temporary object (line, rectangle, ellipse etc.),
 * and when the user finished drawing (released mouse) the drawn object information is sent to observer
 */
public class DrawShape extends JPanel implements Subject{

    private ArrayList<Observer> observers  = new ArrayList<>();
    private Color plotColour;
    private BufferedImage imagePanel;
    private float lineWidth = 2f;
    protected Boolean grid = false;
    protected double gridSize = 0;

    /**
     /**
     * constructor
     * @param imagePanel -BufferedImage in which an object is drawn
     * @param plotColour -plot colour
     * @param observer Observer -class that wants to receive a drawn object information.
     *                    Usually, a class that has a canvas to draw this object (rectangle)
     * @param grid - set true if grid is on
     * @param gridSize - grid size that divides the canvas: e.g gridSize = 2 means the grid divides canvas into two horizontally and vertically
     */
    public DrawShape(BufferedImage imagePanel, Color plotColour, Observer observer, boolean grid, double gridSize){
        this.imagePanel = imagePanel;
        this.plotColour = plotColour;
        this.grid = grid;
        this.gridSize = gridSize;
        attachObserver(observer);
    }

    /**
     * attach observer that wants to get object information
     * @param o
     */
    @Override
    public void attachObserver(Observer o){
        observers.add(o);
    }

    /**
     * not used here, instead paintUpdated(VecShape shape) plays similar roles
     */
    @Override
    public void notifyObservers(String location){ }

    /**
     * Tells observers that new shape has made.
     * Each DrawShape's children class need to call this method to send new shape information to observer
     * Alternative for notifyObservers(String location);
     * @param shape -new shape
     */
    public void paintUpdated(VecShape shape){
        for(Observer observer: observers){
            ((Canvas)observer).updateShapes(shape);
        }
    }

    /**
     * This function is used to adjust clicked coordinate automatically when user is using grid
     * Returns closest grid x or y coordinate
     * @param coordinate x or y clicked point
     * @return adjusted x or y clicked point based on grid size
     */
    protected double adjustPoint(double coordinate){
        double gridInterval = imagePanel.getWidth() / gridSize;
        return Math.floor((coordinate + gridInterval / 2) / gridInterval) * gridInterval ;
    }

    /**
     * get line (plot) colour registered
     * @return plotColour
     */
    public Color getLineColour() {return plotColour;}

    /**
     * get image panel registered
     * @return image panel
     */
    public BufferedImage getImagePanel() {return imagePanel;}

    /**
     * get line width
     * @return line width
     */
    public float getLineWidth() {return lineWidth;}

}
