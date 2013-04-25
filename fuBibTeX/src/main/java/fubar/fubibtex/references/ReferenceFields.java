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
        
        /****************************************************
         * Inproceedings
         ***************************************************/
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

        /****************************************************
         * Article
         ***************************************************/
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

        /****************************************************
         * Book
         ***************************************************/
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

        /****************************************************
         * Booklet
         ***************************************************/
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();

        reqFieldTypes.add(Reference.FieldType.Title);
        
        optFieldTypes.add(Reference.FieldType.Author);
        optFieldTypes.add(Reference.FieldType.Howpublished);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Year);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Keywords);

        requiredFields.put(Reference.Type.Booklet, reqFieldTypes);
        optionalFields.put(Reference.Type.Booklet, optFieldTypes);
        
        /****************************************************
         * Conference
         ***************************************************/
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();

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

        requiredFields.put(Reference.Type.Conference, reqFieldTypes);
        optionalFields.put(Reference.Type.Conference, optFieldTypes);
        
        /****************************************************
         * Inbook
         ***************************************************/
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();

        // NOTE: Pages is optional with Chapter, as is Author/Editor
        reqFieldTypes.add(Reference.FieldType.Author);
        reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.Pages);
        reqFieldTypes.add(Reference.FieldType.Publisher);
	reqFieldTypes.add(Reference.FieldType.Year);
        
        optFieldTypes.add(Reference.FieldType.Volume);
        optFieldTypes.add(Reference.FieldType.Series);
        optFieldTypes.add(Reference.FieldType.Type);
        optFieldTypes.add(Reference.FieldType.Edition);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Keywords);

        requiredFields.put(Reference.Type.Inbook, reqFieldTypes);
        optionalFields.put(Reference.Type.Inbook, optFieldTypes);
        
        /****************************************************
         * Incollection
         ***************************************************/
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();

        reqFieldTypes.add(Reference.FieldType.Author);
        reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.Booktitle);
        reqFieldTypes.add(Reference.FieldType.Publisher);
	reqFieldTypes.add(Reference.FieldType.Year);
        
        optFieldTypes.add(Reference.FieldType.Editor);
        optFieldTypes.add(Reference.FieldType.Volume);
        optFieldTypes.add(Reference.FieldType.Series);
        optFieldTypes.add(Reference.FieldType.Type);
        optFieldTypes.add(Reference.FieldType.Chapter);
        optFieldTypes.add(Reference.FieldType.Pages);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Edition);
        optFieldTypes.add(Reference.FieldType.Keywords);

        requiredFields.put(Reference.Type.Incollection, reqFieldTypes);
        optionalFields.put(Reference.Type.Incollection, optFieldTypes);
        
        /****************************************************
         * Manual
         ***************************************************/
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();

        reqFieldTypes.add(Reference.FieldType.Title);
        
        optFieldTypes.add(Reference.FieldType.Author);
        optFieldTypes.add(Reference.FieldType.Organization);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Edition);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Year);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Keywords);

        requiredFields.put(Reference.Type.Manual, reqFieldTypes);
        optionalFields.put(Reference.Type.Manual, optFieldTypes);
        
        /****************************************************
         * Mastersthesis
         ***************************************************/
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();

        reqFieldTypes.add(Reference.FieldType.Author);
        reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.School);
	reqFieldTypes.add(Reference.FieldType.Year);
        
        optFieldTypes.add(Reference.FieldType.Type);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Keywords);

        requiredFields.put(Reference.Type.Mastersthesis, reqFieldTypes);
        optionalFields.put(Reference.Type.Mastersthesis, optFieldTypes);
        
        /****************************************************
         * Phdthesis
         ***************************************************/
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();

        reqFieldTypes.add(Reference.FieldType.Author);
        reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.School);
	reqFieldTypes.add(Reference.FieldType.Year);
        
        optFieldTypes.add(Reference.FieldType.Type);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Keywords);

        requiredFields.put(Reference.Type.Mastersthesis, reqFieldTypes);
        optionalFields.put(Reference.Type.Mastersthesis, optFieldTypes);
        
        /****************************************************
         * Proceedings
         ***************************************************/
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();
        
        reqFieldTypes.add(Reference.FieldType.Title);
	reqFieldTypes.add(Reference.FieldType.Year);
        
        optFieldTypes.add(Reference.FieldType.Editor);
        optFieldTypes.add(Reference.FieldType.Volume);
        optFieldTypes.add(Reference.FieldType.Series);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Keywords);
        
        requiredFields.put(Reference.Type.Proceedings, reqFieldTypes);
        optionalFields.put(Reference.Type.Proceedings, optFieldTypes);
        
        /****************************************************
         * Techreport
         ***************************************************/
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();
        
        reqFieldTypes.add(Reference.FieldType.Author);
	reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.Institution);
	reqFieldTypes.add(Reference.FieldType.Year);
        
        optFieldTypes.add(Reference.FieldType.Type);
        optFieldTypes.add(Reference.FieldType.Number);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Keywords);
        
        requiredFields.put(Reference.Type.Techreport, reqFieldTypes);
        optionalFields.put(Reference.Type.Techreport, optFieldTypes);
        
        /****************************************************
         * Unpublished
         ***************************************************/
        reqFieldTypes = new ArrayList<Reference.FieldType>();
        optFieldTypes = new ArrayList<Reference.FieldType>();
        
        reqFieldTypes.add(Reference.FieldType.Author);
	reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.Note);
        
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Year);
        optFieldTypes.add(Reference.FieldType.Key);
        optFieldTypes.add(Reference.FieldType.Keywords);
        
        requiredFields.put(Reference.Type.Unpublished, reqFieldTypes);
        optionalFields.put(Reference.Type.Unpublished, optFieldTypes);
        
        /****************************************************
         * Misc
         ***************************************************/
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
