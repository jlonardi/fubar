/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.fubibtex.references;

import java.util.List;

/**
 *
 * @author aaltotuo
 */
public interface IReferenceManager {
	/**
	 * Tries to add a reference. If the reference does not have all the required
	 * fields, the addition will fail.
	 * @param ref
	 * @return Boolean determining if the addition succeeded.
	 */
	boolean addReference(Reference ref);

        /**
	 * Searches for references with filter keyword in a specific field.
	 * @param type The field used for filtering.
	 * @param filter The string to filter with.
	 * @return List of references found by filtering.
	 */
	List<Reference> getReferencesByFilter(Reference.FieldType type, String filter);
        
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
	List<Reference> getReferencesByFilter(Reference.FieldType type, String filter, String splitBy);

	/**
	 * Returns all references contained in the manager
	 * @return List of references.
	 */
	List<Reference> getReferences();
	
	
	/**
	 * Exports references from data model to some datastore. Datastore type is implementation-specific
	 * @return Boolean determining if the export succeeded.
	 */
	boolean exportTo();

	/**
	 * Imports references from some datastore to internal data model. Datastore type is implementation-specific
	 * @return Boolean determining if the import succeeded.
	 */
	boolean importFrom();
	
	/**
	 * Checks if a citation key is already in a reference saved into the manager
	 * @param citationKey existence of which needs to be checked 
	 * @return Boolean true if Reference with given key exists, otherwise false
	 */
	boolean containsCitationKey(String citationKey);
	
	
	/**
	 * Clears the whole reference list saved into the manager.
	 * 
	 */
	boolean clearReferenceList();
}

