/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fubar.gui;

import fubar.fubibtex.references.Reference;
import fubar.fubibtex.references.Reference.FieldType;
import fubar.fubibtex.ui_adapter.GUIReferenceManagerF;
import fubar.fubibtex.ui_adapter.IGUIReferenceManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
    GUIReferenceManagerF manager = new GUIReferenceManagerF();
    private Robot robot;

    @Override
    protected void setUp() throws Exception {
        try {
            // Set the Look and Feel of the application to the operating
            // system's look and feel.
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (InstantiationException e) {
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println(e);
        }
        File file = new File("src/test/resources/test.data");
        manager.setDatastore(file);
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

        // EI PYSTY KUNNOLLA TESTAAMAAN POLUN TAKIA, FILECHOOSER AVAA
        // AINA NÄKYMÄKSI KOTIHAKEMISTON JOTEN POLKU TIEDOSTOON VAIHETELEE.

//        testFrame.button("importBibtext").click();
//        fileChooser.requireVisible();
//        fileChooser.approve();
//        fileChooser.requireVisible();
//        fileChooser.selectFile(new File("src\test\resources\test.bib"));
//        fileChooser.approve();
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
            if (i < fieldTypes.size() / 2) {
                testFrame.textBox(fieldTypes.get(i).name() + "TextField").setText(fieldTypes.get(i).name());
            }
        }

        testFrame.label("citationKeyErrorLabel").requireNotVisible();
        testFrame.textBox("citationKeyField").requireText("");
        testFrame.textBox("citationKeyField").enterText("L");
        testFrame.textBox("citationKeyField").enterText("o");
        testFrame.textBox("citationKeyField").enterText("L");
        testFrame.textBox("citationKeyField").enterText("3");
        testFrame.textBox("citationKeyField").enterText("0");
        testFrame.textBox("citationKeyField").enterText("1");
        testFrame.textBox("citationKeyField").enterText("3");
        testFrame.label("citationKeyErrorLabel").requireVisible();
        testFrame.button("addButton").click();
        JOptionPaneFinder.findOptionPane().using(robot).okButton().click();
        testFrame.textBox("citationKeyField").deleteText();
        testFrame.button("citationBuilderButton").click();
        testFrame.textBox("citationKeyField").requireText("Authorar");
        testFrame.textBox("citationKeyField").deleteText();
        testFrame.label("citationKeyErrorLabel").requireNotVisible();
        testFrame.textBox("citationKeyField").enterText("t");
        testFrame.textBox("citationKeyField").enterText("e");
        testFrame.textBox("citationKeyField").enterText("s");
        testFrame.textBox("citationKeyField").enterText("t");

        testFrame.button("addButton").click();
        testFrame.panel("addReferenceView").requireNotVisible();
        listViewBaseState();

        testFrame.list("referenceList").item("[test] | Author | Title | Booktitle | Year");
        testFrame.button("save").requireEnabled();
//        testFrame.button("save").click();
//        testFrame.button("save").requireDisabled();
    }

    public void testExportList() {
        testFrame.list("exportList").requireItemCount(0);
        testFrame.list("referenceList").selectItem("[LoL3013] | Jarno Lonardi | Koodia koodia koodia... | Koodikirja | 3013");
        testFrame.button("addToExportList").click();
        testFrame.list("exportList").requireItemCount(1);
        testFrame.list("exportList").selectItem("[LoL3013] | Jarno Lonardi | Koodia koodia koodia... | Koodikirja | 3013");
        testFrame.button("removeFromExportList").click();
        testFrame.list("exportList").requireItemCount(0);
    }

    public void testModify() {
        testFrame.button("editReferenceButton").requireDisabled();
        testFrame.list("referenceList").selectItem("[LoL3013] | Jarno Lonardi | Koodia koodia koodia... | Koodikirja | 3013");
        testFrame.button("editReferenceButton").requireEnabled();
        testFrame.button("editReferenceButton").click();
        testFrame.panel("modifyReferenceView").requireVisible();
        testFrame.textBox(Reference.FieldType.Year.name() + "TextFieldModify").deleteText();
        testFrame.textBox(Reference.FieldType.Year.name() + "TextFieldModify").enterText("2012");
        testFrame.button("citationBuilderButtonModify").click();
        testFrame.button("modifyButton").click();
        testFrame.panel("modifyReferenceView").requireNotVisible();
        testFrame.list("referenceList").clickItem("[Jarno Lonardi12] | Jarno Lonardi | Koodia koodia koodia... | Koodikirja | 2012");
        testFrame.list("referenceList").requireSelection("[Jarno Lonardi12] | Jarno Lonardi | Koodia koodia koodia... | Koodikirja | 2012");
    }

    public void testSaveButton() {
        testFrame.list("referenceList").selectItem("[LoL3013] | Jarno Lonardi | Koodia koodia koodia... | Koodikirja | 3013");
        testFrame.button("editReferenceButton").click();
        testFrame.button("modifyButton").click();
        testFrame.button("save").requireEnabled();
        testFrame.button("save").click();
        testFrame.button("save").requireDisabled();
    }
    
    public void testExportByTag() {
        testFrame.button("exportByTag").click();
        JOptionPaneFinder.findOptionPane().using(robot).textBox("OptionPane.textField").setText("test");
        JOptionPaneFinder.findOptionPane().using(robot).okButton().click();
    }
    
    public void testReferenceDelete() {
        testFrame.button("deleteReferenceButton").requireDisabled();
        testFrame.list("referenceList").selectItem("[LoL3013] | Jarno Lonardi | Koodia koodia koodia... | Koodikirja | 3013");
        testFrame.button("deleteReferenceButton").requireEnabled();
        testFrame.button("editReferenceButton").click();
        testFrame.button("returnButtonModify").click();
        testFrame.button("deleteReferenceButton").requireDisabled();
        testFrame.list("referenceList").selectItem("[LoL3013] | Jarno Lonardi | Koodia koodia koodia... | Koodikirja | 3013");
        testFrame.button("deleteReferenceButton").requireEnabled();
        testFrame.button("deleteReferenceButton").click();
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
}