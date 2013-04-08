package fubar.fubibtex.references;

import java.io.Writer;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.List;

/**
 *
 * @author fubar
 */
public class Reference {

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
     * @return value of the field
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

        try {

            output.write("\n@"
                    + referenceType.toString().toUpperCase()
                    + " {"
                    + citationKey
                    + ",\n");

            for (Map.Entry<FieldType, String> entry : fields.entrySet()) {
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
     * Returns the citation key of this reference.
     *
     * @return The citation key of this reference.
     */
    @Override
    public String toString() {
        return this.citationKey;
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
        Masterthesis,
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
        Instituition,
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
