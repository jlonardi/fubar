package references;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fubar
 */
public class ReferenceManager {
    private ArrayList<Reference> referenceList;
    
    public ReferenceManager() {
        referenceList = new ArrayList<>();
    }
    
    /**
     * Tries to add a reference. If the reference does not have all the required
     * fields, the addition will fail.
     * @param ref
     * @return Boolean determining if the addition succeeded.
     */
    public boolean addReference(Reference ref) {
        List<Reference.FieldType> reqFields = ReferenceFields.getRequiredFields(ref.getType());
        
        for (Reference.FieldType type : reqFields)
            if (ref.getField(type) == null)
                return false;
        
        return true;
    }
    
    public boolean importFrom(Reader input) {
        return false;
    }
    
    public boolean exportTo(Writer output) {
        return false;
    }
    
    /**
     * Searches for references with filter keyword in a specific field.
     * @param type The field used for filtering.
     * @param filter The string to filter with.
     * @return List of references found by filtering.
     */
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
