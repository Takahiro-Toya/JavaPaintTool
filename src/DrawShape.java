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

    /**
     * constructor
     * @param imagePanel -BufferedImage in which an object is drawn
     * @param plotColour -plot colour
     * @param observer Observer -class that wants to receive a drawn object information.
     *                    Usually, a class that has a canvas to draw this object (rectangle)
     */
    public DrawShape(BufferedImage imagePanel, Color plotColour, Observer observer){
        this.imagePanel = imagePanel;
        this.plotColour = plotColour;
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
