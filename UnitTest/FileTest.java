import VecShape.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FileTest {
    VecConverter converter;

    @BeforeEach
    private void initial(){converter = new VecConverter();}


     @Test
     private void testHexToRgb(){
         assertEquals(Color.orange.toString(), converter.hexToRgb("#FFC800").toString());
     }

     @Test
     public void testConvertEllipseToStringWithoutFill(){
         ArrayList<VecShape> shapes = new ArrayList<VecShape>();
         shapes.add(new VecEllipse(0.0, 0.0, 1.0, 1.0, Color.gray, Color.PINK, false));
         assertEquals("PEN #808080\n" + "ELLIPSE 0.0 0.0 1.0 1.0\n", converter.convertToString(shapes));
     }
     @Test
     public void testConvertLineToString(){
         ArrayList<VecShape> shapes = new ArrayList<VecShape>();
         shapes.add(new VecLine(0.0, 0.0, 1.0, 1.0, Color.green));
         assertEquals("PEN #00FF00\n" + "LINE 0.0 0.0 1.0 1.0\n", converter.convertToString(shapes));
     }

    @Test
    public void testConvertPlotToString(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        shapes.add(new VecPlot(1.0, 0.5, Color.YELLOW));
        assertEquals("PEN #FFFF00\n" + "PLOT 1.0 0.5\n", converter.convertToString(shapes));
    }

    @Test
    public void testConvertPolygonToStringWithoutFill(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        double[] x = new double[3];
        double[] y = new double[3];
        x[0] = 1; y[0] = 1;
        x[1] = 0; y[1] = 0;
        x[2] = 0.5; y[2] = 0.5;
        shapes.add(new VecPolygon(x, y, Color.GRAY, Color.PINK, false));
        assertEquals("PEN #808080\n" + "POLYGON 1.0 1.0 0.0 0.0 0.5 0.5\n", converter.convertToString(shapes));
    }

     @Test
     public void testConvertRectangleToStringWithoutFill(){
         ArrayList<VecShape> shapes = new ArrayList<VecShape>();
         shapes.add(new VecRectangle(0, 0, 1, 1, Color.YELLOW, Color.ORANGE, false));
         assertEquals("PEN #FFFF00\n" + "RECTANGLE 0.0 0.0 1.0 1.0\n", converter.convertToString(shapes));
     }

    @Test
    public void testConvertEllipseToStringWithFill(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        shapes.add(new VecEllipse(0.0, 0.0, 1.0, 1.0, Color.gray, Color.PINK, true));
        assertEquals("PEN #808080\n" + "FILL #FFAFAF\n" + "ELLIPSE 0.0 0.0 1.0 1.0\n", converter.convertToString(shapes));
    }

    @Test
    public void testConvertPolygonToStringWithFill(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        double[] x = new double[3];
        double[] y = new double[3];
        x[0] = 1; y[0] = 1;
        x[1] = 0; y[1] = 0;
        x[2] = 0.5; y[2] = 0.5;
        shapes.add(new VecPolygon(x, y, Color.GRAY, Color.PINK, true));
        assertEquals("PEN #808080\n" + "FILL #FFAFAF\n" + "POLYGON 1.0 1.0 0.0 0.0 0.5 0.5\n", converter.convertToString(shapes));
    }

     @Test
     public void testConvertReactangleToStringWithFill(){
         ArrayList<VecShape> shapes = new ArrayList<VecShape>();
         shapes.add(new VecRectangle(0, 0, 1, 1, Color.YELLOW, Color.ORANGE, true));
         assertEquals("PEN #FFFF00\n" + "FILL #FFC800\n" + "RECTANGLE 0.0 0.0 1.0 1.0\n", converter.convertToString(shapes));
     }

     @Test
     public void testConvertShapesToStringWithoutFill(){
         ArrayList<VecShape> shapes = new ArrayList<VecShape>();
         double[] x = new double[3];
         double[] y = new double[3];
         x[0] = 1; y[0] = 1;
         x[1] = 0; y[1] = 0;
         x[2] = 0.5; y[2] = 0.5;
         shapes.add(new VecPolygon(x, y, Color.GRAY, Color.PINK, false));
         shapes.add(new VecRectangle(0, 0, 1, 1, Color.YELLOW, Color.ORANGE, false));
         shapes.add(new VecEllipse(0.0, 0.0, 1.0, 1.0, Color.gray, Color.PINK, false));
         shapes.add(new VecLine(0.0, 0.0, 1.0, 1.0, Color.green));
         shapes.add(new VecPlot(1.0, 0.5, Color.YELLOW));
         assertEquals("PEN #808080\n" +
                 "POLYGON 1.0 1.0 0.0 0.0 0.5 0.5\n" +
                 "PEN #FFFF00\n" +
                 "RECTANGLE 0.0 0.0 1.0 1.0\n" +
                 "PEN #808080\n" +
                 "ELLIPSE 0.0 0.0 1.0 1.0\n" +
                 "PEN #00FF00\n" +
                 "LINE 0.0 0.0 1.0 1.0\n" +
                 "PEN #FFFF00\n" +
                 "PLOT 1.0 0.5\n", converter.convertToString(shapes));
     }

     @Test
     public void testConvertShapesToStringWithDifferentFillColours(){
         ArrayList<VecShape> shapes = new ArrayList<VecShape>();
         double[] x = new double[3];
         double[] y = new double[3];
         x[0] = 1; y[0] = 1;
         x[1] = 0; y[1] = 0;
         x[2] = 0.5; y[2] = 0.5;
         shapes.add(new VecPolygon(x, y, Color.GRAY, Color.PINK, true));
         shapes.add(new VecRectangle(0, 0, 1, 1, Color.YELLOW, Color.ORANGE, true));
         shapes.add(new VecEllipse(0.0, 0.0, 1.0, 1.0, Color.PINK, Color.PINK, true));
         assertEquals("PEN #808080\n" +
                 "FILL #FFAFAF\n" +
                 "POLYGON 1.0 1.0 0.0 0.0 0.5 0.5\n" +
                 "PEN #FFFF00\n" +
                 "FILL #FFC800\n" +
                 "RECTANGLE 0.0 0.0 1.0 1.0\n" +
                 "PEN #FFAFAF\n" +
                 "FILL #FFAFAF\n" +
                 "ELLIPSE 0.0 0.0 1.0 1.0\n", converter.convertToString(shapes));
     }

    @Test
    public void testConvertShapesToStringWithSameFillColours(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        double[] x = new double[3];
        double[] y = new double[3];
        x[0] = 1; y[0] = 1;
        x[1] = 0; y[1] = 0;
        x[2] = 0.5; y[2] = 0.5;
        shapes.add(new VecPolygon(x, y, Color.YELLOW, Color.PINK, true));
        shapes.add(new VecRectangle(0, 0, 1, 1, Color.YELLOW, Color.ORANGE, true));
        shapes.add(new VecEllipse(0.0, 0.0, 1.0, 1.0, Color.YELLOW, Color.PINK, true));
        assertEquals("PEN #FFFF00\n" +
                "FILL #FFAFAF\n" +
                "POLYGON 1.0 1.0 0.0 0.0 0.5 0.5\n" +
                "FILL #FFC800\n" +
                "RECTANGLE 0.0 0.0 1.0 1.0\n" +
                "FILL #FFAFAF\n" +
                "ELLIPSE 0.0 0.0 1.0 1.0\n", converter.convertToString(shapes));
    }

    @Test
    public void testConvertShapesToStringWithFillAndNoFill(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        double[] x = new double[3];
        double[] y = new double[3];
        x[0] = 1; y[0] = 1;
        x[1] = 0; y[1] = 0;
        x[2] = 0.5; y[2] = 0.5;
        shapes.add(new VecPolygon(x, y, Color.GRAY, Color.PINK, true));
        shapes.add(new VecRectangle(0, 0, 1, 1, Color.YELLOW, Color.ORANGE, false));
        shapes.add(new VecEllipse(0.0, 0.0, 1.0, 1.0, Color.PINK, Color.PINK, true));
        assertEquals("PEN #808080\n" +
                "FILL #FFAFAF\n" +
                "POLYGON 1.0 1.0 0.0 0.0 0.5 0.5\n" +
                "PEN #FFFF00\n" +
                "FILL OFF\n" +
                "RECTANGLE 0.0 0.0 1.0 1.0\n" +
                "PEN #FFAFAF\n" +
                "FILL #FFAFAF\n" +
                "ELLIPSE 0.0 0.0 1.0 1.0\n", converter.convertToString(shapes));
    }

    @Test
    public void testConvertEmptyShapeList(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        assertEquals("", converter.convertToString(shapes));
    }

    @Test
    public void testConvertEllipseStringToShapeWithoutFill(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        shapes.add(new VecEllipse(0.0, 0.0, 1.0, 1.0, Color.ORANGE, Color.PINK, false));
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #FFC800\n");
        str.add("ELLIPSE 0.0 0.0 1.0 1.0\n");
        try {
            assertEquals(converter.convertToString(shapes), converter.convertToString(converter.convertToShape(str)));
        }catch (VecShapeException e){
            fail();
        }
    }

    @Test
     public void testConvertLineStringToShape(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        shapes.add(new VecLine(0.0, 0.0, 0.9, 0.9, Color.YELLOW));
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #FFFF00\n");
        str.add("LINE 0.0 0.0 0.9 0.9\n");
        try {
            assertEquals(converter.convertToString(shapes), converter.convertToString(converter.convertToShape(str)));
        }catch (VecShapeException e){
            fail();
        }
    }

    @Test
    public void testConvertPlotStringToShape(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        shapes.add(new VecPlot(0.0, 1.0, Color.blue));
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #0000FF\n");
        str.add("PLOT 0.0 1.0\n");
        try {
            assertEquals(converter.convertToString(shapes), converter.convertToString(converter.convertToShape(str)));
        }catch (VecShapeException e){
            fail();
        }
    }

    @Test
    public void testConvertPolygonStringToShapeListWithoutFill(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        double[] x = new double[3];
        double[] y = new double[3];
        x[0] = 1; y[0] = 1;
        x[1] = 0; y[1] = 0;
        x[2] = 0.5; y[2] = 0.5;
        shapes.add(new VecPolygon(x, y, Color.GRAY, Color.PINK, false));
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #808080\n");
        str.add("POLYGON 1.0 1.0 0.0 0.0 0.5 0.5\n");
        try {
            assertEquals(converter.convertToString(shapes), converter.convertToString(converter.convertToShape(str)));
        }catch (VecShapeException e){
            fail();
        }
    }

    @Test
    public void testConvertRectangleStringToShapeWithoutFill(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        shapes.add(new VecRectangle(0.0, 0.0, 0.8, 0.9, Color.BLUE, Color.PINK, false));
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #0000FF\n");
        str.add("RECTANGLE 0.0 0.0 0.8 0.9\n");
        try {
            assertEquals(converter.convertToString(shapes), converter.convertToString(converter.convertToShape(str)));
        }catch (VecShapeException e){
            fail();
        }
    }

    @Test
    public void testConvertEllipseStringToShapeWithFill(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        shapes.add(new VecEllipse(0.0, 0.0, 1.0, 1.0, Color.ORANGE, Color.PINK, true));
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #FFC800\n");
        str.add("FILL #FFAFAF");
        str.add("ELLIPSE 0.0 0.0 1.0 1.0\n");
        try {
            assertEquals(converter.convertToString(shapes), converter.convertToString(converter.convertToShape(str)));
        }catch (VecShapeException e){
            fail();
        }
    }

    @Test
    public void testConvertPolygonStringToShapeListWithFill(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        double[] x = new double[3];
        double[] y = new double[3];
        x[0] = 1; y[0] = 1;
        x[1] = 0; y[1] = 0;
        x[2] = 0.5; y[2] = 0.5;
        shapes.add(new VecPolygon(x, y, Color.GRAY, Color.PINK, true));
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #808080\n");
        str.add("FILL #FFAFAF");
        str.add("POLYGON 1.0 1.0 0.0 0.0 0.5 0.5\n");
        try {
            assertEquals(converter.convertToString(shapes), converter.convertToString(converter.convertToShape(str)));
        }catch (VecShapeException e){
            fail();
        }
    }

    @Test
    public void testConvertRectangleStringToShapeWithFill(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        shapes.add(new VecRectangle(0.0, 0.0, 0.8, 0.9, Color.BLUE, Color.PINK, true));
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #0000FF\n");
        str.add("FILL #FFAFAF");
        str.add("RECTANGLE 0.0 0.0 0.8 0.9\n");
        try {
            assertEquals(converter.convertToString(shapes), converter.convertToString(converter.convertToShape(str)));
        }catch (VecShapeException e){
            fail();
        }
    }

    @Test
    public void testConvertStringsToShapes(){
        ArrayList<VecShape> shapes = new ArrayList<VecShape>();
        double[] x = new double[3];
        double[] y = new double[3];
        x[0] = 1; y[0] = 1;
        x[1] = 0; y[1] = 0;
        x[2] = 0.5; y[2] = 0.5;
        shapes.add(new VecPolygon(x, y, Color.GRAY, Color.PINK, true));
        shapes.add(new VecRectangle(0.0, 0.0, 0.8, 0.9, Color.BLUE, Color.PINK, true));
        shapes.add(new VecPlot(0.0, 1.0, Color.blue));
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #808080\n");
        str.add("FILL #FFAFAF");
        str.add("POLYGON 1.0 1.0 0.0 0.0 0.5 0.5\n");
        str.add("PEN #0000FF\n");
        str.add("FILL #FFAFAF");
        str.add("RECTANGLE 0.0 0.0 0.8 0.9\n");
        str.add("PLOT 0.0 1.0\n");
        try {
            assertEquals(converter.convertToString(shapes), converter.convertToString(converter.convertToShape(str)));
        }catch (VecShapeException e){
            fail();
        }
    }

    @Test
    public void testEllipseException(){
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #FFC800\n");
        str.add("FILL #FFAFAF");
        str.add("ELLIPSE 0.0 0.0 1.0\n");
        try {
            converter.convertToShape(str);
        }catch (VecShapeException e){
            assertEquals("Ellipse can not be construct correctly, check if the .vec file is broken.", e.getMessage());
        }
    }

    @Test
    public void testLineException(){
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #FFC800\n");
        str.add("LINE 0.0 0.0 1.0\n");
        try {
            converter.convertToShape(str);
        }catch (VecShapeException e){
            assertEquals("Line can not be construct correctly, "
                    + "check if the .vec file is broken.", e.getMessage());
        }
    }

    @Test
    public void testPlotException(){
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #FFC800\n");
        str.add("PLOT 0.0 0.0 1.0\n");
        try {
            converter.convertToShape(str);
        }catch (VecShapeException e){
            assertEquals("Plot can not be construct correctly, "
                    + "check if the .vec file is broken.", e.getMessage());
        }
    }

    @Test
    public void testPolygonException(){
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #808080\n");
        str.add("FILL #FFAFAF");
        str.add("POLYGON 1.0 1.0 0.0 0.5 0.5\n");
        try {
            converter.convertToShape(str);
        }catch (VecShapeException e){
            assertEquals("Polygon can not be construct correctly, "
                    + "check if the .vec file is broken.", e.getMessage());
        }
    }

    @Test
    public void testRectangleException(){
        ArrayList<String> str = new ArrayList<>();
        str.add("PEN #808080\n");
        str.add("FILL #FFAFAF");
        str.add("RECTANGLE 1.0 1.0 0.0 0.5 0.5\n");
        try {
            converter.convertToShape(str);
        }catch (VecShapeException e){
            assertEquals("Rectangle can not be construct correctly, "
                    + "check if the .vec file is broken.", e.getMessage());
        }
    }

}
