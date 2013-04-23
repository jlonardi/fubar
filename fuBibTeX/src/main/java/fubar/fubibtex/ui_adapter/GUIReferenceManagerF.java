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

    ReferenceManagerF dataStoreManager = new ReferenceManagerF();
    ReferenceManagerF exportManager = new ReferenceManagerF();
    File datastore;

    public File getDatastore() {
        return datastore;
    }

    @Override
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
    public List<Reference> getReferencesByFilterFromDatastore(Reference.FieldType type, String filter, String splitBy) {
        return dataStoreManager.getReferencesByFilter(type, filter, splitBy);
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
		return exportManager.clearReferenceList();
    }

    @Override
    public List<Reference> getExportList() {
        return exportManager.getReferences();
    }

	@Override
	public boolean dataStoreContainsCitationKey(String citationKey) {
		return dataStoreManager.containsCitationKey(citationKey);
	}

	@Override
	public boolean copyToExportList(List<Reference> referenceList) {
		boolean retVal = false;
		for (Reference reference : referenceList) {
			retVal = exportManager.addReference(reference);
		}
		return retVal;
	}

    @Override
    public boolean importFromFile(File file) {
        this.dataStoreManager.setImportFile(file);
        if(!this.dataStoreManager.importFrom()) return false;
        this.dataStoreManager.setImportFile(this.datastore);
        return true;
    }

	@Override
	public boolean deleteReferenceFromDatastore(Reference ref) {
		return dataStoreManager.deleteReference(ref);
	}
	
	
	
}
