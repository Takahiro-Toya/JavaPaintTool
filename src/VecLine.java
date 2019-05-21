import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Defines Plot object
 * Stores plot information
 */
public class VecLine extends VecShape {

    /**
     * constructor
     * @param sx start x .vec file scale (0.0 ~ 1.0)
     * @param sy start y .vec file scale (0.0 ~ 1.0)
     * @param ex end x .vec file scale (0.0 ~ 1.0)
     * @param ey end y .vec file scale (0.0 ~ 1.0)
     * @param color pen colour
     */
    public VecLine(double sx, double sy, double ex, double ey, Color color){
        super(sx, sy, ex, ey, color, null, false, Mode.LINE);
    }

    /**
     * Get Line object that is ready to be drawn as image
     * @param size -basically canvas width or height
     * @return Shape object
     */
    @Override
    public Shape getShape(int size){
        return new Line2D.Double(getSx() * size, getSy() * size, getEx() * size, getEy() * size);
    }


    /**
     * Get points of this Line
     * @return point as Arraylist
     */
    @Override
    public ArrayList<Double> getPoint(){
        ArrayList<Double> arrayList = new ArrayList<>();
        arrayList.add(getSx());
        arrayList.add(getSy());
        arrayList.add(getEx());
        arrayList.add(getEy());
        return arrayList;
    }

    /**
     * Converts this object information to string to produce .vec file
     * @return string that is ready to write to .vec file
     */
    @Override
    public String toString() {
        return "LINE " + getSx() + " " + getSy() + " " + getEx() + " " + getEy() + "\n";
    }
}
