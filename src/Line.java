import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Line extends ShapeInfo {

    public Line(double sx, double sy, double ex, double ey, Color color){
        super(sx, sy, ex, ey, color, null, false, VecPaint.Mode.LINE);
    }

    @Override
    public Shape getShape(int size){
        return new Line2D.Double(getSx() * size, getSy() * size, getEx() * size, getEy() * size);
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
