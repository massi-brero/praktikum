package q8388415.brero_massimiliano.PTNetEditor;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.views.PTNMenu;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.MainFrame;

/**
 * This class starts the application.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNBootstrap {

	/**
	 * Object needed for main class to start application non-statically.
	 */
    private static PTNBootstrap bootstrap;
    /**
     * Check if we already have an instance running.
     */
    private static Boolean isRunning = false;
    
    public PTNBootstrap() {
    }

    /**
     * Starts application.
     * @param args
     */
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
        this.initializeListeners(net,appControl);

        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MainFrame mainframe = new MainFrame(desktop, controlPanel, menu);
            	appControl.addSimulationListener(mainframe);
            }
        });

    }

    /**
     * Initial setup of controllers, views and models that have to 
     * listen to each other.
     * 
     * @param appControl 
     * @param net 
     */
	private void initializeListeners(PTNNet net, PTNAppController appControl) {

		if (net != null && appControl != null)
			net.addPropertyChangeListener(appControl);
		
	}
    
}
