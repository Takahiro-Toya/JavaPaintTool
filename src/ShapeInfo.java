import java.awt.*;
import java.util.ArrayList;

public abstract class ShapeInfo {

    private double sx;
    private double sy;
    private double ex;
    private double ey;
    private VecPaint.Mode mode;
    private Color lineColour;
    private Color fillColour;
    private boolean fill;

    public ShapeInfo(double sx, double sy, double ex, double ey, Color color, Color fillColour, boolean fill, VecPaint.Mode mode){
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
        this.lineColour = color;
        this.mode = mode;
        this.fillColour = fillColour;
        this.fill = fill;
    }

    protected double getSx(){
        return sx;
    }

    protected double getSy(){
        return sy;
    }

    protected double getEx(){
        return ex;
    }

    protected double getEy(){
        return ey;
    }

    public VecPaint.Mode getMode(){
        return mode;
    }

    public Color getLineColour(){
        return lineColour;
    }

    public Color getFillColour(){
        return fillColour;
    }

    public boolean getFill(){
        return fill;
    }

    public abstract Shape getShape(int size);

    public abstract ArrayList<Double> getPoint();

    public abstract String toString();

}

