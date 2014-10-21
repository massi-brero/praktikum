package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public abstract class NodeView extends JLabel implements PTNIScaleListener {
	
	protected ImageIcon iconStandard;
	protected ImageIcon iconSelected;
	protected JLabel nodeLabel;
	private boolean selected = false;
	private float scale = 1.0f;
	private PTNNodeTypes type;
	final float scaleFactor = 0.1f;

	public NodeView(String sourceStandardIcon, String sourceSelectedIcon) {
		
		this.nodeLabel = new JLabel("");
		iconStandard = new ImageIcon(sourceStandardIcon);
		iconSelected = new ImageIcon(sourceSelectedIcon);
		this.init();

	}
	
	private void init() {
		this.add(nodeLabel);
		this.setIcon(iconStandard);
		this.setLayout(new FlowLayout());
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setFocusable(true);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setHorizontalTextPosition(JLabel.CENTER);
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
		
		this.updateSize(scale-1);
	}


	public PTNNodeTypes getType() {
		return type;
	}

	public void setType(PTNNodeTypes type) {
		this.type = type;
	}
	
	@Override
	public void increaseScale() {
		
		if (scale <= 1.5) {
			scale += scaleFactor;
			this.updateSize(scaleFactor);
			
		}
		
	}

	@Override
	public void decreaseScale() {
		
		if (scale > 1 + scaleFactor) {
			scale -= scaleFactor;
			this.updateSize(-scaleFactor);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "+") {
			this.increaseScale();
		}
		else if (e.getActionCommand() == "-") {
			this.decreaseScale();
		}
			
		
	}


	protected void updateSize(float factor) {
		
		int width = this.getIcon().getIconWidth();
		int height = this.getIcon().getIconHeight();
		Image image = ((ImageIcon) this.getIcon()).getImage();
		
		image = image.getScaledInstance((int)(width+width*factor), (int)(height+height*factor), Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(image));
		this.setSize((int)(this.getWidth() + this.getWidth()*factor), (int)(this.getHeight() + this.getHeight()*factor));
		this.updateLabelTextSize(factor);
		this.repaint();
		
	}

	public float getScale() {
		return scale;
	}


	public void setScale(float scale) {
		this.scale = scale;
	}
	
	protected void updateLabelTextSize(float factor) {
		int changeSize = (int)Math.signum(factor);
		Font nodeLabelFont = nodeLabel.getFont();
		this.nodeLabel.setFont(new Font(nodeLabelFont.getFontName(), Font.PLAIN, (int)(nodeLabelFont.getSize() + changeSize)));
	}
	

}
