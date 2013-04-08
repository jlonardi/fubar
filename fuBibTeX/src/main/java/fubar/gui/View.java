package fubar.gui;

import java.awt.Dimension;

public interface View {
    /**
     * Resize and draws the view in relation to the dimensions of the frame
     * which has been passed as parameters.
     * @param dimension the dimension that the view is rendered in relation to
     */
    void render(Dimension dimension);
    
}
