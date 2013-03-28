package fubar.fubibtex;

import java.io.BufferedWriter;
import java.io.FileWriter;

import fubar.fubibtex.references.*;
import fubar.gui.MainFrame;

/**
 *
 * @author petteri
 */
public class MainApp {
    
    public static void main(String[] args) {
        
        MainFrame frame = new MainFrame();
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
