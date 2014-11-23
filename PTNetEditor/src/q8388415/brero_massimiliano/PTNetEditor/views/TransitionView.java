package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.Font;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNDesktopController;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class TransitionView extends NodeView {
	
	PTNDesktopController listener;
	final static String sourceIconStandard = "rectangle.png";
	final static String sourceIconSelected = "rectangle_selected.png";
	// we need a global scale variable e. g. for creating new nodes of this type
	static int currentPlaceScale = 1;
	public static Dimension currentSize = null;
	final Dimension DEFAULT_SIZE = new Dimension(70, 70);
	
	public TransitionView(String id) {
		super(id, sourceIconStandard, sourceIconSelected);
		this.init();
	}
	
	private void init() {
		setSize(PlaceView.DEFAULT_SIZE);
		currentSize = currentSize == null ? PlaceView.DEFAULT_SIZE : currentSize;
		if (1 < PlaceView.currentPlaceScale) 
			this.updateSize(currentPlaceScale-1);
		this.setType(PTNNodeTypes.transition);
		nameLabel.setBounds(+500, -5, 10, 20);
	}
	
	/**
	 * Adopts token label to scale and updates global scale info for this node type.
	 */
	@Override
	public void updateSize(int factor) {

		super.updateSize(factor);
		currentPlaceScale = scale;
		currentSize = this.getSize();
		
	}
	
    /**
     * Sets all current size information to its default values.
     * We need that e.g. when reading a file an importing 
     * @ return void
     */
    public static void resetSize() {
        currentSize = PlaceView.DEFAULT_SIZE;
        currentPlaceScale = 1;
    }
	

}
