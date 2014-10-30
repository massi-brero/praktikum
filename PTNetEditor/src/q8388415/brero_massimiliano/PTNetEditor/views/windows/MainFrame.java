package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.JFrame;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class MainFrame extends JFrame {

	private PTNDesktop desktop;
	private PTNControlPanel controlPanel;
	private ScrollPane scPane;

	public MainFrame(PTNDesktop desktop, PTNControlPanel controlPanel) {
		
		//super("PTN Editor");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.desktop = desktop;
		this.setupScrollPane();
		this.getContentPane().add(controlPanel, BorderLayout.PAGE_END);
		this.getContentPane().add(scPane, BorderLayout.CENTER);

		this.pack();
		this.setVisible(true);
		
	}


	
	private void setupScrollPane() {
		
		scPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
		scPane.setPreferredSize(new Dimension(500, 300));
		scPane.add(desktop);
		
	}

	public PTNDesktop getPTNDesktop() {
		return this.desktop;
	}


}
