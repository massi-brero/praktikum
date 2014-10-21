package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import snippet.ButtListener;

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

	private void init() {

		setSize(DEFAULT_SIZE);
		this.setType(PTNNodeTypes.place);
		nodeLabel.setVerticalTextPosition(JLabel.NORTH);
		// setIconTextGap((int)Math.floor(-DEFAULT_SIZE.getWidth()/3.2));
	}

	public void updateSize(float factor) {

		int addSize = (int) Math.signum(factor)*2;
		Font tokenFont = this.getFont();
		this.setFont(new Font(tokenFont.getFontName(), Font.PLAIN, (int) (tokenFont.getSize() + addSize)));
		super.updateSize(factor);
	}

}
