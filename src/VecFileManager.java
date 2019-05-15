import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleToIntFunction;

public class VecFileManager extends JMenuItem implements Subject {

    /**
     * Enums names of every component of the menu bar
     */
    public enum MenuNames{
        File("File"), Undo("Undo"), Save("Save"), New("New"), Open("Open"), Clear("Clear");
        private final String name;
        MenuNames(String s){
            name = s;
        }
    }
    // Variables
    private File vecFile;
    private ArrayList<String> Openlist = new ArrayList();
    private String content = "";

    private ArrayList<ShapeInfo> shapesToSave = new ArrayList<>();

    public static ArrayList<ShapeInfo> shapesToOpen = new ArrayList<>();

    private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * The constructor
     * @param name the name of the component
     */
    public VecFileManager(String name) {
        super(name);
    }

    /**
     * Get the current file
     */
    public File getFile(){
        return vecFile;
    }

    /**
     * Create a Jmenu bar and add all the component into it
     * @return return the completed menu bar
     */
    public JMenuBar createJmenu(){
        JMenuBar bar = new JMenuBar();

        JMenu menu = new JMenu(MenuNames.File.name);

        VecFileManager saveManager = new VecFileManager(MenuNames.Save.name);
        VecFileManager newManager = new VecFileManager(MenuNames.New.name);
        VecFileManager openManager = new VecFileManager(MenuNames.Open.name);
        VecFileManager undoManager = new VecFileManager(MenuNames.Undo.name);
        VecFileManager clearManager = new VecFileManager(MenuNames.Clear.name);

        saveManager.addActionListener(getSaveListener());
        newManager.addActionListener(getNewListener());
        openManager.addActionListener(getOpenListener());
        undoManager.addActionListener(undoShape());
        clearManager.addActionListener(clearShape());

        menu.add(saveManager);
        menu.add(newManager);
        menu.add(openManager);

        bar.add(menu);
        bar.add(undoManager);
        bar.add(clearManager);

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

    // havent finished
    public ActionListener getNewListener(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
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

    public void saveShapes(ArrayList<ShapeInfo> shapes){
        this.shapesToSave = shapes;
    }

    /**
     * convert the shapes to string and store them into a String called content
     */
    private void convertToString(){
        for (Observer o: observers){
            o.update("SaveBtn");
        }

        for (int a = 0; a < shapesToSave.size(); a++){
            ShapeInfo temp = shapesToSave.get(a);
            // detect if the line colour has changed
            if (a == 0 || (temp.getLineColour() != shapesToSave.get(a - 1).getLineColour())){
                content += "PEN " + String.format("#%02x%02x%02x", temp.getLineColour().getRed(), temp.getLineColour().getGreen(), temp.getLineColour().getBlue()).toUpperCase() + "\n";
            }
            // detect if should fill
            if (temp.getFill()){
                content += "FILL " + String.format("#%02x%02x%02x", temp.getFillColour().getRed(), temp.getFillColour().getGreen(), temp.getFillColour().getBlue()).toUpperCase() + "\n";
            }
            // add the coordinates
            content += temp.toString();
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
        ShapeInfo info = null;
        Color fillColour = null;
        Color lineColour = Color.black;
        boolean fill = false;

        for (String str: Openlist) {
            System.out.println(str);
            if (str.startsWith("PEN")){
                String string = "";
                for (int a = str.indexOf('#'); a < str.length(); a++){
                   string += str.charAt(a);
                }
                lineColour = hexToRgb(string);
            }else if (str.startsWith("FILL")){
                String string = "";
                for (int a = str.indexOf('#'); a < str.length(); a++){
                    string += str.charAt(a);
                }
                fill = true;
                fillColour = hexToRgb(string);
            }else{
                 String[] file = str.split("\\s+");
                 String shapeName = file[0];
                 switch (shapeName){
                     case "PLOT":
                         double x = Double.valueOf(file[1]);
                         double y = Double.valueOf(file[2]);
                         shapesToOpen.add(new Plot(x, y, lineColour));
                         break;
                     case "LINE":
                         double lsx = Double.valueOf(file[1]);
                         double lsy = Double.valueOf(file[2]);
                         double lex = Double.valueOf(file[3]);
                         double ley = Double.valueOf(file[4]);
                         shapesToOpen.add(new Line(lsx, lsy, lex, ley, lineColour));
                         break;
                     case "RECTANGLE":
                         double tsx = Double.valueOf(file[1]);
                         double tsy = Double.valueOf(file[2]);
                         double tex = Double.valueOf(file[3]);
                         double tey = Double.valueOf(file[4]);
                         shapesToOpen.add(new Rectangle(tsx, tsy, tex, tey, lineColour, fillColour, fill));
                         fill = false;
                         break;
                     case  "ELLIPSE":
                         double esx = Double.valueOf(file[1]);
                         double esy = Double.valueOf(file[2]);
                         double eex = Double.valueOf(file[3]);
                         double eey = Double.valueOf(file[4]);
                         shapesToOpen.add(new Ellipse(esx, esy, eex, eey, lineColour, fillColour, fill));
                         fill = false;
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
                        shapesToOpen.add(new Polygon(px, py, lineColour, fillColour, fill));
                        fill = false;
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
    public ActionListener undoShape() {
        ActionListener undoListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyObservers("UndoBtn");
            }
        };

        return undoListener;
    }

    public ActionListener clearShape() {
        ActionListener clearListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapesToSave.clear();
                shapesToOpen.clear();
                Openlist.clear();
                notifyObservers("ClearBtn");
            }
        };

        return clearListener;
    }

    public void attachObservers(Observer observer){
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
    public ArrayList<ShapeInfo> getShapesToOpen() {
        return shapesToOpen;
    }

}
