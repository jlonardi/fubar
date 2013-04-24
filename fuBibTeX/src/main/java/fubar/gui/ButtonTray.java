package fubar.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ButtonTray extends JPanel {

    private JButton save, exportBibtext, importBibtext;
    private ActionListener exportSingleListener, importListener;
    private final JFileChooser fc;
    private MainFrame mainFrame;

    public ButtonTray(MainFrame main) {

        mainFrame = main;
        setupListeners();
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("BibTeX files .bib", "bib");
        fc.setFileFilter(filter);

        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(layout);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Just for development
        //this.setBackground(Color.GREEN);

        save = new JButton("Save");
        save.setName("save");
        try {
            File imageFile = new File("src/main/resources/gui/save.png");
            BufferedImage img = ImageIO.read(imageFile);
            save.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.manager.saveToDatastore();
                save.setEnabled(false);
            }
        });
        save.setEnabled(false);
        this.add(save);

        exportBibtext = new JButton("Export");
        exportBibtext.setName("exportBibtext");
        try {
            File imageFile = new File("src/main/resources/gui/export.png");
            BufferedImage img = ImageIO.read(imageFile);
            exportBibtext.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
        exportBibtext.addActionListener(exportSingleListener);
        this.add(exportBibtext);

        importBibtext = new JButton("Import");
        importBibtext.setName("importBibtext");
        try {
            File imageFile = new File("src/main/resources/gui/import.png");
            BufferedImage img = ImageIO.read(imageFile);
            importBibtext.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
        importBibtext.addActionListener(importListener);
        this.add(importBibtext);

        this.setVisible(true);
    }

    private void setupListeners() {

        exportSingleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showSaveDialog(ButtonTray.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    String path = file.getAbsoluteFile() + ".bib";
                    file = new File(path);
                    MainFrame.manager.exportToFile(file);
                    System.out.println("Exporting: " + path);
                } else {
                    System.out.println("Save command cancelled by user.");
                }

            }
        };

        importListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(ButtonTray.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    String path = file.getAbsoluteFile() + "";
                    file = new File(path);
                    MainFrame.manager.importFromFile(file);
                    System.out.println("Importing: " + path);
                    mainFrame.renderAll();
                } else {
                    System.out.println("Open command cancelled by user.");
                }

            }
        };
    }

    public void dataChanged() {
        save.setEnabled(true);
    }
}
