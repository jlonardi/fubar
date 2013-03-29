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
        
        reqFieldTypes.add(Reference.FieldType.AUTHOR);
        reqFieldTypes.add(Reference.FieldType.TITLE);
        reqFieldTypes.add(Reference.FieldType.BOOKTITLE);
        reqFieldTypes.add(Reference.FieldType.YEAR);
        
        List<Reference.FieldType> got = ReferenceFields.getRequiredFields(Reference.Type.INPROCEEDINGS);
        
        for (Reference.FieldType ft : got)
            assertTrue(reqFieldTypes.contains(ft));
    }
    
    public void testReturnsRightOptionalFields() {
        ArrayList<Reference.FieldType> optFieldTypes = new ArrayList<Reference.FieldType>();
        
        optFieldTypes.add(Reference.FieldType.EDITOR);
        optFieldTypes.add(Reference.FieldType.PAGES);
        optFieldTypes.add(Reference.FieldType.ORGANIZATION);
        optFieldTypes.add(Reference.FieldType.PUBLISHER);
        optFieldTypes.add(Reference.FieldType.ADDRESS);
        optFieldTypes.add(Reference.FieldType.MONTH);
        optFieldTypes.add(Reference.FieldType.NOTE);
        optFieldTypes.add(Reference.FieldType.KEY);
        
        List<Reference.FieldType> got = ReferenceFields.getOptionalFields(Reference.Type.INPROCEEDINGS);
        
        for (Reference.FieldType ft : got)
            assertTrue(optFieldTypes.contains(ft));
    }
}
