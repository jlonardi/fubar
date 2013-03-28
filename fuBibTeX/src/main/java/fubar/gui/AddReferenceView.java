package fubar.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JPanel;

public class AddReferenceView extends JPanel implements View{

    private MainFrame frame;
    
    public AddReferenceView(MainFrame frame) {
        this.frame = frame;
        // Just for developing the layout
        this.setBackground(Color.red);
    }
    
    private JPanel setupTypeListPanel() {
        JPanel panel = new JPanel();
        return null;
    }
    @Override
    public void render(Dimension dimension, Insets inset) {
        this.setSize(dimension);
    }
    
}
