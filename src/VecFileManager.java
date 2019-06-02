import VecInterface.Observer;
import VecInterface.Subject;
import VecShape.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class VecFileManager extends JMenuBar implements Subject {
    /**
     * Enums names of every component of the menu bar
     */
    public enum MenuNames{
        File("File"), Edit("Edit"), Undo("Undo"), Redo("Redo"), Save("Save"), Open("Open"), Clear("Clear"),
        Png("Export as PNG"), Bmp("Export as BMP"), Jpeg("Export as JPEG");
        private final String name;
        MenuNames(String s){
            name = s;
        }
    }
    // Variables
    private File vecFile;

    private ArrayList<Observer> observers = new ArrayList<>();

    private VecConverter converter = new VecConverter();

    /**
     * The constructor
     */
    public VecFileManager(){
        super();
    }

    /**
     * Create a Jmenu bar and add all the component into it
     * @return return the completed menu bar
     */
    public JMenuBar createJmenuBar(){
        JMenuBar bar = new JMenuBar();

        KeyStroke ksSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke ksOpen = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke ksUndo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke ksRedo = KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke ksClear = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
        JMenu fileMenu = new JMenu(MenuNames.File.name);
        JMenu editMenu = new JMenu(MenuNames.Edit.name);

        JMenuItem saveManager = new JMenuItem(MenuNames.Save.name);
        JMenuItem openManager = new JMenuItem(MenuNames.Open.name);
        JMenuItem undoManager = new JMenuItem(MenuNames.Undo.name);
        JMenuItem redoManager = new JMenuItem(MenuNames.Redo.name);
        JMenuItem clearManager = new JMenuItem(MenuNames.Clear.name);
        JMenuItem pngManager = new JMenuItem(MenuNames.Png.name);
        JMenuItem bmpManager = new JMenuItem(MenuNames.Bmp.name);
        JMenuItem jpegManager = new JMenuItem(MenuNames.Jpeg.name);

        saveManager.setAccelerator(ksSave);
        openManager.setAccelerator(ksOpen);
        undoManager.setAccelerator(ksUndo);
        redoManager.setAccelerator(ksRedo);
        clearManager.setAccelerator(ksClear);

        saveManager.addActionListener(createSaveListener());
        openManager.addActionListener(createOpenListener());
        undoManager.addActionListener(createUndoListener());
        redoManager.addActionListener(createRedoListener());
        clearManager.addActionListener(createClearListner());
        pngManager.addActionListener(createPngListener());
        bmpManager.addActionListener(createBmpListener());
        jpegManager.addActionListener(createJpegListener());

        fileMenu.add(saveManager);
        fileMenu.add(openManager);
        fileMenu.add(pngManager);
        fileMenu.add(bmpManager);
        fileMenu.add(jpegManager);
        editMenu.add(undoManager);
        editMenu.add(redoManager);
        editMenu.add(clearManager);

        bar.add(fileMenu);
        bar.add(editMenu);

        return bar;
    }

    /**
     * convert VecShape ArrayList to String ArrayList to output .vec file
     * @param shapes -shapes list to save
     */
    public void saveShape(ArrayList<VecShape> shapes){
        String content = converter.convertToString(shapes);
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("*.vec", "vec"));
        if (chooser.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
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

            }catch (IOException es){
                es.printStackTrace();
            }
        }
    }

    /**
     * convert BufferedImage to png format, and save output png file to selected location
     * @param image -an image to convert to png
     */
    public void exportAsPng(BufferedImage image){

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
        if (chooser.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getPath();
            try {
                if (!path.toLowerCase().endsWith(".png")) {
                    File file = new File(path + ".png");
                    ImageIO.write(image, "png", file);
                }
            } catch (IOException ex) {

            }
        }
    }


    /**
     * convert BufferedImage to bmp format, and save output bmp file to selected location
     * @param image -an image to convert to bmp
     */
    public void exportAsBmp(BufferedImage image){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("*.BMP", "BMP"));
        if (chooser.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getPath();
            try {
                if (!path.toLowerCase().endsWith(".BMP")) {
                    File file = new File(path + ".BMP");
                    ImageIO.write(image, "BMP", file);
                }
            } catch (IOException ex) {

            }
        }
    }

    /**
     * convert BufferedImage to jpeg format, and save output .jpg file to selected location
     * @param image -an image to convert to jpeg
     */
    public void exportAsJpeg(BufferedImage image){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("*.jpg", "jpg"));
        if (chooser.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getPath();
            try {
                if (!path.toLowerCase().endsWith(".jpg")) {
                    File file = new File(path + ".jpg");
                    ImageIO.write(image, "jpg", file);
                }
            } catch (IOException ex) {

            }
        }
    }


    /**
     * Action when jpeg export button is clicked
     * @return return the finished listener
     */
    private ActionListener createJpegListener() {
        ActionListener jpegListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyObservers("JpegExport");
            }
        };
        return jpegListener;
    }


    /**
     * Action when bmp export button is clicked
     * @return return the finished listener
     */
    private ActionListener createBmpListener(){
        ActionListener bmpListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { notifyObservers("BmpExport");
            }
        };
        return bmpListener;
    }

    /**
     * Action when png export button is clicked
     * @return return the finished listener
     */
    private ActionListener createPngListener(){
        ActionListener pngListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){ notifyObservers("PngExport");}
        };
        return pngListener;
    }

    /**
     * Action when save button is clicked
     * @return return the finished listener
     */
    private ActionListener createSaveListener(){
        ActionListener saveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyObservers("SaveBtn");
            }
        };
        return saveListener;
    }

    /**
     * Action when open button is clicked
     * @return
     */
    private ActionListener createOpenListener(){
        ActionListener openListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyObservers("OpenBtn");
            }
        };
        return openListener;
    }

    /**
     * Display a file navigation system (JFileChoose), then pass the opened contents as a string arraylist to VecConverter
     * VecConverter.converToShape converts this string arrayList to VecShape arrayList
     * @return return shapes list that is ready to draw on canvas
     */
    public ArrayList<VecShape> getOpenedShapes(){

        ArrayList<String> openList = new ArrayList<>();

        ArrayList<VecShape> shapesToOpen = new ArrayList<>();

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("*.vec", "vec"));
        if (chooser.showOpenDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
            vecFile = chooser.getSelectedFile();
            // load this file if is is a .vec file
            if (vecFile.getName().toLowerCase().endsWith(".vec")) {
                try {
                    InputStreamReader reader = new InputStreamReader(new FileInputStream(vecFile));
                    BufferedReader br = new BufferedReader(reader);
                    String text;
                    while ((text = br.readLine()) != null) {
                        openList.add(text);
                    }
                    shapesToOpen = converter.convertToShape(openList);
                    br.close();
                    reader.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (VecShapeException e2){
                    JOptionPane.showMessageDialog(null, "This vec file can not be read " +
                            "correctly.");

                    openList.clear();
                    e2.printStackTrace();
                }
                // else, show a message that tells the users the forate is not supported
            }else{
                JOptionPane.showMessageDialog(null, "File format not supported");
            }
        }

        return shapesToOpen;
    }



    /**
     * Behaviour of when undo button is clicked
     * @return undoListener that notifies observers that undo button is clicked
     */
    private ActionListener createUndoListener() {
        ActionListener undoListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyObservers("UndoBtn");
            }
        };

        return undoListener;
    }

    /**
     * Behaviour of when redo button is clicked
     * @return redoListener that notifies observers that redo button is clicked
     */
    private ActionListener createRedoListener(){
        ActionListener redoListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyObservers("RedoBtn");
            }
        };
        return redoListener;
    }

    /**
     * Behaviour of when clear button is clicked
     * @return clearListener that notifies observers that clear button is clicked
     */
    private ActionListener createClearListner() {
        ActionListener clearListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear this canvas?", "Clear Warning", JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.YES_OPTION){
                    notifyObservers("ClearBtn");
                }
            }
        };

        return clearListener;
    }

    /**
     * attach observer that wants to changes in this GUI component
     * basically, in this assignment, this observer is the canvas
     * @param observer new observer to be registered
     */
    @Override
    public void attachObserver(Observer observer){
        observers.add(observer);
    }

    /**
     * notify changes to observers
     * @param location -need to specify location as this application have multiple subject and one observer
     */
    @Override
    public void notifyObservers(String location){
        for (int i = 0; i < observers.size(); i++){
            observers.get(i).update(location);
        }
    }
}
