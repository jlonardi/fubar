/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.fubibtex.ui_adapter;

import fubar.fubibtex.references.Reference;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;

/**
 *
 * @author aaltotuo
 */
public class GUIReferenceManagerFTest extends TestCase {
	
	public GUIReferenceManagerFTest(String testName) {
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
	// TODO add test methods here. The name must begin with 'test'. For example:
	// public void testHello() {}
	
	public void testDummy()
	{
		assertTrue(true);
	}
	
	public void testGetNullDatastore()
	{
		GUIReferenceManagerF grm = new GUIReferenceManagerF();
		assertNull(grm.getDatastore());		
	}

	public void testSetDatastore()
	{
		GUIReferenceManagerF grm = new GUIReferenceManagerF();
		File input = new File("test");
		File output;
		grm.setDatastore(input);
		
		output = grm.getDatastore();
		
		assertEquals(input, output);	
	}

	public void testReferenceManagersNotNull()
	{
		GUIReferenceManagerF grm = new GUIReferenceManagerF();
		assertNotNull(grm.dataStoreManager);
		assertNotNull(grm.exportManager);
	}
	
	
	public void testAddReferenceToDatastore()
	{
		GUIReferenceManagerF grm = new GUIReferenceManagerF();
		Reference r = new Reference(Reference.Type.Inproceedings);
		r.setCitationKey("TEST1");
		r.setField(Reference.FieldType.Author, "Timo Testaaja");
		r.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r.setField(Reference.FieldType.Year, "2013");
		
		grm.setDatastore(new File("test.bib"));
		assertTrue(grm.addReferenceToDatastore(r));
		
	}
	
	public void testGetReferencesFromDatastore(){
			GUIReferenceManagerF grm = new GUIReferenceManagerF();
		grm.setDatastore(new File("test.bib"));
		
		List<Reference> rl;
		
		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		grm.addReferenceToDatastore(r1);
		
		Reference r2 = new Reference(Reference.Type.Inproceedings);
		r2.setCitationKey("TEST2");
		r2.setField(Reference.FieldType.Author, "Timo Testaaja");
		r2.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys, part II - Paluu");
		r2.setField(Reference.FieldType.Booktitle, "What made me the tester I am today.");
		r2.setField(Reference.FieldType.Year, "2013");
		grm.addReferenceToDatastore(r2);
		
		Reference r3 = new Reference(Reference.Type.Inproceedings);
		r3.setCitationKey("TEST3");
		r3.setField(Reference.FieldType.Author, "Tiina Testaaja");
		r3.setField(Reference.FieldType.Title, "Testaa, testaa, hyvä tulee.");
		r3.setField(Reference.FieldType.Booktitle, "Your easy guide to unit testing");
		r3.setField(Reference.FieldType.Year, "2013");
		grm.addReferenceToDatastore(r3);

		
		rl = grm.getReferencesFromDatastore();
		assertEquals(rl.size(), 3);
	}
	
	public void testGetReferencesByFilterFromDatastore(){
		GUIReferenceManagerF grm = new GUIReferenceManagerF();
		grm.setDatastore(new File("test.bib"));
		
		List<Reference> rl;
		
		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		grm.addReferenceToDatastore(r1);
		
		Reference r2 = new Reference(Reference.Type.Inproceedings);
		r2.setCitationKey("TEST2");
		r2.setField(Reference.FieldType.Author, "Timo Testaaja");
		r2.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys, part II - Paluu");
		r2.setField(Reference.FieldType.Booktitle, "What made me the tester I am today.");
		r2.setField(Reference.FieldType.Year, "2013");
		grm.addReferenceToDatastore(r2);
		
		Reference r3 = new Reference(Reference.Type.Inproceedings);
		r3.setCitationKey("TEST3");
		r3.setField(Reference.FieldType.Author, "Tiina Testaaja");
		r3.setField(Reference.FieldType.Title, "Testaa, testaa, hyvä tulee.");
		r3.setField(Reference.FieldType.Booktitle, "Your easy guide to unit testing");
		r3.setField(Reference.FieldType.Year, "2013");
		grm.addReferenceToDatastore(r3);

		
		rl = grm.getReferencesByFilterFromDatastore(Reference.FieldType.Year, "2010");
		assertEquals(rl.size(), 1);
		assertEquals(rl.get(0).getField(Reference.FieldType.Year), "2010");
		
		rl = grm.getReferencesByFilterFromDatastore(Reference.FieldType.Author, "Timo Testaaja");
		assertEquals(rl.size(), 2);
	
	}
	
	public void testSaveToDatastore(){
		GUIReferenceManagerF grm = new GUIReferenceManagerF();
		grm.setDatastore(new File("testSaveToDatastore.bib"));
		
		List<Reference> rl;
		
		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		grm.addReferenceToDatastore(r1);
		
		assertTrue(grm.saveToDatastore());
	}

	public void testLoadFromDatastore(){
		GUIReferenceManagerF grm = new GUIReferenceManagerF();
		grm.setDatastore(new File("testSaveToDatastore.bib"));
		
		List<Reference> rl;
		
		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		grm.addReferenceToDatastore(r1);
		
		grm.saveToDatastore();
		
		assertTrue(grm.loadFromDatastore());
	}
	
	public void testAddToExportList(){
		GUIReferenceManagerF grm = new GUIReferenceManagerF();

		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		
		assertTrue(grm.addToExportList(r1));	
	}
	
	public void testExportToFile(){
		GUIReferenceManagerF grm = new GUIReferenceManagerF();

		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		
		grm.addToExportList(r1);
	
		assertTrue(grm.exportToFile(new File("testExportToFile.bib")));
	}
	
	public void testClearExportList(){
		GUIReferenceManagerF grm = new GUIReferenceManagerF();
		try{
			grm.clearExportList();
		}
		catch (Exception e){
			assertEquals("Not supported yet.", e.getMessage());
		}
	}
	
	public void testGetExportList(){
		GUIReferenceManagerF grm = new GUIReferenceManagerF();
		List<Reference> rl;

		Reference r1 = new Reference(Reference.Type.Inproceedings);
		r1.setCitationKey("TEST1");
		r1.setField(Reference.FieldType.Author, "Timo Testaaja");
		r1.setField(Reference.FieldType.Title, "Testaamisen sietämätön keveys");
		r1.setField(Reference.FieldType.Booktitle, "How I stopped worrying and learned to love unit testing.");
		r1.setField(Reference.FieldType.Year, "2010");
		
		grm.addToExportList(r1);
		
		rl = grm.getExportList();
		assertEquals(1, rl.size());
	}
}
