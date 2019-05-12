import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class VecFileManager extends JMenuItem {
    /**
     * Enums names of every component of the menu bar
     */
    public enum MenuNames{
        File("File"), Undo("Undo"), Save("Save"), New("New"), Open("Open");
        private final String name;
        private MenuNames(String s){
            name = s;
        }
    }
    // Variables
    private File vecFile;
    private ArrayList Savelist = new ArrayList();
    private ArrayList Openlist = new ArrayList();


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

        saveManager.addActionListener(getSaveListener());
        newManager.addActionListener(getNewListener());
        openManager.addActionListener(getOpenListener());


        menu.add(saveManager);
        menu.add(newManager);
        menu.add(openManager);


        bar.add(menu);
        bar.add(undoManager);

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
                    vecFile = chooser.getSelectedFile();
                    String path = vecFile.getPath();
                    try{
                        if (!path.toLowerCase().endsWith(".vec")){
                            vecFile = new File(path + ".vec");
                        }
                        FileOutputStream fos = new FileOutputStream(vecFile);
                        fos.close();
                        FileWriter fileWriter = new FileWriter(vecFile);
                        ////
                        fileWriter.write("\n");
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
                    try {
                        InputStreamReader reader = new InputStreamReader(new FileInputStream(vecFile));
                        BufferedReader br = new BufferedReader(reader);
                        String text = null;
                        while ((text = br.readLine()) != null){
                            Openlist.add(text);
                        }
                        // test
                        System.out.println(Openlist);
                        // test
                        br.close();
                        reader.close();

                    } catch (Exception e1) {
                       e1.printStackTrace();
                    }
                }
            }
        };
        return listener;
    }


}
