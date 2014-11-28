package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.utils.PTNNodeHelper;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PTNMenu;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

/**
 * This controller handles basic actions occuring in the desktop like mous dragging.
 * It's also a thread since it will listen when it's time to delete nodes.
 * 
 * @author 8388415
 *
 */
public class PTNDesktopController implements MouseMotionListener, MouseListener, ActionListener, Runnable {

	private PTNDesktop desktop;
	private PTNNodeHelper nodeHelper;
	private volatile Point oldLocation;
	static boolean isDragged = false;

	public PTNDesktopController(PTNDesktop dt) {

		this.desktop = dt;
		nodeHelper = new PTNNodeHelper(desktop);
		oldLocation = new Point(-1, -1);

	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {

		JComponent source = (JComponent) e.getComponent();
		e.translatePoint(source.getX(), source.getY());

		//here we are drawing an arc
		if (PTNAppController.isDrawing) {

			Point start = new Point(source.getLocation().x + source.getWidth()/2, source.getLocation().y + source.getHeight()/2);
			Point end = new Point(e.getX(), e.getY());		
			desktop.updateArcs("", start, end);
			
		} else {
			
			if (!isDragged) {
				isDragged = true;
			} else {

				int diffX = e.getX() - (int) oldLocation.getX();
				int diffY = e.getY() - (int) oldLocation.getY();
				
				if (desktop.hasSelected()) {
					
					ArrayList<NodeView> nodes = desktop.getNodeViews();
					Iterator<NodeView> it = nodes.iterator();
					
					while (it.hasNext()) {
						NodeView node = (NodeView) it.next();
						if(node.isSelected()) {
							node.setLocation(node.getX() + diffX, node.getY() + diffY);		
							desktop.redrawArcs((NodeView)node);
						}
					}
					
				} else {
					source.setLocation(source.getX() + diffX, source.getY() + diffY);
					desktop.redrawArcs((NodeView)source);
				}
				
			}
			
		}

		oldLocation = e.getPoint();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * Context menu wanted ? -> Right Click
	 * Node selected? -> Left Click
	 */
	public void mouseClicked(MouseEvent e) {
		
		NodeView source = (NodeView) e.getComponent();
		
		if (source instanceof JLabel && 3 == e.getButton()) {

			nodeHelper.handleContextmenu(source);
			
		} else if (PTNAppController.selectMode) {
			
			source.setSelected(!source.isSelected());
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	/**
	 * We got to correct our mouse location to take our menu bar into account.
	 */
	public void mouseReleased(MouseEvent e) {
		
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		mouseLocation = new Point(mouseLocation.x, mouseLocation.y - 2*PTNMenu.HEIGHT);
		NodeView source = (NodeView) e.getComponent();
		JComponent target = (JComponent) desktop.findComponentAt(mouseLocation);

		boolean isAllowedTarget = (source instanceof PlaceView && target instanceof TransitionView) || 
									(source instanceof TransitionView && target instanceof PlaceView);
		
		if (isDragged) {
			//Dragging is over so reset moving variables.
			isDragged = false;
			oldLocation.setLocation(-1, -1);
		} else if (PTNAppController.isDrawing && isAllowedTarget) {
			//We can cast safely to node view since we now know that we have a NodeView type under the mouse pointer.
			this.drawTempEdge(source, target);
			desktop.callNewArcDialog(source, (NodeView)target);
			PTNAppController.isDrawing = false;
			
		}
			
		//delete all temporary arcs that may have be lingering on the desktop.
		desktop.removeArc("");
	
		desktop.requestFocus();

	}

	// Draws an arc that is displayed until user inputs an correct id.
	private void drawTempEdge(JComponent source, JComponent target) {
		
		Point start = new Point(source.getLocation().x + source.getWidth()/2, source.getLocation().y + source.getHeight()/2);
		Point end = new Point(target.getLocation().x + target.getWidth()/2, target.getLocation().y + target.getHeight()/2);
		desktop.updateArcs("", start, end);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("action");
		
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
				if (JOptionPane.OK_OPTION == (JOptionPane.showConfirmDialog(desktop, "Wollen Sie die Knoten wirklich löschen?", "Löschen", JOptionPane.WARNING_MESSAGE)))
					desktop.deleteSelected();
				PTNAppController.deleteSelection = false;
			}
			
		}
		
	}

}
