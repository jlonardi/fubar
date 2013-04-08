package fubar.fubibtex.references;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Definitions for the different types of BibTeX references. Also contains the
 * definitions for each type's field preferences.
 * @author fubar
 */
public class ReferenceFields {
    private static EnumMap<Reference.Type, List<Reference.FieldType>> requiredFields;
    private static EnumMap<Reference.Type, List<Reference.FieldType>> optionalFields;
    
    private static boolean initialized = false;
    
    /**
     * Returns the required fields for a given type of reference.
     * @param type The type of the reference.
     * @return List of the required fields.
     */
    public static List<Reference.FieldType> getRequiredFields(Reference.Type type) {
        if (!initialized) initialize();
        return requiredFields.get(type);
    }
    
    /**
     * Returns the optional fields for a given type of reference.
     * @param type The type of the reference.
     * @return List of the optional fields.
     */
    public static List<Reference.FieldType> getOptionalFields(Reference.Type type) {
        if (!initialized) initialize();
        return optionalFields.get(type);
    }
    
    /**
     * Initializes the required and optional fields for all types of references.
     */
    private static void initialize() {
        requiredFields = new EnumMap<Reference.Type, List<Reference.FieldType>>(Reference.Type.class);
        optionalFields = new EnumMap<Reference.Type, List<Reference.FieldType>>(Reference.Type.class);
        
        ArrayList<Reference.FieldType> reqFieldTypes = new ArrayList<Reference.FieldType>();
        ArrayList<Reference.FieldType> optFieldTypes = new ArrayList<Reference.FieldType>();
        
        reqFieldTypes.add(Reference.FieldType.Author);
        reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.Booktitle);
		reqFieldTypes.add(Reference.FieldType.Year);
        
        optFieldTypes.add(Reference.FieldType.Editor);
        optFieldTypes.add(Reference.FieldType.Pages);
        optFieldTypes.add(Reference.FieldType.Organization);
        optFieldTypes.add(Reference.FieldType.Publisher);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        
        requiredFields.put(Reference.Type.Inproceedings, reqFieldTypes);
        optionalFields.put(Reference.Type.Inproceedings, optFieldTypes);
        
        initialized = true;
    }
    
}
