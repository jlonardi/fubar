package fubar.gui;

import fubar.fubibtex.references.Reference;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ReferenceListView extends JPanel implements View {

    private JList referenceList, exportList;
    private JScrollPane listScroller, exportScroller;
    private JPanel listPanel, controlPanel, exportPanel, exportListHolder, buttonPanel;
    private JButton addReferenceButton, addToExportList, removeFromExportList;
    private JLabel exportLabel;
    private MainFrame frame;

    public ReferenceListView(final MainFrame frame) {
        this.frame = frame;
        this.setLayout(null);

        // Set up the left side of the view that holds the list
        listPanel = new JPanel();
        listPanel.setLayout(null);

        // Set up the central area of the view that holds the control buttons
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        // Set up the right side of the view that holds the export list.
        exportPanel = new JPanel();
        exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
        exportListHolder = new JPanel();

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

        exportLabel = new JLabel("References to be exported");
        exportPanel.add(exportLabel);
        exportList = new JList(MainFrame.manager.getExportList().toArray());
        exportList.setName("exportList");
        exportScroller = new JScrollPane(exportList);

        exportListHolder.add(exportScroller);

        exportPanel.add(exportListHolder);

        // Set up the buttons
        Dimension buttonSize = new Dimension(120, 30);
        addReferenceButton = new JButton("Add new entry");
        addReferenceButton.setName("addReferenceButton");
        addReferenceButton.setPreferredSize(buttonSize);
        addReferenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showView(ViewType.ADD_REFERENCE);
            }
        });
        addToExportList = new JButton("\u25BA");
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

        removeFromExportList = new JButton("\u25C4");
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
        listPanel.setBounds(0, 0, (int) (this.getSize().width * 0.4), (int) (this.getSize().height));
        controlPanel.setBounds((int) (this.getSize().width * 0.4), 0, (int) (this.getSize().width * 0.2), (int) (this.getSize().height));
        exportPanel.setBounds((int) (this.getSize().width * 0.6), 0, (int) (this.getSize().width * 0.4), (int) (this.getSize().height));
        listScroller.setBounds(
                (int) (listPanel.getSize().width * 0.03),
                10,
                (int) (listPanel.getSize().width * 0.94),
                (int) (listPanel.getSize().height * 0.94));
//        exportScroller.setBounds(
//                (int) (exportScroller.getSize().width * 0.03),
//                (int) (exportScroller.getSize().width * 0.10),
//                (int) (exportScroller.getSize().width * 0.80),
//                (int) (exportScroller.getSize().height * 0.80));
        referenceList.setListData(MainFrame.manager.getReferencesFromDatastore().toArray());
        referenceList.revalidate();
    }
}