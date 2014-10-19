package snippet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.JFrame;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

class DragFrame extends JFrame {

	private PTNDesktop desktop;
	private PTNControlPanel controlPanel;
	private ScrollPane scPane;
	//TODO this should be given by bootstrap class
	PTNAppController appControl = new PTNAppController();

	public DragFrame() {
		
		super("PTN Editor");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setupScrollPane();
		controlPanel = new PTNControlPanel(appControl);
		this.getContentPane().add(controlPanel, BorderLayout.PAGE_END);
		this.getContentPane().add(scPane, BorderLayout.CENTER);

		
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {

		JFrame frame = new DragFrame();

	}
	
	private void setupScrollPane() {
		
		scPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
		desktop = new PTNDesktop(appControl);
		scPane.setPreferredSize(new Dimension(500, 300));
		scPane.add(desktop);
		
	}

	public PTNDesktop getPTNDesktop() {
		return this.desktop;
	}


}
