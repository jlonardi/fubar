/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.fubibtex.references;

import java.util.List;
import java.io.StringWriter;
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
        ref = new Reference(Reference.Type.INPROCEEDINGS);
        reqFields = ReferenceFields.getRequiredFields(Reference.Type.INPROCEEDINGS);
        
        ref.setCitationKey("PL09");
        ref.setField(Reference.FieldType.AUTHOR, "Petteri Linnakangas");
        ref.setField(Reference.FieldType.TITLE, "SysteemiJuttuja");
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testReturnsRightType() {
        assertEquals(ref.getType(), Reference.Type.INPROCEEDINGS);
    }
    
    public void testReturnsRightField() {
        assertEquals("Petteri Linnakangas", ref.getField(Reference.FieldType.AUTHOR));
    }
    
    public void testReturnRightCitationKey() {
        assertEquals("PL09", ref.getCitationKey());
    }
    
    public void testReturnsCitationKeyOnToString() {
        assertEquals("PL09", ref.toString());
    }
    
    public void testSavesCorrectly() {
        /**
         * @INPROCEEDINGS {PL09,
         *     AUTHOR = {Petteri Linnakangas},
         *     TITLE = {SysteemiJuttuja},
         * }
         */
        String properReturn = "\n@" + ref.getType().toString().toUpperCase();
        properReturn += " {" + ref.getCitationKey() + ",\n";
        properReturn += "\t" + Reference.FieldType.AUTHOR.toString().toUpperCase(); 
        properReturn += " = {" + ref.getField(Reference.FieldType.AUTHOR) + "},\n";
        properReturn += "\t" + Reference.FieldType.TITLE.toString().toUpperCase(); 
        properReturn += " = {" + ref.getField(Reference.FieldType.TITLE) + "},\n";
        properReturn += "}\n";
        
        StringWriter sw = new StringWriter();
        
        ref.save(sw);
        
        assertEquals(properReturn, sw.toString());
    }
    
    public void testProperlySetsCitationKey() {
        ref.setCitationKey("PL10");
        assertEquals("PL10", ref.getCitationKey());
    }
    
    public void testRecognizesMissingFields() {
        // The InProceedings now misses BookTitle and Year
        List<Reference.FieldType> missing = ref.getMissingFields();
        
        assertTrue(missing.contains(Reference.FieldType.BOOKTITLE));
        assertTrue(missing.contains(Reference.FieldType.YEAR));
        assertFalse(missing.contains(Reference.FieldType.CROSSREF));
    }
}
