package q8388415.brero_massimiliano.PTNetEditor;

import java.io.File;

import javax.swing.SwingUtilities;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNParser;
import q8388415.brero_massimiliano.PTNetEditor.views.PTNMenu;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.MainFrame;

/**
 * This class starts the application.
 * 
 * @author Laptop
 *
 */
public class PTNBootstrap {

    private static PTNBootstrap bootstrap;
    private PTNParser parser;
    private File sourceFile;

    public PTNBootstrap() {
    }

    public static void main(String[] args) {

        bootstrap = new PTNBootstrap();
        bootstrap.init();

    }

    /**
     * Sets up the application, instantiates all needed controllers and the desktop.
     */
    public void init() {

        sourceFile = new File("src\\snippet\\Kaffee.pnml");
        
        /**
         * Here the net is instantiates which basically all of the components will use.
         */
        PTNNet net = new PTNNet();
        /**
         * Initialize controllers for drawing and basic control operations.
         */
        PTNAppController appControl = new PTNAppController();
        final PTNDesktop desktop = new PTNDesktop(appControl, net);
        final PTNControlPanel controlPanel = PTNControlPanel.getInstance();
        controlPanel.initialize(desktop, appControl);
        final PTNMenu menu = new PTNMenu(desktop, appControl, net);

        this.setUpModeListeners();
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame(desktop, controlPanel, menu);
            }
        });

    }

	private void setUpModeListeners() {
		// TODO Auto-generated method stub
		
	}
    
}
