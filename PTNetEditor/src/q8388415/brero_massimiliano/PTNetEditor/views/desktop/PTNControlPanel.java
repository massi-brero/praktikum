package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.peer.ButtonPeer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.views.partials.PTNEnlargementPanel;

public class PTNControlPanel extends JPanel {
	
	private JButton deselect;
	private JButton delSelection;
	private JPanel buttonPanel;
	private JPanel controllerPanel;
	
	public PTNControlPanel(PTNAppController appControl) {
		
		deselect = new JButton("unselect");
		delSelection = new JButton("erase");
		deselect.addActionListener(appControl);
		delSelection.addActionListener(appControl);

		this.init();
		
	}

	private void init() {
		
		buttonPanel = new JPanel();
		controllerPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,1));
		controllerPanel.setLayout(new GridLayout(0,1));
		
		// add enlargement Panels
		PTNEnlargementPanel placeSizePanel = new PTNEnlargementPanel("Place Size");
		PTNEnlargementPanel transitionSizePanel = new PTNEnlargementPanel("Transition Size");
		PTNEnlargementPanel arrowHeadSizePanel = new PTNEnlargementPanel("Arrowhead Size");
		
		controllerPanel.add(placeSizePanel);
		controllerPanel.add(transitionSizePanel);
		controllerPanel.add(arrowHeadSizePanel);
		
		// add basic buttons
		buttonPanel.add(deselect);
		buttonPanel.add(delSelection);
		//buttonPanel.add(new JButton("unused"));
		
		this.setSize(new Dimension(700, 30));
		this.setLayout(new GridLayout(0, 2));
		this.add(buttonPanel);
		this.add(controllerPanel);
		setDoubleBuffered(true);
	}

}
