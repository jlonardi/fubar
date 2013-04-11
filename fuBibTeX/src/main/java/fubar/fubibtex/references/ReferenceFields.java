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

        ArrayList<Reference.FieldType> reqFieldTypesBook = new ArrayList<Reference.FieldType>();
        ArrayList<Reference.FieldType> optFieldTypesBook = new ArrayList<Reference.FieldType>();

		//Required fields: author or editor, title, publisher, year
		//Optional fields: volume, series, address, edition, month, note, key
        reqFieldTypesBook.add(Reference.FieldType.Author);
        reqFieldTypesBook.add(Reference.FieldType.Title);
        reqFieldTypesBook.add(Reference.FieldType.Publisher);
		reqFieldTypesBook.add(Reference.FieldType.Year);
        
        optFieldTypesBook.add(Reference.FieldType.Volume);
        optFieldTypesBook.add(Reference.FieldType.Series);
        optFieldTypesBook.add(Reference.FieldType.Address);
        optFieldTypesBook.add(Reference.FieldType.Edition);
        optFieldTypesBook.add(Reference.FieldType.Month);
        optFieldTypesBook.add(Reference.FieldType.Note);
        optFieldTypesBook.add(Reference.FieldType.Key);

        requiredFields.put(Reference.Type.book, reqFieldTypesBook);
        optionalFields.put(Reference.Type.book, optFieldTypesBook);

		ArrayList<Reference.FieldType> reqFieldTypesArticle = new ArrayList<Reference.FieldType>();
		ArrayList<Reference.FieldType> optFieldTypesArticle = new ArrayList<Reference.FieldType>();

		reqFieldTypesArticle.add(Reference.FieldType.Author);
        reqFieldTypesArticle.add(Reference.FieldType.Title);
        reqFieldTypesArticle.add(Reference.FieldType.Journal);
		reqFieldTypesArticle.add(Reference.FieldType.Year);
        
        optFieldTypesArticle.add(Reference.FieldType.Volume);
        optFieldTypesArticle.add(Reference.FieldType.Number);
        optFieldTypesArticle.add(Reference.FieldType.Pages);
        optFieldTypesArticle.add(Reference.FieldType.Month);
        optFieldTypesArticle.add(Reference.FieldType.Note);
        optFieldTypesArticle.add(Reference.FieldType.Key);

        requiredFields.put(Reference.Type.article, reqFieldTypesArticle);
        optionalFields.put(Reference.Type.article, optFieldTypesArticle);

		//Optional fields: author, title, howpublished, month, year, note, key
		
		ArrayList<Reference.FieldType> optFieldTypesMisc = new ArrayList<Reference.FieldType>();
        
        optFieldTypesMisc.add(Reference.FieldType.Author);
        optFieldTypesMisc.add(Reference.FieldType.Title);
        optFieldTypesMisc.add(Reference.FieldType.Howpublished);
        optFieldTypesMisc.add(Reference.FieldType.Month);
		optFieldTypesMisc.add(Reference.FieldType.Year);
        optFieldTypesMisc.add(Reference.FieldType.Note);
        optFieldTypesMisc.add(Reference.FieldType.Key);

        optionalFields.put(Reference.Type.Misc, optFieldTypesMisc);		

        initialized = true;
    }
    
}
