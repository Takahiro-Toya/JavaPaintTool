import DrawVecShape.VecCanvas;
import VecShape.VecShape;
import DrawVecShape.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawShapeTest implements VecCanvas{


    private ArrayList<VecShape> shapes;
    private BufferedImage tempImage;
    private VecCanvas canvas;

    @Override
    public void updateShapes(VecShape shape){
        shapes.add(shape);
    }


    @BeforeEach
    public void setup_test(){
        shapes = new ArrayList<VecShape>();
        tempImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    }

    @Test
    public void testDrawPlotInitialise(){
        DrawShape canvas = new DrawPlot(tempImage, Color.WHITE, false, 10, this);
    }

}
