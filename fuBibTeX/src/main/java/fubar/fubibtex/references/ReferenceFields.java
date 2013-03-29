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
        
        reqFieldTypes.add(Reference.FieldType.AUTHOR);
        reqFieldTypes.add(Reference.FieldType.TITLE);
        reqFieldTypes.add(Reference.FieldType.BOOKTITLE);
		reqFieldTypes.add(Reference.FieldType.YEAR);
        
        optFieldTypes.add(Reference.FieldType.EDITOR);
        optFieldTypes.add(Reference.FieldType.PAGES);
        optFieldTypes.add(Reference.FieldType.ORGANIZATION);
        optFieldTypes.add(Reference.FieldType.PUBLISHER);
        optFieldTypes.add(Reference.FieldType.ADDRESS);
        optFieldTypes.add(Reference.FieldType.MONTH);
        optFieldTypes.add(Reference.FieldType.NOTE);
        optFieldTypes.add(Reference.FieldType.KEY);
        
        requiredFields.put(Reference.Type.INPROCEEDINGS, reqFieldTypes);
        optionalFields.put(Reference.Type.INPROCEEDINGS, optFieldTypes);
        
        initialized = true;
    }
    
}
