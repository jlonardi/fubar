package fubar.fubibtex.references;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
			referenceScanner.useDelimiter("@");

			while (referenceScanner.hasNext()) {
				String referenceString = referenceScanner.next().replaceAll("  ", " ").trim();
				referenceString = cleanStringTerminators(referenceString);

				//Before using the next scanner, let's get reference type and citation key and start creating our Reference object...
				String referenceType = buildReferenceType(referenceString);

				Reference r = new Reference(Reference.Type.valueOf(referenceType.toString().toLowerCase().trim()));

				referenceString = referenceString.substring(referenceType.length() + 1); //Magic +1 to remove unnecessary bracket,parenthesis or comma
				
				String referenceCitation = buildCitation(referenceString);

				r.setCitationKey(referenceCitation.toString());

				referenceString = referenceString.substring(referenceCitation.length() + 1 + separatorLen); //Magic +1 to remove comma, separatorLen for line separator

				fieldScanner = new Scanner(referenceString);
				fieldScanner.useDelimiter(separator); //I really, really fucking hate special cases...

				while (fieldScanner.hasNext()) {
					String field = fieldScanner.next();
					String key = buildKey(field);

					field = cleanField(field);
					field = cleanStringTerminators(field);

					r.setField(Reference.FieldType.valueOf(key.toString().trim().toLowerCase()), field);
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

			for (int i = 0; i < referenceList.size(); i++) {
				Reference r = referenceList.get(i);
				EnumMap<Reference.FieldType, String> fields = r.getFields();
				
				pw.print("@");
				pw.print(r.getType());
				pw.print("{");
				pw.print(r.getCitationKey());
				pw.println(",");
				
				for (Map.Entry<Reference.FieldType, String> entry : fields.entrySet()) {
					pw.print(entry.getKey().toString());
					pw.print(" = ");
					pw.println("{"+ entry.getValue().toString() +"},");					
				}
				pw.println("}");
				pw.println();
			}
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
	private String cleanStringTerminators(String string) {
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
	private String buildKey(String field) {
		StringBuilder key = new StringBuilder();

		for (int i = 0; i < field.length(); i++) {
			if (field.charAt(i) == '=') {
				break;
			}
			key.append(field.charAt(i));
		}
		return key.toString();
	}

	/**
	 * Builds a reference type string from the given input string.
	 * @param String Input data from the Scanner
	 * @return reference type name as a string
	 */	
	private String buildReferenceType(String referenceString) {
		StringBuilder referenceType = new StringBuilder();
		
		for (int i = 0; i < referenceString.length(); i++) {
			if (referenceString.charAt(i) == '{' || referenceString.charAt(i) == '(') {
				break;
			}
			referenceType.append(referenceString.charAt(i));
		}
		return referenceType.toString();
	}

	/**
	 * Builds a reference citation string from the given input string.
	 * @param String Input data from the Scanner
	 * @return reference citation name as a string
	 */	
	private String buildCitation(String referenceString) {
		StringBuilder citationKey = new StringBuilder();
		for (int i = 0; i < referenceString.length(); i++) {
			if (referenceString.charAt(i) == ',') {
				break;
			}
			citationKey.append(referenceString.charAt(i));
		}
		return citationKey.toString();

	}

	/**
	 * Cleans a string designated to be a reference field from unnecessary crap
	 * @param String string with crap
	 * @return string without crap
	 */
	private String cleanField(String field) {
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
}
