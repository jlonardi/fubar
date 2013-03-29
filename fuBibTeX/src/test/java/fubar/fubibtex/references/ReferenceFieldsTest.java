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
        
        reqFieldTypes.add(Reference.FieldType.author);
        reqFieldTypes.add(Reference.FieldType.title);
        reqFieldTypes.add(Reference.FieldType.booktitle);
        reqFieldTypes.add(Reference.FieldType.year);
        
        List<Reference.FieldType> got = ReferenceFields.getRequiredFields(Reference.Type.inproceedings);
        
        for (Reference.FieldType ft : got)
            assertTrue(reqFieldTypes.contains(ft));
    }
    
    public void testReturnsRightOptionalFields() {
        ArrayList<Reference.FieldType> optFieldTypes = new ArrayList<Reference.FieldType>();
        
        optFieldTypes.add(Reference.FieldType.editor);
        optFieldTypes.add(Reference.FieldType.pages);
        optFieldTypes.add(Reference.FieldType.organization);
        optFieldTypes.add(Reference.FieldType.publisher);
        optFieldTypes.add(Reference.FieldType.address);
        optFieldTypes.add(Reference.FieldType.month);
        optFieldTypes.add(Reference.FieldType.note);
        optFieldTypes.add(Reference.FieldType.key);
        
        List<Reference.FieldType> got = ReferenceFields.getOptionalFields(Reference.Type.inproceedings);
        
        for (Reference.FieldType ft : got)
            assertTrue(optFieldTypes.contains(ft));
    }
}
