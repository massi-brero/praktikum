package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class PlaceView extends NodeView {

    final static String sourceIconStandard = "circle.png";
    final static String sourceIconSelected = "circle_selected.png";
    final static Dimension DEFAULT_SIZE = new Dimension(70, 70);
    private int token = 0;
    // we need a global scale variable e. g. for creating new nodes of this type
    public static int currentPlaceScale = 1;
    public static Dimension currentSize = null;
    public final static String DOT_SIGN = "\u2022";

    public PlaceView(String id, int token) {
        super(id, sourceIconStandard, sourceIconSelected);
        this.init();
        this.updateToken(token);
    }

    /**
     * Initializes this component's size and elements.
     * 
     * @return void
     */
    private void init() {

        this.setSize(PlaceView.DEFAULT_SIZE);
        currentSize = currentSize == null ? PlaceView.DEFAULT_SIZE : currentSize;
        if (1 < currentPlaceScale)
            this.updateSize(currentPlaceScale - 1);
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
        currentPlaceScale = scale;
        currentSize = this.getSize();

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
        currentSize = PlaceView.DEFAULT_SIZE;
        currentPlaceScale = 1;
    }

}
