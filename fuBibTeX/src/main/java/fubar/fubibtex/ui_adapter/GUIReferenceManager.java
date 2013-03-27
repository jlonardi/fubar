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
public class GUIReferenceManager implements IGUIReferenceManager {
	ReferenceManagerF dataStoreManager;
	ReferenceManagerF exportManager;
	
	File datastore;
	
	
	@Override
	public void initGUIReferenceManager(File datastore) {
		this.datastore = datastore;
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
		return exportManager.exportTo();
	}	

	@Override
	public boolean clearExportList() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
