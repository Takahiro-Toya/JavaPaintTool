import VecInterface.Observer;
import VecShape.VecShape;

import java.awt.*;
import VecShape.*;
import java.util.ArrayList;

public class VecConvertor {

    private ArrayList<String> Openlist;
    private String content;
    private static ArrayList<VecShape> shapesToSave;

    private ArrayList<VecShape> shapesToOpen;

    private ArrayList<Observer> observers;

    public VecConvertor(ArrayList<String> openlist, String content, ArrayList<VecShape> shapesToSave, ArrayList<VecShape> shapesToOpen, ArrayList<Observer> observers) {
        Openlist = openlist;
        this.content = content;
        this.shapesToSave = shapesToSave;
        this.shapesToOpen = shapesToOpen;
        this.observers = observers;
    }

    public VecConvertor() { }

    /**
     * convert the shapes to string and store them into a String called content
     * @return the content of the converted string
     */
    public String convertToString() {
        boolean isoff = true;
        VecShape temp = null;
        Color currentColour = null;
        for (Observer o : observers) {
            o.update("SaveBtn");
        }
        for (int a = 0; a < shapesToSave.size(); a++) {
            VecShape current = shapesToSave.get(a);
            if (a != 0) {
                temp = shapesToSave.get(a - 1);
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
    public void convertToShape() throws VecShapeException {
        Color fillColour = null;
        Color lineColour = Color.black;
        boolean fill = false;
        for (String str : Openlist) {
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

        for (Observer o : observers) {
            o.update("OpenBtn");
        }
    }

    /**
     * register array of VecShape as a save list, call this method from canvas class to pass shape list to file manager
     * @param shapes shapes to save
     */
    public void saveShapes(ArrayList<VecShape> shapes) {
        this.shapesToSave = shapes;
    }

    public void clearShapes(){
        this.shapesToSave.clear();
        this.shapesToOpen.clear();
        Openlist.clear();
        content = "";
    }
}


