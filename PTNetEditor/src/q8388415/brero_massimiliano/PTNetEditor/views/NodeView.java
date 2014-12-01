package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.hamcrest.core.SubstringMatcher;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

/**
 * Base Class for the node views. The node's name is displayed in an JLabel nameLabel
 * which is part of the sourrounding Jlabel representing the node.
 * 
 * @author brero
 *
 */
public abstract class NodeView extends JLabel implements PTNIScaleListener {
	
	private final int SCALE_MAX = 4;
	private final int SCALE_MIN = 0;
	// number of pixels the icon will change on scaling operations
	private final int CHANGE_WIDTH = 4;
	private final int CHANGE_HEIGHT = 4;
	protected ImageIcon iconStandard;
	protected ImageIcon iconSelected;
	protected JLabel nameLabel;
	protected final int LABEL_TEXT_LENGTH = 11;
	private boolean selected = false;
	protected int scale = 1;
	final int scaleFactor = 1;
	private PTNNodeTypes type;
	private String id;

	public NodeView(String id, String sourceStandardIcon, String sourceSelectedIcon) {
		
		nameLabel = new JLabel("");
		iconStandard = new ImageIcon(sourceStandardIcon);
		iconSelected = new ImageIcon(sourceSelectedIcon);
		this.setId(id);
		this.init();

	}
	
	private void init() {
		
		this.add(nameLabel);
		this.setIcon(iconStandard);
		this.setLayout(new FlowLayout());
		this.setDoubleBuffered(true);
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setOpaque(false);
		this.setVerticalAlignment(JLabel.CENTER);
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setVerticalTextPosition(JLabel.CENTER);
	
	}
	
	/**
	 * Sets image source path for icon representing this node type
	 * @param $s String
	 */
	public void setIconSource(ImageIcon ic) {
		//@Todo check if path exists
		this.setIcon(ic);
	}
	
	public JLabel getNameLabel() {
		return nameLabel;
	}

	@Override
	public void setName(String label) {
		// name of JComponent is the same as that from the displayed label
		
		String adjustedLabel = label.length() > LABEL_TEXT_LENGTH ?
		                            label.substring(0, LABEL_TEXT_LENGTH-1) : label;
		/**
         * If we have an extra long text we will cut it,
         * so can can read the first word(s). The full label text can 
         * be viewed with the tooltip window.
         * .
         */
	    this.setToolTipText(label);
	    super.setName(adjustedLabel);
		nameLabel.setText(adjustedLabel);
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
		//@todo Warum funktioniert scale - 1 besser als scale?
		this.updateIconSize(scale);
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
		this.updateIcon();

		Image image = ((ImageIcon) this.getIcon()).getImage();
		image = image.getScaledInstance(width + CHANGE_WIDTH * factor, height + CHANGE_HEIGHT * factor, Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(image));
	}

	/**
	 * Updates Icon depending on node state. Should be overwritten if a
	 * node can have more than 2 states (like transition nodes):
	 */
	protected void updateIcon() {
	   
	    setIcon(this.isSelected() ? iconSelected : iconStandard);
	    
	}

	public float getScale() {
		return scale;
	}


	public void setScale(int scale) {
		this.scale = scale;
	}
	
	protected void updateLabelTextSize(int factor) {
		int changeSize = factor;
		Font nodeLabelFont = nameLabel.getFont();
		this.nameLabel.setFont(new Font(nodeLabelFont.getFontName(), Font.PLAIN, (int)(nodeLabelFont.getSize() + changeSize)));
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
		
}
