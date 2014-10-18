package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class NodeView extends JLabel {
	
	protected ImageIcon iconStandard;
	protected ImageIcon iconSelected;
	protected JLabel nodeLabel;
	private boolean selected = false;
	private Point basePosition;

	public NodeView(String sourceStandardIcon, String sourceSelectedIcon) {
		
		setLayout(new FlowLayout());
		iconStandard = new ImageIcon(sourceStandardIcon);
		iconSelected = new ImageIcon(sourceSelectedIcon);
		setIcon(iconStandard);
		this.nodeLabel = new JLabel("");
		add(nodeLabel);
		setBasePosition(new Point(0,0));
		
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setFocusable(true);
		
	}
	
	
	/**
	 * Sets image source path for icon representing this node type
	 * @param $s String
	 */
	public void setIconSource(ImageIcon ic) {
		//@Todo check if path exists
		this.setIcon(ic);
	}
	
	public JLabel getLabel() {
		return nodeLabel;
	}

	public void setLabelText(String label) {
		this.nodeLabel.setText(label);
	}

	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected)
			setIcon(iconSelected);			
		else
			setIcon(iconStandard);
	}
	
	public Point getBasePosition() {
		return basePosition;
	}

	public void setBasePosition(Point basePosition) {
		this.basePosition = basePosition;
	}
	
	
}
