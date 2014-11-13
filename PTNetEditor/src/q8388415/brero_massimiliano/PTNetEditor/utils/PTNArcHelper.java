package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.Dimension;
import java.awt.Point;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;

/**
 * Offers methods for dealing with arc computations, like normalizing their start and end point.
 * @author Laptop
 *
 */
public class PTNArcHelper {
	
	/**
	 * 
	 * @param location
	 * @return Dimension now in the center of our NodeView
	 */
	public Point normalizeLocation(PTNNode node) {
		
		PTNNodeTypes type = node.getType();
		Dimension size = null;
		Point normalizedLocation = null;
		
		if (type == PTNNodeTypes.place) {
			//@todo handle this without initializing an object

			size = PlaceView.currentSize;
		} else if (type == PTNNodeTypes.transition) {
			size = (new TransitionView("")).getSize();
		}
		
		normalizedLocation = new Point(node.getLocation().x + (int)size.getWidth()/2, node.getLocation().y + (int)size.getHeight()/2);
		
		return normalizedLocation;
	}


}
