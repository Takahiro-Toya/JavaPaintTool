import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class VecFileManager extends JMenuItem {
    public enum MenuNames{
        File("File"), Undo("Undo"), Save("Save"), New("New"), Open("Open");
        private final String name;
        private MenuNames(String s){
            name = s;
        }
    }
    private File vecFile;
    String fileName;



    public VecFileManager(String name) {
        super(name);
    }

    public File getFile(){
        return vecFile;
    }

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




    public ActionListener getSaveListener(){
        JFileChooser chooser = new JFileChooser();
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chooser.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
                    vecFile = chooser.getSelectedFile();
                    String name = chooser.getName(vecFile);
                    vecFile = new File(chooser.getCurrentDirectory(), name + ".txt");
                    try{
                        FileOutputStream fos = new FileOutputStream(vecFile);
                        fos.close();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
            }
        };
        return listener;
    }

    public ActionListener getNewListener(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
    }

    public ActionListener getOpenListener(){
        java.awt.event.ActionListener listener = new ActionListener() {
            JFileChooser chooser = new JFileChooser();
            public void actionPerformed(ActionEvent e) {
                if (chooser.showOpenDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
                    vecFile = chooser.getSelectedFile();
                    fileName = chooser.getName(vecFile);
                    try {
                        InputStreamReader reader = new InputStreamReader(new FileInputStream(vecFile));
                        BufferedReader br = new BufferedReader(reader);
                    } catch (FileNotFoundException e1) {

                    }
                }
            }
        };
        return listener;
    }


}
