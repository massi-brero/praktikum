package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNInitializationException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.views.ArcView;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

/**
 * Offers methods for dealing with arc computations, like normalizing their start and end point.
 * @author Laptop
 *
 */
public class PTNArcHelper {
	
	private PTNControlPanel controlPanel;
	private PTNDesktop desktop;
	private PTNNet net;
	final private String PREFIX_ID = "PTNArc_";
	
	public PTNArcHelper(PTNDesktop desktop, PTNNet net) {
		this.desktop = desktop;
		this.net = net;
		controlPanel = PTNControlPanel.getInstance();
	}
	
	/**
	 * Sets start and end points right in the middle of the source and target node.
	 * The line then gets an offset, so starting and end point will be right from or
	 * at the boundary of the node#s icon.
	 * 
	 * If you want to change that, this is the place to change.
	 * 
	 * @param node
	 * 		Type: {@link PTNINodeDTO}. Information of node from which or to whom the arc 
	 * 		has to be drawn.
	 * @param isSource
	 * 		Type Boolean. Whether the node is start or ending point of the arc.
	 * @return
	 * 	Type: Point. Calculated Position with offset.
	 */
	public Point normalizeLocation(PTNINodeDTO node, Boolean isSource) {
		
		PTNNodeTypes type = node.getType();
		Dimension size = null;
		Point normalizedLocation = null;
		
		if (type == PTNNodeTypes.STELLE)
			size = PlaceView.getCurrentSize();
		else if (type == PTNNodeTypes.TRANSITION)
			size = TransitionView.getCurrentSize();
		
		// location right in the middle of the node
		Point centeredLocation = new Point(node.getLocation().x + (int)size.getWidth()/2, 
		                            		node.getLocation().y + (int)size.getHeight()/2);
		
		normalizedLocation = this.addOffset(centeredLocation, node, isSource);
		
		return normalizedLocation;
	}
	
	/**
	 * Checks if node is source or target an delegates to the respective method.
	 * 
	 * @param centeredLocation
	 * 		Type: Point. Point right in the middle of the node icon.
	 * @param source
	 * 		Type: PTNINodeDTO. Information about the node.
	 * @param isSource
	 * 		Type Boolean. Is node starting or ending point of arc?
	 * @return
	 */
	private Point addOffset(Point centeredLocation, PTNINodeDTO source, Boolean isSource) {
		
		Point normalizedLocation = null;
		
		if (PTNNodeTypes.STELLE == source.getType())
			normalizedLocation = this.addOffSetToPlace(centeredLocation, isSource);
		else 
			normalizedLocation = this.addOffSetToTransition(centeredLocation, isSource);
		
		return normalizedLocation;
	}

	private Point addOffSetToTransition(Point centeredLocation, Boolean isSource) {
		// TODO Auto-generated method stub
		return centeredLocation;
	}

	/**
	 * Computes 
	 * 
	 * @param centeredLocation
	 * @param isSource
	 * @return
	 */
	private Point addOffSetToPlace(Point centeredLocation, Boolean isSource) {
		// TODO Auto-generated method stub
		return centeredLocation;
	}

	/**
	 * 
	 * @param arcView
	 */
	public void addArcListener(ArcView arcView) {
		
		try {
			controlPanel.addArcScaleListener(arcView);
		} catch (PTNInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Check if there's already such an arc with identical start and end points.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Boolean isAlreadyOnDesktop(Point start, Point end) {
		Hashtable<String, ArcView> arcViews = desktop.getArcViews();
		Iterator<Map.Entry<String, ArcView>> it = arcViews.entrySet().iterator();
		
		while (it.hasNext()) {
			ArcView arcView = it.next().getValue();
			
			// we do not compare with temporary arcs
			if(arcView.getStart().equals(start) 
					&& arcView.getEnd().equals(end)
						&& !arcView.getId().isEmpty()) {
				return true;
			}
		}	
		
		return false;
		
	}
	
	public void showErrorPaneDoubleArc() {
		JOptionPane.showConfirmDialog(desktop, "Diese Kante existiert bereits oder sie überlappt sich exakt mit einer existierenden!", 
				"Schon vorhanden.", JOptionPane.PLAIN_MESSAGE);
		
	}

    /**
	 * Prepares arc to be shown in view. This method will also activate the
	 * target node view if the corresponding model node is activated.
	 * 
	 * @param id
	 * @param normalizedSourceLocation
	 * @param normalizedTargetLocation
	 * @param netControl
	 */
	public void initArcView(ArcView arcView, PTNNode targetModel, NodeView targetView) {

		if (targetModel.getType() == PTNNodeTypes.TRANSITION) {

			((TransitionView)targetView).setIsActivated(((PTNTransition)targetModel).isActivated());

		}

		this.addArcListener(arcView);
		desktop.updateArcs(arcView);
	}

	/**
	 * Generates a unique id concatenating id prefix and number of arcs 
	 * added so far +1.
	 * 
	 * @return
	 * 		String new id for an arc
	 */
	public String generateId() {
		return PREFIX_ID.concat(String.valueOf(net.getNumberOfArcs()+1));
	}


}
