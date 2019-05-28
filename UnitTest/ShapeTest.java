import static org.junit.jupiter.api.Assertions.*;

import VecShape.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.util.Random;

class ShapeTest {

    private static Random random = new Random();

    @Test
    public void testVecPlotGetShape(){
        double x = 0.4;
        double y = 0.4;
        VecPlot plotObj = new VecPlot(x, y, Color.YELLOW);
        int size = random.nextInt(350);
        Line2D expected = new Line2D.Double(x * size, y * size, x * size, y * size);
        Line2D result = (Line2D)plotObj.getShape(size);
        try {
            assertEquals(expected.getP1(), result.getP1());
            assertEquals(expected.getP2(), result.getP2());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecLineGetShape(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        VecLine lineObj = new VecLine(x1, y1, x2, y2, Color.YELLOW);
        int size = random.nextInt(350);
        Line2D expected = new Line2D.Double(x1 * size, y1 * size, x2 * size, y2 * size);
        Line2D result = (Line2D)lineObj.getShape(size);
        try {
            assertEquals(expected.getP1(), result.getP1());
            assertEquals(expected.getP2(), result.getP2());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecRectGetShape(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        VecRectangle rectObj = new VecRectangle(x1, y1, x2, y2, Color.YELLOW, Color.LIGHT_GRAY, true);
        int size = random.nextInt(350);
        Rectangle2D expected = new Rectangle2D.Double(x1 * size, y1 * size, (x2 - x1) * size, (y2 - y1) * size);
        Rectangle2D result = (Rectangle2D)rectObj.getShape(size);
        try {
            assertEquals(expected.getMinX(), result.getMinX());
            assertEquals(expected.getMinY(), result.getMinY());
            assertEquals(expected.getMaxX(), result.getMaxX());
            assertEquals(expected.getMaxY(), result.getMaxY());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecEllipseGetShape(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        VecEllipse elipObj = new VecEllipse(x1, y1, x2, y2, Color.YELLOW, Color.LIGHT_GRAY, true);
        int size = random.nextInt(350);
        Ellipse2D expected = new Ellipse2D.Double(x1 * size, y1 * size, (x2 - x1) * size, (y2 - y1) * size);
        Ellipse2D result = (Ellipse2D)elipObj.getShape(size);
        try {
            assertEquals(expected.getMinX(), result.getMinX());
            assertEquals(expected.getMinY(), result.getMinY());
            assertEquals(expected.getMaxX(), result.getMaxX());
            assertEquals(expected.getMaxY(), result.getMaxY());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecPolygonGetShape(){
        double[] x = {0.5, 0.2, 0.8};
        double[] y = {0.3, 0.8, 0.8};
        VecPolygon polyObj = new VecPolygon(x, y, Color.YELLOW, Color.LIGHT_GRAY, true);
        int size = random.nextInt(350);
        int[] xi = {(int)(x[0] * size), (int)(x[1] * size), (int)(x[2] * size)};
        int[] yi = {(int)(y[0] * size), (int)(y[1] * size), (int)(y[2] * size)};
        Polygon expected = new Polygon(xi, yi, 3);
        Polygon result = (Polygon)polyObj.getShape(size);
        try {
            for (int i = 0; i < 3; i++){
                assertEquals(expected.xpoints[i], result.xpoints[i]);
                assertEquals(expected.ypoints[i], result.ypoints[i]);
            }
            assertEquals(expected.npoints, result.npoints);
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testGetPenColour(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        Color colour = Color.YELLOW;
        VecPlot plot = new VecPlot(x1, y1, colour);
        VecLine line = new VecLine(x1, y1, x2, y2, colour);
        VecRectangle rect = new VecRectangle(x1, y1, x2, y2, colour, Color.LIGHT_GRAY, true);
        VecEllipse ellip = new VecEllipse(x1, y1, x2, y2, colour, Color.LIGHT_GRAY, true);

        try {
            assertEquals(colour, plot.getLineColour());
            assertEquals(colour, line.getLineColour());
            assertEquals(colour, rect.getLineColour());
            assertEquals(colour, ellip.getLineColour());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testGetFillColour(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        Color colour = Color.RED;
        Color fillColour = Color.BLUE;
        VecPlot plot = new VecPlot(x1, y1, colour);
        VecLine line = new VecLine(x1, y1, x2, y2, colour);
        VecRectangle rect = new VecRectangle(x1, y1, x2, y2, colour, fillColour, true);
        VecEllipse ellip = new VecEllipse(x1, y1, x2, y2, colour, fillColour, true);

        try {
            assertNull(plot.getFillColour());
            assertNull(line.getFillColour());
            assertEquals(fillColour, rect.getFillColour());
            assertEquals(fillColour, ellip.getFillColour());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testGetFillColourNull(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        Color colour = Color.RED;
        Color fillColour = Color.BLUE;
        VecRectangle rect = new VecRectangle(x1, y1, x2, y2, colour, fillColour, false);
        VecEllipse ellip = new VecEllipse(x1, y1, x2, y2, colour, fillColour, false);

        try {
            assertNull(rect.getFillColour());
            assertNull(ellip.getFillColour());
        } catch (Exception e){
            fail();
        }
    }

    // when dragging from bottom left to top right
    @Test
    public void testVecRectangleFail(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        try {
            VecEllipse elipObj = new VecEllipse(x1, y2, x2, y1, Color.YELLOW, Color.LIGHT_GRAY, true);
        } catch (Exception e){
            return;
        }
    }





    // when dragging from bottom right to top left
    @Test
    public void testVecEllipseFail(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        try {
            VecEllipse elipObj = new VecEllipse(x2, y2, x1, y1, Color.YELLOW, Color.LIGHT_GRAY, true);
        } catch (Exception e){
            return;
        }
    }



    @Test
    public void testToStringPlot(){
        double x = 0.4;
        double y = 0.4;
        VecPlot plotObj = new VecPlot(x, y, Color.YELLOW);
        String expected = "PLOT " + x + " " + y + "\n";
        try {
            assertEquals(expected, plotObj.toString());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testToStringLine(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        VecLine lineObj = new VecLine(x1, y1, x2, y2, Color.YELLOW);
        String expected = "LINE " + x1 + " " + y1 + " " + x2 + " " + y2 + "\n";
        try {
            assertEquals(expected, lineObj.toString());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testToStringRectangle(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        VecRectangle rectObj = new VecRectangle(x1, y1, x2, y2, Color.YELLOW, Color.LIGHT_GRAY, true);
        String expected = "RECTANGLE " + x1 + " " + y1 + " " + x2 + " " + y2 + "\n";
        try {
            assertEquals(expected, rectObj.toString());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testToStringEllipse(){
        double x1 = 0.4;
        double y1 = 0.4;
        double x2 = 0.8;
        double y2 = 0.8;
        VecEllipse rectObj = new VecEllipse(x1, y1, x2, y2, Color.YELLOW, Color.LIGHT_GRAY, true);
        String expected = "ELLIPSE " + x1 + " " + y1 + " " + x2 + " " + y2 + "\n";
        try {
            assertEquals(expected, rectObj.toString());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testToStringPolygon(){
        double[] x = {0.5, 0.2, 0.8};
        double[] y = {0.3, 0.8, 0.8};
        VecPolygon polyObj = new VecPolygon(x, y, Color.YELLOW, Color.LIGHT_GRAY, true);
        String expected = "POLYGON 0.5 0.3 0.2 0.8 0.8 0.8\n";
        try {

            assertEquals(expected, polyObj.toString());
        } catch (Exception e){
            fail();
        }
    }


}