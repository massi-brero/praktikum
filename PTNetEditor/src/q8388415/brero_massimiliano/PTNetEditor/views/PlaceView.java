package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

/**
 * Represents the view of a place node.
 * PlaceView#currentTransitionScale and PlaceView#currentSize are used to keep track of the 
 * actual size an scaling factor of this node type, e g. for other classes who need to know about the current size of
 * node an his icon. The attribute scale inherited
 * from NodeView is used for internal scaling process.
 * Thus those two are synchronized in each from NodeView derived class.
 * @author Laptop
 *
 */
public class PlaceView extends NodeView {

    final private static String sourceIconStandard = "circle.png";
    final private static String sourceIconSelected = "circle_selected.png";
    final private static Dimension DEFAULT_SIZE = new Dimension(70, 70);
    private int token = 0;
    /**
	 * Current scaling factor for nodes' components.
	 */
    private static int currentPlaceScale = 0;
	/**
	 * Current size of this node family
	 */
    private static Dimension currentSize = null;
    private final String DOT_SIGN = "\u2022";

    public PlaceView(String id, int token) {
        super(id, sourceIconStandard, sourceIconSelected);
        this.init();
        this.updateToken(token);
    }

    /**
     * Initializes this component's size and elements.
     * If scale is > 1 then init() will resize all components 
     * of the node.
     * 
     * @return void
     */
    private void init() {

    	this.setSize(DEFAULT_SIZE);
        PlaceView.currentSize = PlaceView.currentSize == null ? DEFAULT_SIZE : PlaceView.currentSize;
        scale = PlaceView.currentPlaceScale;
        
        //now we scale our other components like icon etc.
        if (0 < PlaceView.currentPlaceScale)
            this.updateSize(PlaceView.currentPlaceScale);    
        this.setType(PTNNodeTypes.place);
        nameLabel.setVerticalTextPosition(JLabel.NORTH);

    }

    /**
     * Adopts token label to scale and updates global scale info for this node
     * type.
     */
    @Override
    public void updateSize(int factor) {

        int addSize = factor * 2;
        Font tokenFont = this.getFont();
        this.setFont(new Font(tokenFont.getFontName(), Font.PLAIN, (int) (tokenFont.getSize() + addSize)));
        super.updateSize(factor);
        PlaceView.currentPlaceScale = scale;
        PlaceView.currentSize = this.getSize();

    }

    /**
     * 
     * 
     * @param text
     * @return void
     */
    public void updateToken(int token) {

        try {

            switch (token) {
            case 0:
                this.setText("");
                break;
            case 1:
                this.setText(DOT_SIGN);
                break;
            default:
                this.setText(String.valueOf(token));
            }

        } catch (NumberFormatException e) {
            // do nothing; token will not be changed
            return;
        }

    }
    
    /**
     * Sets all current size information to its default values.
     * We need that e.g. when reading a file an importing 
     * @ return void
     */
    public static void resetSize() {
    	PlaceView.currentSize = DEFAULT_SIZE;
    	PlaceView.currentPlaceScale = 1;
    }
    
    /**
     * 
     * @return
     * 		Dot sign as unicode code.
     */
    public String getDOTSign() {
    	return DOT_SIGN;
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
