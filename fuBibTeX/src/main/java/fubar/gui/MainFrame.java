package fubar.gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MainFrame {

    private JFrame frame;
    private ArrayList<View> views;
    private ArrayList<JPanel> panels;
    private Dimension frameSize;
    private ButtonTray buttonTray;
    private ReferenceListView listView;
    private AddReferenceView addReferenceView;

    public MainFrame() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        int frameWidth = (int) (0.7 * screenWidth);
        int frameHeight = (int) (0.7 * screenHeight);
        frameSize = new Dimension(frameWidth, frameHeight);

        initFrame();
    }

    private void initPanels(JLayeredPane pane) {
        views = new ArrayList();
        panels = new ArrayList();

        listView = new ReferenceListView(this);
        listView.setVisible(true);
        panels.add(listView);
        views.add(listView);
        

        addReferenceView = new AddReferenceView(this);
        addReferenceView.setVisible(false);
        panels.add(addReferenceView);
        views.add(addReferenceView);
        
        for(JPanel panel : panels) pane.add(panel);
    }

    private void initFrame() {
        // Frame setup
        frame = new JFrame("fuBibTeX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameSize);
        
        // Setup for the main panel.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setVisible(true);
        
        // Setup the button tray
        buttonTray = new ButtonTray();
        buttonTray.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        mainPanel.add(buttonTray);
        
        // Setup of the content pane for the frame.
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(frame.getSize());
        layeredPane.setOpaque(true);
        mainPanel.add(layeredPane);
        
        initPanels(layeredPane);

        frame.setContentPane(mainPanel);
        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent evt) {
                // Resize of all the views on frame render.
                Dimension newSize = frame.getSize();
                newSize.height -= buttonTray.getSize().height;
                Insets frameInsets = frame.getInsets();
                for (View view : views) {
                    view.render(newSize, frameInsets);
                }
            }
            // No action needed for other events.
            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }
    
    public void showView(ViewType type) {
        for (JPanel panel : panels) {
            panel.setVisible(false);
        }
        switch(type) {
            case REFERENCE_LIST : this.listView.setVisible(true);
            case ADD_REFERENCE : this.addReferenceView.setVisible(true);
       }
    }

    public void draw() {
        frame.pack();
        frame.setVisible(true);
    }
}
