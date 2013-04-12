package fubar.fubibtex.references;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fubar
 */
public class ReferenceManagerF implements IReferenceManager {

	private ArrayList<Reference> referenceList;
	//This referencemanager uses files as datastores.
	private File importFile;
	private File exportFile;
	private boolean isDebug = false;

	public ReferenceManagerF() {
		referenceList = new ArrayList<Reference>();
	}

	/**
	 * Tries to add a reference. If the reference does not have all the required
	 * fields, the addition will fail.
	 *
	 * @param ref
	 * @return Boolean determining if the addition succeeded.
	 */
	@Override
	public boolean addReference(Reference ref) {
		List<Reference.FieldType> reqFields = ReferenceFields.getRequiredFields(ref.getType());

		for (Reference.FieldType type : reqFields) {
			if (ref.getField(type) == null) {
				return false;
			}
		}

		referenceList.add(ref);
		
		return true;
	}

	public File getImportFile() {
		return importFile;
	}

	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}

	public File getExportFile() {
		return exportFile;
	}

	public void setExportFile(File exportFile) {
		this.exportFile = exportFile;
	}

	@Override
	public boolean importFrom() {
		String separator = System.getProperty("line.separator");
		int separatorLen = separator.length();
		
		Scanner referenceScanner;
		Scanner fieldScanner;
		try {
			referenceScanner = new Scanner(new BufferedReader(new FileReader(importFile)));
			referenceScanner.useDelimiter("@|"+ separator + "@");

			while (referenceScanner.hasNext()) {
				String referenceString = referenceScanner.next().replaceAll("  ", " ").trim();
				referenceString = cleanStringTerminators(referenceString);
				
				//Before using the next scanner, let's get reference type and citation key and start creating our Reference object...
				String referenceType = buildReferenceType(referenceString);

				Reference r = new Reference(Reference.Type.getTypeByString(referenceType));

				referenceString = referenceString.substring(referenceType.length());
				
				String referenceCitation = buildCitation(referenceString);

				r.setCitationKey(referenceCitation.toString());

				referenceString = referenceString.substring(referenceString.indexOf(separator));
				
				fieldScanner = new Scanner(referenceString);
				fieldScanner.useDelimiter(separator);

				while (fieldScanner.hasNext()) {
					String field = fieldScanner.next();
					String key = buildKey(field);

					field = cleanField(field);
					field = cleanStringTerminators(field);
					
					r.setField(Reference.FieldType.getFieldTypeByString(key), field);
				}
				fieldScanner.close();

				referenceList.add(r);
			}
			referenceScanner.close();
		} catch (Exception ex) {
			Logger.getLogger(ReferenceManagerF.class.getName()).log(Level.SEVERE, null, ex);

			referenceList.clear();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean exportTo() {
		try {
			
			PrintWriter pw = new PrintWriter(new FileWriter(exportFile));
			for(Reference r : referenceList) r.save(pw);
			pw.close();
			
		} catch (Exception ex) {
			Logger.getLogger(ReferenceManagerF.class.getName()).log(Level.SEVERE, null, ex);
			exportFile.delete();
			return false;
		}

		return true;
	}

	/**
	 * Searches for references with filter keyword in a specific field.
	 *
	 * @param type The field used for filtering.
	 * @param filter The string to filter with.
	 * @return List of references found by filtering.
	 */
	@Override
	public List<Reference> getReferencesByFilter(Reference.FieldType type, String filter) {
		ArrayList<Reference> refsFound = new ArrayList<Reference>();

		for (Reference ref : referenceList) {
			String val = ref.getField(type);
			if (val.contains(filter)) {
				refsFound.add(ref);
			}
		}

		return refsFound;
	}

	/**
	 * Returns all references contained in the manager
	 *
	 * @return List of references.
	 */
	@Override
	public List<Reference> getReferences() {
		return referenceList;
	}

	/////////////////////////////////////////////////
	//
	// Arr, internal helper-methods be here, matey...
	//
	/////////////////////////////////////////////////

	/**
	 * Removes BibTex-leftovers/line separator from string-endings left by the Scanner  
	 * @param String to be cleaned
	 * @ret Cleaned string.
	 */
	protected String cleanStringTerminators(String string) {
		String ret = string;
		if (string.endsWith("\",") || string.endsWith("},")) {
			ret = string.substring(0, string.length() - 2);
		} else if (string.endsWith(",") || string.endsWith(")") || string.endsWith("}")) {
			ret = string.substring(0, string.length() - 1);
		}

		if (string.endsWith(System.getProperty("line.separator"))) {
			ret = string.substring(0, string.length() - System.getProperty("line.separator").length());
		}

		return ret;
	}

	/**
	 * Builds a reference key string from the given input string.
	 * @param String Input data from the Scanner
	 * @return reference key name as a string
	 */
	protected String buildKey(String field) {
		StringBuilder key = new StringBuilder();
		String ret = "";
		
		for (int i = 0; i < field.length(); i++) {
			if (field.charAt(i) == '=') {
				ret = key.toString().toUpperCase().trim();
				break;
			}
			else {
				key.append(field.charAt(i));
			}
		}
		return ret;
	}

	/**
	 * Builds a reference type string from the given input string.
	 * @param String Input data from the Scanner
	 * @return reference type name as a string
	 */	
	protected String buildReferenceType(String referenceString) {
		StringBuilder referenceType = new StringBuilder();
		String ret = "";

		for (int i = 0; i < referenceString.length(); i++) {
			if (referenceString.charAt(i) == '{' || referenceString.charAt(i) == '(') {
				ret = referenceType.toString().toUpperCase().trim();
				break;
			}
			else 
			{
				referenceType.append(referenceString.charAt(i));
			}
		}
		return ret;
	}

	/**
	 * Builds a reference citation string from the given input string.
	 * @param String Input data from the Scanner
	 * @return reference citation name as a string
	 */	
	protected String buildCitation(String referenceString) {
		StringBuilder citationKey = new StringBuilder();
		String ret = "";
		
		for (int i = 0; i < referenceString.length(); i++) {
			if (referenceString.charAt(i) == ',') {
				ret = citationKey.toString().trim();
				break;
			}
			else if (referenceString.charAt(i) == '{' || referenceString.charAt(i) == '(' || referenceString.charAt(i) == ' ')
			{
				continue;
			}
			else
			{
				citationKey.append(referenceString.charAt(i));
			}
		}
		
		return ret;
	}

	/**
	 * Cleans a string designated to be a reference field from unnecessary crap
	 * @param String string with crap
	 * @return string without crap
	 */
	protected String cleanField(String field) {
		String ret = field;
		if (field.indexOf('{') > 0) {
			ret = field.substring(field.indexOf('{') + 1);
		} else if (field.indexOf('"') > 0) {
			ret = field.substring(field.indexOf('"') + 1);
		} else if (field.indexOf("=") > 0) {
			ret = field.substring(field.indexOf("=") + 1);
		}
		return ret;
	}

	@Override
	public boolean containsCitationKey(String citationKey) {
		for (Reference reference : referenceList) 
		{
			if (reference.getCitationKey().compareTo(citationKey)==0)
			{
				return true;
			}
		}
		return false;
	}
}
