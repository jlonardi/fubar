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

public class ModifyReferenceView extends AddReferenceView {
    
    protected Reference editedReference;
    protected JButton modifyButton;
    protected ActionListener modifyListener;
    protected DocumentListener modifyCitationKeyListener;
    
    public ModifyReferenceView(final MainFrame frame) {
        super(frame);
        super.typeList.setVisible(false);
        super.addButton.setVisible(false);
        
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
    
    public void setEditedReference(Reference ref) {
        editedReference = ref;
        selectedType = editedReference.getType();
        super.updateView();
        fillFields();
    }
    
    private void fillFields() {
        List<FieldType> requiredFields = fubar.fubibtex.references.ReferenceFields.getRequiredFields(selectedType);
        List<FieldType> optionalFields = fubar.fubibtex.references.ReferenceFields.getOptionalFields(selectedType);
        
        super.citationKeyField.setText(editedReference.getCitationKey());
        
        for(FieldType ft : requiredFields) {
            JTextField textField = map.get(ft);
            if(textField != null) textField.setText(editedReference.getField(ft));
        }
        
        for(FieldType ft : optionalFields) {
            JTextField textField = map.get(ft);
            if(textField != null) textField.setText(editedReference.getField(ft));
        }
    }
    
    private void modifyListeners() {
        // Now the add listener insteaf of adding a new Reference just sets new values
        modifyListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Type type = editedReference.getType();
                
                if(MainFrame.manager.dataStoreContainsCitationKey(
                        citationKeyField.getText()) && 
                        !editedReference.getCitationKey().equals(citationKeyField.getText())) {
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
                if (MainFrame.manager.dataStoreContainsCitationKey(
                        citationKeyField.getText()) && 
                        !editedReference.getCitationKey().equals(citationKeyField.getText())) {
                    citationKeyError.setVisible(true);
                } else {
                    citationKeyError.setVisible(false);
                }

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println(!editedReference.getCitationKey().equals(citationKeyField.getText()));
                if (MainFrame.manager.dataStoreContainsCitationKey(
                        citationKeyField.getText()) && 
                        !editedReference.getCitationKey().equals(citationKeyField.getText())) {
                    citationKeyError.setVisible(true);
                } else {
                    citationKeyError.setVisible(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println(!editedReference.getCitationKey().equals(citationKeyField.getText()));
                if (MainFrame.manager.dataStoreContainsCitationKey(
                        citationKeyField.getText()) && 
                        !editedReference.getCitationKey().equals(citationKeyField.getText())) {
                    citationKeyError.setVisible(true);
                } else {
                    citationKeyError.setVisible(false);
                }

            }
        };
    }
}
