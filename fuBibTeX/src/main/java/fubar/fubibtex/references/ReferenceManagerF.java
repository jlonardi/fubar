package fubar.fubibtex.references;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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

        // SEPARATORIA EI KÄYTETÄ KOSKA SE ON JÄRJESTELMÄKOHTAINEN

        //String separator = System.getProperty("line.separator");
        //System.out.println("separator: " + separator);
        //int separatorLen = separator.length();

        Scanner referenceScanner;
        Scanner fieldScanner;
        try {

//----------------------- MUUTETTUA OSUUTTA ------------------------------------		

            // LISÄTTY LUKEMAAN UTF8
            referenceScanner = new Scanner(new BufferedReader(new InputStreamReader(
                    new FileInputStream(importFile), "UTF8")));
            // MUUTETTU REGEX JA OTETTU POIS SEPARATOR
            referenceScanner.useDelimiter("@");

//---------------------- MUUTETTU OSUUS LOPPU ----------------------------------				


            while (referenceScanner.hasNext()) {
                String referenceString = referenceScanner.next().replaceAll("  ", " ").trim();
                if (!referenceString.equals("")) {
                    referenceString = cleanStringTerminators(referenceString);
                    referenceString = convertAccented(referenceString);

                    //Before using the next scanner, let's get reference type and citation key and start creating our Reference object...
                    String referenceType = buildReferenceType(referenceString);

                    Reference r = new Reference(Reference.Type.getTypeByString(referenceType));

                    referenceString = referenceString.substring(referenceType.length());

                    String referenceCitation = buildCitation(referenceString);

                    r.setCitationKey(referenceCitation.toString());

//----------------------- MUUTETTUA OSUUTTA ------------------------------------	
                    fieldScanner = new Scanner(referenceString);

                    // EI TARVITA SEPARATORIA DELIMITERIKSI, KÄYTETÄÄN NEXTLINEÄ
                    //fieldScanner.useDelimiter(separator);

                    while (fieldScanner.hasNext()) {
                        // SCANNERI TUNNISTAA ERI JÄRJESTELMIEN VÄLISET RIVINVAIHDOT
                        String field = fieldScanner.nextLine();
                        /*
                         *  Jos ei sisällä = merkkiä niin kyseessä on rivi
                         *  jolla ei ole bibtextiä kiinnostavaa tietoa. Oletus
                         *  on että tiedot ovat tallennettu riveittäin.
                         */
                        if (field.contains("=")) {
                            String key = buildKey(field);
                            field = cleanField(field);
                            field = cleanStringTerminators(field);
                            r.setField(Reference.FieldType.getFieldTypeByString(key), field);
                        }
                    }
//---------------------- MUUTETTU OSUUS LOPPU ----------------------------------						
                    fieldScanner.close();
                    referenceList.add(r);
                }
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

            //PrintWriter pw = new PrintWriter(new FileWriter(exportFile));
            PrintWriter pw = new PrintWriter(exportFile, "UTF-8");

            for (Reference r : referenceList) {
                r.save(pw);
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
	 * Searches for references with filter keyword in a specific field. Multiple
         * filter words can be given by splitting them up with e.g. commas and
         * specifying the splitBy parameter.
	 * @param type The field used for filtering.
	 * @param filter The string to filter with.
         * @param splitBy If the splitBy is not null, filter will be split into
         * multiple filter words to search with.
	 * @return List of references found by filtering.
	 */
    @Override
    public List<Reference> getReferencesByFilter(Reference.FieldType type, String filter, String splitBy) {
        String[] filters = filter.split(splitBy);
        
        ArrayList<Reference> refsFound = new ArrayList<Reference>();
        
        for (Reference ref : referenceList) {
            for (String filt : filters) {
                String val = ref.getField(type).toLowerCase();
                if (val.contains(filt) && !refsFound.contains(ref)) {
                    refsFound.add(ref);
                }
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
     * Removes BibTex-leftovers/line separator from string-endings left by the
     * Scanner
     *
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


        // POISTETTU TERMINATORIN PUHIDSTAMINEN

        return ret;
    }

    /**
     * Builds a reference key string from the given input string.
     *
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
            } else {
                key.append(field.charAt(i));
            }
        }
        return ret;
    }

    /**
     * Builds a reference type string from the given input string.
     *
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
            } else {
                referenceType.append(referenceString.charAt(i));
            }
        }
        return ret;
    }

    /**
     * Builds a reference citation string from the given input string.
     *
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
            } else if (referenceString.charAt(i) == '{' || referenceString.charAt(i) == '(' || referenceString.charAt(i) == ' ') {
                continue;
            } else {
                citationKey.append(referenceString.charAt(i));
            }
        }



        return ret;
    }

    /**
     * Cleans a string designated to be a reference field from unnecessary crap
     *
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
        for (Reference reference : referenceList) {
            if (reference.getCitationKey().compareTo(citationKey) == 0) {
                return true;
            }
        }
        return false;
    }

    protected String convertAccented(String input) {

        if (input.contains("\\\"{A}")) {
            input = input.replace("\\\"{A}", "Ä");
        }

        if (input.contains("\\\"{a}")) {
            input = input.replace("\\\"{a}", "ä");
        }

        if (input.contains("\\r{A}")) {
            input = input.replace("\\r{A}", "Å");
        }

        if (input.contains("\\r{a}")) {
            input = input.replace("\\r{a}", "å");
        }

        if (input.contains("\\\"{O}")) {
            input = input.replace("\\\"{O}", "Ö");
        }

        if (input.contains("\\\"{o}")) {
            input = input.replace("\\\"{o}", "ö");
        }

        return input;
    }

	@Override
	public boolean clearReferenceList() {
		referenceList.clear();
		return referenceList.isEmpty();
	}

}
