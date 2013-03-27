package fubar.fubibtex.references;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fubar
 */
public class ReferenceManagerF implements IReferenceManager {
    
	private ArrayList<Reference> referenceList;
    
	//This referencemanager uses files as datastores.
	private File importFile;
	private File exportFile;
	
    public ReferenceManagerF() {
        referenceList = new ArrayList<>();
    }
    
    /**
     * Tries to add a reference. If the reference does not have all the required
     * fields, the addition will fail.
     * @param ref
     * @return Boolean determining if the addition succeeded.
     */
	@Override
    public boolean addReference(Reference ref) {
        List<Reference.FieldType> reqFields = ReferenceFields.getRequiredFields(ref.getType());
        
        for (Reference.FieldType type : reqFields)
            if (ref.getField(type) == null)
                return false;
        
        return true;
    }

	public File getImportFile() {
		return importFile;
	}

	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}

	public File getExportFile() {
		return exportFile;
	}

	public void setExportFile(File exportFile) {
		this.exportFile = exportFile;
	}
    
	@Override
    public boolean importFrom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
	@Override
    public boolean exportTo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Searches for references with filter keyword in a specific field.
     * @param type The field used for filtering.
     * @param filter The string to filter with.
     * @return List of references found by filtering.
     */
	@Override
    public List<Reference> getReferencesByFilter(Reference.FieldType type, String filter) {
        ArrayList<Reference> refsFound = new ArrayList<>();        
        
        for (Reference ref : referenceList) {
            String val = ref.getField(type);
            if (val.contains(filter))
                refsFound.add(ref);
        }
        
        return refsFound;
    }
    
}
