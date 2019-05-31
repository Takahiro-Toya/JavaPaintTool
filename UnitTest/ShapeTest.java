import static org.junit.jupiter.api.Assertions.*;

import VecShape.*;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Line;
import javax.swing.*;
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



    @Test
    public void testVecPlotPointsMinusToString(){
        double x = -1;
        double y = -1;
        VecPlot plot = new VecPlot(x, y, Color.YELLOW);
        String expected = "PLOT -1.0 -1.0\n";
        try {
            assertEquals(expected, plot.toString());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecLinePointsMinusToString(){
        double x1 = -10;
        double y1 = -100;
        double x2 = -30;
        double y2 = 10;
        VecLine line = new VecLine(x1, y1, x2, y2, Color.GRAY);
        String expected = "LINE -10.0 -100.0 -30.0 10.0\n";
        try {
            assertEquals(expected, line.toString());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecRectanglePointsMinusToString(){
        double x1 = -30;
        double y1 = -110;
        double x2 = -10;
        double y2 = -100;
        VecRectangle rect = new VecRectangle(x1, y1, x2, y2, Color.GRAY, null, false);
        String expected = "RECTANGLE -30.0 -110.0 -10.0 -100.0\n";
        try {
            assertEquals(expected, rect.toString());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecEllipsePointsMinusToString(){
        double x1 = -30;
        double y1 = -110;
        double x2 = -10;
        double y2 = -100;
        VecEllipse ellip = new VecEllipse(x1, y1, x2, y2, Color.GRAY, null, false);
        String expected = "ELLIPSE -30.0 -110.0 -10.0 -100.0\n";
        try {
            assertEquals(expected, ellip.toString());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecPolygonPointsMinusToString(){
        double[] x = {-5, -20, -0.8};
        double[] y = {-3, -80, -0.8};
        VecPolygon poly = new VecPolygon(x, y, Color.GRAY, null, false);
        String expected = "POLYGON -5.0 -3.0 -20.0 -80.0 -0.8 -0.8\n";
        try {
            assertEquals(expected, poly.toString());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecPlotPointsMinusGetShape(){
        double x = -1;
        double y = -1;
        VecPlot plot = new VecPlot(x, y, Color.YELLOW);
        double expected_x = -1.0;
        double expected_y = -1.0;

        try {
            assertEquals(expected_x, ((Line2D.Double)plot.getShape(1)).getX1());
            assertEquals(expected_y, ((Line2D.Double)plot.getShape(1)).getY1());
            assertEquals(expected_x, ((Line2D.Double)plot.getShape(1)).getX2());
            assertEquals(expected_y, ((Line2D.Double)plot.getShape(1)).getY2());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecLinePointsMinusGetShape(){
        double x1 = -10;
        double y1 = -100;
        double x2 = -30;
        double y2 = 10;
        VecLine line = new VecLine(x1, y1, x2, y2, Color.GRAY);
        double expected_x1 = -10;
        double expected_y1 = -100;
        double expected_x2 = -30;
        double expected_y2 = 10;
        try {
            assertEquals(expected_x1, ((Line2D.Double)line.getShape(1)).getX1());
            assertEquals(expected_y1, ((Line2D.Double)line.getShape(1)).getY1());
            assertEquals(expected_x2, ((Line2D.Double)line.getShape(1)).getX2());
            assertEquals(expected_y2, ((Line2D.Double)line.getShape(1)).getY2());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecRectanglePointsMinusGetShape(){
        double x1 = -30;
        double y1 = -110;
        double x2 = -10;
        double y2 = -100;
        VecRectangle rect = new VecRectangle(x1, y1, x2, y2, Color.GRAY, null, false);
        double expected_x1 = -30;
        double expected_y1 = -110;
        double expected_x2 = -10;
        double expected_y2 = -100;
        try {
            assertEquals(expected_x1, ((Rectangle2D.Double)rect.getShape(1)).getMinX());
            assertEquals(expected_y1, ((Rectangle2D.Double)rect.getShape(1)).getMinY());
            assertEquals(expected_x2, ((Rectangle2D.Double)rect.getShape(1)).getMaxX());
            assertEquals(expected_y2, ((Rectangle2D.Double)rect.getShape(1)).getMaxY());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecEllipsePointsMinusGetShape(){
        double x1 = -30;
        double y1 = -110;
        double x2 = -10;
        double y2 = -100;
        VecEllipse ellip = new VecEllipse(x1, y1, x2, y2, Color.GRAY, null, false);
        double expected_x1 = -30;
        double expected_y1 = -110;
        double expected_x2 = -10;
        double expected_y2 = -100;
        try {
            assertEquals(expected_x1, ((Ellipse2D.Double)ellip.getShape(1)).getMinX());
            assertEquals(expected_y1, ((Ellipse2D.Double)ellip.getShape(1)).getMinY());
            assertEquals(expected_x2, ((Ellipse2D.Double)ellip.getShape(1)).getMaxX());
            assertEquals(expected_y2, ((Ellipse2D.Double)ellip.getShape(1)).getMaxY());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecPolygonPointsMinusGetShape(){
        double[] x = {-5, -20, -0.8};
        double[] y = {-3, -80, -0.8};
        VecPolygon poly = new VecPolygon(x, y, Color.GRAY, null, false);
        try {
            for (int i = 0; i < x.length; i++){
                assertEquals((int)x[i], ((Polygon)poly.getShape(1)).xpoints[i]);
                assertEquals((int)y[i], ((Polygon)poly.getShape(1)).ypoints[i]);
            }

        } catch (Exception e){
            fail();
        }
    }
    @Test
    public void testVecRectangleStartEndSame(){
        double x1 = 10;
        double y1 = 100;
        double x2 = 10;
        double y2 = 100;
        VecRectangle rect = new VecRectangle(x1, y1, x2, y2, Color.GRAY, null, false);
        double expected_x1 = 10;
        double expected_y1 = 100;
        double expected_x2 = 10;
        double expected_y2 = 100;
        try {
            assertEquals(expected_x1, ((Rectangle2D.Double)rect.getShape(1)).getMinX());
            assertEquals(expected_y1, ((Rectangle2D.Double)rect.getShape(1)).getMinY());
            assertEquals(expected_x2, ((Rectangle2D.Double)rect.getShape(1)).getMaxX());
            assertEquals(expected_y2, ((Rectangle2D.Double)rect.getShape(1)).getMaxY());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecEllipseStartEndSame(){
        double x1 = 10;
        double y1 = 100;
        double x2 = 10;
        double y2 = 100;
        VecEllipse ellip = new VecEllipse(x1, y1, x2, y2, Color.GRAY, null, false);
        double expected_x1 = 10;
        double expected_y1 = 100;
        double expected_x2 = 10;
        double expected_y2 = 100;
        try {
            assertEquals(expected_x1, ((Ellipse2D.Double)ellip.getShape(1)).getMinX());
            assertEquals(expected_y1, ((Ellipse2D.Double)ellip.getShape(1)).getMinY());
            assertEquals(expected_x2, ((Ellipse2D.Double)ellip.getShape(1)).getMaxX());
            assertEquals(expected_y2, ((Ellipse2D.Double)ellip.getShape(1)).getMaxY());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testVecShapeThrowExceptionColorNotSpecified(){
        double x1 = 5;
        double y1 = 10;
        try {
            VecPlot plot = new VecPlot(x1, y1, null);
            fail();
        } catch (VecShapeException e){
            assertEquals("Must specify line colour.", e.getMessage());
        }
    }

    @Test
    public void testVecShapeFillableThrowExceptionFillColourNotSpecifiedWithFillModeTrue(){
        try{
            VecRectangle rect = new VecRectangle(10, 10, 20, 20, Color.RED, null, true);
            fail();
        } catch (VecShapeException e){
            assertEquals("Must specify fill colour when fill mode is true.", e.getMessage());
        }
    }

    @Test
    public void testVecRectangleThrowExceptionBecausePointsSetIncorrectly(){
        try {
            VecRectangle rect = new VecRectangle(10, 10, 0, 0, Color.GRAY, null, false);
        } catch (VecShapeException e){
            assertEquals("VecRectangle points need to be 'sx <= ex and sy <= ey'", e.getMessage());
        }
    }

    @Test
    public void testVecEllipseThrowExceptionBecausePointsSetIncorrectly(){
        try {
            VecEllipse ellipse = new VecEllipse(10, 10, 0, 0, Color.GRAY, null, false);
        } catch (VecShapeException e){
            assertEquals("VecEllipse points need to be 'sx <= ex and sy <= ey'", e.getMessage());
        }
    }

    @Test
    public void testVecPolyThrowExceptionBecauseNumPointsDoesNotMatch(){
        double[] x = {0.987656, 0.9876545678, 0.345678};
        double[] y = {0.3455678, 0.98765456, 0.87654567, 0.87654567};
        try {
            VecPolygon poly = new VecPolygon(x, y, Color.WHITE, null, false);
        } catch (VecShapeException e){
            assertEquals("VecPolygon's number of x and y points do not match.", e.getMessage());
        }
    }

    @Test
    public void testVecPlotResizeToFitCanvas(){
        double x = 0.55553248925;
        double y = 0.24534583453;
        int size = 357;
        VecPlot plot = new VecPlot(x, y, Color.WHITE);
        double expected_x = x * size;
        double expected_y = y * size;
        try {
            assertEquals(expected_x, ((Line2D.Double)plot.getShape(size)).getX1());
            assertEquals(expected_y, ((Line2D.Double)plot.getShape(size)).getY1());
            assertEquals(expected_x, ((Line2D.Double)plot.getShape(size)).getX2());
            assertEquals(expected_y, ((Line2D.Double)plot.getShape(size)).getY2());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testVecLineResizeToFitCanvas(){
        double x1 = 0.55553248925;
        double y1 = 0.24534583453;
        double x2 = 0.23456785345;
        double y2 = 0.98765678987;
        int size = 357;
        VecLine line = new VecLine(x1, y1, x2, y2, Color.WHITE);
        double expected_x1 = x1 * size;
        double expected_y1 = y1 * size;
        double expected_x2 = x2 * size;
        double expected_y2 = y2 * size;
        try {
            assertEquals(expected_x1, ((Line2D.Double)line.getShape(size)).getX1());
            assertEquals(expected_y1, ((Line2D.Double)line.getShape(size)).getY1());
            assertEquals(expected_x2, ((Line2D.Double)line.getShape(size)).getX2());
            assertEquals(expected_y2, ((Line2D.Double)line.getShape(size)).getY2());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testVecRectAndEllipseResizeToFitCanvas(){
        double x1 = 0.55553248925;
        double y1 = 0.24534583453;
        double x2 = 0.89876543456;
        double y2 = 0.98765678987;
        int size = 357;
        VecRectangle rect = new VecRectangle(x1, y1, x2, y2, Color.WHITE, null, false);
        VecEllipse ellip = new VecEllipse(x1, y1, x2, y2, Color.WHITE, null, false);
        double expected_x1 = x1 * size;
        double expected_y1 = y1 * size;
        double expected_x2 = x2 * size;
        double expected_y2 = y2 * size;
        try {
            assertEquals(expected_x1, ((Rectangle2D.Double)rect.getShape(size)).getMinX());
            assertEquals(expected_y1, ((Rectangle2D.Double)rect.getShape(size)).getMinY());
            assertEquals(expected_x2, ((Rectangle2D.Double)rect.getShape(size)).getMaxX());
            assertEquals(expected_y2, ((Rectangle2D.Double)rect.getShape(size)).getMaxY());
            assertEquals(expected_x1, ((Ellipse2D.Double)ellip.getShape(size)).getMinX());
            assertEquals(expected_y1, ((Ellipse2D.Double)ellip.getShape(size)).getMinY());
            assertEquals(expected_x2, ((Ellipse2D.Double)ellip.getShape(size)).getMaxX());
            assertEquals(expected_y2, ((Ellipse2D.Double)ellip.getShape(size)).getMaxY());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testVecPolyResizeToFitCanvas(){
        double[] x = {0.87654567, 0.98765678, 0.87654566787654, 0.65434565654};
        double[] y = {0.876545678, 0.9876545678, 0.99999999999, 0.9876234636};
        int size = 357;
        VecPolygon poly = new VecPolygon(x, y, Color.WHITE, null, false);
        int[] expected_x = new int[x.length];
        int[] expected_y = new int[y.length];
        for (int i = 0; i < x.length; i++){
            expected_x[i] = (int)(x[i] * size);
            expected_y[i] = (int)(y[i] * size);
        }
        try {
            for (int i = 0; i < x.length; i++){
                assertEquals(expected_x[i], (((Polygon)poly.getShape(size)).xpoints)[i]);
                assertEquals(expected_y[i], (((Polygon)poly.getShape(size)).ypoints)[i]);
            }
        } catch (Exception e) {
            fail();
        }
    }








}