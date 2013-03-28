package fubar.gui;

import fubar.fubibtex.references.Reference;
import fubar.fubibtex.references.Reference.FieldType;
import fubar.fubibtex.references.Reference.Type;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddReferenceView extends JPanel implements View {

    private MainFrame frame;
    private JPanel basePanel, typeSelectionPanel, fieldPanel, requiredPanel,
            optionalPanel, controlPanel;
    private ActionListener selectionListener, returnListener, addListener;
    private JComboBox typeList;
    private JTextField citationKeyField;
    private EnumMap<FieldType, JTextField> map;

    public AddReferenceView(MainFrame frame) {
        this.frame = frame;
        this.setLayout(new BorderLayout());
        map = new EnumMap(fubar.fubibtex.references.Reference.FieldType.class);

        basePanel = new JPanel();
        basePanel.setLayout(null);
        basePanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        this.add(basePanel, BorderLayout.CENTER);

        // Invoke all the setups
        setupListeners();
        setupTypeSelectionPanel();
        setupFieldPanel();
        setupControlPanel();

        // Just for developing the layout
        /*
         this.setBackground(Color.red);
         basePanel.setBackground(Color.PINK);
         typeSelectionPanel.setBackground(Color.CYAN);
         fieldPanel.setBackground(Color.YELLOW);
         controlPanel.setBackground(Color.GREEN);
         */
    }

    /**
     * Sets up the top panel of the view that contains the combo box for
     * selection the type of reference that you want to add.
     */
    private void setupTypeSelectionPanel() {
        typeSelectionPanel = new JPanel();
        typeSelectionPanel.setLayout(new BoxLayout(typeSelectionPanel, BoxLayout.X_AXIS));
        typeSelectionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        basePanel.add(typeSelectionPanel);

        Type[] types = fubar.fubibtex.references.Reference.Type.values();
        typeList = new JComboBox(types);
        typeList.setMaximumSize(new Dimension(500, 20));
        typeList.addActionListener(selectionListener);
        typeList.setSelectedIndex(0);

        typeSelectionPanel.add(typeList);
        typeSelectionPanel.add(Box.createHorizontalGlue());
        typeSelectionPanel.add(Box.createHorizontalGlue());
        typeSelectionPanel.add(Box.createHorizontalGlue());
    }

    /**
     * Sets up the larger middle panel that contains the input fields for
     * creating a new reference.
     */
    private void setupFieldPanel() {

        fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));

        requiredPanel = new JPanel();
        requiredPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        requiredPanel.setBorder(BorderFactory.createTitledBorder(
                "Required fields"));
        fieldPanel.add(requiredPanel);

        optionalPanel = new JPanel();
        optionalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        optionalPanel.setBorder(BorderFactory.createTitledBorder(
                "Optional fields"));
        fieldPanel.add(optionalPanel);

        basePanel.add(fieldPanel);
    }

    /**
     * Sets up the bottom panel that contains the buttons to return back to the
     * list view and the addition button for adding the reference to the
     * database.
     */
    private void setupControlPanel() {

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Dimension buttonSize = new Dimension(100, 30);

        JButton returnButton = new JButton("Return");
        returnButton.setPreferredSize(buttonSize);
        returnButton.addActionListener(returnListener);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(addListener);
        addButton.setPreferredSize(buttonSize);

        controlPanel.add(returnButton);
        controlPanel.add(addButton);

        basePanel.add(controlPanel);
    }

    /**
     * Updates the field panel to correspond the selected reference types
     * fields.
     *
     * @param requiredFields
     * @param optionalFields
     */
    private void updateFieldPanel() {

        Type selectedType = (Type) typeList.getSelectedItem();
        List<FieldType> requiredFields = fubar.fubibtex.references.ReferenceFields.getRequiredFields(selectedType);
        List<FieldType> optionalFields = fubar.fubibtex.references.ReferenceFields.getOptionalFields(selectedType);

        if (requiredPanel == null) {
            return;
        }
        requiredPanel.removeAll();
        requiredPanel.revalidate();
        optionalPanel.removeAll();
        optionalPanel.revalidate();

        if (requiredFields == null) {
            return;
        }

        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        label.setText("Citation key");
        label.setPreferredSize(new Dimension(80, 20));
        citationKeyField = new JTextField(20);
        panel.add(label);
        panel.add(citationKeyField);
        requiredPanel.add(panel);

        map.clear();

        for (FieldType type : requiredFields) {
            panel = new JPanel();
            label = new JLabel();
            label.setText(type.name());
            label.setPreferredSize(new Dimension(80, 20));
            JTextField textField = new JTextField(20);
            panel.add(label);
            panel.add(textField);
            requiredPanel.add(panel);
            map.put(type, textField);
        }
        requiredPanel.revalidate();

        if (optionalFields == null) {
            return;
        }

        for (FieldType type : optionalFields) {
            panel = new JPanel();
            label = new JLabel();
            label.setText(type.name());
            label.setPreferredSize(new Dimension(80, 20));
            JTextField textField = new JTextField(20);
            panel.add(label);
            panel.add(textField);
            optionalPanel.add(panel);
            map.put(type, textField);
        }
        optionalPanel.revalidate();

    }

    /**
     * Sets up all the listeners used in this views components.
     */
    private void setupListeners() {
        // The listener for the combo box.
        selectionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFieldPanel();
            }
        };
        // The listener for returning to the listing view.
        returnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showView(ViewType.REFERENCE_LIST);
            }
        };
        // The listener for the add button
        addListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Type type = (Type) typeList.getSelectedItem();
                ArrayList<JTextField> textfields = new ArrayList();
                Reference ref = new Reference(type);
                if (citationKeyField == null) {
                    return;
                }
                ref.setCitationKey(citationKeyField.getText());
                textfields.add(citationKeyField);

                for (FieldType key : fubar.fubibtex.references.ReferenceFields.getRequiredFields(type)) {
                    JTextField field = map.get(key);
                    if (field.getText().equals("") || citationKeyField.getText().equals("")) {
                        JOptionPane.showMessageDialog(frame.getFrame(),
                                "You must fill all the required fields to proceed.",
                                "Add reference error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        ref.setField(key, field.getText());
                        textfields.add(field);
                    }
                }

                for (FieldType key : fubar.fubibtex.references.ReferenceFields.getOptionalFields(type)) {
                    JTextField field = map.get(key);
                    if (field.getText().equals("")) {
                        ref.setField(key, null);
                    } else {
                        ref.setField(key, field.getText());
                    }
                    textfields.add(field);
                }
                
                updateFieldPanel();
                
                MainFrame.manager.addReferenceToDatastore(ref);
                frame.showView(ViewType.REFERENCE_LIST);
            }
        };
    }

    @Override
    public void render(Dimension dimension) {
        this.setSize(dimension);
        basePanel.setSize(this.getSize());
        typeSelectionPanel.setBounds(0, 0, (int) (basePanel.getSize().width), (int) (basePanel.getSize().height * 0.1));
        fieldPanel.setBounds(0, (int) (basePanel.getSize().height * 0.1), (int) (basePanel.getSize().width), (int) (basePanel.getSize().height * 0.75));
        controlPanel.setBounds(0, (int) (basePanel.getSize().height * 0.85), (int) (basePanel.getSize().width), (int) (basePanel.getSize().height * 0.15));
    }
}
