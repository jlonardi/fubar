package fubar.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ButtonTray extends JPanel implements View {
    
    JButton save, exportBibtext, importBibtext;
    
    public ButtonTray() {
        
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(layout);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Just for development
        //this.setBackground(Color.GREEN);
        
        save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.manager.saveToDatastore();
            }
        });
        this.add(save);
        
        exportBibtext = new JButton("Export");
        this.add(exportBibtext);
        
        importBibtext = new JButton("Import");
        this.add(importBibtext);
        
        this.setVisible(true);
    }
    @Override
    public void render(Dimension dimension) {
    }
}
