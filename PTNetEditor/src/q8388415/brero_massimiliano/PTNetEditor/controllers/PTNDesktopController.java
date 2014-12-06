package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNSimulationException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNSimulationInterpreter;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIModeListener;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNNodeHelper;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

/**
 * This controller handles basic actions occurring in the desktop like mouse
 * dragging. It's also a thread since it will listen when it's time to delete
 * nodes.
 * 
 * @author 8388415
 *
 */
public class PTNDesktopController implements MouseMotionListener, MouseListener, Runnable, PTNIModeListener {

	private PTNDesktop desktop;
	private PTNNodeHelper nodeHelper;
	private volatile Point currentDraggingPosistion;
	static boolean isDragged = false;
	private Boolean isInSimulationMode = false;  
	PTNSimulationInterpreter simInterpreter;

	public PTNDesktopController(PTNDesktop dt, PTNNet net) {

		this.desktop = dt;
		nodeHelper = new PTNNodeHelper(desktop, net);
		simInterpreter = new PTNSimulationInterpreter(desktop, net);
		/**
		 * Position of mouse when dragging.
		 */
		currentDraggingPosistion = new Point(-1, -1);

	}

	/**
	 * Handles when arcs are drawn or nodes shall be moved by dragging the mouse after selecting them.
	 * Does nothing in simulation mode.
	 * 
	 * @param e
	 *            MouseEvent
	 */
	@Override
	public void mouseDragged(MouseEvent e) {

		JComponent source = (JComponent) e.getComponent();
		e.translatePoint(source.getX(), source.getY());

		if (!isInSimulationMode) {
			
			// here we are drawing an arc
			if (!PTNAppController.moveNodes) {
				
				Point start = new Point(source.getLocation().x + source.getWidth() / 2, source.getLocation().y + source.getHeight() / 2);
				Point end = new Point(e.getX(), e.getY());
				desktop.updateArcs("", start, end);
				
			} else {
				
				if (!isDragged) {
					isDragged = true;
				} else {
					
					// now somebody drags...!
					int diffX = e.getX() - (int) currentDraggingPosistion.getX();
					int diffY = e.getY() - (int) currentDraggingPosistion.getY();
					
					if (desktop.hasSelected()) { // here we many have to move more
						// than one element
						
						ArrayList<NodeView> nodes = desktop.getNodeViews();
						Iterator<NodeView> it = nodes.iterator();
						
						while (it.hasNext()) {
							NodeView node = (NodeView) it.next();
							if (node.isSelected())
								moveNode(diffX, diffY, node);
						}
					} else { // ok it's just one node that is dragged
						moveNode(diffX, diffY, (NodeView) source);
					}
					
				}
				
			}
			
			currentDraggingPosistion = e.getPoint();
		}

	}

	/**
	 * Resets location when moving a node for the moved node and all arcs
	 * attached to it. We do not allow to move the node over the top and left
	 * boundaries, so no negative coordinates are allowed;
	 * 
	 * @param diffX
	 * @param diffY
	 * @param nodeView
	 */
	private void moveNode(int diffX, int diffY, NodeView nodeView) {
		int moveToX = nodeView.getX();
		int moveToY = nodeView.getY();

		if (nodeView.getLocation().x < 0)
			moveToX = 0;
		else
			moveToX = moveToX + diffX;

		if (nodeView.getLocation().y < 0)
			moveToY = 0;
		else
			moveToY = moveToY + diffY;

		nodeView.setLocation(moveToX, moveToY);
		nodeHelper.updateNodeModelLocation(nodeView);
		desktop.redrawArcs((NodeView) nodeView);

	}

	@Override
	/**
	 * Context menu wanted ? -> Right Click
	 * Node selected? -> Left Click
	 */
	public void mouseClicked(MouseEvent e) {
		
		NodeView source = null;
		if (e.getComponent() instanceof NodeView)
			source = (NodeView)e.getComponent();

		if (!isInSimulationMode) {			
			if (source instanceof JLabel && 3 == e.getButton()) // context menu
				nodeHelper.handleContextmenu(source);
			else if (PTNAppController.selectMode) // select/deselect element
				source.setSelected(!source.isSelected());
		} else {
			if (source.getType() == PTNNodeTypes.TRANSITION && ((TransitionView)source).isActivated()) {
				try {
					simInterpreter.handleClick((TransitionView) source);
				} catch (PTNSimulationException e2) {
					JOptionPane.showConfirmDialog(desktop, e2.getMessage(), "Simulationsstop", JOptionPane.WARNING_MESSAGE);
				}
			}
		}

	}

	/**
	 * 
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

		NodeView source = null;
		Point mouseLocation = currentDraggingPosistion;
		mouseLocation = new Point(currentDraggingPosistion.x, currentDraggingPosistion.y);
		JComponent target = this.getComponentAtMouseLocation(mouseLocation);

		if (e.getComponent() instanceof NodeView)
			source = (NodeView) e.getComponent();


		boolean isAllowedTarget = (source instanceof PlaceView && target instanceof TransitionView) 
										|| (source instanceof TransitionView && target instanceof PlaceView);
		

		if (PTNAppController.moveNodes 
				&& isDragged
					&& !isInSimulationMode) {
			// Dragging is over so reset moving variables.
			isDragged = false;
			currentDraggingPosistion.setLocation(-1, -1);
		} else if (isAllowedTarget 
						&& !isInSimulationMode
							&& e.getButton() == MouseEvent.BUTTON1) {
			// We can cast safely to node view since we now know that we have a
			// NodeView type under the mouse pointer.
			this.drawTempEdge(source, target);
			desktop.addNewArc(source, (NodeView) target);
			PTNAppController.moveNodes = false;
		}
		// delete all temporary or id-less arcs that may have be lingering on
		// the desktop.
		desktop.removeArc("");
		desktop.requestFocus();

	}

	/**
	 * This method is used instead of Container#getComponentAt(Point p) because
	 * that one will not always work correctly with lightweight components.
	 * 
	 * @param mouseLocation
	 * @return JComponent A node or the desktop.
	 */
	private JComponent getComponentAtMouseLocation(Point mouseLocation) {

		for (Component component : desktop.getComponents()) {
			if (component.getBounds().contains(mouseLocation))
				return (JComponent) component;
		}

		return null;
	}

	// Draws an arc that is displayed until user inputs an correct id.
	private void drawTempEdge(JComponent source, JComponent target) {
		Point start = new Point(source.getLocation().x + source.getWidth() / 2, source.getLocation().y + source.getHeight() / 2);
		Point end = new Point(target.getLocation().x + target.getWidth() / 2, target.getLocation().y + target.getHeight() / 2);
		desktop.updateArcs("", start, end);
	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// continue waiting even if interrupted
			}

			/**
			 * select and deselect nodes
			 */
			if (PTNAppController.deselectAll) {
				desktop.deselectNodes();
				PTNAppController.deselectAll = false;
			} else if (PTNAppController.deleteSelection) {
				PTNAppController.deleteSelection = false;
				if (JOptionPane.OK_OPTION == (JOptionPane.showConfirmDialog(desktop, "Wollen Sie die Knoten wirklich löschen?", "Löschen", JOptionPane.WARNING_MESSAGE)))
					desktop.deleteSelected();
			}

		}

	}

	@Override
	public void startSimulationMode() {
		isInSimulationMode = true;
	}

	@Override
	public void startEditorMode() {
		isInSimulationMode = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
