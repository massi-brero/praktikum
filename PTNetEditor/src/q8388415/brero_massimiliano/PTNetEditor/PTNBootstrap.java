package q8388415.brero_massimiliano.PTNetEditor;

import java.io.File;

import javax.swing.JOptionPane;
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
 * @author 8388415 - Massimiliano Brero
 *
 */
public class PTNBootstrap {

    private static PTNBootstrap bootstrap;
    private PTNParser parser;
    private File sourceFile;
    private static Boolean isRunning = false;

    public PTNBootstrap() {
    }

    public static void main(String[] args) {
    	
    	if (!isRunning) {    		
    		isRunning = true;
    		bootstrap = new PTNBootstrap();
    		bootstrap.init();
    	} else {
    		JOptionPane.showConfirmDialog(null, "Instanz bereits vorhanden", "Schreibfehler", JOptionPane.WARNING_MESSAGE);
    	}

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
        final PTNAppController appControl = new PTNAppController();
        final PTNDesktop desktop = new PTNDesktop(appControl, net);
        final PTNControlPanel controlPanel = PTNControlPanel.getInstance();
        controlPanel.initialize(desktop, appControl);
        final PTNMenu menu = new PTNMenu(desktop, appControl, net);

        this.setUpModeListeners();
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MainFrame mainframe = new MainFrame(desktop, controlPanel, menu);
            	appControl.addSimulationListener(mainframe);
            	appControl.addSimulationListener(desktop);
            }
        });

    }

	private void setUpModeListeners() {
		// TODO Auto-generated method stub
		
	}
    
}
