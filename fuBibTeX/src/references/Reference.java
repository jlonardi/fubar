package references;

import java.io.PrintWriter;
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
    
    public Reference(Type type) {
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
    
    public List<FieldType> getMissingFields() {
        List<FieldType> reqFields = ReferenceFields.getRequiredFields(referenceType);
        ArrayList<FieldType> missing = new ArrayList<>();
        
        for (FieldType req : reqFields) {
            if (!fields.containsKey(req))
                missing.add(req);
        }
        
        return missing;
    }
    
    public boolean save(PrintWriter file) {
        
        file.write("@" + referenceType.toString().toUpperCase() + " {\n");
        
        for (Map.Entry<FieldType, String> entry : fields.entrySet())
            file.write(entry.getKey().toString().toUpperCase() 
                        + " = " 
                        + entry.getValue()
                        + "\n");
        
        file.write("}\n");
        
        return false;
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
