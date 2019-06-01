import VecShape.VecShape;

import java.awt.*;
import VecShape.*;

import java.util.ArrayList;

/**
 * This class provides some functions to produce .vec file and read a string input to generate VecShape
 */
public class VecConverter {

    /**
     * constructor
     */
    public VecConverter() { }

    /**
     * convert shapes to a sequence of string
     * @param shapes shapes arrayList to convert to String
     * @return a sequence of string
     */
    public String convertToString(ArrayList<VecShape> shapes) {
        String content = "";
        boolean isoff = true;
        VecShape temp = null;
        Color currentColour = null;

        for (int a = 0; a < shapes.size(); a++) {
            VecShape current = shapes.get(a);
            if (a != 0) {
                temp = shapes.get(a - 1);
            }
            // detect if the line colour has changed
            if (a == 0 || (current.getLineColour() != temp.getLineColour())) {
                content += "PEN " + String.format("#%02x%02x%02x", current.getLineColour().getRed(), current.getLineColour().getGreen(), current.getLineColour().getBlue()).toUpperCase() + "\n";
            }
            // detect if should fill
            if (a == 0 && current.getFill()) {
                content += "FILL " + String.format("#%02x%02x%02x", current.getFillColour().getRed(), current.getFillColour().getGreen(), current.getFillColour().getBlue()).toUpperCase() + "\n";
                isoff = false;
            } else if ((a != 0 && (current.getFillColour() != currentColour || isoff) && current.getFill())) {
                content += "FILL " + String.format("#%02x%02x%02x", current.getFillColour().getRed(), current.getFillColour().getGreen(), current.getFillColour().getBlue()).toUpperCase() + "\n";
                isoff = false;
                currentColour = current.getFillColour();
            } else if (current.getFill() == false && isoff == false) {
                content += "FILL OFF\n";
                isoff = true;
            }
            // add the coordinates
            content += current.toString();
        }
        return content;
    }

    /**
     * convert a hex string to rgb color that can be recognised by java
     *
     * @param colorStr a string of hex
     * @return color
     */
    public Color hexToRgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    /**
     * convert the input string to objects
     */
    public ArrayList<VecShape> convertToShape(ArrayList<String> openList) throws VecShapeException {
        ArrayList<VecShape> shapesToOpen = new ArrayList<>();
        Color fillColour = null;
        Color lineColour = Color.black;
        boolean fill = false;
        for (String str : openList) {
            if (str.startsWith("PEN")) {
                String string = "";
                for (int a = str.indexOf('#'); a < str.length(); a++) {
                    string += str.charAt(a);
                }
                lineColour = hexToRgb(string);
            } else if (str.startsWith("FILL")) {
                String string = "";
                if (str.endsWith("OFF")) {
                    fill = false;
                } else {
                    for (int a = str.indexOf('#'); a < str.length(); a++) {
                        string += str.charAt(a);
                    }
                    fill = true;
                    fillColour = hexToRgb(string);
                }
            } else {
                String[] file = str.split("\\s+");
                String shapeName = file[0];
                switch (shapeName) {
                    case "PLOT":
                        if (file.length != 3) {
                            throw new VecShapeException("Plot can not be construct correctly, "
                                    + "check if the .vec file is broken.");
                        }
                        double x = Double.valueOf(file[1]);
                        double y = Double.valueOf(file[2]);
                        shapesToOpen.add(new VecPlot(x, y, lineColour));
                        break;
                    case "LINE":
                        if (file.length != 5) {
                            throw new VecShapeException("Line can not be construct correctly, "
                                    + "check if the .vec file is broken.");
                        }
                        double lsx = Double.valueOf(file[1]);
                        double lsy = Double.valueOf(file[2]);
                        double lex = Double.valueOf(file[3]);
                        double ley = Double.valueOf(file[4]);
                        shapesToOpen.add(new VecLine(lsx, lsy, lex, ley, lineColour));
                        break;
                    case "RECTANGLE":
                        if (file.length != 5) {
                            throw new VecShapeException("Rectangle can not be construct correctly, "
                                    + "check if the .vec file is broken.");
                        }
                        double tsx = Double.valueOf(file[1]);
                        double tsy = Double.valueOf(file[2]);
                        double tex = Double.valueOf(file[3]);
                        double tey = Double.valueOf(file[4]);
                        shapesToOpen.add(new VecRectangle(tsx, tsy, tex, tey, lineColour, fillColour, fill));
                        break;
                    case "ELLIPSE":
                        if (file.length != 5) {
                            throw new VecShapeException("Ellipse can not be construct correctly, "
                                    + "check if the .vec file is broken.");
                        }
                        double esx = Double.valueOf(file[1]);
                        double esy = Double.valueOf(file[2]);
                        double eex = Double.valueOf(file[3]);
                        double eey = Double.valueOf(file[4]);
                        shapesToOpen.add(new VecEllipse(esx, esy, eex, eey, lineColour, fillColour, fill));
                        break;
                    case "POLYGON":
                        double[] px = new double[(file.length - 1) / 2];
                        double[] py = new double[(file.length - 1) / 2];
                        int cy = 0;
                        int cx = 0;
                        for (int a = 1; a < file.length - 1; a += 2) {
                            px[cx] = Double.valueOf(file[a]);
                            cx++;
                        }
                        for (int b = 2; b < file.length; b += 2) {
                            py[cy] = Double.valueOf(file[b]);
                            cy++;
                        }
                        if (px.length != py.length) {
                            throw new VecShapeException("Polygon can not be construct correctly, "
                                    + "check if the .vec file is broken");
                        }
                        shapesToOpen.add(new VecPolygon(px, py, lineColour, fillColour, fill));
                        break;
                    default:
                        break;
                }

            }
        }

        return shapesToOpen;
    }
}


