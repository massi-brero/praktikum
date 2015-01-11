package q8388415.brero_massimiliano.PTNetEditor.types;

import java.awt.event.ActionListener;

/**
 * A simple interface. Must be implemented by classes which have
 * to manipulate the net or elements every time a node or arrowhead size 
 * changes.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public interface PTNIScaleListener extends ActionListener {
	
	void increaseScale();
	void decreaseScale();

}
