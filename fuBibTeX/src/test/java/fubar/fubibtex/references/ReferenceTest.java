/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.fubibtex.references;

import java.util.List;
import java.io.StringWriter;
import static junit.framework.Assert.assertEquals;
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
        ref = new Reference(Reference.Type.Inproceedings);
        reqFields = ReferenceFields.getRequiredFields(Reference.Type.Inproceedings);
        
        ref.setCitationKey("PL09");
        ref.setField(Reference.FieldType.Author, "Petteri Linnakangas");
        ref.setField(Reference.FieldType.Title, "SysteemiJuttuja");
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testReturnsRightType() {
        assertEquals(ref.getType(), Reference.Type.Inproceedings);
    }
    
    public void testReturnsRightField() {
        assertEquals("Petteri Linnakangas", ref.getField(Reference.FieldType.Author));
    }
    
    public void testReturnRightCitationKey() {
        assertEquals("PL09", ref.getCitationKey());
    }
    
    public void testReturnsCitationKeyOnToString() {
        assertEquals("PL09", ref.toString());
    }
    
    public void testSavesCorrectly() {
        /**
         * @Inproceedings {PL09,
         *     Author = {Petteri Linnakangas},
         *     Title = {SysteemiJuttuja},
         * }
         */
        String properReturn = "\n@" + ref.getType().toString().toUpperCase();
        properReturn += " {" + ref.getCitationKey() + ",\n";
        properReturn += "\t" + Reference.FieldType.Author.toString().toUpperCase(); 
        properReturn += " = {" + ref.getField(Reference.FieldType.Author) + "},\n";
        properReturn += "\t" + Reference.FieldType.Title.toString().toUpperCase(); 
        properReturn += " = {" + ref.getField(Reference.FieldType.Title) + "},\n";
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
        
        assertTrue(missing.contains(Reference.FieldType.Booktitle));
        assertTrue(missing.contains(Reference.FieldType.Year));
        assertFalse(missing.contains(Reference.FieldType.Crossref));
    }
	
	public void testConvertAccented(){
		Reference r = new Reference(Reference.Type.Inproceedings);
		
		assertEquals("\\\"{A}", r.convertAccented('Ä'));
		assertEquals("\\\"{a}", r.convertAccented('ä'));
		assertEquals("\\r{A}" , r.convertAccented('Å'));
		assertEquals("\\r{a}" , r.convertAccented('å'));
		assertEquals("\\\"{O}", r.convertAccented('Ö'));
		assertEquals("\\\"{o}", r.convertAccented('ö'));
	}
	
	public void testPrepareForPrint(){
		Reference r = new Reference(Reference.Type.Inproceedings);
		
		r.setCitationKey("ÄÄ05");
		r.setField(Reference.FieldType.Author,    "Äku Änkkä");
		r.setField(Reference.FieldType.Title,     "Ääkkösten alkeet");
		r.setField(Reference.FieldType.Booktitle, "Öökkösten alkeet");
		r.setField(Reference.FieldType.Editor,    "Örån Årmät");

		r = r.prepareForPrint();
		
		assertEquals("\\\"{A}ku \\\"{A}nkk\\\"{a}",        r.getField(Reference.FieldType.Author));
		assertEquals("\\\"{A}\\\"{a}kk\\\"{o}sten alkeet", r.getField(Reference.FieldType.Title));
		assertEquals("\\\"{O}\\\"{o}kk\\\"{o}sten alkeet", r.getField(Reference.FieldType.Booktitle));
		assertEquals("\\\"{O}r\\r{a}n \\r{A}rm\\\"{a}t",   r.getField(Reference.FieldType.Editor));
		
		
	}
	
}
