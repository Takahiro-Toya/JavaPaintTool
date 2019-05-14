import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Rectangle extends ShapeInfo{


    public Rectangle(double sx, double sy, double ex, double ey, Color lineColor, Color fillColour, boolean fill){
        super(sx, sy, ex, ey, lineColor, fillColour, fill, VecPaint.Mode.RECTANGLE);
    }


    @Override
    public Shape getShape(int size){
        return new Rectangle2D.Double(getSx() * size, getSy() * size, getEx() * size, getEy() * size);
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
