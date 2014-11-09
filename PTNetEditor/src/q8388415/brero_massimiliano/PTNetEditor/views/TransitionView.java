package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNDesktopController;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class TransitionView extends NodeView {
	
	PTNDesktopController listener;
	final static String sourceIconStandard = "rectangle.png";
	final static String sourceIconSelected = "rectangle_selected.png";
	final Dimension DEFAULT_SIZE;
	
	public TransitionView(String id) {
		super(id, sourceIconStandard, sourceIconSelected);
		DEFAULT_SIZE = new Dimension(70, 70);
		this.init();
	}
	
	private void init() {
		setSize(DEFAULT_SIZE);
		this.setType(PTNNodeTypes.transition);
		nameLabel.setBounds(+500, -5, 10, 20);
		//setIconTextGap((int)Math.floor(-DEFAULT_SIZE.getWidth()/3.2));
	}

}
