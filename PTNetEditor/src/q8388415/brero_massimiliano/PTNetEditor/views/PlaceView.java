package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;

import snippet.ButtListener;

public class PlaceView extends NodeView {
	
	final static String sourceIconStandard = "circle.png";
	final static String sourceIconSelected = "circle_selected.png";
	
	public PlaceView(String tokenNumber) {
		super(sourceIconStandard, sourceIconSelected);
		this.init();
		this.setText(tokenNumber);
	}
	
	private void init() {
		setSize(new Dimension(70, 70));
		nodeLabel.setBounds(+50, -5, 10, 20);
		setIconTextGap((int)Math.floor(-getWidth()/3.2));
	}

}
