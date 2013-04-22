package fubar.gui;

import fubar.fubibtex.references.CitationKeyBuilder;
import fubar.fubibtex.references.Reference;
import fubar.fubibtex.references.Reference.FieldType;
import fubar.fubibtex.references.Reference.Type;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *  A view that shows the part of the UI where you can create new references.
 *  The view contains 3 main parts, the top section where reisdes the type selector
 *  and a field for the id. The middle section contains the fields for the selected
 *  type. This section is divided in two parts, the required and optional fields.
 *  The bottom part contains the control buttons to return in the list view and
 *  the button to add the reference.
 *  @author Jarno Lonardi
 */
public class AddReferenceView extends View {

    protected MainFrame frame;
    protected JPanel basePanel, typeSelectionPanel, fieldPanel, requiredPanel,
            optionalPanel, controlPanel;
    protected ActionListener selectionListener, returnListener, addListener,
            citationBuilderListener;
    protected DocumentListener citationKeyListener;
    protected JLabel citationKeyError;
    protected JButton addButton, returnButton, citationBuilderButton;
    protected JComboBox typeList;
    protected JTextField citationKeyField;
    protected EnumMap<FieldType, JTextField> map;
    protected Type selectedType;
    protected Dimension buttonSize;
    private String className;

    public AddReferenceView(MainFrame frame) {
        this.frame = frame;
        if(this.getClass().getName().contains("ModifyReferenceView")) {
            this.className = "Modify";
        } else {
            this.className = "";
        }
        this.setLayout(new BorderLayout());
        // A map that maps the field types into the textfields that are drawn
        map = new EnumMap(fubar.fubibtex.references.Reference.FieldType.class);
        // The "main" panel of the view
        basePanel = new JPanel();
        basePanel.setLayout(null);
        basePanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        this.add(basePanel, BorderLayout.CENTER);

        // Invoke all the setups
        setupListeners();
        setupTypeSelectionPanel();
        setupFieldPanel();
        setupControlPanel();
        updateView();

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
        typeSelectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        typeSelectionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        typeSelectionPanel.setName("typeSelectionPanel");
        basePanel.add(typeSelectionPanel);

        Type[] types = fubar.fubibtex.references.Reference.Type.values();
        typeList = new JComboBox(types);
        typeList.setMaximumSize(new Dimension(500, 20));
        typeList.addActionListener(selectionListener);
        typeList.setSelectedIndex(0);
        typeList.setName("typeList");

        typeSelectionPanel.add(typeList);

        // A panel that contains the citation key field, key label and error label
        JPanel panel = new JPanel();

        // Sets up the input field for the citation key
        JLabel label = new JLabel();
        label.setText("Citation key");
//        label.setName("citationKeyLabel");
        label.setPreferredSize(new Dimension(80, 20));
        citationKeyField = new JTextField(20);
        citationKeyField.setName("citationKeyField");
        citationKeyField.getDocument().addDocumentListener(citationKeyListener);
        panel.add(label);
        panel.add(citationKeyField);

        // Sets up the error label
        citationKeyError = new JLabel();
        citationKeyError.setName("citationKeyErrorLabel");
        try {
            File imageFile = new File("src/main/resources/gui/error.png");
            BufferedImage img = ImageIO.read(imageFile);
            citationKeyError.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
        citationKeyError.setToolTipText("Key not unique");
        citationKeyError.setVisible(false);
        citationKeyError.setMinimumSize(new Dimension(30, 30));
        panel.add(citationKeyError);

        typeSelectionPanel.add(panel);
    }

    /**
     * Sets up the larger middle panel that contains the input fields for
     * creating a new reference.
     */
    private void setupFieldPanel() {

        fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));

        requiredPanel = new JPanel();
        requiredPanel.setName("requiredPanel");
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
        // Sets up the panel where the buttons reside
        controlPanel = new JPanel();
        controlPanel.setName("controlPanel");
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        buttonSize = new Dimension(100, 30);
        
        // Creartes the buttons and adds them into the panel
        returnButton = new JButton("Return");
        returnButton.setName("returnButton");
        returnButton.setPreferredSize(buttonSize);
        returnButton.addActionListener(returnListener);
        addButton = new JButton("Add");
        addButton.setName("addButton");
        addButton.addActionListener(addListener);
        addButton.setPreferredSize(buttonSize);
        citationBuilderButton = new JButton("Suggest Citation Key");
        citationBuilderButton.setName("citationBuilderButton");
        citationBuilderButton.addActionListener(citationBuilderListener);
        citationBuilderButton.setPreferredSize(new Dimension(180, 30));
        
        controlPanel.add(returnButton);
        controlPanel.add(addButton);
        controlPanel.add(citationBuilderButton);

        basePanel.add(controlPanel);
    }

    /**
     * Updates the view.
     */
    protected void updateView() {
        
        List<FieldType> requiredFields = fubar.fubibtex.references.ReferenceFields.getRequiredFields(selectedType);
        List<FieldType> optionalFields = fubar.fubibtex.references.ReferenceFields.getOptionalFields(selectedType);

        if (requiredPanel == null) {
            return;
        }
        // Removes all the elements from the field panels
        requiredPanel.removeAll();
        requiredPanel.revalidate();
        requiredPanel.repaint();
        optionalPanel.removeAll();
        optionalPanel.revalidate();
        optionalPanel.repaint();

        if (requiredFields == null && optionalFields == null) {
            return;
        }

        JPanel panel;
        JLabel label;

        map.clear();
        // Sets up the required fields
        if (requiredFields != null) {
            for (FieldType type : requiredFields) {
                panel = new JPanel();
                label = new JLabel();
                label.setText(type.name());
                label.setName(type.name() + "Label");
                label.setPreferredSize(new Dimension(80, 20));
                JTextField textField = new JTextField(20);
                textField.setName(type.name() + "TextField" + className); //className for distinguish between the fields in add and modify views
                panel.add(label);
                panel.add(textField);
                requiredPanel.add(panel);
                map.put(type, textField);
            }
            requiredPanel.repaint();
        }
        
        // Sets up the optional fields
        if (optionalFields != null) {
            for (FieldType type : optionalFields) {
                panel = new JPanel();
                label = new JLabel();
                label.setText(type.name());
                label.setPreferredSize(new Dimension(80, 20));
                JTextField textField = new JTextField(20);
                textField.setName(type.name() + "TextField"+ className);  //className for distinguish between the fields in add and modify views
                panel.add(label);
                panel.add(textField);
                optionalPanel.add(panel);
                map.put(type, textField);
            }
            optionalPanel.repaint();
        }
    }
    
    /**
     *  Cheks if the currently inserted value in the citation key field
     *  is unique.
     */
    private void checkIfUnique() {
        if (!citationKeyField.getText().equals("") && MainFrame.manager.dataStoreContainsCitationKey(citationKeyField.getText())) {
            citationKeyError.setVisible(true);
        } else {
            citationKeyError.setVisible(false);
        }
    }
    
    /**
     * Sets up all the listeners used in this views components.
     */
    private void setupListeners() {
        // The listener for the combo box.
        selectionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedType = (Type) typeList.getSelectedItem();
                updateView();
            }
        };
        // The listener for returning to the listing view.
        returnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateView();
                citationKeyField.setText("");
                frame.showView(ViewType.REFERENCE_LIST);
            }
        };
        // The listener for the add button
        addListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Type type = (Type) typeList.getSelectedItem();
                Reference ref = new Reference(type);
                // Checks if the citation key is unique
                if(!citationKeyField.getText().equals("") && MainFrame.manager.dataStoreContainsCitationKey(
                        citationKeyField.getText())) {
                    frame.showMessage(
                                "Citation key is allready in use.",
                                "Add reference error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                }
                ref.setCitationKey(citationKeyField.getText());
                // Picks up all the data from the required text fields and
                // shows an error if all fields are not filled.
                for (FieldType key : fubar.fubibtex.references.ReferenceFields.getRequiredFields(type)) {
                    JTextField field = map.get(key);
                    if (field.getText().equals("")) {
                        frame.showMessage(
                                "You must fill all the required fields to proceed.",
                                "Add reference error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        ref.setField(key, field.getText());
                    }
                }
                // Creates the optional fields from the optional section's
                // text panels.
                for (FieldType key : fubar.fubibtex.references.ReferenceFields.getOptionalFields(type)) {
                    JTextField field = map.get(key);
                    if (!field.getText().equals("")) {
                        ref.setField(key, field.getText());
                    }
                }
                // Clears the view
                updateView();
                citationKeyField.setText("");
                // And changes the view into the list view
                MainFrame.manager.addReferenceToDatastore(ref);
                frame.dataUpdated();
                frame.showView(ViewType.REFERENCE_LIST);
            }
            
        };

        // A listener that checks if the currently entered citation key is used or not
        citationKeyListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkIfUnique();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkIfUnique();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkIfUnique();
            }
        };
        
        citationBuilderListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Type type = (Type) typeList.getSelectedItem();
                Reference ref = new Reference(type);
                
                JTextField field = map.get(Reference.FieldType.Year);
                if (!field.getText().equals("")) {
                    ref.setField(Reference.FieldType.Year, field.getText());
                }
                
                field = map.get(Reference.FieldType.Author);
                if (!field.getText().equals("")) {
                    ref.setField(Reference.FieldType.Author, field.getText());
                }
                
                String suggestion = CitationKeyBuilder.suggestCitation(ref);
                citationKeyField.setText(suggestion);
            }
        };
    }

    @Override
    public void render(Dimension dimension) {
        this.setSize(dimension);
        basePanel.setSize(this.getSize());
        typeSelectionPanel.setBounds(0, 0, (int) (basePanel.getSize().width), (int) (basePanel.getSize().height * 0.1));
        fieldPanel.setBounds(0, (int) (basePanel.getSize().height * 0.1), (int) (basePanel.getSize().width), (int) (basePanel.getSize().height * 0.75));
        controlPanel.setBounds(0, (int) (basePanel.getSize().height * 0.9), (int) (basePanel.getSize().width), (int) (basePanel.getSize().height * 0.15));
        requiredPanel.setBounds(0, (int) 0, (int) (basePanel.getSize().width), (int) ((basePanel.getSize().height * 0.75)) / 2);
        optionalPanel.setBounds(0, (int) (0 + requiredPanel.getHeight()), (int) (basePanel.getSize().width), (int) ((basePanel.getSize().height * 0.75)) / 2);
		this.revalidate();
		this.repaint();
	}
}