package fubar.fubibtex;

import java.io.BufferedWriter;
import java.io.FileWriter;

import fubar.fubibtex.references.*;
import fubar.fubibtex.ui_adapter.GUIReferenceManagerF;
import fubar.fubibtex.ui_adapter.IGUIReferenceManager;
import fubar.gui.MainFrame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author petteri
 */
public class MainApp {
    
    public static void main(String[] args) {
        
        IGUIReferenceManager manager = new IGUIReferenceManager() {

            @Override
            public boolean addReferenceToDatastore(Reference ref) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public List<Reference> getReferencesByFilterFromDatastore(Reference.FieldType type, String filter) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public List<Reference> getReferencesFromDatastore() {
                ArrayList<Reference> list = new ArrayList();
                Reference ref = new Reference(Reference.Type.InProceedings);
                ref.setField(Reference.FieldType.Title, "Systeemihommia");
                ref.setField(Reference.FieldType.Author, "Petteri Linnakangas");
                ref.setCitationKey("Petteri2012");
                list.add(ref);
                ref = new Reference(Reference.Type.InProceedings);
                ref.setField(Reference.FieldType.Title, "Koodia koodia koodia...");
                ref.setField(Reference.FieldType.Author, "Jarno Lonardi");
                ref.setCitationKey("LoL3013");
                list.add(ref);
                return list;
            }

            @Override
            public boolean loadFromDatastore() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean saveToDatastore() {
                return true;
            }

            @Override
            public boolean addToExportList(Reference ref) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean exportToFile(File file) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean clearExportList() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        };
        
        MainFrame frame = new MainFrame(manager);
        frame.draw();
        
        try {
            System.out.println("Opening file...");
            BufferedWriter file = new BufferedWriter(new FileWriter("bibtexfile.bib", true));
            System.out.println("File opened. Creating reference...");
            Reference ref = new Reference(Reference.Type.InProceedings);
            
            System.out.println("Adding fields to reference...");
            ref.setField(Reference.FieldType.Title, "Systeemihommia");
            ref.setField(Reference.FieldType.Author, "Petteri Linnakangas");
            
            System.out.println("Saving reference to file.");
            ref.save(file);
            
            System.out.println("Closing file.");
            file.close();
            
        } catch (Exception e) {
            System.out.println("There was an exception: " + e.getMessage());
        }
       
    }
    
}
