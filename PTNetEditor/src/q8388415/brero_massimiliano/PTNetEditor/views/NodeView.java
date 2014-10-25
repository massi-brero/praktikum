package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public abstract class NodeView extends JLabel implements PTNIScaleListener {
	
	private final int SCALE_MAX = 5;
	private final int SCALE_MIN = 1;
	// number of pixels the icon will change on scaling operations
	private final int CHANGE_WIDTH = 4;
	private final int CHANGE_HEIGHT = 4;
	protected ImageIcon iconStandard;
	protected ImageIcon iconSelected;
	protected JLabel nodeLabel;
	private boolean selected = false;
	private int scale = 1;
	final int scaleFactor = 1;
	private PTNNodeTypes type;

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
	
	public JLabel getNodeLabel() {
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
		//TODO Warum funktioniert scale - 1 besser als scale?
		this.updateIconSize(scale-1);
	}


	public PTNNodeTypes getType() {
		return type;
	}

	public void setType(PTNNodeTypes type) {
		this.type = type;
	}
	
	@Override
	public void increaseScale() {
		
		if (scale <= SCALE_MAX) {
			scale += scaleFactor;
			this.updateSize(scaleFactor);
			
		}
		
	}

	@Override
	public void decreaseScale() {
		
		if (scale > SCALE_MIN) {
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

	/**
	 * Updates size of complete container according to given factor (including icon, text label ...).
	 * @param factor float scaling value
	 */
	protected void updateSize(int factor) {

		this.updateIconSize(factor);
		//We have to increase height by factor 2 so our node label text will keep its distance from the icon.
		this.setSize(this.getWidth() + CHANGE_WIDTH * factor, this.getHeight() + CHANGE_HEIGHT * 2 * factor);
		this.updateLabelTextSize(factor);
		this.repaint();
		
	}
	
	/**
	 * Updates size of node pic.
	 * @param factor float scaling value
	 */
	protected void updateIconSize(int factor) {
		
		int width = this.getIcon().getIconWidth();
		int height = this.getIcon().getIconHeight();
		
		// by reseting the icon we won't loose on quality due to scaling fractions when we have repeated scaling operations
		this.setIcon(this.isSelected() ? iconSelected : iconStandard);

		Image image = ((ImageIcon) this.getIcon()).getImage();
		image = image.getScaledInstance(width + CHANGE_WIDTH * factor, height + CHANGE_HEIGHT * factor, Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(image));
	}

	public float getScale() {
		return scale;
	}


	public void setScale(int scale) {
		this.scale = scale;
	}
	
	protected void updateLabelTextSize(int factor) {
//		JLabel nodeLabel = this.getNodeLabel();
//		// keep distance from node icon
//		nodeLabel.setPreferredSize(new Dimension(nodeLabel.getWidth(), nodeLabel.getHeight() + factor* 10));
		int changeSize = factor;
		Font nodeLabelFont = nodeLabel.getFont();
		this.nodeLabel.setFont(new Font(nodeLabelFont.getFontName(), Font.PLAIN, (int)(nodeLabelFont.getSize() + changeSize)));
	}
		

}
