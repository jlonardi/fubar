package fubar.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonTray extends JPanel implements View {
    
    JButton save, exportBibtext, importBibtext;
    
    public ButtonTray() {
        
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(layout);
        
        // Just for development
        this.setBackground(Color.GREEN);
        
        save = new JButton("Save");
        this.add(save);
        
        exportBibtext = new JButton("Export");
        this.add(exportBibtext);
        
        importBibtext = new JButton("Import");
        this.add(importBibtext);
        
        this.setVisible(true);
    }
    @Override
    public void render(Dimension dimension, Insets insets) {
    }
}
