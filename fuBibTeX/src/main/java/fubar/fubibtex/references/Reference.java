package fubar.fubibtex.references;

import java.io.Writer;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fubar
 */
public class Reference implements Cloneable{

    private static ArrayList<FieldType> ToStringPreferenceList;
    
    static {
        ToStringPreferenceList = new ArrayList<FieldType>();
        ToStringPreferenceList.add(FieldType.Author);
        ToStringPreferenceList.add(FieldType.Title);
        ToStringPreferenceList.add(FieldType.Booktitle);
        ToStringPreferenceList.add(FieldType.Chapter);
        ToStringPreferenceList.add(FieldType.Year);
        ToStringPreferenceList.add(FieldType.Keywords);
    }
    
    private Type referenceType;
    private EnumMap<FieldType, String> fields;
    private String citationKey;

    /**
     * Constructs a new reference based on the type.
     *
     * @param type The type of this reference
     */
    public Reference(Type type) {
        fields = new EnumMap<FieldType, String>(FieldType.class);
        referenceType = type;
    }

    /**
     *
     * @return The type of this reference
     */
    public Type getType() {
        return referenceType;
    }

    /**
     *
     * @param type The type of the field
     * @return value of the field, null if not found.
     */
    public String getField(FieldType type) {
        return fields.get(type);
    }

    /**
     *
     * @param type Type of the field.
     * @param value Value of the field.
     */
    public void setField(FieldType type, String value) {
        fields.put(type, value);
    }

    /**
     *
     * @param key New citation key.
     */
    public void setCitationKey(String key) {
        this.citationKey = key;
    }

    /**
     *
     * @return The citation key of this reference
     */
    public String getCitationKey() {
        return this.citationKey;
    }

    /**
     * Checks the reference for possible required fields that are missing.
     *
     * @return A list of the missing fields.
     */
    public List<FieldType> getMissingFields() {
        List<FieldType> reqFields = ReferenceFields.getRequiredFields(referenceType);
        ArrayList<FieldType> missing = new ArrayList<FieldType>();

        for (FieldType req : reqFields) {
            if (!fields.containsKey(req)) {
                missing.add(req);
            }
        }

        return missing;
    }

    /**
     * Tries to save the reference to a Writer stream.
     *
     * @param output
     * @return Value indicating success.
     */
    public boolean save(Writer output) {
		
		//So that we wont mess up References with print-related crap, we'll make a copy of the Reference and only modify that
		Reference rfp=null;
		try {
			rfp = (Reference)this.clone();
		} catch (CloneNotSupportedException ex) {
			Logger.getLogger(Reference.class.getName()).log(Level.SEVERE, null, ex);
		}

		rfp = rfp.prepareForPrint();
		
        try {

            output.write("\n@"
                    + rfp.referenceType.toString().toUpperCase()
                    + " {"
                    + rfp.citationKey
                    + ",\n");

            for (Map.Entry<FieldType, String> entry : rfp.fields.entrySet()) {
                output.write("\t"
                        + entry.getKey().toString().toUpperCase()
                        + " = "
                        + "{" + entry.getValue() + "},"
                        + "\n");
            }

            output.write("}\n");

        } catch (Exception e) {
            System.out.println("Could not save reference to writer: " + e.getMessage());
            return false;
        }

        return true;
    }

	/**
	 * Returns a copy of this reference readied for printing into file.
	 * This basically means that all Nordic characters are morphed into format that Bibtex wants.
	 */
	protected Reference prepareForPrint() {
		Reference referenceForPrinting = this;
		StringBuilder citKeySB = new StringBuilder();
		
		for (int i = 0; i < referenceForPrinting.citationKey.length(); i++) {
			char c = referenceForPrinting.citationKey.charAt(i);
			citKeySB.append(convertAccented(c));
		}
		referenceForPrinting.setCitationKey(citKeySB.toString());
		
		for (Map.Entry<FieldType, String> entry : fields.entrySet()) {
			String key = entry.getValue();
			StringBuilder keySB = new StringBuilder();
			for (int i = 0; i < key.length(); i++) {
				char c = key.charAt(i);
				keySB.append(convertAccented(c));
			}
			entry.setValue(keySB.toString());
		}

		return referenceForPrinting;
	}
	
    /**
     * Returns a human readable representation of the Reference. Builds it
     * using the preferences provided in this class.
     * @return The citation key of this reference.
     */
    @Override
    public String toString() {
        String ret = "[" + this.citationKey + "]";
        
        for (FieldType ft : ToStringPreferenceList) {
            String field = this.getField(ft);
            if (field != null)
                ret += " | " + field;
        }
        
        return ret;
    }

	protected String convertAccented(char c) {
		String retVal = "";
		
		switch (c)
		{	
			case 'Ä':
				retVal = "\\\"{A}";
				break;
			case 'ä':
				retVal = "\\\"{a}";
				break;
			case 'Å':
				retVal = "\\r{A}";
				break;
			case 'å':
				retVal = "\\r{a}";
				break;
			case 'Ö':
				retVal = "\\\"{O}";
				break;
			case 'ö':
				retVal = "\\\"{o}";
				break;
			default:
				retVal = String.valueOf(c);
		}
		
		return retVal;
	}
	
    public enum Type {

        article,
        book,
        Booklet,
        Conference,
        Inbook,
        Incollection,
        Inproceedings,
        Manual,
        Mastersthesis,
        Misc,
        Phdthesis,
        Proceedings,
        Techreport,
        Unpublished;

        public static Type getTypeByString(String s) {
            for (Type type : values()) {
                if (type.name().toUpperCase().equals(s.toUpperCase())) {
                    return type;
                }
            }
            return null;
        }
    }

    public enum FieldType {

        Address,
        Annote,
        Author,
        Booktitle,
        Chapter,
        Crossref,
        Edition,
        Editor,
        Howpublished,
        Institution,
        Isbn,
        Journal,
        Key,
        Keywords,
        Month,
        Note,
        Number,
        Organization,
        Pages,
        Publisher,
        School,
        Series,
        Title,
        Type,
        Volume,
        Year;

        public static FieldType getFieldTypeByString(String s) {
            for (FieldType type : values()) {
                if (type.name().toUpperCase().equals(s.toUpperCase())) {
                    return type;
                }
            }
            return null;
        }
    }
}
