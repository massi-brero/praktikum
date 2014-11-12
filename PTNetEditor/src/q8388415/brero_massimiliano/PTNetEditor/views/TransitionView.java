package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.Font;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNDesktopController;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class TransitionView extends NodeView {
	
	PTNDesktopController listener;
	final static String sourceIconStandard = "rectangle.png";
	final static String sourceIconSelected = "rectangle_selected.png";
	// we need a global sclae variable e. g. for creating new nodes of this type
	static int currentPlaceScale = 1;
	final Dimension DEFAULT_SIZE = new Dimension(70, 70);
	
	public TransitionView(String id) {
		super(id, sourceIconStandard, sourceIconSelected);
		this.init();
	}
	
	private void init() {
		setSize(DEFAULT_SIZE);
		if (1 < this.currentPlaceScale) 
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
		
	}
	

}
