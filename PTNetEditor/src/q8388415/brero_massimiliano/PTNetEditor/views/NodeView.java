package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class NodeView extends JLabel {
	
	protected ImageIcon icon;
	protected JLabel nodeLabel;

	
	public NodeView(String iconSource) {
		
		//this.setContentAreaFilled(false);
		icon = new ImageIcon(iconSource);
		setIcon(icon);
		setLayout(new FlowLayout());
		this.nodeLabel = new JLabel("");
		add(nodeLabel);
		this.setFocusable(true);
		
	}
	
	
	/**
	 * Sets image source path for icon representing this node type
	 * @param $s String
	 */
	public void setIconSource(String $s) {
		//@Todo check if path exists
		this.icon = new ImageIcon($s);
	}
	
	public JLabel getLabel() {
		return nodeLabel;
	}

	public void setLabelText(String label) {
		this.nodeLabel.setText(label);
	}
	
	
	

}
