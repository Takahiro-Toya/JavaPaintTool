import java.awt.*;
import java.util.ArrayList;

/**
 * Java standard Polygon does not support Double precise drawing, so this class allows VecPolygon object to have double values.
 * But, when drawing a polygon object, this class draws an integer precise polygon.
 * So, this class stores a polygon's coordinates as double precise value for producing .vec file, and
 * when drawing an polygon on a canvas, convert stored double values to int values.
 */
public class VecPolygon extends VecShape {

    private double[] vecPointXs; // .vec file scale (0.0 ~ 1.0)
    private double[] vecPointYs; // .vec file scale (0.0 ~ 1.0)

    /**
     * constructor
     * @param xVertices all x coordinates as array. value must be .vec file scale (0.0 ~ 1.0)
     * @param yVertices all y coordinates as array. value must be .vec file scale (0.0 ~ 1.0)
     * @param penColour pen colour
     * @param fillColour fill colour
     * @param fill set true if this polygon needs to be filled
     */
    public VecPolygon(double[] xVertices, double[] yVertices, Color penColour, Color fillColour, boolean fill){
        super(xVertices[0], yVertices[0], xVertices[xVertices.length - 1], yVertices[yVertices.length - 1],
                penColour, fillColour, fill, Mode.POLYGON);
        vecPointXs = new double[xVertices.length];
        vecPointXs = new double[yVertices.length];
        vecPointXs = xVertices;
        vecPointYs = yVertices;
    }

    /**
     * Get Ellipse object that is ready to be drawn as image
     * @param size -basically canvas width or height
     * @return Shape object
     */
    @Override
    public java.awt.Polygon getShape(int size){
        int[] XsForDrawing = new int[vecPointXs.length];
        int[] YsForDrawing = new int[vecPointYs.length];
        for(int i = 0; i < vecPointYs.length; i++){
            XsForDrawing[i] = (int)(vecPointXs[i] * size);
            YsForDrawing[i] = (int)(vecPointYs[i] * size);
        }

        return new java.awt.Polygon(XsForDrawing, YsForDrawing, XsForDrawing.length);
    }


    /**
     * Get points of this Polygon
     * @return point as Arraylist
     */
    @Override
    public ArrayList<Double> getPoint(){
        ArrayList<Double> arrayList = new ArrayList<>();
        for (int i = 0; i < vecPointXs.length; i++){
            arrayList.add(vecPointXs[i]);
            arrayList.add(vecPointYs[i]);
        }
        return arrayList;
    }

    /**
     * Converts this object information to string  to produce .vec file
     * @return string that is ready to write to .vec file
     */
    @Override
    public String toString() {
        String str = "POLYGON";
        for (int a = 0; a < vecPointXs.length; a++) {
            if (a != vecPointXs.length - 1){
                str = str + " " + vecPointXs[a] + " " + vecPointYs[a];
            }else {
                str = str + " " + vecPointXs[a] + " " + vecPointYs[a] + "\n";
            }
        }
        return str;
    }
}
