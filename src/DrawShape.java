import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawShape extends JPanel {

    private Observer observer;
    private Color lineColour;
    private BufferedImage imagePanel;
    private float lineWidth = 2f;

    public DrawShape(BufferedImage imagePanel, Color lineColour, Observer observer){
        this.imagePanel = imagePanel;
        this.lineColour = lineColour;
        this.observer = observer;
    }

    public void paintUpdated(ShapeInfo shape){
        ((VecPaint)observer).updateShapes(shape);
    }

    public Observer getObserver(){return observer;}

    public Color getLineColour() {return lineColour;}

    public BufferedImage getImagePanel() {return imagePanel;}

    public float getLineWidth() {return lineWidth;}

}
