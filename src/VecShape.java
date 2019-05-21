import java.awt.*;
import java.util.ArrayList;

/**
 * Defines a shape object
 * holds start/ end points, line / fill colour, fill mode
 * start & end points must be vector scale that is 0.0 ~ 1.0
 */
public abstract class VecShape {

    enum Mode{PLOT, LINE, RECTANGLE, ELLIPSE, POLYGON};

    private double sx;
    private double sy;
    private double ex;
    private double ey;
    private Mode mode;
    private Color lineColour;
    private Color fillColour;
    private boolean fill;

    /**
     * Constructor
     * @param sx -start x point of shape: scale 0.0 ~ 1.0
     * @param sy -start y point of shape: scale 0.0 ~ 1.0
     * @param ex -end x point of shape: scale 0.0 ~ 1.0
     * @param ey -end y point of shape: scale 0.0 ~ 1.0
     * @param lineColor -line colour
     * @param fillColour -fill colour,
     * @param fill -set true if the shpae needs to be filled
     * @param mode -drawing mode
     */
    public VecShape(double sx, double sy, double ex, double ey, Color lineColor, Color fillColour, boolean fill, Mode mode){
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
        this.lineColour = lineColor;
        this.mode = mode;
        this.fillColour = fillColour;
        this.fill = fill;
    }

    /**
     * get start x point
     * @return start point
     */
    public double getSx(){
        return sx;
    }

    /**
     * get start y point
     * @return start point
     */
    public double getSy(){
        return sy;
    }

    /**
     * get end x point
     * @return end point
     */
    public double getEx(){
        return ex;
    }

    /**
     * get end y point
     * @return end point
     */
    public double getEy(){
        return ey;
    }

    /**
     * get mode of this object
     * @return mode
     */
    public Mode getMode(){
        return mode;
    }

    /**
     * get line colour
     * @return line colour of this object
     */
    public Color getLineColour(){
        return lineColour;
    }


    /**
     * get fill colour
     * @return fill colour of this object
     */
    public Color getFillColour(){
        return fillColour;
    }

    /**
     * get if the object is filled
     * @return fill mode of this object
     */
    public boolean getFill(){
        return fill;
    }

    /**
     * get Shape that is resized to visible size (drawing size)
     * (this object stored vector scale coordinates, so resize it by multiplying these coordinates by the canvas size)
     * @param size -basically canvas width or height
     * @return shape that is resized and ready to draw
     */
    public abstract Shape getShape(int size);

    /**
     * get All points of this object
     * @return ArrayList
     */
    public abstract ArrayList<Double> getPoint();

    /**
     * convert shape information to .vec file output format
     * @return .vec format
     */
    public abstract String toString();

}

