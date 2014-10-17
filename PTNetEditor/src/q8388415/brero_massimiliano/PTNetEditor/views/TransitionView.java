package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;

import snippet.ButtListener;

public class TransitionView extends NodeView {
	
	ButtListener listener;
	final static String sourceIconStandard = "rectangle.png";
	final static String sourceIconSelected = "rectangle_selected.png";
	
	public TransitionView() {
		super(sourceIconStandard, sourceIconSelected);
		this.init();

	}
	
	private void init() {
		setSize(new Dimension(70, 70));
		nodeLabel.setBounds(+50, -5, 10, 20);
		setIconTextGap((int)Math.floor(-getWidth()/3.2));
	}

}
