import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Plot extends ShapeInfo {

    public Plot(double x, double y, Color color){
        super(x, y, x, y, color, null, false, VecPaint.Mode.PLOT);
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
        return arrayList;
    }

    @Override
    public String toString() {
        return "PLOT " + getSx() + " " + getSy() + "\n";
    }
}
