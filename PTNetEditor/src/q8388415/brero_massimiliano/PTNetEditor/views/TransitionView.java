package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

/**
 * Represents the view of a transition node.
 * TransitionView#currentTransitionScale and TransitionView#currentSize are used to keep track of the 
 * actual size an scaling factor of this node type, e g. for other classes who need to know about the 
 * current size of node an his icon. The attribute scale inherited from NodeView is used for internal 
 * scaling process.
 * Thus those two are synchronized in each from NodeView derived class.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class TransitionView extends NodeView implements PTNINodeDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * Paths to icon images which are "loaded" depending on the node#s state.
	 */
	final private static String sourceIconStandard = "/resources/elements/rectangle2.png";
	final private static String sourceIconSelected = "/resources/elements/rectangle2_selected.png";
	final private static String sourceIconActivated = "/resources/elements/rectangle2_activated.png";
	
	/**
	 * The node's states.
	 */
	private Icon iconActivated;
	private Boolean isActivated = false;
	/**
	 * Current scaling factor for nodes' components.
	 * Is static so it can be used globally for newly created nodes
	 * or when other classes need informations about the current place scale.
	 */
	protected static int currentTransitionScale = 0;
	/**
	 * Current size of this node family
	 * Is static so it can be used globally for newly created nodes
	 * or when other classes need informations about the current place size.
	 */
	protected static Dimension currentSize = null;
	
	private final static Dimension DEFAULT_SIZE = new Dimension(70, 70);

	/**
	 * 
	 * @param id
	 * 	The node#s id.
	 */
	public TransitionView(String id) {
		super(id, sourceIconStandard, sourceIconSelected, 16, 32);
		this.changeWidth = 2;
		iconActivated = new ImageIcon(TransitionView.class.getResource(sourceIconActivated));
		this.init();
	}

    /**
     * Initializes this component's size and elements.
     * If scale is > 1 then init() will resize all components 
     * of the node.
     * 
     * @return void
     */
	private void init() {
		
		setSize(DEFAULT_SIZE);
		TransitionView.currentSize = TransitionView.currentSize == null ? DEFAULT_SIZE : TransitionView.currentSize;
        scale = TransitionView.currentTransitionScale;
        
        //now we scale our other components like icon etc.
		if (0 < TransitionView.currentTransitionScale)
			this.updateSize(TransitionView.currentTransitionScale);
		this.setType(PTNNodeTypes.TRANSITION);
		nameLabel.setVerticalTextPosition(JLabel.NORTH);
		
	}

	/**
	 * Updates global scale info for this node type.
	 */
	@Override
	public void updateSize(int factor) {
		
		super.updateSize(factor);
		TransitionView.currentTransitionScale = scale;
		TransitionView.currentSize = this.getSize();

	}

	/**
	 * Sets all current size information to its default values. We need that
	 * e.g. when reading a file an importing @ return void
	 */
	public static void resetSize() {
		TransitionView.currentSize = DEFAULT_SIZE;
		TransitionView.currentTransitionScale = 0;
	}

	/**
	 * Getter.
	 * Can this transition be switched?
	 * 
	 * @return Boolean
	 */
	public Boolean isActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean isActivated) {

		this.isActivated = isActivated;
		this.updateIcon();
		this.updateIconSize();
		
	}

	/**
	 * Overrides method so we can fit an additional icon in when transition is
	 * activated.
	 */
	@Override
	protected void updateIcon() {

		if (getSelected())
			this.setIcon(iconSelected);
		else if (isActivated)
			this.setIcon(iconActivated);
		else
			this.setIcon(iconStandard);

	}
	
	
	
    /**
     * 
     * @return Dimension 
     * 		Current size of this node type i.e. all transitions on the desktop.
     */
	public static Dimension getCurrentSize() {
		if (null == currentSize)
			return DEFAULT_SIZE;
		else
			return currentSize;
	}

	/**
	 * Getter.
	 * 
	 * @return String
	 */
	@Override
	public String getNodeName() {
		return this.getName();
	}

	/**
	 * A transition really has no token. We need this
	 * because Transition view implements {@link PTNINodeDTO}.
	 * 
	 * @return Integer
	 */
	@Override
	public Integer getToken() {
		return null;
	}
	
	/**
	 * Setter.
	 * 
	 * @param selected Boolean
	 */
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
	}

}
