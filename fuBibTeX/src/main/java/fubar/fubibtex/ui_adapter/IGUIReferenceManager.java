/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.fubibtex.ui_adapter;

import fubar.fubibtex.references.Reference;
import java.io.File;
import java.util.List;

/**
 *
 * @author aaltotuo
 */
public interface IGUIReferenceManager {
		
	/**
	 * Tries to add a refence to the datamodel linked with the datastore.
	 * If the reference does not have all the required fields, the addition will fail.
	 * @param ref
	 * @return Boolean determining if the addition succeeded.
	 */
	boolean addReferenceToDatastore(Reference ref);
	
	/**
	 * Searches for references with filter keyword in a specific field from the datamodel linked with the datastore.
	 * @param type The field used for filtering.
	 * @param filter The string to filter with.
	 * @return List of references found by filtering.
	 */
	List<Reference> getReferencesByFilterFromDatastore(Reference.FieldType type, String filter);
        
        /**
	 * Searches for references with filter keyword in a specific field from the datamodel linked with the datastore and.
         * Splits the filter parameter with the splitBy parameter.
	 * @param type The field used for filtering.
	 * @param filter The string to filter with.
         * @param splitBy If
	 * @return List of references found by filtering.
	 */
	List<Reference> getReferencesByFilterFromDatastore(Reference.FieldType type, String filter, String splitBy);
	
	/**
	 * Returns all references contained in the data model
	 * @return List of references.
	 */
	List<Reference> getReferencesFromDatastore();
	
	/**
	 * Loads references from datastore to internal data model. 
	 * @return Boolean determining if the import succeeded.
	 */
	public boolean loadFromDatastore();

	/**
	 * Saves references from data model to datastore. 
	 * @return Boolean determining if the import succeeded.
	 */	
	public boolean saveToDatastore();
	
	/**
	 * Tries to add a refence to the list of references to be exported to a file.
	 * If the reference does not have all the required fields, the addition will fail.
	 * @param ref
	 * @return Boolean determining if the addition succeeded.
	 */	
	public boolean addToExportList(Reference ref);
	
	/**
	 * Exports references in exportlist to a file. 
	 * @return Boolean determining if the export succeeded.
	 */		
	public boolean exportToFile(File file);
        
        /**
	 * Imports all references contained in the file in the datastore. 
	 * @return Boolean determining if the import succeeded.
	 */		
	public boolean importFromFile(File file);

	/**
	 * Copies the given list of References to the export list.
	 * @param referenceList List of References to be copied to the export list
	 * @return Boolean determining if the export succeeded.
	 */		
	public boolean copyToExportList(List<Reference> referenceList);
	
	/**
	 * Empties the exportlist.
	 * @return Boolean determining if the list was cleared.
	 */
	public boolean clearExportList();
    
	/**
	 * Returns the list of References selected for export
	 */
	public List<Reference> getExportList();

	/**
	 * Set the datastore-file used by the manager
	 * @param File 
	 */
	public void setDatastore(File file);
	
	/**
	 * Checks if a citation key is already in a reference saved into the datastore
	 * @param citationKey existance of which needs to be checked 
	 * @return Boolean true if Reference with given key exists, otherwise false
	 */
	public boolean dataStoreContainsCitationKey(String citationKey);
	
	/**
	 * 
	 */
	
	/**
	 * Tries to delete a refence from the datamodel linked with the datastore.
	 * @param ref
	 * @return Boolean determining if the deletion succeeded.
	 */
	boolean deleteReferenceFromDatastore(Reference ref);
	
	
}
