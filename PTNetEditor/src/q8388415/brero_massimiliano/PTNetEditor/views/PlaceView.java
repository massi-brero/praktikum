package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

/**
 * Represents the view of a place node.
 * PlaceView#currentTransitionPlaceling factor of this node type, e g. for other classes who need 
 * to know about the current size of node an his icon. The attribute scale inherited
 * from NodeView is used for internal scaling process.
 * Thus those two are synchronized in each from NodeView derived class.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PlaceView extends NodeView {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Paths to icon images which are "loaded" depending on the node#s state.
	 */
	final private static String sourceIconStandard = "/resources/elements/circle2.png";
    final private static String sourceIconSelected = "/resources/elements/circle2_selected.png";
    final private static Dimension DEFAULT_SIZE = new Dimension(50, 70);
    private int token = 0;
    /**
	 * Current scaling factor for nodes' components.
	 * Is static so it can be used globally for newly created nodes
	 * or when other classes need informations about the current place scale.
	 */
    private static int currentPlaceScale = 0;
	/**
	 * Current size of this node family
	 * Is static so it can be used globally for newly created nodes
	 * or when other classes need informations about the current place size.
	 */
    private static Dimension currentSize = null;
    
    /**
     * DOT sign used when toekn number = 1.
     */
    private final String DOT_SIGN = "\u2022";

    /**
     * 
     * @param id String
     * 		This node's id.
     * @param token int
     * 		Number of tokens.
     */
    public PlaceView(String id, int token) {
        super(id, sourceIconStandard, sourceIconSelected, 34, 34);
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
        this.setType(PTNNodeTypes.STELLE);
        nameLabel.setVerticalTextPosition(JLabel.NORTH);

    }

    /**
     * Adopts token label to scale and updates global scale info for this node
     * type.
     * 
     * @param factor int
     * 		factor by which the size must be increased/decreased.
     */
    @Override
    public void updateSize(int factor) {
    	
        int addSize = factor * 2;
        Font tokenFont = this.getFont();
        this.setFont(new Font(tokenFont.getFontName(), Font.PLAIN, 
        					  (int) (tokenFont.getSize() + addSize)));
        super.updateSize(factor);
        PlaceView.currentPlaceScale = scale;
        PlaceView.currentSize = this.getSize();

    }

    /**
     * Setter. Depending on the value the token number will be displayed...
     * <ul>
     * <li>not at all when value = 0</li>
     * <li>as DOT when value = 1</li>
     * <li>as figures when value is between 2 and 999  </li>
     * </ul>
     * 
     * @param token int
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
    	PlaceView.currentPlaceScale = 0;
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
     * @return Dimension
     * 		 Current size of this node type.
     */
	public static Dimension getCurrentSize() {
		if (null == currentSize)
			return DEFAULT_SIZE;
		else
			return currentSize;
	}

	/**
	 * Getter
	 * 
	 * @return String
	 */
	@Override
	public String getNodeName() {
		return this.getName();
	}

	/**
	 * Getter.
	 * Returns token. Also a {@link PTNINodeDTO} method.
	 * 
	 * @return Integer
	 */
	@Override
	public Integer getToken() {
		return token;
	}
	
	/**
	 * Setter.
	 * 
	 * @param selected Boolean
	 */
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
	}

}
