package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNDesktopController;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

/**
 * Represents the view of a transition node.
 * TransitionView#currentTransitionScale and TransitionView#currentSize are used to keep track of the 
 * actual size an scaling factor of this node type, e g. for other classes who need to know about the current size of
 * node an his icon. The attribute scale inherited
 * from NodeView is used for internal scaling process.
 * Thus those two are synchronized in each from NodeView derived class.
 * 
 * @author Laptop
 *
 */
public class TransitionView extends NodeView {

	PTNDesktopController listener;
	final private static String sourceIconStandard = "rectangle.png";
	final private static String sourceIconSelected = "rectangle_selected.png";
	final private String sourceIconActivated = "rectangle_activated.png";
	private Icon iconActivated;
	private Boolean isActivated = false;
	/**
	 * Current scaling factor for nodes' components
	 */
	protected static int currentTransitionScale = 0;
	/**
	 * Current size of this node family
	 */
	protected static Dimension currentSize = null;

	private final static Dimension DEFAULT_SIZE = new Dimension(70, 70);

	public TransitionView(String id) {
		super(id, sourceIconStandard, sourceIconSelected);
		iconActivated = new ImageIcon(sourceIconActivated);
		this.init();
	}

	private void init() {
		
		setSize(DEFAULT_SIZE);
		TransitionView.currentSize = TransitionView.currentSize == null ? DEFAULT_SIZE : TransitionView.currentSize;
        scale = TransitionView.currentTransitionScale;
        
        //now we scale our other components like icon etc.
		if (0 < TransitionView.currentTransitionScale)
			this.updateSize(TransitionView.currentTransitionScale);
		this.setType(PTNNodeTypes.transition);
		nameLabel.setBounds(+500, -5, 10, 20);
		
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

	public Boolean isActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean isActivated) {

		this.isActivated = isActivated;
		this.updateIcon();
		System.out.println(currentTransitionScale);
		this.updateIconSize(TransitionView.currentTransitionScale);
		
	}

	/**
	 * Overrides method so we can fit an additional icon in when transition is
	 * activated.
	 */
	@Override
	protected void updateIcon() {

		if (isSelected())
			this.setIcon(iconSelected);
		else if (isActivated)
			this.setIcon(iconActivated);
		else
			this.setIcon(iconStandard);

	}
	
	
	
    /**
     * 
     * @return
     * 		Dimension size of this node type.
     */
	public static Dimension getCurrentSize() {
		return currentSize;
	}

}
