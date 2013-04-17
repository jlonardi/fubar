/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.fubibtex.references;

import static junit.framework.Assert.*;
import junit.framework.TestCase;

/**
 *
 * @author petteri
 */
public class CitationKeyBuilderTest extends TestCase {
        
    public CitationKeyBuilderTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testRightSuggestionWithMultipleAuthors() {
        Reference ref = new Reference(Reference.Type.Misc);
        ref.setField(Reference.FieldType.Author, "Linnakangas, Petteri and Lonardi, Jarno and Aalto, Tuomo");
        ref.setField(Reference.FieldType.Year, "2013");
        String citekey = CitationKeyBuilder.suggestCitation(ref);
        
        System.out.println("Got citation key: " + citekey);
        
        assertEquals("LLA13", citekey);
    }
    
    public void testRightSuggestionWithOneAuthor() {
        Reference ref = new Reference(Reference.Type.Misc);
        ref.setField(Reference.FieldType.Author, "Linnakangas, Petteri");
        ref.setField(Reference.FieldType.Year, "2013");
        String citekey = CitationKeyBuilder.suggestCitation(ref);
        
        System.out.println("Got citation key: " + citekey);
        
        assertEquals("Linnakangas13", citekey);
    }
    
    public void testRightSuggestionWithNoAuthors() {
        Reference ref = new Reference(Reference.Type.Misc);
        ref.setField(Reference.FieldType.Year, "2013");
        String citekey = CitationKeyBuilder.suggestCitation(ref);
        
        System.out.println("Got citation key: " + citekey);
        
        assertEquals("13", citekey);
    }
    
    public void testRightSuggestionWithNoYear() {
        Reference ref = new Reference(Reference.Type.Misc);
        ref.setField(Reference.FieldType.Author, "Linnakangas, Petteri and Lonardi, Jarno and Aalto, Tuomo");
        String citekey = CitationKeyBuilder.suggestCitation(ref);
        
        System.out.println("Got citation key: " + citekey);
        
        assertEquals("LLA", citekey);
    }
    
    public void testEmptyStringOnNoYearOrAuthor() {
        Reference ref = new Reference(Reference.Type.Misc);
        String citekey = CitationKeyBuilder.suggestCitation(ref);
        assertNotNull("Null string returned", citekey);
        assertEquals("", citekey);
    }
}
