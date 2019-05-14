import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Ellipse extends ShapeInfo{


    public Ellipse(double sx, double sy, double ex, double ey, Color color, Color fillColour, boolean fill){
        super(sx, sy, ex, ey, color, fillColour, fill, VecPaint.Mode.ELLIPSE);
    }


    @Override
    public Shape getShape(int size){
        return new Ellipse2D.Double(getSx() * size, getSy() * size, getEx() * size, getEy() * size);

    }

    @Override
    public ArrayList<Double> getPoint(){
        ArrayList<Double> arrayList = new ArrayList<>();
        arrayList.add(getSx());
        arrayList.add(getSy());
        arrayList.add(getEx());
        arrayList.add(getEy());
        return arrayList;
    }
}
