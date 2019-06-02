package DrawVecShape;

import VecShape.VecShape;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Class to draw a shape
 * This class provides JPanel to draw a temporary object (line, rectangle, ellipse etc.),
 * and when the user finished drawing (released mouse) the drawn object information is sent to observer
 */
public class DrawShape extends JPanel {

    private ArrayList<VecCanvas> observers  = new ArrayList<>();
    private Color lineColour;
    private BufferedImage imagePanel;
    private float lineWidth;
    private Boolean grid;
    private int gridSize;

    /**
     /**
     * constructor
     * @param imagePanel -BufferedImage in which an object is drawn
     * @param lineColour -plot colour
     * @param lineWidth -line width
     * @param canvasObserver VecInterface.Observer -class that wants to receive a drawn object information.
     *                    Usually, a class that has a canvas to draw this object (rectangle)
     * @param grid - set true if grid is on
     * @param gridSize - grid size that divides the canvas: e.g gridSize = 2 means the grid divides canvas into two horizontally and vertically
     */
    public DrawShape(BufferedImage imagePanel, float lineWidth, Color lineColour, VecCanvas canvasObserver, boolean grid, int gridSize){
        this.imagePanel = imagePanel;
        this.lineColour = lineColour;
        this.lineWidth = lineWidth;
        this.grid = grid;
        this.gridSize = gridSize;
        this.observers.add(canvasObserver);
    }

    /**
     * This function may be useful in the future if the application needs to have several canvases
     * For example, Microsoft power point has small canvases at the left side.
     * @param canvasObserver canvas class that implements VecCanvas
     */
    public void attachCanvasObserver(VecCanvas canvasObserver){
        this.observers.add(canvasObserver);
    }

    /**
     * Tells observers that new shape has made.
     * Each DrawVecShape.DrawShape's children class need to call this method to send new shape information to observer
     * Alternative for notifyObservers(String location);
     * @param shape -new shape
     */
    protected void paintUpdated(VecShape shape){
        for(VecCanvas observer: observers){
            (observer).updateShapes(shape);
        }
    }

    /**
     * This function is used to adjust clicked coordinate automatically when user is using grid
     * Returns closest grid x or y coordinate
     * @param coordinate x or y clicked point
     * @return adjusted x or y clicked point based on grid size
     */
    protected double adjustPoint(double coordinate){
        double gridInterval = imagePanel.getWidth() / (double)gridSize; // distance between each grid line
        return Math.floor((coordinate + gridInterval / 2) / gridInterval) * gridInterval ; // round down
    }

    /**
     * get line (plot) colour registered
     * @return plotColour
     */
    protected Color getLineColour() {return lineColour;}

    /**
     * get image panel registered
     * @return image panel
     */
    protected BufferedImage getImagePanel() {return imagePanel;}

    /**
     * get line width
     * @return line width
     */
    protected float getLineWidth() {return lineWidth;}

    /**
     * get grid boolean value
     * @return true if grid is on
     */
    protected boolean getIsGridOn() {return grid;}

    /**
     * get grid size (2 if grid divides canvas into two)
     * @return grid size
     */
    protected int getGridSize() {return gridSize;}
}
