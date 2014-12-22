package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PTNSimAlertLabel extends JLabel {

	private static final long serialVersionUID = 4255335603816491859L;
	private String simLabelText = "Simulationsmodus";
	final Dimension DEFAULT_SIZE = new Dimension(simLabelText.length(), 8);
	private ImageIcon icon = new ImageIcon("icons/exclamation.png");
	

	public PTNSimAlertLabel() {
		this.init();
	}
	
	private void init() {
		this.setText(simLabelText);
		this.setPreferredSize(DEFAULT_SIZE);
		this.setIcon(icon);
	}
	
}
