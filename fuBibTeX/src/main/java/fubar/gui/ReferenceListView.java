package fubar.gui;

import fubar.fubibtex.references.Reference;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ReferenceListView extends View {

    private JList referenceList, exportList;
    private JScrollPane listScroller, exportScroller;
    private JPanel listPanel, controlPanel, exportPanel, exportListHolder, buttonPanel;
    private JButton addReferenceButton, addToExportList, removeFromExportList;
    private JLabel exportLabel;
    private MainFrame frame;

    public ReferenceListView(final MainFrame frame) {
        this.frame = frame;
        this.setLayout(null);

        // Set up the top of the view that holds the list
        listPanel = new JPanel();
        listPanel.setLayout(null);

        // Set up the central area of the view that holds the control buttons
        controlPanel = new JPanel();
        controlPanel.setMinimumSize(new Dimension(150,20));
        
        // Set up the bottom of the view that holds the export list.
        exportPanel = new JPanel();
        //exportPanel.setLayout(null);
        exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
        exportListHolder = new JPanel();
        exportListHolder.setLayout(null);
        //exportListHolder.setLayout(new BoxLayout(exportListHolder, BoxLayout.Y_AXIS));

        // Layout visualization for development
//        listPanel.setBackground(Color.red);
//        controlPanel.setBackground(Color.BLUE);
//        exportPanel.setBackground(Color.yellow);

        // Set up the list and the scroller
        referenceList = new JList(MainFrame.manager.getReferencesFromDatastore().toArray());
        referenceList.setLayoutOrientation(JList.VERTICAL);
        referenceList.setName("referenceList");
        listScroller = new JScrollPane(referenceList);
        listScroller.setName("listScroller");
        listPanel.add(listScroller);
        
        // Set up the list for the references to be exported
        exportLabel = new JLabel("          References to be exported");
        exportPanel.add(exportLabel);
        exportList = new JList(MainFrame.manager.getExportList().toArray());
        exportList.setLayoutOrientation(JList.VERTICAL);
        exportList.setName("exportList");
        exportScroller = new JScrollPane(exportList);
        exportScroller.setName("exportScroller");
        
        exportListHolder.add(exportScroller);
        
        exportPanel.add(exportListHolder);

        // Set up the buttons
        Dimension buttonSize = new Dimension(140, 35);
        addReferenceButton = new JButton("New reference");
        addReferenceButton.setName("addReferenceButton");
        addReferenceButton.setPreferredSize(buttonSize);
        addReferenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showView(ViewType.ADD_REFERENCE);
            }
        });
        addToExportList = new JButton();
        try {
            
            File imageFile = new File("src/main/resources/gui/add.png");
            if(imageFile.exists()) 
                System.out.println("Image file found!");
            else 
                System.out.println("Image file not found!");
            BufferedImage img = ImageIO.read(imageFile);
            addToExportList.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
        addToExportList.setPreferredSize(buttonSize);
        addToExportList.setName("addToExportList");
        addToExportList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(referenceList.getSelectedValue() != null) {
                    MainFrame.manager.addToExportList((Reference)referenceList.getSelectedValue());
                    exportList.removeAll();
                    exportList.setListData(MainFrame.manager.getExportList().toArray());
                } 
            }
        });

        removeFromExportList = new JButton();
        try {
            
            File imageFile = new File("src/main/resources/gui/remove.png");
            if(imageFile.exists()) 
                System.out.println("Image file found!");
            else 
                System.out.println("Image file not found!");
            BufferedImage img = ImageIO.read(imageFile);
            removeFromExportList.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
        removeFromExportList.setPreferredSize(buttonSize);
        removeFromExportList.setName("removeFromExportList");
        removeFromExportList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(exportList.getSelectedValue() != null) {
                    MainFrame.manager.getExportList().remove(
                            (Reference)exportList.getSelectedValue());
                    exportList.removeAll();
                    exportList.setListData(MainFrame.manager.getExportList().toArray());
                    exportList.validate();
                    exportList.repaint();
                }
            }
        });
        
//        JPanel dummyPanel = new JPanel();
//        dummyPanel.setMinimumSize(new Dimension(150,20));
//        dummyPanel.setPreferredSize(new Dimension(150,20));
//        dummyPanel.setBackground(Color.CYAN);
//        
//        controlPanel.add(dummyPanel);
        controlPanel.add(addReferenceButton);
        controlPanel.add(addToExportList);
        controlPanel.add(removeFromExportList);

        this.add(listPanel);
        this.add(controlPanel);
        this.add(exportPanel);
    }

    @Override
    public void render(Dimension dimension) {
        this.setSize(dimension);
        listPanel.setBounds(0, 0, (int) (this.getSize().width ), (int) (this.getSize().height * 0.5));
        controlPanel.setBounds(0 ,(int) (this.getSize().height * 0.5), 
                               (int) (this.getSize().width), (int) (this.getSize().height * 0.1));
        exportPanel.setBounds(0, (int) (this.getSize().height * 0.6), 
                               (int) (this.getSize().width), (int) (this.getSize().height * 0.4));
        listScroller.setBounds(
                (int) (listPanel.getSize().width * 0.03),
                10,
                (int) (listPanel.getSize().width * 0.94),
                (int) (listPanel.getSize().height * 0.94));
        exportScroller.setSize(listScroller.getSize());
        exportScroller.setBounds(
                (int) (exportPanel.getSize().width * 0.03),
                (int) (exportPanel.getSize().height * 0.07),
                (int) (exportPanel.getWidth() * 0.94 ),
                (int) (exportPanel.getHeight() * 0.8));
        referenceList.setListData(MainFrame.manager.getReferencesFromDatastore().toArray());
        referenceList.revalidate();
    }
}