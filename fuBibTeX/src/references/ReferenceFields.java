package references;

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
    
    public static List<Reference.FieldType> getRequiredFields(Reference.Type type) {
        if (!initialized) initialize();
        return requiredFields.get(type);
    }
    
    public static List<Reference.FieldType> getOptionalFields(Reference.Type type) {
        if (!initialized) initialize();
        return optionalFields.get(type);
    }
    
    private static void initialize() {
        requiredFields = new EnumMap<>(Reference.Type.class);
        optionalFields = new EnumMap<>(Reference.Type.class);
        
        ArrayList<Reference.FieldType> reqFieldTypes = new ArrayList<>();
        ArrayList<Reference.FieldType> optFieldTypes = new ArrayList<>();
        
        reqFieldTypes.add(Reference.FieldType.Author);
        reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.BookTitle);
        reqFieldTypes.add(Reference.FieldType.Year);
        
        optFieldTypes.add(Reference.FieldType.Editor);
        optFieldTypes.add(Reference.FieldType.Pages);
        optFieldTypes.add(Reference.FieldType.Organization);
        optFieldTypes.add(Reference.FieldType.Publisher);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        
        requiredFields.put(Reference.Type.InProceedings, reqFieldTypes);
        optionalFields.put(Reference.Type.InProceedings, optFieldTypes);
        
        initialized = true;
    }
    
}
