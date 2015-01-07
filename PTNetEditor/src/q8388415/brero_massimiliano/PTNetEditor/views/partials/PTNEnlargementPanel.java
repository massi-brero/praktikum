package q8388415.brero_massimiliano.PTNetEditor.views.partials;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;

/**
 * 
 * Partial for control panel to change size of a net element type.
 * Contains:
 * <ul>
 * <li>increasement button</li>
 * <li>decreasement button</li>
 * <li>label text</li>
 * <li>optional button</li>
 * </ul>
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNEnlargementPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JButton plusButton;
	private JButton minusButton;
	final int START_HEIGHT = 20;
	final int BUTTON_WIDTH = 15;
	final int BUTTON_HEIGHT = 15;
	
	/**
	 * 
	 * @param labelText String
	 * 		Text of this enlargement control partial..
	 * @param iconPath String
	 * 		Optional Icon for this enlargement partial.		
	 */
	public PTNEnlargementPanel(String labelText, String iconPath)  {
		
		label  = new JLabel(labelText);
		this.setIcon(iconPath);
	
		plusButton = new JButton("plusButton");
		plusButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
		plusButton.setMargin(new Insets(0, 0, 2, 0));
		plusButton.setText("+");
		plusButton.setVerticalTextPosition(JButton.CENTER);
		plusButton.setHorizontalTextPosition(JButton.CENTER);
		plusButton.setFocusable(false);
		
		minusButton = new JButton("minusButton");
		minusButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		minusButton.setMargin(new Insets(0, 0, 0, 0));
		minusButton.setText("-");
		minusButton.setVerticalTextPosition(JButton.CENTER);
		minusButton.setHorizontalTextPosition(JButton.CENTER);
		minusButton.setFocusable(false);
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.init();
	}
	
	/**
	 * Just in case someone needs a naked size control partial.
	 */
	public PTNEnlargementPanel() {
		this("", "");
	}

	/**
	 * Setter.
	 * @param iconPath String
	 */
	private void setIcon(String iconPath) {
		
		if (!iconPath.isEmpty()) {
			
			try {
				ImageIcon icon = new ImageIcon(PTNEnlargementPanel.class.getResource(iconPath));
				label.setIcon(icon);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Basis initialization.
	 */
	public void init() {
		
		plusButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		minusButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		this.setCustomSize();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.add(label);
		this.add(plusButton);
		this.add(minusButton);
		
		this.setVisible(true);
		
	}
	
	/**
	 * Sets size so label with icon + 2 buttons will fit in this panel.
	 */
	protected void setCustomSize() {
		int vSpace = 40;
		int width = label.getPreferredSize().width + 2 * BUTTON_WIDTH + vSpace;
		this.setPreferredSize(new Dimension(width, START_HEIGHT));
	}

	/**
	 * Getter.
	 * 
	 * @return JLabel
	 * 		Label with text for this partial.
	 */
	public JLabel getLabel() {
		return label;
	}

	/**
	 * Setter.
	 * 
	 * @param label JLabel
	 * 		Label with text for this partial.
	 */
	public void setLabel(JLabel label) {
		this.label = label;
		this.setCustomSize();
		this.repaint();
	}
	
	/**
	 * Listeners for "+" and "-" button.
	 * 
	 * @param l ActionListener
	 */
	public void addButtonsListener(ActionListener l) {
		
		plusButton.addActionListener(l);
		minusButton.addActionListener(l);
		
	}
	
	/**
	 * This method ensures that our objects really have the methods for 
	 * increasing an decreasing their size
	 * @param l {@link PTNIScaleListener}
	 */
	public void addScaleListener(PTNIScaleListener l) {
		
		plusButton.addActionListener(l);
		minusButton.addActionListener(l);
				
	}

}
