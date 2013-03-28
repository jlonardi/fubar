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
    
    public Reference(Type type) {
        fields = new EnumMap<FieldType, String>(FieldType.class);
        referenceType = type;
    }
    
    public Type getType() {
        return referenceType;
    }

    public String getField(FieldType type) {
        return fields.get(type);
    }
    
    public void setField(FieldType type, String value) {
        fields.put(type, value);
    }
    
    public void setCitationKey(String key) {
        this.citationKey = key;
    }
    
    public String getCitationKey() {
        return this.citationKey;
    }
    public List<FieldType> getMissingFields() {
        List<FieldType> reqFields = ReferenceFields.getRequiredFields(referenceType);
        ArrayList<FieldType> missing = new ArrayList<FieldType>();
        
        for (FieldType req : reqFields) {
            if (!fields.containsKey(req))
                missing.add(req);
        }
        
        return missing;
    }
    
    public boolean save(Writer output) {
        
        try {
            
            output.write("\n@" 
                            + referenceType.toString().toUpperCase() 
                            + " {" 
                            + citationKey 
                            + ",\n");

            for (Map.Entry<FieldType, String> entry : fields.entrySet())
                output.write("\t" 
                                + entry.getKey().toString().toUpperCase() 
                                + " = " 
                                + "{" + entry.getValue() + "},"
                                + "\n");

            output.write("}\n");
            
        } catch (Exception e) {
            System.out.println("Could not save reference to writer: " + e.getMessage());
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return this.citationKey;
    }
    
    public enum Type {
        Article,
        Book,
        Booklet,
        Conference,
        InBook,
        InCollection,
        InProceedings,
        Manual,
        MastersThesis,
        Misc,
        PhdThesis,
        Proceedings,
        TechReport,
        Unpublished,
    }
    
    public enum FieldType {
        Address,
        Annote,
        Author,
        BookTitle,
        Chapter,
        CrossRef,
        Edition,
        Editor,
        HowPublished,
        Institution,
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
        Year
    }
}
