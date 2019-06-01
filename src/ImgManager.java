import VecInterface.Observer;
import VecInterface.Subject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImgManager extends JMenuItem implements Subject {

    private ArrayList<Observer> observers = new ArrayList<>();

    public ImgManager(String name) {
        super(name);
    }

    public ImgManager createExport(){
        ImgManager manager = new ImgManager("Export");
        manager.addActionListener(getExportListener());
        return manager;
    }

    public ActionListener getExportListener(){
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyObservers("Export");
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
                if (chooser.showSaveDialog(new Label()) == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getPath();
                    try {
                        if (!path.toLowerCase().endsWith(".png")) {
                            File file = new File(path + ".png");
                            ImageIO.write(null, "png", file);
                        }
                    } catch (IOException ex) {

                    }
                }
            }
        };
        return listener;
    }

    @Override
    public void attachObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String location) {
        for (int i = 0; i < observers.size(); i++){
            observers.get(i).update(location);
        }
    }
}
