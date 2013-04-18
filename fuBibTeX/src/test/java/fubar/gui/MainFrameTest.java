/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.gui;

import fubar.fubibtex.references.Reference;
import fubar.fubibtex.references.Reference.FieldType;
import fubar.fubibtex.ui_adapter.IGUIReferenceManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import junit.framework.TestCase;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.ComponentLookupScope;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JFileChooserFinder;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JFileChooserFixture;

public class MainFrameTest extends TestCase {

    private FrameFixture testFrame;
    private MainFrame frame;
    IGUIReferenceManager manager = createManagerStub();
    private Robot robot;
    private String ref1, ref2;

    @Override
    protected void setUp() throws Exception {
        Reference ref = new Reference(Reference.Type.Inproceedings);
        ref.setField(Reference.FieldType.Title, "Systeemihommia");
        ref.setField(Reference.FieldType.Author, "Petteri Linnakangas");
        ref.setCitationKey("Petteri2012");
        ref1 = ref.toString();
        manager.addReferenceToDatastore(ref);
        ref = new Reference(Reference.Type.Inproceedings);
        ref.setField(Reference.FieldType.Title, "Koodia koodia koodia...");
        ref.setField(Reference.FieldType.Author, "Jarno Lonardi");
        ref.setCitationKey("LoL3013");
        ref2 = ref.toString();
        manager.addReferenceToDatastore(ref);
        super.setUp();
        robot = BasicRobot.robotWithNewAwtHierarchy();
        robot.settings().componentLookupScope(ComponentLookupScope.ALL);
        frame = GuiActionRunner.execute(new GuiQuery<MainFrame>() {
            @Override
            protected MainFrame executeInEDT() {
                return new MainFrame(manager);
            }
        });

        testFrame = new FrameFixture(robot, (JFrame) frame);
        testFrame.show();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        testFrame.cleanUp();
    }

    /**
     * Tests the initial view when the application is opened.
     */
    public void testInitialVisibility() {
        System.out.println("Testing the initial looks of the frame.");
        unchangedDataButtonTray();
        listViewBaseState();
        testFrame.panel("addReferenceView").requireNotVisible();
        frame.setVisible(false);
        frame.setVisible(true);
    }

    public void testImport() {
        System.out.println("Testing the BibTeX exporting.");

        JFileChooserFixture fileChooser;

        testFrame.button("importBibtext").click();

        fileChooser = JFileChooserFinder.findFileChooser().using(robot);
        fileChooser.requireVisible();
        fileChooser.cancel();

        testFrame.button("importBibtext").click();
        fileChooser.requireVisible();
        fileChooser.approve();
        fileChooser.requireVisible();
        fileChooser.selectFile(new File("test"));
        fileChooser.approve();
    }

    public void testExport() {
        System.out.println("Testing the BibTeX importing.");

        JFileChooserFixture fileChooser;

        testFrame.button("exportBibtext").click();

        fileChooser = JFileChooserFinder.findFileChooser().using(robot);
        fileChooser.requireVisible();
        fileChooser.cancel();

        testFrame.button("exportBibtext").click();
        fileChooser.requireVisible();
        fileChooser.approve();
        fileChooser.requireVisible();
        fileChooser.selectFile(new File("test"));
        fileChooser.approve();
    }

    public void testAddingReference() {
        System.out.println("Testing to add a reference and save it");
        testFrame.button("addReferenceButton").click();
        testFrame.panel("listView").requireNotVisible();
        unchangedDataButtonTray();
        addReferenceViewBaseState();

        testFrame.comboBox("typeList").click();
        testFrame.comboBox("typeList").selectItem(fubar.fubibtex.references.Reference.Type.Misc.name());
        testFrame.comboBox("typeList").selectItem(fubar.fubibtex.references.Reference.Type.Inproceedings.name());
        testFrame.textBox("citationKeyField").enterText("test");

        testFrame.button("returnButton").click();
        testFrame.panel("addReferenceView").requireNotVisible();
        listViewBaseState();

        testFrame.button("addReferenceButton").click();
        testFrame.panel("listView").requireNotVisible();
        addReferenceViewBaseState();

        testFrame.comboBox("typeList").requireSelection(fubar.fubibtex.references.Reference.Type.Inproceedings.name());
        testFrame.textBox("citationKeyField").requireText("");

        testFrame.textBox("citationKeyField").enterText("test");
        testFrame.button("addButton").click();
        JOptionPaneFinder.findOptionPane().using(robot).okButton().click();
        testFrame.panel("listView").requireNotVisible();
        addReferenceViewBaseState();
        List<FieldType> fieldTypes = fubar.fubibtex.references.ReferenceFields.getRequiredFields(Reference.Type.Inproceedings);
        for (FieldType type : fieldTypes) {
            testFrame.textBox(type.name() + "TextField").setText(type.name());
        }
        fieldTypes = fubar.fubibtex.references.ReferenceFields.getOptionalFields(Reference.Type.Inproceedings);
        for (int i = 0; i < fieldTypes.size(); i++) {
            if (i % 2 == 0) {
                testFrame.textBox(fieldTypes.get(i).name() + "TextField").setText(fieldTypes.get(i).name());
            }
        }

        testFrame.button("addButton").click();
        testFrame.panel("addReferenceView").requireNotVisible();
        listViewBaseState();

        testFrame.list("referenceList").item("test");
        testFrame.button("save").requireEnabled();
        testFrame.button("save").click();
        testFrame.button("save").requireDisabled();
    }

    public void testExportList() {
        testFrame.list("exportList").requireItemCount(0);
        testFrame.list("referenceList").selectItem(ref2);
        testFrame.button("addToExportList").click();
        testFrame.list("exportList").requireItemCount(1);
        testFrame.list("exportList").selectItem(ref2);
        testFrame.button("removeFromExportList").click();
        testFrame.list("exportList").requireItemCount(0);
    }

    private void listViewBaseState() {
        testFrame.panel("listView").requireVisible();
        testFrame.list("referenceList").requireVisible();
        testFrame.button("addReferenceButton").requireEnabled();
    }

    private void addReferenceViewBaseState() {
        testFrame.panel("addReferenceView").requireVisible();
        testFrame.panel("typeSelectionPanel").requireVisible();
        testFrame.comboBox("typeList").requireVisible();
        testFrame.panel("requiredPanel").requireVisible();
        testFrame.panel("controlPanel").requireVisible();
        testFrame.button("returnButton").requireEnabled();
        testFrame.button("returnButton").requireVisible();
        testFrame.button("addButton").requireEnabled();
        testFrame.button("addButton").requireVisible();
    }

    private void unchangedDataButtonTray() {
        testFrame.panel("buttonTray").requireVisible();
        testFrame.button("save").requireDisabled();
        testFrame.button("exportBibtext").requireEnabled();
        testFrame.button("importBibtext").requireEnabled();
    }

    private void changedDataButtonTray() {
        testFrame.panel("buttonTray").requireVisible();
        testFrame.button("save").requireEnabled();
        testFrame.button("exportBibtext").requireEnabled();
        testFrame.button("importBibtext").requireEnabled();
    }

    private IGUIReferenceManager createManagerStub() {
        return new IGUIReferenceManager() {
            ArrayList<Reference> list = new ArrayList();
            ArrayList<Reference> exportList = new ArrayList();
           
            @Override
            public boolean addReferenceToDatastore(Reference ref) {
                list.add(ref);
                return true;
            }

            @Override
            public List<Reference> getReferencesByFilterFromDatastore(Reference.FieldType type, String filter) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public List<Reference> getReferencesFromDatastore() {
                return list;
            }

            @Override
            public boolean loadFromDatastore() {
                return true;
            }

            @Override
            public boolean saveToDatastore() {
                return true;
            }

            @Override
            public boolean addToExportList(Reference ref) {
                exportList.add(ref);
                return true;
            }

            @Override
            public boolean exportToFile(File file) {
                return true;
            }

            @Override
            public boolean clearExportList() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setDatastore(File file) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public List<Reference> getExportList() {
                return exportList;
            }

            @Override
            public boolean dataStoreContainsCitationKey(String citationKey) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
}