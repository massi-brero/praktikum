package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIArcDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
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

    final private static String sourceIconStandard = "circle2.png";
    final private static String sourceIconSelected = "circle2_selected.png";
    final private static Dimension DEFAULT_SIZE = new Dimension(50, 70);
	private static final int ICON_WIDTH = 35;
	private static final int ICON_HEIGHT = 35;
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

    	this.customizeIcon();
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
     * @return
     * 		Dimension size of this node type.
     */
	public static Dimension getCurrentSize() {
		if (null == currentSize)
			return DEFAULT_SIZE;
		else
			return currentSize;
	}

	@Override
	public String getNodeName() {
		return this.getName();
	}

	/**
	 * Returns token. Also a {@link PTNINodeDTO} method.
	 */
	@Override
	public Integer getToken() {
		return token;
	}
	
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		this.customizeIcon();
	}

	@Override
	protected void customizeIcon() {
    	Image image = ((ImageIcon) this.getIcon()).getImage();
		image = image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
    	this.setIcon(new ImageIcon(image));
		
	}

}
