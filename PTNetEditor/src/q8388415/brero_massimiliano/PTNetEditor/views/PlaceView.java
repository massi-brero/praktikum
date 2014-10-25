package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class PlaceView extends NodeView {

	final static String sourceIconStandard = "circle.png";
	final static String sourceIconSelected = "circle_selected.png";
	final Dimension DEFAULT_SIZE;

	public PlaceView(String tokenNumber) {
		super(sourceIconStandard, sourceIconSelected);
		DEFAULT_SIZE = new Dimension(70, 70);
		this.init();
		this.setText(tokenNumber);
	}

	/**
	 * Initializes this component's size and elements.
	 * @return void
	 */
	private void init() {

		setSize(DEFAULT_SIZE);
		this.setType(PTNNodeTypes.place);
		nodeLabel.setVerticalTextPosition(JLabel.NORTH);
		// setIconTextGap((int)Math.floor(-DEFAULT_SIZE.getWidth()/3.2));
	}
	
	/**
	 * Adopts token label to scale.
	 */
	@Override
	public void updateSize(int factor) {

		int addSize = factor*2;
		Font tokenFont = this.getFont();
		this.setFont(new Font(tokenFont.getFontName(), Font.PLAIN, (int) (tokenFont.getSize() + addSize)));
		super.updateSize(factor);
		
	}
	


}
