package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PTNSimAlertPanel extends JPanel {
	
	private JLabel simLabel;
	private String simLabelText = "Simulationsmodus";
	final Dimension DEFAULT_SIZE = new Dimension(30, 10);
	

	public PTNSimAlertPanel() {
		simLabel = new JLabel(simLabelText);
		this.init();
	}
	
	private void init() {
		this.add(simLabel);
		this.setPreferredSize(DEFAULT_SIZE);
	}
	
}
