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
	
	public PTNArcHelper(PTNDesktop desktop, PTNNet net) {
		this.desktop = desktop;
		this.net = net;
		controlPanel = PTNControlPanel.getInstance();
	}
	
	/**
	 * Sets start and end points right in the middle of the source and target node.
	 * If you want to change that, this is the place to change.
	 * 
	 * @param location
	 * @return Dimension now in the center of our NodeView
	 */
	public Point normalizeLocation(PTNNode node) {
		
		PTNNodeTypes type = node.getType();
		Dimension size = null;
		Point normalizedLocation = null;
		
		if (type == PTNNodeTypes.place)
			size = PlaceView.getCurrentSize();
		else if (type == PTNNodeTypes.transition)
			size = TransitionView.getCurrentSize();
		
		normalizedLocation = new Point(node.getLocation().x + (int)size.getWidth()/2, 
		                            node.getLocation().y + (int)size.getHeight()/2);
		
		return normalizedLocation;
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
	
	public int showErrorPaneIdExists() {
		return JOptionPane.showConfirmDialog(desktop, "Diese ID ist bereits vergeben.", 
				"Ung�ltige ID", JOptionPane.WARNING_MESSAGE);
	}
	
	public int showErrorPaneEmptyId() {
		return JOptionPane.showConfirmDialog(desktop, "Sie m�ssen eine ID mit mind. einem Zeichen eingeben.", 
				"Ung�ltige ID", JOptionPane.WARNING_MESSAGE);
	}

	public void showErrorPaneDoubleArc() {
		JOptionPane.showConfirmDialog(desktop, "Diese Kante existiert bereits oder sie �berlappt sich exakt mit einer existierenden!", 
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

		if (targetModel.getType() == PTNNodeTypes.transition) {

			((TransitionView)targetView).setIsActivated(((PTNTransition)targetModel).isActivated());

		}

		this.addArcListener(arcView);
		desktop.updateArcs(arcView);
	}


}
