package fubar.fubibtex;

import fubar.fubibtex.ui_adapter.GUIReferenceManagerF;
import fubar.gui.MainFrame;
import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author petteri
 */
public class MainApp {

    public static void main(String[] args) {
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
        GUIReferenceManagerF rm = new GUIReferenceManagerF();
        rm.setDatastore(new File("fubitex.data"));
        MainFrame frame = new MainFrame(rm);
    }
}
