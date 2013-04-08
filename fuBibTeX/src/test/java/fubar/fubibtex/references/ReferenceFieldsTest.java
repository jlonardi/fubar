/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.fubibtex.references;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author petteri
 */
public class ReferenceFieldsTest extends TestCase {
    
    public ReferenceFieldsTest(String testName) {
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
    
    public void testReturnsRightRequiredFields() {
        ArrayList<Reference.FieldType> reqFieldTypes = new ArrayList<Reference.FieldType>();
        
        reqFieldTypes.add(Reference.FieldType.Author);
        reqFieldTypes.add(Reference.FieldType.Title);
        reqFieldTypes.add(Reference.FieldType.Booktitle);
        reqFieldTypes.add(Reference.FieldType.Year);
        
        List<Reference.FieldType> got = ReferenceFields.getRequiredFields(Reference.Type.Inproceedings);
        
        for (Reference.FieldType ft : got)
            assertTrue(reqFieldTypes.contains(ft));
    }
    
    public void testReturnsRightOptionalFields() {
        ArrayList<Reference.FieldType> optFieldTypes = new ArrayList<Reference.FieldType>();
        
        optFieldTypes.add(Reference.FieldType.Editor);
        optFieldTypes.add(Reference.FieldType.Pages);
        optFieldTypes.add(Reference.FieldType.Organization);
        optFieldTypes.add(Reference.FieldType.Publisher);
        optFieldTypes.add(Reference.FieldType.Address);
        optFieldTypes.add(Reference.FieldType.Month);
        optFieldTypes.add(Reference.FieldType.Note);
        optFieldTypes.add(Reference.FieldType.Key);
        
        List<Reference.FieldType> got = ReferenceFields.getOptionalFields(Reference.Type.Inproceedings);
        
        for (Reference.FieldType ft : got)
            assertTrue(optFieldTypes.contains(ft));
    }
}
