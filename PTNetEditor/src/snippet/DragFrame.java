package snippet;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

class DragFrame extends JFrame {

	private PTNDesktop desktop;
	private PTNControlPanel controlPanel;
	//TODO this should be given by bootstrap class
	PTNAppController appControl = new PTNAppController();

	public DragFrame() {
		
		controlPanel = new PTNControlPanel(appControl);
		desktop = new PTNDesktop(appControl);
		getContentPane().add(controlPanel, BorderLayout.SOUTH);
		getContentPane().add(desktop, BorderLayout.NORTH);

		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {

		JFrame frame = new DragFrame();

	}

	public PTNDesktop getPTNDesktop() {
		return this.desktop;
	}


}
