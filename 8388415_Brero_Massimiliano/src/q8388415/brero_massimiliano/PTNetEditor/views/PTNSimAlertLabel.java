package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Label with text to be displayed in menu bar when app is in simulation mode.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNSimAlertLabel extends JLabel {

	private static final long serialVersionUID = 4255335603816491859L;
	private String simLabelText = "Simulationsmodus";
	final Dimension DEFAULT_SIZE = new Dimension(simLabelText.length(), 8);
	private ImageIcon icon = new ImageIcon("icons/exclamation.png");
	

	/**
	 * Standard Constructor.
	 */
	public PTNSimAlertLabel() {
		this.init();
	}
	
	/**
	 * Set basic params like text...
	 */
	private void init() {
		this.setText(simLabelText);
		this.setPreferredSize(DEFAULT_SIZE);
		this.setIcon(icon);
	}
	
}
