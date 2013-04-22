package fubar.fubibtex.references;

/**
 * A class for automatically building citation keys to BibTeX references
 * @author fubar
 */
public class CitationKeyBuilder {
    
    /**
     * Builds a citation key for the given Reference. Uses the authors' last
     * names and the year to generate the suggestion. If these are not found,
     * nothing is generated.
     * Multiple authors should be formatted in the following way:
     * "Lastname, Firstname and Lastname, Firstname"
     * @param ref
     * @return Suggested citation key. Empty string if can't be generated.
     */
    public static String suggestCitation(Reference ref) {
        String citationKey = "";
        
        String field = ref.getField(Reference.FieldType.Author);
        
        if (field != null) {
            String authors[] = field.split(" and ");

            if (authors.length < 2)
                citationKey += authors[0].split(",")[0];
            else 
                for (String author : authors)
                    citationKey += author.charAt(0);
        }
        
        field = ref.getField(Reference.FieldType.Year);
        
        if (field != null && field.length() > 3)
            citationKey += field.substring(2, 4);
        
        return citationKey;
    }
}
