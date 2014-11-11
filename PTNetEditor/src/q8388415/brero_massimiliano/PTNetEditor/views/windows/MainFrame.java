package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import q8388415.brero_massimiliano.PTNetEditor.views.PTNMenu;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class MainFrame extends JFrame {

	private PTNDesktop desktop;
	private PTNControlPanel controlPanel;
	private JScrollPane scPane;

	public MainFrame(PTNDesktop desktop, PTNControlPanel controlPanel, PTNMenu menu) {
		
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
		scPane.setPreferredSize(new Dimension(500, 300));
		JViewport viewport = new JViewport();
        viewport.setView(desktop);
		scPane.setViewportView(viewport);
		
	}

	public PTNDesktop getPTNDesktop() {
		return this.desktop;
	}


}
