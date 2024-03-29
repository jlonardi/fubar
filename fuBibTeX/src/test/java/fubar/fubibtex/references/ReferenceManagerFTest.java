/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.fubibtex.references;

import java.io.File;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;

/**
 *
 * @author aaltotuo
 */
public class ReferenceManagerFTest extends TestCase {

	public ReferenceManagerFTest(String testName) {
		super(testName);
	}
	ReferenceManagerF rm = new ReferenceManagerF();
	ReferenceManagerF rmExp;
	File output = new File("export_test1.temp");
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void setUpExportTests()
	{
		rmExp = new ReferenceManagerF();
		rmExp.setExportFile(output);
	
		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		rmExp.addReference(r1);
		
		Reference r2 = new Reference(Reference.Type.Inproceedings);
		r2.setCitationKey("TEST2");
		r2.setField(Reference.FieldType.Author, "Timo Testaaja");
		r2.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys, part II - Paluu");
		r2.setField(Reference.FieldType.Booktitle, "What made me the tester I am today.");
		r2.setField(Reference.FieldType.Year, "2013");
		rmExp.addReference(r2);
		
		Reference r3 = new Reference(Reference.Type.Inproceedings);
		r3.setCitationKey("TEST3");
		r3.setField(Reference.FieldType.Author, "Tiina Testaaja");
		r3.setField(Reference.FieldType.Title, "Testaa, testaa, hyvä tulee.");
		r3.setField(Reference.FieldType.Booktitle, "Your easy guide to unit testing");
		r3.setField(Reference.FieldType.Year, "2013");
		rmExp.addReference(r3);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	protected void tearDownExportTests() {
		rmExp = null;
	}
	
	// TODO add test methods here. The name must begin with 'test'. For example:
	// public void testHello() {}

	public void testAddReferenceReturnValueWithCorrectFields() {

		rm = new ReferenceManagerF();
		Reference r = new Reference(Reference.Type.Inproceedings);
		r.setCitationKey("TEST1");
		r.setField(Reference.FieldType.Author, "Timo Testaaja");
		r.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r.setField(Reference.FieldType.Year, "2013");

		assertTrue(rm.addReference(r));
		rm = null;
	}

	public void testAddReferenceReturnValueWithMissingRequiredFields() {
		rm = new ReferenceManagerF();
		Reference r = new Reference(Reference.Type.Inproceedings);
		assertFalse(rm.addReference(r));
		rm = null;
	}

	public void testListSizeBeforeInsert() {
		rm = new ReferenceManagerF();
		assert (rm.getReferences().isEmpty());
		rm = null;
	}

	public void testListSizeAfterInsert() {
		rm = new ReferenceManagerF();
		Reference r = new Reference(Reference.Type.Inproceedings);
		r.setCitationKey("TEST1");
		r.setField(Reference.FieldType.Author, "Timo Testaaja");
		r.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r.setField(Reference.FieldType.Year, "2013");
		rm.addReference(r);

		assert (rm.getReferences().size() > 0);
		rm = null;
	}

	public void testImportFileOperations() {
		rm = new ReferenceManagerF();
		File f = new File("foo.txt");

		rm.setImportFile(f);
		assertEquals(f, rm.getImportFile());
		rm = null;
	}

	public void testExportFileOperations() {
		rm = new ReferenceManagerF();
		File f = new File("foo.txt");

		rm.setExportFile(f);
		assertEquals(f, rm.getExportFile());
		rm = null;
	}
	
	public void testExportAndImport1()
	{
		setUpExportTests();
		ReferenceManagerF in = new ReferenceManagerF();
		List<Reference> rl;
		rmExp.exportTo();
		System.out.println("import file path: " + output.getAbsolutePath());
		in.setImportFile(output);
		in.importFrom();
		
		rl = in.getReferences();
		
		assertEquals(3, rl.size());
		
		tearDownExportTests();
	}
	

	public void testGetReferencesByFilterWithGoodInput()
	{
		rm = new ReferenceManagerF();
		List<Reference> rl;
				
		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		rm.addReference(r1);
		
		Reference r2 = new Reference(Reference.Type.Inproceedings);
		r2.setCitationKey("TEST2");
		r2.setField(Reference.FieldType.Author, "Timo Testaaja");
		r2.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys, part II - Paluu");
		r2.setField(Reference.FieldType.Booktitle, "What made me the tester I am today.");
		r2.setField(Reference.FieldType.Year, "2013");
		rm.addReference(r2);
		
		Reference r3 = new Reference(Reference.Type.Inproceedings);
		r3.setCitationKey("TEST3");
		r3.setField(Reference.FieldType.Author, "Tiina Testaaja");
		r3.setField(Reference.FieldType.Title, "Testaa, testaa, hyvä tulee.");
		r3.setField(Reference.FieldType.Booktitle, "Your easy guide to unit testing");
		r3.setField(Reference.FieldType.Year, "2013");
		rm.addReference(r3);

		rl = rm.getReferencesByFilter(Reference.FieldType.Year, "2010");
		assertEquals(rl.size(), 1);
		assertEquals(rl.get(0).getField(Reference.FieldType.Year), "2010");
		
		rl = rm.getReferencesByFilter(Reference.FieldType.Author, "Timo Testaaja");
		assertEquals(rl.size(), 2);
		
	}
	
	public void testGetReferencesByFilterWithBadInput()
	{
		rm = new ReferenceManagerF();
		List<Reference> rl;
		
		rl = rm.getReferencesByFilter(Reference.FieldType.Year, "2000");
		
		assertTrue(rl.isEmpty());
	}
        

	public void testGetReferencesBySplitFiltersReturnsRightRefs()
	{
		rm = new ReferenceManagerF();
		List<Reference> rl;
                
		Reference r1 = new Reference(Reference.Type.Misc);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		rm.addReference(r1);
		
		Reference r2 = new Reference(Reference.Type.Misc);
		r2.setCitationKey("TEST2");
		r2.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys, part II - Paluu");
		rm.addReference(r2);
		
		Reference r3 = new Reference(Reference.Type.Misc);
		r3.setCitationKey("TEST3");
		r3.setField(Reference.FieldType.Title, "Testaa, testaa, hyvä tulee.");
		rm.addReference(r3);
                
		rl = rm.getReferencesByFilter(Reference.FieldType.Title, "testaamisen,hyvä", ",");

		assertTrue(rl.contains(r1));
		assertTrue(rl.contains(r2));
		assertTrue(rl.contains(r3));
                
	}
        
	public void testGetReferencesBySplitFiltersReturnsNoDuplicates()
	{
		rm = new ReferenceManagerF();
		List<Reference> rl;

		Reference r1 = new Reference(Reference.Type.Misc);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		rm.addReference(r1);
		
		Reference r2 = new Reference(Reference.Type.Misc);
		r2.setCitationKey("TEST2");
		r2.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys, part II - Paluu");
		rm.addReference(r2);

		rl = rm.getReferencesByFilter(Reference.FieldType.Title, "keveys,sietämätön", ",");

		assertEquals(2, rl.size());
	}

	//Test helper classes
	public void testCleanStringTerminatosWithBracketAndComma() {
		rm = new ReferenceManagerF();
		String dirtyString = "author = {Roumani, Hamzeh},";
		String result = rm.cleanStringTerminators(dirtyString);
		assertEquals(result, "author = {Roumani, Hamzeh");
		rm = null;
	}

	public void testCleanStringTerminatosWithQuotationAndComma() {
		rm = new ReferenceManagerF();
		String dirtyString = "AUTHOR = \"L. S. Vygotsky\",";
		String result = rm.cleanStringTerminators(dirtyString);
		assertEquals(result, "AUTHOR = \"L. S. Vygotsky");
		rm = null;
	}

	public void testCleanStringTerminatosWithComma() {
		rm = new ReferenceManagerF();
		String dirtyString = "YEAR = 1978,";
		String result = rm.cleanStringTerminators(dirtyString);
		assertEquals(result, "YEAR = 1978");
		rm = null;
	}

	public void testCleanStringTerminatosWithParenthesis() {
		rm = new ReferenceManagerF();
		String dirtyString = "YEAR = 1978)";
		String result = rm.cleanStringTerminators(dirtyString);
		assertEquals(result, "YEAR = 1978");
		rm = null;
	}

	public void testCleanStringTerminatosWithBracket() {
		rm = new ReferenceManagerF();
		String dirtyString = "YEAR = 1978}";
		String result = rm.cleanStringTerminators(dirtyString);
		assertEquals(result, "YEAR = 1978");
		rm = null;
	}

	public void testBuildKey() {
		rm = new ReferenceManagerF();
		String stringContainingKey = "AUTHOR = {Vihavainen, Arto and Paksula, Matti and Luukkainen, Matti},";
		String result = rm.buildKey(stringContainingKey);
		assertEquals("AUTHOR", result);
		rm = null;
	}

	public void testBuildKeyWithEmptyInput() {
		rm = new ReferenceManagerF();
		String stringContainingKey = "";
		String result = rm.buildKey(stringContainingKey);
		assertEquals("", result);
		rm = null;
	}	
	
	public void testBuildReferenceTypeWithBracket() {
		rm = new ReferenceManagerF();
		String stringContainingType = "inproceedings{VPL11,";
		String result = rm.buildReferenceType(stringContainingType);
		assertEquals("INPROCEEDINGS", result);
		rm = null;
	}

	public void testBuildReferenceTypeWithBracketAndSpace() {
		rm = new ReferenceManagerF();
		String stringContainingType = "INPROCEEDINGS {TAA1,";
		String result = rm.buildReferenceType(stringContainingType);
		assertEquals("INPROCEEDINGS", result);
		rm = null;
	}
	
	
	public void testBuildReferenceTypeWithParenthesis() {
		rm = new ReferenceManagerF();
		String stringContainingType = "inproceedings(VPL11,";
		String result = rm.buildReferenceType(stringContainingType);
		assertEquals("INPROCEEDINGS", result);
		rm = null;
	}

	public void testBuildReferenceTypeWithEmptyInput() {
		rm = new ReferenceManagerF();
		String stringContainingType = "";
		String result = rm.buildReferenceType(stringContainingType);
		assertEquals("", result);
		rm = null;
	}
	
	public void testBuildCitation() {
		rm = new ReferenceManagerF();
		String stringContainingCitation = "KB04,";
		String result = rm.buildCitation(stringContainingCitation);
		assertEquals("KB04", result);
		rm = null;
	}

	public void testBuildCitationWithEmptyInput() {
		rm = new ReferenceManagerF();
		String stringContainingCitation = "";
		String result = rm.buildCitation(stringContainingCitation);
		assertEquals("", result);
		rm = null;
	}	
	
	public void testCleanFieldWithBracket() {
		rm = new ReferenceManagerF();
		String input  = "title = {Learning and teaching programming: A review and discussion},";
		String result = rm.cleanField(input);
		assertEquals("Learning and teaching programming: A review and discussion},", result);
		rm = null;
	}

	public void testCleanFieldWithQuotation() {
		rm = new ReferenceManagerF();
		String input  = "title = \"Learning and teaching programming: A review and discussion\",";
		String result = rm.cleanField(input);
		assertEquals("Learning and teaching programming: A review and discussion\",", result);
		rm = null;
	}

	public void testCleanFieldWithEquals() {
		rm = new ReferenceManagerF();
		String input  = "title = Learning and teaching programming: A review and discussion";
		String result = rm.cleanField(input);
		assertEquals(" Learning and teaching programming: A review and discussion", result);
		rm = null;
	}
	
	public void testCleanFieldWithEmptyInput() {
		rm = new ReferenceManagerF();
		String input  = "";
		String result = rm.cleanField(input);
		assertEquals("", result);
		rm = null;
	}	
	
	public void testContainsCitationKeyWhenCKExists()
	{
		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		rm.addReference(r1);
		
		Reference r2 = new Reference(Reference.Type.Inproceedings);
		r2.setCitationKey("TEST2");
		r2.setField(Reference.FieldType.Author, "Timo Testaaja");
		r2.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys, part II - Paluu");
		r2.setField(Reference.FieldType.Booktitle, "What made me the tester I am today.");
		r2.setField(Reference.FieldType.Year, "2013");
		rm.addReference(r2);
		
		Reference r3 = new Reference(Reference.Type.Inproceedings);
		r3.setCitationKey("TEST3");
		r3.setField(Reference.FieldType.Author, "Tiina Testaaja");
		r3.setField(Reference.FieldType.Title, "Testaa, testaa, hyvä tulee.");
		r3.setField(Reference.FieldType.Booktitle, "Your easy guide to unit testing");
		r3.setField(Reference.FieldType.Year, "2013");
		rm.addReference(r3);
		
		assertTrue(rm.containsCitationKey("TEST2"));	
	}
	
	public void testContainsCitationKeyWhenCKDoesntExist()
	{
		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		rm.addReference(r1);
	
		assertFalse(rm.containsCitationKey("TEST2"));
	}
	
	public void testConvertAccented(){
		assertEquals("Äku Änkkä",        rm.convertAccented("\\\"{A}ku \\\"{A}nkk\\\"{a}"));
		assertEquals("Ääkkösten alkeet", rm.convertAccented("\\\"{A}\\\"{a}kk\\\"{o}sten alkeet"));
		assertEquals("Öökkösten alkeet", rm.convertAccented("\\\"{O}\\\"{o}kk\\\"{o}sten alkeet"));
		assertEquals("Örån Årmät",       rm.convertAccented("\\\"{O}r\\r{a}n \\r{A}rm\\\"{a}t"));
	}
	
	public void testClearReferenceList(){
		List<Reference> rl;
		
		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		rm.addReference(r1);
		
		Reference r2 = new Reference(Reference.Type.Inproceedings);
		r2.setCitationKey("TEST2");
		r2.setField(Reference.FieldType.Author, "Timo Testaaja");
		r2.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys, part II - Paluu");
		r2.setField(Reference.FieldType.Booktitle, "What made me the tester I am today.");
		r2.setField(Reference.FieldType.Year, "2013");
		rm.addReference(r2);
		
		rm.clearReferenceList();
		assertFalse(rm.containsCitationKey("TEST1"));
		assertFalse(rm.containsCitationKey("TEST2"));
		rl = rm.getReferences();
		assertEquals(0, rl.size());
	}
	
	public void testDeleteReference(){
		List<Reference> rl;
		
		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		rm.addReference(r1);
		
		Reference r2 = new Reference(Reference.Type.Inproceedings);
		r2.setCitationKey("TEST2");
		r2.setField(Reference.FieldType.Author, "Timo Testaaja");
		r2.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys, part II - Paluu");
		r2.setField(Reference.FieldType.Booktitle, "What made me the tester I am today.");
		r2.setField(Reference.FieldType.Year, "2013");
		rm.addReference(r2);	
		
		rm.deleteReference(r2);
		assertFalse(rm.containsCitationKey("TEST2"));
		rl = rm.getReferences();
		assertEquals(1, rl.size());
	}
	
}
