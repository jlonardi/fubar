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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

    private static JFrame frame;
    private ArrayList<View> views;
    private ArrayList<JPanel> panels;
    private Dimension frameSize, size;
    private ButtonTray buttonTray;
    private ReferenceListView listView;
    private AddReferenceView addReferenceView;
    public static IGUIReferenceManager manager;
    Dimension screenSize;
    Insets frameInsets;
    
    /**
     * Sets up a new frame for the application.
     * @param UImanager a manager to handle Reference resources.
     */
    public MainFrame(IGUIReferenceManager UImanager) {

        manager = UImanager;
        manager.loadFromDatastore();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        int frameWidth = (int) (0.4 * screenWidth);
        int frameHeight = (int) (0.4 * screenHeight);
        frameSize = new Dimension(frameWidth, frameHeight);
        
        initFrame();
        
        this.pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initPanels(JLayeredPane pane) {
        views = new ArrayList();
        panels = new ArrayList();

        listView = new ReferenceListView(this);
        listView.setVisible(true);
        listView.setName("listView");
        panels.add(listView);
        views.add(listView);


        addReferenceView = new AddReferenceView(this);
        addReferenceView.setVisible(false);
        addReferenceView.setName("addReferenceView");
        panels.add(addReferenceView);
        views.add(addReferenceView);

        for (JPanel panel : panels) {
            pane.add(panel);
        }
    }

    private void initFrame() {
        // Frame setup
        this.setTitle("fuBibTeX");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameSize);
        this.setMinimumSize(new Dimension(750, 500));

        // Setup for the main panel.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setVisible(true);

        // Setup the button tray
        buttonTray = new ButtonTray(this);
        buttonTray.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        buttonTray.setName("buttonTray");
        mainPanel.add(buttonTray);

        // Setup of the content pane for the frame.
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(this.getSize());
        layeredPane.setOpaque(true);
        mainPanel.add(layeredPane);

        initPanels(layeredPane);

        this.setContentPane(mainPanel);
        this.addComponentListener(new ComponentListener() {
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
        renderAll();
    }

    public void showView(ViewType type) {
        for (JPanel panel : panels) {
            panel.setVisible(false);
        }
        renderAll();
        switch (type) {
            case REFERENCE_LIST:
                this.listView.setVisible(true);
                break;
            case ADD_REFERENCE:
                this.addReferenceView.setVisible(true);
                break;
        }
    }

    public void renderAll() {
        // Resize of all the views on frame render.
        frameInsets = this.getInsets();
        size = this.getSize();
        size.height -= buttonTray.getSize().height;
        size.height -= frameInsets.top + frameInsets.bottom;
        size.width -= frameInsets.left + frameInsets.right;
        for (View view : views) {
            view.render(size);
        }
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this,message,title,messageType);
    }
    
    public void dataUpdated() {
        this.buttonTray.dataChanged();
    }
}