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

    /**
     * Returns the required fields for a given type of reference.
     * @param type The type of the reference.
     * @return List of the required fields.
     */
    public static List<Reference.FieldType> getRequiredFields(Reference.Type type) {
        return requiredFields.get(type);
    }
    
    /**
     * Returns the optional fields for a given type of reference.
     * @param type The type of the reference.
     * @return List of the optional fields.
     */
    public static List<Reference.FieldType> getOptionalFields(Reference.Type type) {
        return optionalFields.get(type);
    }
    
    /**
     * Initializes the required and optional fields for all types of references.
     */
    static {

        requiredFields = new EnumMap<Reference.Type, List<Reference.FieldType>>(Reference.Type.class);
        optionalFields = new EnumMap<Reference.Type, List<Reference.FieldType>>(Reference.Type.class);
        
        // Inproceedings
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
        optFieldTypes.add(Reference.FieldType.Keywords);
        
        requiredFields.put(Reference.Type.Inproceedings, reqFieldTypes);
        optionalFields.put(Reference.Type.Inproceedings, optFieldTypes);

        // Book
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();

	//Required fields: author or editor, title, publisher, year
	//Optional fields: volume, series, address, edition, month, note, key
        reqFieldTypes.add(Reference.FieldType.Author);
        reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.Publisher);
	reqFieldTypes.add(Reference.FieldType.Year);
        
        optFieldTypes.add(Reference.FieldType.Volume);
        optFieldTypes.add(Reference.FieldType.Series);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Edition);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Keywords);

        requiredFields.put(Reference.Type.book, reqFieldTypes);
        optionalFields.put(Reference.Type.book, optFieldTypes);

	reqFieldTypes = new ArrayList<Reference.FieldType>();
	optFieldTypes = new ArrayList<Reference.FieldType>();

	reqFieldTypes.add(Reference.FieldType.Author);
        reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.Journal);
	reqFieldTypes.add(Reference.FieldType.Year);
        
        optFieldTypes.add(Reference.FieldType.Volume);
        optFieldTypes.add(Reference.FieldType.Number);
        optFieldTypes.add(Reference.FieldType.Pages);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Keywords);

        requiredFields.put(Reference.Type.article, reqFieldTypes);
        optionalFields.put(Reference.Type.article, optFieldTypes);

        // Misc
	// Optional fields: author, title, howpublished, month, year, note, key	
	optFieldTypes = new ArrayList<Reference.FieldType>();
        
        optFieldTypes.add(Reference.FieldType.Author);
        optFieldTypes.add(Reference.FieldType.Title);
        optFieldTypes.add(Reference.FieldType.Howpublished);
        optFieldTypes.add(Reference.FieldType.Month);
	optFieldTypes.add(Reference.FieldType.Year);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Keywords);

        optionalFields.put(Reference.Type.Misc, optFieldTypes);
    }
    
}
