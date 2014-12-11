package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNInitializationException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
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
 * Offers methods for dealing with arc computations, like normalizing their
 * start and end point.
 * 
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
	 * Sets start and end points right in the middle of the source and target
	 * node. The line then gets an offset, so starting and end point will be
	 * right from or at the boundary of the node#s icon.
	 * 
	 * If you want to change that, this is the place to change.
	 * 
	 * @param arc
	 *            Type: {@link PTNArc}. ArcModel that holds informations about
	 *            the arc like source and target
	 * @param normalizeSource
	 *            {@link Boolean} Whether the start or ending point of the arc
	 *            is needed. This we we may only normalize only starting or only
	 *            ending point.
	 * @return {@link Point} Calculated Position including offset.
	 */
	public Point normalizeLocation(PTNArc arc, Boolean normalizeSource) {

		PTNNode node = normalizeSource ? arc.getSource() : arc.getTarget();
		Point normalizedLocation = null;

		if (node != null) {
			PTNNodeTypes type = node.getType();
			Dimension size = null;

			if (type == PTNNodeTypes.STELLE)
				size = PlaceView.getCurrentSize();
			else if (type == PTNNodeTypes.TRANSITION)
				size = TransitionView.getCurrentSize();

			// location right in the middle of the node
			//Point centeredLocation = new Point(node.getLocation().x + (int) size.getWidth() / 2, node.getLocation().y + (int) size.getHeight() / 2);

			normalizedLocation = this.addOffset(arc, normalizeSource);
		}

		return normalizedLocation;
	}

	/**
	 * Checks if node is source or target an delegates to the respective method.
	 * 
	 * @param centeredLocation
	 *            Type: Point. Point right in the middle of the node icon.
	 * @param source
	 *            Type: PTNINodeDTO. Information about the node.
	 * @param normalizeSource
	 *            Type Boolean. Is normalization needed for starting or ending
	 *            point of arc?
	 * @return type: Point. New starting and ending points with the calculated
	 *         offset.
	 */
	private Point addOffset(PTNArc arc, Boolean normalizeSource) {

		Point normalizedLocation = null;
		PTNNode node = normalizeSource ? arc.getSource() : arc.getTarget();

		if (null != node) {
			if (PTNNodeTypes.STELLE == node.getType())
				normalizedLocation = this.addOffsetToPlace(arc, normalizeSource);
			else
				normalizedLocation = this.addOffSetToTransition(arc, normalizeSource);
		}

		return normalizedLocation;
	}

	/**
	 * 
	 * Computes the offset for an arc starting or ending point if source is a
	 * place. Depending on the direction the arc is facing we have a given x
	 * offset (icon width divided by 2) or a given y offset (icon height divided
	 * by 2). With a little trigonometric magic and the arc's slope the missing
	 * offset can be calculated.
	 * 
	 * 
	 * @param centeredLocation
	 *            Type: Point. Point right in the middle of the node icon.
	 * @param nodeView
	 * @param isSource
	 *            Type Boolean. Is node starting or ending point of arc?
	 * @return type: Point. New starting and ending points with the calculated
	 *         offset.
	 */
	private Point addOffSetToTransition(PTNArc arc, Boolean normalizeSource) {

		PTNNode source = arc.getSource();
		PTNNode target = arc.getTarget();
		Point centeredLocation = normalizeSource ? this.getCenteredLocation(source) : this.getCenteredLocation(target);
		Point normalizedLocation = centeredLocation;

		if (source != null && target != null) {

			double gradient = this.getGradient(source, target);
			/**
			 * adjust gradient for easier checking to span 0° - 360°
			 */
			double adjustedGradient = (gradient < 0.0) ? 360 + gradient : gradient;
			Boolean pointsRight = (adjustedGradient >= 135 && adjustedGradient < 225) ? true : false;
			Boolean pointsUp = (adjustedGradient >= 45 && adjustedGradient < 135) ? true : false;
			Boolean pointsLeft = (adjustedGradient >= 315 || adjustedGradient < 45) ? true : false;
			Boolean pointsDown = (adjustedGradient >= 225 && adjustedGradient < 315) ? true : false;
			int offsetX = 0;
			int offsetY = 0;
			NodeView nodeView = normalizeSource ? desktop.getNodeViewById(source.getId()) : desktop.getNodeViewById(target.getId());

			/**
			 * Okay, we could boil this down to two situations, but this way the code
			 * is easier to read and/or to change.
			 */
			if (nodeView != null) {
				int givenX = (int) (nodeView.getIcon().getIconWidth() / 2);
				int givenY = (int) (nodeView.getIcon().getIconHeight() / 2);
				double tanGradient =  Math.tan(Math.toRadians(gradient));
				
				if (pointsRight) {
					offsetX = givenX;
					offsetY = (int)(offsetX * tanGradient);
				} else if (pointsUp) {
					offsetY = -givenY;
					offsetX = (int)(offsetY / tanGradient);
				} else if (pointsLeft) {
					offsetX = -givenX;
					offsetY = (int)(offsetX * tanGradient);
				} else if (pointsDown) {
					offsetY = givenY;
					offsetX = (int)(offsetY / tanGradient);
				}	
				
				System.out.println("offsetX" + offsetX);
				System.out.println("offsetY" + offsetY);
				System.out.println("givenX" + givenX);
				System.out.println("givenY" + givenY);
				System.out.println(adjustedGradient);
				System.out.println();
				offsetX = normalizeSource ? -offsetX : offsetX;
				offsetY = normalizeSource ? -offsetY : offsetY;
				
				/**
				 * Since we do not always get 100 percent correct values when using Math.atan2(),
				 * we will insert this hack and correct the x offset and change the y offset by that
				 * correction ratio.
				 */
				offsetY = Math.abs(offsetX) > givenX ? offsetY + (int)(offsetY / 2) : offsetY;
				offsetX = Math.abs(offsetX) > givenX ? givenX * Integer.signum(offsetX) : offsetX;
				
				normalizedLocation = new Point(centeredLocation.x + offsetX, centeredLocation.y + offsetY);
			}
		}
		return normalizedLocation;
	}

	/**
	 * Computes the offset for an arc starting or ending point if source is a
	 * place. Since the radius is given by the icon size, trigonometric
	 * functions can be used after calculation the arcs gradient. Thus is
	 * important to <strong>crop the circle in the icon source cleanly</strong>
	 * or else we may get a little space between the arcs starting and ending
	 * point and the icon image itself.
	 * 
	 * @param centeredLocation
	 *            Type: Point. Point right in the middle of the node icon.
	 * @param nodeView
	 * @param normalizeSource
	 *            Type Boolean. Is node starting or ending point of arc?
	 * @return type: Point. New starting and ending points with the calculated
	 *         offset.
	 */
	private Point addOffsetToPlace(PTNArc arc, Boolean normalizeSource) {

		PTNNode source = arc.getSource();
		PTNNode target = arc.getTarget();
		Point centeredLocation = normalizeSource ? this.getCenteredLocation(source) : this.getCenteredLocation(target);
		Point normalizedLocation = centeredLocation;

		if (source != null && target != null) {

			double gradient = this.getGradient(source, target);
			NodeView nodeView = normalizeSource ? desktop.getNodeViewById(source.getId()) : desktop.getNodeViewById(target.getId());

			if (nodeView != null) {
				int radius = (int) (nodeView.getIcon().getIconWidth() / 2);
				int offsetX = (int) (radius * (Math.cos(Math.toRadians(gradient))));
				int offsetY = (int) (radius * (Math.sin(Math.toRadians(gradient))));

				if (!normalizeSource) {
					offsetX = -offsetX;
					offsetY = -offsetY;
				}
				normalizedLocation = new Point(centeredLocation.x + offsetX, centeredLocation.y + offsetY);
			}

		}
		return normalizedLocation;
	}

	/**
	 * Computes and returns the gradient between two nodes.
	 * 
	 * @param source
	 *            {@link PTNNode}
	 * @param target
	 *            {@link PTNNode}
	 * @return {@link Double} Gradient between starting and ending point.
	 */
	private double getGradient(PTNNode source, PTNNode target) {
		Point centeredLocationSource = this.getCenteredLocation(source);
		Point centeredLocationTarget = this.getCenteredLocation(target);
		return Math.toDegrees(Math.atan2(centeredLocationTarget.getLocation().y - centeredLocationSource.getLocation().y, 
											centeredLocationTarget.getLocation().x - centeredLocationSource.getLocation().x));
	}
	
	/**
	 * Calculates  location right in the middle of the node.
	 * 
	 * @param node
	 * 		{@link PTNINodeDTO}
	 * @return
	 * 		{@link Point} center of Node.
	 */
	public Point getCenteredLocation(PTNINodeDTO node) {
		
		Dimension size = null;
		
		if (node.getType() == PTNNodeTypes.STELLE)
			size = PlaceView.getCurrentSize();
		else if (node.getType() == PTNNodeTypes.TRANSITION)
			size = TransitionView.getCurrentSize();

		return new Point(node.getLocation().x + (int) size.getWidth() / 2, node.getLocation().y + (int) size.getHeight() / 2);

		
	}

	/**
	 * 
	 * @param arcView
	 *            Adds an arc as scales listener.
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
			if (arcView.getStart().equals(start) && arcView.getEnd().equals(end) && !arcView.getId().isEmpty()) {
				return true;
			}
		}

		return false;

	}

	public void showErrorPaneDoubleArc() {
		JOptionPane.showConfirmDialog(desktop, "Diese Kante existiert bereits oder sie überlappt sich exakt mit einer existierenden!", "Schon vorhanden.",
				JOptionPane.PLAIN_MESSAGE);

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
			((TransitionView) targetView).setIsActivated(((PTNTransition) targetModel).isActivated());
		}

		this.addArcListener(arcView);
		desktop.updateArcs(arcView);
	}

	/**
	 * Generates a unique id concatenating id prefix and number of arcs added so
	 * far +1.
	 * 
	 * @return String new id for an arc
	 */
	public String generateId() {
		return PREFIX_ID.concat(String.valueOf(net.getNumberOfArcs() + 1));
	}

}
