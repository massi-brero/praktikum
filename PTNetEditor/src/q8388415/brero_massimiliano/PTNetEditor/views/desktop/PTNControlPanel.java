package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;

public class PTNControlPanel extends JPanel {
	
	public PTNControlPanel(PTNAppController appControl) {
		
		JButton unselect = new JButton("unselect");
		JButton delSelection = new JButton("erase");
		unselect.setPreferredSize(new Dimension(80,50));
		delSelection.setPreferredSize(new Dimension(80,50));
		unselect.addActionListener(appControl);
		delSelection.addActionListener(appControl);
		
		this.add(unselect);
		this.add(delSelection);
		setDoubleBuffered(true);
		
	}

}
