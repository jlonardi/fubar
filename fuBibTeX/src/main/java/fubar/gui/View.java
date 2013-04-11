package fubar.gui;

import java.awt.Dimension;
import javax.swing.JPanel;

public abstract class View extends JPanel{
    /**
     * Resize and draws the view in relation to the dimensions of the frame
     * which has been passed as parameters.
     * @param dimension the dimension that the view is rendered in relation to
     */
    abstract void render(Dimension dimension);
    
}
