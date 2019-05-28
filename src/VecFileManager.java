import VecInterface.Observer;
import VecInterface.Subject;
import VecShape.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class VecFileManager extends JMenuBar implements Subject {

    /**
     * Enums names of every component of the menu bar
     */
    public enum MenuNames{
        File("File"), Edit("Edit"), Undo("Undo"), Redo("Redo"), Save("Save"), Open("Open"), Clear("Clear");
        private final String name;
        MenuNames(String s){
            name = s;
        }
    }
    // Variables
    private File vecFile;
    private ArrayList<String> Openlist = new ArrayList();
    private String content = "";

    private ArrayList<VecShape> shapesToSave = new ArrayList<>();

    private ArrayList<VecShape> shapesToOpen = new ArrayList<>();

    private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * The constructor
     */
    public VecFileManager() {
        super();
    }

    /**
     * Create a Jmenu bar and add all the component into it
     * @return return the completed menu bar
     */
    public JMenuBar createJmenuBar(){
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = new JMenu(MenuNames.File.name);
        JMenu editMenu = new JMenu(MenuNames.Edit.name);

        JMenuItem saveManager = new JMenuItem(MenuNames.Save.name);
        JMenuItem openManager = new JMenuItem(MenuNames.Open.name);
        JMenuItem undoManager = new JMenuItem(MenuNames.Undo.name);
        JMenuItem redoManager = new JMenuItem(MenuNames.Redo.name);
        JMenuItem clearManager = new JMenuItem(MenuNames.Clear.name);

        saveManager.addActionListener(getSaveListener());
        openManager.addActionListener(getOpenListener());
        undoManager.addActionListener(undoShape());
        redoManager.addActionListener(redoShape());
        clearManager.addActionListener(clearShape());

        fileMenu.add(saveManager);
        fileMenu.add(openManager);
        editMenu.add(undoManager);
        editMenu.add(redoManager);
        editMenu.add(clearManager);

        bar.add(fileMenu);
        bar.add(editMenu);

        return bar;
    }

    /**
     * Save the current file into the chosen directory
     * @return return the finished listener
     */
    public ActionListener getSaveListener(){
        ActionListener saveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.addChoosableFileFilter(new FileFilter() {
                    // set the default saving file extension to .vec
                    @Override
                    public boolean accept(File f) {
                        if (f.getName().endsWith(".vec")){
                            return true;
                        }else{
                            return f.getName().toLowerCase().endsWith(".vec");
                        }
                    }
                    @Override
                    public String getDescription() {
                        return "VEC Documents (*.vec)";
                    }
                });
                if (chooser.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
                    convertToString();
                    vecFile = chooser.getSelectedFile();
                    String path = vecFile.getPath();
                    try{
                        if (!path.toLowerCase().endsWith(".vec")){
                            vecFile = new File(path + ".vec");
                        }
                        FileOutputStream fos = new FileOutputStream(vecFile);
                        fos.close();
                        FileWriter fileWriter = new FileWriter(vecFile);
                        fileWriter.write(content);
                        fileWriter.close();
                        String name = chooser.getName(vecFile);

                    }catch (IOException es){
                        es.printStackTrace();
                    }
                }
            }
        };
        return saveListener;
    }

    /**
     * Load the content of the chosen file into a string
     * @return return this finished listener
     */
    public ActionListener getOpenListener(){
        java.awt.event.ActionListener listener = new ActionListener() {
            JFileChooser chooser = new JFileChooser();
            public void actionPerformed(ActionEvent e) {
                if (chooser.showOpenDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
                    vecFile = chooser.getSelectedFile();
                    // load this file if is is a .vec file
                    if (vecFile.getName().toLowerCase().endsWith(".vec")) {
                        try {
                            InputStreamReader reader = new InputStreamReader(new FileInputStream(vecFile));
                            BufferedReader br = new BufferedReader(reader);
                            String text;
                            while ((text = br.readLine()) != null) {
                                Openlist.add(text);
                            }
                            convertToShape();
                            br.close();
                            reader.close();

                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        // else, show a message that tells the users the forate is not supported
                    }else{
                        JOptionPane.showMessageDialog(null, "File format not supported");
                    }
                }
            }
        };
        return listener;
    }

    public void saveShapes(ArrayList<VecShape> shapes){
        this.shapesToSave = shapes;
    }

    /**
     * convert the shapes to string and store them into a String called content
     */
    private void convertToString() {
        boolean isoff = true;
        VecShape temp = null;
        Color currentColour = null;
        for (Observer o : observers) {
            o.update("SaveBtn");
        }
        for (int a = 0; a < shapesToSave.size(); a++) {
            VecShape current = shapesToSave.get(a);
            if (a != 0){
                temp = shapesToSave.get(a - 1);
            }
            // detect if the line colour has changed
            if (a == 0 || (current.getLineColour() != temp.getLineColour())) {
                content += "PEN " + String.format("#%02x%02x%02x", current.getLineColour().getRed(), current.getLineColour().getGreen(), current.getLineColour().getBlue()).toUpperCase() + "\n";
            }
            // detect if should fill
            if (a == 0 && current.getFill()){
                content += "FILL " + String.format("#%02x%02x%02x", current.getFillColour().getRed(), current.getFillColour().getGreen(), current.getFillColour().getBlue()).toUpperCase() + "\n";
                isoff = false;
            }
            else if ((a != 0 && (current.getFillColour() != currentColour || isoff) && current.getFill())) {
                content += "FILL " + String.format("#%02x%02x%02x", current.getFillColour().getRed(), current.getFillColour().getGreen(), current.getFillColour().getBlue()).toUpperCase() + "\n";
                isoff = false;
                currentColour = current.getFillColour();
            }
            else if (current.getFill() == false && isoff == false){
                content += "FILL OFF\n";
                isoff = true;
            }
            // add the coordinates
            content += current.toString();
        }
    }


    /**
     * convert a hex string to rgb color that can be recognised by java
     * @param colorStr a string of hex
     * @return color
     */
    private Color hexToRgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    /**
     * convert the input string to objects
     */
    public void convertToShape(){
        VecShape info = null;
        Color fillColour = null;
        Color lineColour = Color.black;
        boolean fill = false;

        for (String str: Openlist) {
            if (str.startsWith("PEN")){
                String string = "";
                for (int a = str.indexOf('#'); a < str.length(); a++){
                   string += str.charAt(a);
                }
                lineColour = hexToRgb(string);
            }else if (str.startsWith("FILL")){
                String string = "";
                if (str.endsWith("OFF")){
                    fill = false;
                }
                else {
                    for (int a = str.indexOf('#'); a < str.length(); a++) {
                        string += str.charAt(a);
                    }
                    fill = true;
                    fillColour = hexToRgb(string);
                }
            }else{
                 String[] file = str.split("\\s+");
                 String shapeName = file[0];
                 switch (shapeName){
                     case "PLOT":
                         double x = Double.valueOf(file[1]);
                         double y = Double.valueOf(file[2]);
                         shapesToOpen.add(new VecPlot(x, y, lineColour));
                         break;
                     case "LINE":
                         double lsx = Double.valueOf(file[1]);
                         double lsy = Double.valueOf(file[2]);
                         double lex = Double.valueOf(file[3]);
                         double ley = Double.valueOf(file[4]);
                         shapesToOpen.add(new VecLine(lsx, lsy, lex, ley, lineColour));
                         break;
                     case "RECTANGLE":
                         double tsx = Double.valueOf(file[1]);
                         double tsy = Double.valueOf(file[2]);
                         double tex = Double.valueOf(file[3]);
                         double tey = Double.valueOf(file[4]);
                         shapesToOpen.add(new VecRectangle(tsx, tsy, tex, tey, lineColour, fillColour, fill));
                         break;
                     case  "ELLIPSE":
                         double esx = Double.valueOf(file[1]);
                         double esy = Double.valueOf(file[2]);
                         double eex = Double.valueOf(file[3]);
                         double eey = Double.valueOf(file[4]);
                         shapesToOpen.add(new VecEllipse(esx, esy, eex, eey, lineColour, fillColour, fill));
                         break;
                     case "POLYGON":
                        double[] px = new double[(file.length - 1) /2];
                        double[] py = new double[(file.length - 1) / 2];

                        int cy = 0;
                        int cx = 0;
                        for (int a = 1; a < file.length - 1; a += 2){
                            px[cx] = Double.valueOf(file[a]);
                            cx++;
                        }
                        for (int b = 2; b < file.length; b += 2){
                            py[cy] = Double.valueOf(file[b]);
                            cy++;
                        }
                        shapesToOpen.add(new VecPolygon(px, py, lineColour, fillColour, fill));
                        break;
                     default:
                         break;
                 }

            }
        }

        for(Observer o: observers){
            o.update("OpenBtn");
        }
    }

    /**
     * Undo the last shape drawn
     */
    private ActionListener undoShape() {
        ActionListener undoListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyObservers("UndoBtn");
            }
        };

        return undoListener;
    }

    private ActionListener redoShape(){
        ActionListener redoListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyObservers("RedoBtn");
            }
        };
        return redoListener;
    }

    private ActionListener clearShape() {
        ActionListener clearListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapesToSave.clear();
                shapesToOpen.clear();
                Openlist.clear();
                content = "";
                notifyObservers("ClearBtn");
            }
        };

        return clearListener;
    }

    public void attachObserver(Observer observer){
        observers.add(observer);
    }

    public void notifyObservers(String location){
        for (int i = 0; i < observers.size(); i++){
            observers.get(i).update(location);
        }
    }

    /**
     * return undoShapeList
     */
    public ArrayList<VecShape> getShapesToOpen() {
        return shapesToOpen;
    }

}
