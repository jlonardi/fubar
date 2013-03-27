/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.fubibtex.ui_adapter;

import fubar.fubibtex.references.Reference;
import fubar.fubibtex.references.ReferenceManagerF;
import java.io.File;
import java.util.List;

/**
 *
 * @author aaltotuo
 */
public class GUIReferenceManagerF implements IGUIReferenceManager {
	ReferenceManagerF dataStoreManager;
	ReferenceManagerF exportManager;

	File datastore;

	public File getDatastore() {
		return datastore;
	}

	public void setDatastore(File datastore) {
		this.datastore = datastore;
		dataStoreManager.setExportFile(datastore);
		dataStoreManager.setImportFile(datastore);
	}	
	
	@Override
	public boolean addReferenceToDatastore(Reference ref) {
		return dataStoreManager.addReference(ref);
	}

	@Override
	public List<Reference> getReferencesByFilterFromDatastore(Reference.FieldType type, String filter) {
		return dataStoreManager.getReferencesByFilter(type, filter);
	}

	@Override
	public List<Reference> getReferencesFromDatastore() {
		return dataStoreManager.getReferences();
	}	
	
	@Override
	public boolean loadFromDatastore() {
		return dataStoreManager.importFrom();
	}

	@Override
	public boolean saveToDatastore() {
		return dataStoreManager.exportTo();
	}


	@Override
	public boolean addToExportList(Reference ref) {
		return exportManager.addReference(ref);
	}

	@Override
	public boolean exportToFile(File file) {
		exportManager.setExportFile(file);
		return exportManager.exportTo();
	}	

	@Override
	public boolean clearExportList() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
}