import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Defines Ellipse object
 * Stores ellipse information
 */
public class VecEllipse extends VecShape {


    /**
     * constructor
     * @param sx start x .vec file scale (0.0 ~ 1.0)
     * @param sy start y .vec file scale (0.0 ~ 1.0)
     * @param ex end x .vec file scale (0.0 ~ 1.0)
     * @param ey end y .vec file scale (0.0 ~ 1.0)
     * @param penColour pen colour
     * @param fillColour fill colour
     * @param fill set true if the ellipse needs to be filled
     */
    public VecEllipse(double sx, double sy, double ex, double ey, Color penColour, Color fillColour, boolean fill){
        super(sx, sy, ex, ey, penColour, fillColour, fill, Mode.ELLIPSE);
    }


    /**
     * Get Ellipse object that is ready to be drawn as image
     * @param size -basically canvas width or height
     * @return Shape object
     */
    @Override
    public Shape getShape(int size){
        return new Ellipse2D.Double(getSx() * size, getSy() * size, (getEx() - getSx()) * size, (getEy() - getSy()) * size);

    }

    /**
     * Get points of this Ellipse
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
     * Converts this object information to string  to produce .vec file
     * @return string that is ready to write to .vec file
     */
    @Override
    public String toString(){
        return "ELLIPSE " + getSx() + " " + getSy() + " " + getEx() + " " + getEy() + "\n";
    }

}
