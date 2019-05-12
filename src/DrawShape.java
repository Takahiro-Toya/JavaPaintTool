import java.awt.Point;
import java.awt.Color;

public interface DrawShape {

    void writeVecFile();

    void setColour(Color color);

    Point getStartPoint();

    Point getEndPoint();


}
