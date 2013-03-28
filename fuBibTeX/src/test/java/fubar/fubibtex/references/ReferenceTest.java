/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.fubibtex.references;

import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Petteri Linnakangas
 */
public class ReferenceTest extends TestCase {
    
    Reference ref;
    List<Reference.FieldType> reqFields;
    
    public ReferenceTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        ref = new Reference(Reference.Type.InProceedings);
        reqFields = ReferenceFields.getRequiredFields(Reference.Type.InProceedings);
        
        ref.setCitationKey("PL09");
        ref.setField(Reference.FieldType.Author, "Petteri Linnakangas");
        ref.setField(Reference.FieldType.Title, "SysteemiJuttuja");
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testReturnsRightType() {
        assertEquals(ref.getType(), Reference.Type.InProceedings);
    }
    
    public void testReturnsRightField() {
        assertEquals("Petteri Linnakangas", ref.getField(Reference.FieldType.Author));
    }
    
    public void testReturnRightCitationKey() {
        assertEquals("PL09", ref.getCitationKey());
    }
    
    public void testProperlySetsCitationKey() {
        ref.setCitationKey("PL10");
        assertEquals("PL10", ref.getCitationKey());
    }
    
    public void testRecognizesMissingFields() {
        // The InProceedings now misses BookTitle and Year
        List<Reference.FieldType> missing = ref.getMissingFields();
        
        assertTrue(missing.contains(Reference.FieldType.BookTitle));
        assertTrue(missing.contains(Reference.FieldType.Year));
        assertFalse(missing.contains(Reference.FieldType.CrossRef));
    }
}
