import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * java standard Polygon does not support Double precise, so this class allows Polygon object to have double values.
 * But, when drawing a polygon object, this class draws an integer precise polygon.
 */
public class Polygon extends ShapeInfo {

    private int[] xVertices; // for tempporary drawing
    private int[] yVertices;

    private double[] vecPointXs; // for vec file
    private double[] vecPointYs;


    public Polygon(double[] xVertices, double[] yVertices, Color lineColour, Color fillColour, boolean fill){
        super(xVertices[0], yVertices[0], xVertices[xVertices.length - 1], yVertices[yVertices.length - 1], lineColour, fillColour, fill, VecPaint.Mode.POLYGON);
        vecPointXs = new double[xVertices.length];
        vecPointXs = new double[yVertices.length];
        vecPointXs = xVertices;
        vecPointYs = yVertices;

        this.xVertices = new int[xVertices.length];
        this.yVertices = new int[yVertices.length];

        for(int i = 0; i < xVertices.length; i++){
            vecPointXs[i] = xVertices[i];
            vecPointYs[i] = yVertices[i];
            this.xVertices[i] = (int)xVertices[i];
            this.yVertices[i] = (int)yVertices[i];
        }
    }

//    public void drawPoly(BufferedImage imagePanel){
//        Graphics2D g2d = imagePanel.createGraphics();
//        g2d.setStroke(new BasicStroke((lineWidth)));
//        if(fill){
//            g2d.setColor(fillColour);
//            g2d.fillPolygon(xVertices, yVertices, xVertices.length);
//        }
//        g2d.setColor(getLineColour());
//        g2d.drawPolygon(xVertices, yVertices, xVertices.length);
//        g2d.drawLine((int)getEx(), (int)getEy(), (int)getSx(), (int)getSy());
//    }

    @Override
    public java.awt.Polygon getShape(int size){
        int[] XsForDrawing = new int[xVertices.length];
        int[] YsForDrawing = new int[yVertices.length];
        for(int i = 0; i < xVertices.length; i++){
            XsForDrawing[i] = (int)(vecPointXs[i] * size);
            YsForDrawing[i] = (int)(vecPointYs[i] * size);
        }

        return new java.awt.Polygon(XsForDrawing, YsForDrawing, XsForDrawing.length);
    }

    @Override
    public ArrayList<Double> getPoint(){
        ArrayList<Double> arrayList = new ArrayList<>();
        for (int i = 0; i < vecPointXs.length; i++){
            arrayList.add(vecPointXs[i]);
            arrayList.add(vecPointYs[i]);
        }
        return arrayList;
    }

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
