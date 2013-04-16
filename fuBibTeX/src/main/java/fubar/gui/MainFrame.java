package fubar.gui;

import fubar.fubibtex.ui_adapter.IGUIReferenceManager;
import static fubar.gui.ViewType.ADD_REFERENCE;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

    private static JFrame frame;
    private ArrayList<View> views;
//    private ArrayList<JPanel> panels;
    private Dimension frameSize, size;
    private ButtonTray buttonTray;
    private ReferenceListView listView;
    private AddReferenceView addReferenceView;
    private ModifyReferenceView modifyReferenceView;
    public static IGUIReferenceManager manager;
    private Dimension screenSize;
    private Insets frameInsets;
    
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

        listView = new ReferenceListView(this);
        listView.setVisible(true);
        listView.setName("listView");
        views.add(listView);
        pane.add(listView);


        addReferenceView = new AddReferenceView(this);
        addReferenceView.setVisible(false);
        addReferenceView.setName("addReferenceView");
        views.add(addReferenceView);
        pane.add(addReferenceView);
        
        modifyReferenceView = new ModifyReferenceView(this);
        modifyReferenceView.setVisible(false);
        modifyReferenceView.setName("modifyReferenceView");
        views.add(modifyReferenceView);
        pane.add(modifyReferenceView);
    }

    private void initFrame() {
        // Frame setup
        this.setTitle("fuBibTeX");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameSize);
        this.setMinimumSize(new Dimension(750, 550));
        ImageIcon imgIcon = new ImageIcon("src/main/resources/gui/icon.png");
        this.setIconImage(imgIcon.getImage());
        

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
        for (View view : views) {
            view.setVisible(false);
        }
        renderAll();
        switch (type) {
            case REFERENCE_LIST:
                this.listView.setVisible(true);
                break;
            case ADD_REFERENCE:
                this.addReferenceView.setVisible(true);
                break;
            case MODIFY_REFERENCE:
                this.modifyReferenceView.setEditedReference(
                        this.listView.getSelectedReference());
                this.modifyReferenceView.setVisible(true);
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