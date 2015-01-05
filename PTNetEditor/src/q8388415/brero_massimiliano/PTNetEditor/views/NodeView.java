package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
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
 * @author q8388415 - Massimiliano Brero
 *
 */
public abstract class NodeView extends JLabel implements PTNIScaleListener, PTNINodeDTO {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Basic node information. The name of the node has an own JLabel.
	 * It is added to this nodes Jlabel which works as a container for
	 * the name label.
	 */
	private boolean selected = false;
	private PTNNodeTypes type;
	private String id;
	protected JLabel nameLabel;
	protected final int LABEL_TEXT_LENGTH = 14;
	
	/**
	 * Scale variables for object internal computations.
	 */
	protected int scale = 0;
	final int scaleFactor = 1;
	private final int SCALE_MAX = 4;
	private final int SCALE_MIN = 0;
	// number of pixels the icon will change on scaling operations
	protected int changeWidth = 4;
	protected int changeHeight = 4;
	
	/**
	 * The icons the JLabel will load depending on its state.
	 */
	protected ImageIcon iconStandard;
	protected ImageIcon iconSelected;

	/*
	 * Child Classes must override iconWidth and iconHeight.
	 */
	protected int iconWidth = 1;
	protected int iconHeight = 1;

	/**
	 * 
	 * @param id
	 * 		Nodes id.
	 * @param sourceStandardIcon String
	 * 		Path to node image when in standard state.
	 * @param sourceSelectedIcon
	 * 		Path to node image when in selected state.
	 * @param iconWidth int
	 * 		Image width of node icon.
	 * @param iconHeight int
	 * 		Image height of node icon.
	 */
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
	
	/**
	 * Basic initialization for all node types.
	 */
	private void init() {
		
		this.add(nameLabel);
		this.setIcon(iconStandard);
		this.initIcon();
		this.setLayout(new FlowLayout());
		this.setDoubleBuffered(true);
//		this.setBorder(BorderFactory.createLineBorder(Color.black));
//		nameLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setOpaque(false);
		this.setVerticalAlignment(JLabel.CENTER);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setVerticalTextPosition(JLabel.CENTER);
			
	}
	
	/**
	 * Sets image source path for icon representing this node type
	 * @param ic ImageIcon
	 */
	public void setIconSource(ImageIcon ic) {
		//@Todo check if path exists
		this.setIcon(ic);
	}
	
	/**
	 * The mail JLabel of the node contains an extra Jlabel
	 * for the node's name.
	 * 
	 * @return {@link JLabel}
	 * 		Name label for this node thit will be rendered.
	 */
	public JLabel getNameLabel() {
		return nameLabel;
	}

	/**
	 * We override the inherited method to use it for setting our node's name
	 * that will also be displayed on the desktop. Therefore the JComponents name
	 * is the same as for the node.
	 * 
	 * @param label 
	 * 	Node and JComponent name.
	 */
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
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setPreferredSize(new Dimension(this.getWidth() - 2, 
				nameLabel.getFontMetrics(nameLabel.getFont()).getHeight()));


	}

	/**
	 * Getter.
	 * 
	 * @return Boolean
	 * 		Is node in selected state?
	 */
	public boolean getSelected() {
		return selected;
	}

	/**
	 * Setter.
	 * 
	 * If a nodes' status is set on selected the icon will
	 * be changed.
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected)
			setIcon(iconSelected);
		else
			setIcon(iconStandard);
		
		this.updateIconSize();
	}


	/**
	 * Getter
	 * 
	 * @return {@link PTNNodeTypes}
	 * PTNNodeTypes.STELLE or PTNNodeTypes.TRANSITION
	 */
	public PTNNodeTypes getType() {
		return type;
	}

	/**
	 * Setter
	 * 
	 * @param type {@link PTNNodeTypes}
	 * 		PTNNodeTypes.STELLE or PTNNodeTypes.TRANSITION
	 */
	public void setType(PTNNodeTypes type) {
		this.type = type;
	}
	
	/**
	 * Since this class implements {@link PTNIScaleListener}
	 * this method overrides the given method there.
	 * Will increment scale by scaleFactor.
	 */
	@Override
	public void increaseScale() {
		
		if (scale <= SCALE_MAX) {
			scale += scaleFactor;
			this.updateSize(scaleFactor);
		}
		
	}

	/**
	 * Since this class implements {@link PTNIScaleListener}
	 * this method overrides the given method there.
	 * Will reduce scale by scaleFactor.
	 */
	@Override
	public void decreaseScale() {
		
		if (scale > SCALE_MIN) {
			scale -= scaleFactor;
			this.updateSize(-scaleFactor);
		}

	}

	/**
	 * Increase or decrease scale. Listens when an event from the 
	 * corresponding control pane is fired.
	 * 
	 * @param e ActionEvent
	 */
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
	 * 
	 * @param factor float 
	 * 		Scaling value by which node view shall be increased/decreased.
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
	 */
	protected void updateIconSize() {

		// by reseting the icon we won't loose on quality due to scaling fractions when we have repeated scaling operations
		this.updateIcon();
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

	/**
	 * Setter.
	 * 
	 * @param scale int
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	/**
	 * When node is increased/decreased his name JLabel size will be
	 * updated by this method.
	 * 
	 * @param factor
	 */
	protected void updateLabelTextSize(int factor) {
		int changeSize = factor;
		Font nodeLabelFont = nameLabel.getFont();
		nameLabel.setPreferredSize(new Dimension(this.getWidth() - 2, 
												 nameLabel.getFontMetrics(nameLabel.getFont()).getHeight()));
		this.nameLabel.setFont(new Font(nodeLabelFont.getFontName(), Font.PLAIN, (int)(nodeLabelFont.getSize() + changeSize)));
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
	}

	/**
	 * Getter.
	 * 
	 * @return String
	 * 		The node's id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter.
	 * 
	 * @param id String
	 * 		The node's id.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Sets icon to the current width and height.
	 */
	protected void initIcon() {

	    	Image image = ((ImageIcon) this.getIcon()).getImage();
			image = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
	    	this.setIcon(new ImageIcon(image));

	}
	
}
