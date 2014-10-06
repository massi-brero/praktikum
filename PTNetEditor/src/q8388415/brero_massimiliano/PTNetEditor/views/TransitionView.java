package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;

import snippet.ButtListener;

public class TransitionView extends NodeView {
	
	ButtListener listener;
	
	public TransitionView() {
		super("square.png");
		this.init();
	}
	
	private void init() {		
		this.setSize(32,32);
		this.nodeLabel.setBounds(+50, -5, 10,20);
		this.setIconTextGap((int)Math.floor(-getWidth()/3.2));
	}

}
