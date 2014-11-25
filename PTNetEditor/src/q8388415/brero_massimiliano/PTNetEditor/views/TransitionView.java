package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNDesktopController;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class TransitionView extends NodeView {

    PTNDesktopController listener;
    final static String sourceIconStandard = "rectangle.png";
    final static String sourceIconSelected = "rectangle_selected.png";
    final static String sourceIconActivated = "rectangle_activated.png";
    private Icon iconActivated;
    private Boolean isActivated = false;

    // we need a global scale variable e. g. for creating new nodes of this type
    static int currentPlaceScale = 1;
    public static Dimension currentSize = null;
    final Dimension DEFAULT_SIZE = new Dimension(70, 70);

    public TransitionView(String id) {
	super(id, sourceIconStandard, sourceIconSelected);
	iconActivated = new ImageIcon(sourceIconActivated);
	this.init();
    }

    private void init() {
	setSize(PlaceView.DEFAULT_SIZE);
	currentSize = currentSize == null ? PlaceView.DEFAULT_SIZE
		: currentSize;

	if (1 < PlaceView.currentPlaceScale)
	    this.updateSize(currentPlaceScale - 1);

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
	currentPlaceScale = scale;
	currentSize = this.getSize();

    }

    /**
     * Sets all current size information to its default values. We need that
     * e.g. when reading a file an importing @ return void
     */
    public static void resetSize() {
	currentSize = PlaceView.DEFAULT_SIZE;
	currentPlaceScale = 1;
    }

    public Boolean isActivated() {
	return isActivated;
    }

    public void setIsActivated(Boolean isActivated) {

	this.isActivated = isActivated;
	this.updateIcon();
	// @todo Warum funktioniert scale - 1 besser als scale?
	this.updateIconSize(scale - 1);
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

}
