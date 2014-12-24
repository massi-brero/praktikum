package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

/**
 * Base Class for the node views. The node's name is displayed in an JLabel nameLabel
 * which is part of the sourrounding Jlabel representing the node.
 * 
 * @author brero
 *
 */
public abstract class NodeView extends JLabel implements PTNIScaleListener, PTNINodeDTO {
	
	private static final long serialVersionUID = 1L;
	private final int SCALE_MAX = 4;
	private final int SCALE_MIN = 0;
	// number of pixels the icon will change on scaling operations
	protected int changeWidth = 4;
	protected int changeHeight = 4;
	protected ImageIcon iconStandard;
	protected ImageIcon iconSelected;
	protected JLabel nameLabel;
	protected final int LABEL_TEXT_LENGTH = 11;
	private boolean selected = false;
	protected int scale = 0;
	final int scaleFactor = 1;
	private PTNNodeTypes type;
	private String id;
	/*
	 * Child Classes must override iconWidth and iconHeight.
	 */
	protected int iconWidth = 1;
	protected int iconHeight = 1;

	public NodeView(String id, 
					String sourceStandardIcon, 
					String sourceSelectedIcon,
					int iconWidth,
					int iconHeight) {
		
		this.iconWidth = iconWidth;
		this.iconHeight = iconHeight;
		nameLabel = new JLabel("");
		iconStandard = new ImageIcon(NodeView.class.getResource(sourceStandardIcon));
		iconSelected = new ImageIcon(NodeView.class.getResource(sourceSelectedIcon));
		this.setId(id);
		this.init();

	}
	
	private void init() {
		
		this.add(nameLabel);
		this.setIcon(iconStandard);
		this.initIcon();
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

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected)
			setIcon(iconSelected);
		else
			setIcon(iconStandard);
		
		this.updateIconSize();
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

		this.updateIconSize();
		//We have to increase height by factor 2 so our node label text will keep its distance from the icon.
		this.setSize(this.getWidth() + changeWidth * factor, this.getHeight() + changeHeight * 2 * factor);
		this.updateLabelTextSize(factor);
		this.repaint();
		
	}
	
	/**
	 * Updates size of node pic.
	 * @param factor float scaling value
	 */
	protected void updateIconSize() {

		// by reseting the icon we won't loose on quality due to scaling fractions when we have repeated scaling operations
		this.updateIcon();
		System.out.println(changeHeight);
		Image image = ((ImageIcon) this.getIcon()).getImage();
		image = image.getScaledInstance(iconWidth + changeWidth * scale, iconHeight + changeHeight * scale, Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(image));
	}

	/**
	 * Updates Icon depending on node state. Should be overwritten if a
	 * node can have more than 2 states (like transition nodes):
	 */
	protected void updateIcon() {
	   
	    setIcon(this.getSelected() ? iconSelected : iconStandard);
	    
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
	
	protected void initIcon() {

	    	Image image = ((ImageIcon) this.getIcon()).getImage();
			image = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
	    	this.setIcon(new ImageIcon(image));

	}
	
	
		
}
