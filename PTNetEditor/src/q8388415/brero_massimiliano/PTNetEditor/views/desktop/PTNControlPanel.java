package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.controllers.AppController;

public class PTNControlPanel extends JPanel {
	
	public PTNControlPanel(AppController appControl) {
		
		JButton moveSelection = new JButton("move");
		JButton delSelection = new JButton("erase");
		moveSelection.setPreferredSize(new Dimension(80,50));
		delSelection.setPreferredSize(new Dimension(80,50));
		moveSelection.addActionListener(appControl);
		delSelection.addActionListener(appControl);
		
		this.add(moveSelection);
		this.add(delSelection);
		setDoubleBuffered(true);
		
	}

}
