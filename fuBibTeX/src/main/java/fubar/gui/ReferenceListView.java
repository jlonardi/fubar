package fubar.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ReferenceListView extends JPanel implements View {
    private JList referenceList;
    private JScrollPane listScroller;
    private JPanel listPanel, controlPanel;
    private JButton addReferenceButton;
    private MainFrame frame;
    
    public ReferenceListView(final MainFrame frame) {
        this.frame = frame;
        this.setLayout(null);
        
        // Set up the left side of the view that holds the list
        listPanel = new JPanel();
        listPanel.setLayout(null);
        
        // Set up the right side of the view that holds the control buttons
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        
        // Layout visualization for development
        //listPanel.setBackground(Color.red);
        //controlPanel.setBackground(Color.BLUE);
        
        // Set up the list and the scroller
        referenceList = new JList(MainFrame.manager.getReferencesFromDatastore().toArray());
        referenceList.setLayoutOrientation(JList.VERTICAL);
        referenceList.setName("referenceList");
        listScroller = new JScrollPane(referenceList);
        listScroller.setName("listScroller");
        listPanel.add(listScroller);
        
        // Set up the buttons
        addReferenceButton = new JButton("Add new entry");
        addReferenceButton.setName("addReferenceButton");
        addReferenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showView(ViewType.ADD_REFERENCE);
            }
        });
        controlPanel.add(Box.createHorizontalGlue());
        controlPanel.add(addReferenceButton);
        controlPanel.add(Box.createHorizontalGlue());
        
        this.add(listPanel);
        this.add(controlPanel);
    }
    
    @Override
    public void render(Dimension dimension) {
        this.setSize(dimension);
        listPanel.setBounds(0, 0, (int)(this.getSize().width*0.6), (int)(this.getSize().height));
        controlPanel.setBounds((int)(this.getSize().width*0.6), 0, (int)(this.getSize().width*0.4), (int)(this.getSize().height));
        listScroller.setBounds(
                                (int)(listPanel.getSize().width*0.03),
                                10,
                                (int)(listPanel.getSize().width*0.94),
                                (int)(listPanel.getSize().height*0.94)
                            );
        referenceList.setListData(MainFrame.manager.getReferencesFromDatastore().toArray());
        referenceList.revalidate();
    }
}