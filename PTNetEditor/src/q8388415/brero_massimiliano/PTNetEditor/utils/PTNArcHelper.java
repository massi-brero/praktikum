package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.Dimension;
import java.awt.Point;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNInitializationException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.views.ArcView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;

/**
 * Offers methods for dealing with arc computations, like normalizing their start and end point.
 * @author Laptop
 *
 */
public class PTNArcHelper {
	
	private PTNControlPanel controlPanel;
	
	public PTNArcHelper() {
		controlPanel = PTNControlPanel.getInstance();
	}
	
	/**
	 * 
	 * @param location
	 * @return Dimension now in the center of our NodeView
	 */
	public Point normalizeLocation(PTNNode node) {
		
		PTNNodeTypes type = node.getType();
		Dimension size = null;
		Point normalizedLocation = null;
		
		if (type == PTNNodeTypes.place)
			size = PlaceView.currentSize;
		else if (type == PTNNodeTypes.transition)
			size = TransitionView.currentSize;
		
		normalizedLocation = new Point(node.getLocation().x + (int)size.getWidth()/2, node.getLocation().y + (int)size.getHeight()/2);
		
		return normalizedLocation;
	}
	
	public void addArcListener(ArcView arcView) {
		
		try {
			controlPanel.addArcScaleListener(arcView);
		} catch (PTNInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
