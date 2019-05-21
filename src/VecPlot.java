import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Defines Plot object
 * Stores plot information
 */
public class VecPlot extends VecShape {

    /**
     * Constructor
     * @param x x coordinate .vec file scale (0.0 ~ 1.0)
     * @param y y coordinate .vec file scale (0.0 ~ 1.0)
     * @param color plot colour
     */
    public VecPlot(double x, double y, Color color){
        super(x, y, x, y, color, null, false, Mode.PLOT);
    }

    /**
     * Get Plot (Line) object that is ready to be drawn as image
     * @param size -basically canvas width or height
     * @return Shape object
     */
    @Override
    public Shape getShape(int size){
        return new Line2D.Double(getSx() * size, getSy() * size, getEx() * size, getEy() * size);
    }

    /**
     * Get points of this Plot
     * @return point as Arraylist
     */
    @Override
    public ArrayList<Double> getPoint(){
        ArrayList<Double> arrayList = new ArrayList<>();
        arrayList.add(getSx());
        arrayList.add(getSy());
        return arrayList;
    }

    /**
     * Converts this object information to string to produce .vec file
     * @return string that is ready to write to .vec file
     */
    @Override
    public String toString() {
        return "PLOT " + getSx() + " " + getSy() + "\n";
    }
}
