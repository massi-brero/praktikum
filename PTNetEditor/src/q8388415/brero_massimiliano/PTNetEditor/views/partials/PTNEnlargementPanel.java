package q8388415.brero_massimiliano.PTNetEditor.views.partials;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PTNEnlargementPanel extends JPanel {
	
	private JLabel label;
	private JButton plusButton;
	private JButton minusButton;
	final int START_WIDTH = 140;
	final int START_HEIGHT = 40;
	final int BUTTON_WIDTH = 18;
	final int BUTTON_HEIGHT = 18;
	
	public PTNEnlargementPanel(String s) {
		label  = new JLabel(s);
		plusButton = new JButton("plusButton");
		plusButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
		plusButton.setMargin(new Insets(0, 0, 2, 0));
		plusButton.setText("+");
		minusButton = new JButton("minusButton");
		minusButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		minusButton.setMargin(new Insets(0, 0, 0, 0));
		minusButton.setText("-");
		this.init();
	}
	
	public void init() {
		plusButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		minusButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
		BoxLayout box = new BoxLayout(this, BoxLayout.X_AXIS);
		box.preferredLayoutSize(this);
		this.setLayout(box);

		this.add(label);
		this.add(plusButton);
		this.add(minusButton);
		
		this.setVisible(true);
		
	}
	
	public PTNEnlargementPanel() {
		this("");
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}
	

	public void addButtonsListener(ActionListener l) {
		
		plusButton.addActionListener(l);
		minusButton.addActionListener(l);
		
	}

	
	
	
	

}
