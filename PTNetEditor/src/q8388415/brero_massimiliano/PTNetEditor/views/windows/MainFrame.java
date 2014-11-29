package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIModeListener;
import q8388415.brero_massimiliano.PTNetEditor.views.PTNMenu;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class MainFrame extends JFrame implements PTNIModeListener {

	private PTNDesktop desktop;
	private PTNControlPanel controlPanel;
	private JScrollPane scPane = null;
	private PTNMenu menu;

	public MainFrame(PTNDesktop desktop, PTNControlPanel controlPanel, PTNMenu menu) {
		
		this.menu = menu;
		this.controlPanel = controlPanel;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.desktop = desktop;
		this.setupScrollPane();
		this.getContentPane().add(menu, BorderLayout.PAGE_START);
		this.getContentPane().add(scPane, BorderLayout.CENTER);
		this.getContentPane().add(controlPanel, BorderLayout.PAGE_END);
		
		this.pack();
		this.setVisible(true);
		
	}


	private void setupScrollPane() {
		scPane = new JScrollPane();
		JViewport viewport = new JViewport();
        viewport.setView(desktop);
		scPane.setViewportView(viewport);
	}

	public PTNDesktop getPTNDesktop() {
		return this.desktop;
	}

	/**
	 * 
	 */
	@Override
	public void startSimulationMode() {
		controlPanel.setVisible(false);
		menu.switchOffStandardMenu();
		menu.switchOffFileMenu();
	}

	/**
	 * 
	 */
	@Override
	public void startEditorMode() {
		System.out.println("edit");
		controlPanel.setVisible(true);
		menu.switchOnStandardMenu();
		menu.switchOnFileMenu();
	}


}
