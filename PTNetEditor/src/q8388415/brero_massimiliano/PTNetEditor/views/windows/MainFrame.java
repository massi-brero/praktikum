package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIModeListener;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNNetSavedChecker;
import q8388415.brero_massimiliano.PTNetEditor.views.PTNMenu;
import q8388415.brero_massimiliano.PTNetEditor.views.PTNSimAlertLabel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

/**
 * Main window for desktop, menu bar and controll panel.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class MainFrame extends JFrame implements PTNIModeListener {

	private static final long serialVersionUID = 1L;
	/**
	 * Components displayed
	 */
	private PTNDesktop desktop;
	private PTNControlPanel controlPanel;
	private JScrollPane scPane = null;
	private PTNMenu menu;
	/**
	 * Shown only when we're in sim mode.
	 */
	private PTNSimAlertLabel simLabel;
	
	private final String title = "8388415 - Massimiliano Brero";

	/**
	 * The parameters are given by the Bootstrapping process.
	 * That will also initialize those objects.
	 * 
	 * @param desktop {@link PTNDesktop}
	 * @param controlPanel {@link PTNControlPanel}
	 * @param menu {@link PTNMenu}
	 */
	public MainFrame(PTNDesktop desktop, PTNControlPanel controlPanel, PTNMenu menu) {
		
		this.menu = menu;
		this.controlPanel = controlPanel;
		this.setUpSimPanel();
		this.setTitle(title);
		this.desktop = desktop;
		this.setupScrollPane();
		this.getContentPane().add(menu, BorderLayout.PAGE_START);
		this.getContentPane().add(scPane, BorderLayout.CENTER);
		this.getContentPane().add(controlPanel, BorderLayout.PAGE_END);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
		
			@Override
			public void windowClosing(WindowEvent e) {
				if (PTNNetSavedChecker.netHasBeenSaved())
					System.exit(0);
				
			}
			
		});
		
	}

	/**
	 * Initializes simulation Text panel.
	 */
	private void setUpSimPanel() {
		simLabel = new PTNSimAlertLabel();
		String simText = "<html><font color='white'>"+ simLabel.getText() + "</font></html>";
		simLabel.setText(simText);
		simLabel.setBackground(Color.RED);

	}

	/**
	 * Sets up scroll pane an add desktop to that.
	 */
	private void setupScrollPane() {
		scPane = new JScrollPane();
		JViewport viewport = new JViewport();
        viewport.setView(desktop);
		scPane.setViewportView(viewport);
	}

	/**
	 * Getter.
	 * 
	 * @return {@link PTNDesktop}
	 */
	public PTNDesktop getPTNDesktop() {
		return this.desktop;
	}

	/**
	 * Switches the control Panel off and leaves just the modus
	 * drop down in the menu. This way no manipulation of the net 
	 * is possible during the simulation mode.
	 * 
	 */
	@Override
	public void startSimulationMode() {
		
		controlPanel.setVisible(false);
		menu.setBackground(Color.RED);
		menu.add(simLabel);		
		menu.switchOffNodeMenu();
		menu.switchOffFileMenu();

	}

	/**
	 * Resets menu and control panel to editor functions.
	 */
	@Override
	public void startEditorMode() {

		controlPanel.setVisible(true);		
		menu.setBackground(null);
		menu.remove(simLabel);
		menu.switchOnNodeMenu();
		menu.switchOnFileMenu();
		
	}	

}
