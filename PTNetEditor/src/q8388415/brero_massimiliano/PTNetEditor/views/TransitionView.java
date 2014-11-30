package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNDesktopController;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class TransitionView extends NodeView {

	PTNDesktopController listener;
	final private static String sourceIconStandard = "rectangle.png";
	final private static String sourceIconSelected = "rectangle_selected.png";
	final private String sourceIconActivated = "rectangle_activated.png";
	private Icon iconActivated;
	private Boolean isActivated = false;

	// we need a global scale variable e. g. for creating new nodes of this type
	private static int currentPlaceScale = 1;
	private static Dimension currentSize = null;

	private final static Dimension DEFAULT_SIZE = new Dimension(70, 70);

	public TransitionView(String id) {
		super(id, sourceIconStandard, sourceIconSelected);
		iconActivated = new ImageIcon(sourceIconActivated);
		this.init();
	}

	private void init() {
		
		setSize(DEFAULT_SIZE);
		TransitionView.currentSize = TransitionView.currentSize == null ? DEFAULT_SIZE : TransitionView.currentSize;

		if (1 < TransitionView.currentPlaceScale)
			this.updateSize(TransitionView.currentPlaceScale - 1);

		this.setType(PTNNodeTypes.transition);
		nameLabel.setBounds(+500, -5, 10, 20);
	}

	/**
	 * Adopts token label to scale and updates global scale info for this node
	 * type.
	 */
	@Override
	public void updateSize(int factor) {

		super.updateSize(factor);
		TransitionView.currentPlaceScale = scale;
		TransitionView.currentSize = this.getSize();

	}

	/**
	 * Sets all current size information to its default values. We need that
	 * e.g. when reading a file an importing @ return void
	 */
	public static void resetSize() {
		TransitionView.currentSize = DEFAULT_SIZE;
		TransitionView.currentPlaceScale = 1;
	}

	public Boolean isActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean isActivated) {

		this.isActivated = isActivated;
		this.updateIcon();
		// @todo Warum funktioniert scale - 1 besser als scale?
		System.out.println(currentPlaceScale);
		this.updateIconSize(TransitionView.currentPlaceScale - 1);
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
