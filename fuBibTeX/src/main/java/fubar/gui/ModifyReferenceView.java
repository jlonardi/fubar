package fubar.gui;

import fubar.fubibtex.references.Reference;
import fubar.fubibtex.references.Reference.FieldType;
import fubar.fubibtex.references.Reference.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * View for the modification of a selected reference.
 *
 * @author Jarno Lonardi
 */
public class ModifyReferenceView extends AddReferenceView {

    protected Reference editedReference;
    protected JButton modifyButton;
    protected ActionListener modifyListener;
    protected DocumentListener modifyCitationKeyListener;

    public ModifyReferenceView(final MainFrame frame) {
        super(frame);
        super.typeList.setVisible(false);
        super.addButton.setVisible(false);
        // Have to set different names for components that are going to be tested
        // in FEST
        super.addButton.setName("not in use");
        super.typeList.setName("not in use");
        super.typeSelectionPanel.setName("typeSelectionPanelModify");
        super.citationKeyError.setName("citationKeyErrorLabelModify");
        super.citationKeyField.setName("citationKeyFieldModify");
        super.requiredPanel.setName("requiredPanelModify");
        super.controlPanel.setName("controlPanelModify");
        super.returnButton.setName("returnButtonModify");
        super.addButton.setName("addButtonModify");
        super.citationBuilderButton.setName("citationBuilderButtonModify");

        modifyListeners();
        modifyButton = new JButton("Modify");
        modifyButton.setName("modifyButton");
        modifyButton.addActionListener(modifyListener);
        modifyButton.setPreferredSize(super.buttonSize);

        controlPanel.add(modifyButton);

        super.citationKeyError.setVisible(false);
        super.citationKeyField.getDocument().removeDocumentListener(super.citationKeyListener);
        super.citationKeyField.getDocument().addDocumentListener(modifyCitationKeyListener);
    }

    /**
     * Sets the reference that is going under modification.
     *
     * @param ref Reference to be modified.
     */
    public void setEditedReference(Reference ref) {
        editedReference = ref;
        selectedType = editedReference.getType();
        super.updateView();
        fillFields();
    }

    /**
     * Fills the view's typefields with the selected reference information. The
     * parent class map is used to achieve this since that class maps all the
     * text fields with the reference type fields.
     *
     */
    private void fillFields() {
        List<FieldType> requiredFields = fubar.fubibtex.references.ReferenceFields.getRequiredFields(selectedType);
        List<FieldType> optionalFields = fubar.fubibtex.references.ReferenceFields.getOptionalFields(selectedType);

        super.citationKeyField.setText(editedReference.getCitationKey());

        for (FieldType ft : requiredFields) {
            JTextField textField = map.get(ft);
            if (textField != null) {
                textField.setText(editedReference.getField(ft));
            }
        }

        for (FieldType ft : optionalFields) {
            JTextField textField = map.get(ft);
            if (textField != null) {
                textField.setText(editedReference.getField(ft));
            }
        }
    }

    /**
     * Cheks if the currently inserted value in the citation key field is
     * unique.
     */
    private void checkIfUnique() {
        if (!citationKeyField.getText().equals("")
                && MainFrame.manager.dataStoreContainsCitationKey(
                citationKeyField.getText())
                && !editedReference.getCitationKey().equals(citationKeyField.getText())) {
            citationKeyError.setVisible(true);
        } else {
            citationKeyError.setVisible(false);
        }
    }

    /**
     * Sets up the listeners used in this view.
     */
    private void modifyListeners() {
        // Now the add listener insteaf of adding a new Reference just sets new values
        modifyListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Type type = editedReference.getType();

                if (!citationKeyField.getText().equals("")
                        && MainFrame.manager.dataStoreContainsCitationKey(
                        citationKeyField.getText())
                        && !editedReference.getCitationKey().equals(citationKeyField.getText())) {
                    frame.showMessage(
                            "Citation key is allready in use.",
                            "Add reference error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                editedReference.setCitationKey(citationKeyField.getText());

                for (FieldType key : fubar.fubibtex.references.ReferenceFields.getRequiredFields(type)) {
                    JTextField field = map.get(key);
                    if (field.getText().equals("")) {
                        frame.showMessage(
                                "You must fill all the required fields to proceed.",
                                "Add reference error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        editedReference.setField(key, field.getText());
                    }
                }

                for (FieldType key : fubar.fubibtex.references.ReferenceFields.getOptionalFields(type)) {
                    JTextField field = map.get(key);
                    if (!field.getText().equals("")) {
                        editedReference.setField(key, field.getText());
                    }
                }

                updateView();
                citationKeyField.setText("");

                frame.dataUpdated();
                frame.showView(ViewType.REFERENCE_LIST);
            }
        };

        // Now it is allowed to have a non unique key assuming that the key is
        // the same as in the Reference that is under modification
        modifyCitationKeyListener = new DocumentListener() {
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
    }
}