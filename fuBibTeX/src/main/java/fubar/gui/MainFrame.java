package fubar.gui;

import fubar.fubibtex.ui_adapter.IGUIReferenceManager;
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
    public static IGUIReferenceManager manager;

    public MainFrame(IGUIReferenceManager UImanager) {

        manager = UImanager;
        manager.loadFromDatastore();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        int frameWidth = (int) (0.4 * screenWidth);
        int frameHeight = (int) (0.4 * screenHeight);
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

        for (JPanel panel : panels) {
            pane.add(panel);
        }
    }

    private void initFrame() {
        // Frame setup
        frame = new JFrame("fuBibTeX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameSize);
        frame.setMinimumSize(new Dimension(500, 500));

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
                renderAll();
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
        renderAll();
        switch (type) {
            case REFERENCE_LIST:
                this.listView.setVisible(true);
                return;
            case ADD_REFERENCE:
                this.addReferenceView.setVisible(true);
                return;
        }
    }

    public void draw() {
        frame.pack();
        frame.setVisible(true);
    }

    public void renderAll() {
        // Resize of all the views on frame render.
        Insets frameInsets = frame.getInsets();
        Dimension size = frame.getSize();
        size.height -= buttonTray.getSize().height;
        size.height -= frameInsets.top + frameInsets.bottom;
        size.width -= frameInsets.left + frameInsets.right;
        for (View view : views) {
            view.render(size);
        }
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
