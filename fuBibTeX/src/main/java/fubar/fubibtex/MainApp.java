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
        System.out.println("");
        IGUIReferenceManager manager = new IGUIReferenceManager() {
            ArrayList<Reference> list = new ArrayList();
            ArrayList<Reference> exportList = new ArrayList();
            private void init() {
                Reference ref = new Reference(Reference.Type.Inproceedings);
                ref.setField(Reference.FieldType.Title, "Systeemihommia");
                ref.setField(Reference.FieldType.Author, "Petteri Linnakangas");
                ref.setCitationKey("Petteri2012");
                list.add(ref);
                ref = new Reference(Reference.Type.Inproceedings);
                ref.setField(Reference.FieldType.Title, "Koodia koodia koodia...");
                ref.setField(Reference.FieldType.Author, "Jarno Lonardi");
                ref.setCitationKey("LoL3013");
                list.add(ref);
            }
            @Override
            public boolean addReferenceToDatastore(Reference ref) {
                list.add(ref);
                return true;
            }

            @Override
            public List<Reference> getReferencesByFilterFromDatastore(Reference.FieldType type, String filter) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public List<Reference> getReferencesFromDatastore() {
                return list;
            }

            @Override
            public boolean loadFromDatastore() {
                init();
                return true;
            }

            @Override
            public boolean saveToDatastore() {
                return true;
            }

            @Override
            public boolean addToExportList(Reference ref) {
                exportList.add(ref);
                System.out.println(exportList.toString());
                return true;
            }

            @Override
            public boolean exportToFile(File file) {
                return true;
            }

            @Override
            public boolean clearExportList() {
                exportList = new ArrayList();
                return true;
            }

			@Override
			public void setDatastore(File file) {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}

            @Override
            public List<Reference> getExportList() {
                return exportList;
            }
            
        };
        
        MainFrame frame = new MainFrame(manager);
        //frame.draw();
        
        try {
            System.out.println("Opening file...");
            BufferedWriter file = new BufferedWriter(new FileWriter("bibtexfile.bib", true));
            System.out.println("File opened. Creating reference...");
            Reference ref = new Reference(Reference.Type.Inproceedings);
            
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
     
		System.out.println("Testi alkaa...");
		//Pieni demon ReferenceManagerin import & export -toiminnoista.
		IGUIReferenceManager m = new GUIReferenceManagerF();
		
		m.setDatastore(new File("file1.bib"));
		m.loadFromDatastore();
		
		List<Reference> rl = m.getReferencesFromDatastore();
		
		System.out.println("Siirretään referencet exportlistalle");
		for (int i = 0; i < rl.size(); i++) {
			if (m.addToExportList(rl.get(i)) == false)
			{
				System.out.println("Virhe indeksissä " + i);
				System.exit(-1);
			}
		}

		File output = new File("newbib.bib");

		m.exportToFile(output);
		
		IGUIReferenceManager n = new GUIReferenceManagerF();
		
		n.setDatastore(new File("newbib.bib"));
		n.loadFromDatastore();
		
		File output1 = new File("newbib2.bib");

		m.exportToFile(output1);
				
		
    }
    
}
