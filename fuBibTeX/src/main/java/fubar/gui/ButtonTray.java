package fubar.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ButtonTray extends JPanel {

    private JButton save, exportBibtext, importBibtext;
    private final JFileChooser fc;
    private MainFrame mainFrame;

    public ButtonTray(MainFrame main) {

        mainFrame = main;
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
        exportBibtext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showSaveDialog(ButtonTray.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    file = new File(file.getName()+".bib");
                    MainFrame.manager.exportToFile(file);
                    System.out.println("Saving: " + file.getName());
                } else {
                    System.out.println("Save command cancelled by user.");
                }

            }
        });
        this.add(exportBibtext);

        importBibtext = new JButton("Import");
        importBibtext.setName("importBibtext");
        importBibtext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(ButtonTray.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    // -----------TODO---------
                    // GIVE FILE TO MANAGER FOR IMPORT
                    // ------------------------
                    
                    mainFrame.renderAll();
                    System.out.println("Opening: " + file.getName());
                } else {
                    System.out.println("Open command cancelled by user.");
                }

            }
        });
        this.add(importBibtext);

        this.setVisible(true);
    }

    public void dataChanged() {
        save.setEnabled(true);
    }
}
