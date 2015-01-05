package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNDesktopController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNNetController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIArcDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.views.ArcView;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.DeleteArcWindow;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.EditNodeWindow;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.NewNodeWindow;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.ResizeDesktopWindow;

/**
 * When we set up the desktop we'll translate our net structure into node views.
 * 1. We will draw our arcs direct from our net model.
 * 2. This is also done by calling {@link PTNDesktop#reset()} and {@link PTNDesktop#init()}
 *    whenever a file has been parsed.
 * 
 * Every node model type (place, transition) will have its own view representation in an own
 * list for the node views. This way we may have to update nodes in the view
 * list <strong>and</strong> in our net model but we separate views and model tier completely
 * The synchronisation of models and views is is done by the {@link PTNNetController}.
 * You find also the logic for adding and removing nodes there.
 * 
 * The computations for moving nodes or drawing arcs will be taken care of by 
 * the {@link PTNDesktopController}
 * 
 * Protocol: If the node and arc list are to be used in another thread (like net or desktop
 * controller) a monitor must be put on those lists.
 * 
 * @author 8388415 - Massimiliano Brero
 *
 */
public class PTNDesktop extends JLayeredPane implements MouseListener {

	private static final long serialVersionUID = 1L;
	private final int DEFAULT_HEIGHT = 400;
	private final int DEFAULT_WIDTH = 600;
	private ArrayList<NodeView> nodes;
	
	/**
	 * The controllers doing the computation works when drawing or
	 * whenever a synchronisation between views and models is due.
	 */
	private PTNNetController netController;
	private PTNDesktopController desktopController;
	private PTNAppController appController;
	/**
	 * The net model is just needed pass it to the instantiated controllers.
	 * The desktop will not communicate directly with the models.
	 */
	private PTNNet net;
	
	// Using a Hashtable instead of an ArrayList like nodes makes it easier to
	// identify arcs by id for our drawing operations.
	private Hashtable<String, ArcView> arcs;

	// Biggest size of desktop so far; we need this to adapt the scroll pane's
	// bars.
	private Dimension maxSize;

	/**
	 * Basic operations will be put in the controller, like initializing
	 * attributes for buffered painting. Adds needed listeners to Controllers
	 * to identify desktop events.
	 * 
	 * @param appController {@link PTNAppController}
	 * @param net PTNNet
	 * 		The net model is just needed pass it to the instantiated controllers.
	 * 		The desktop will not communicate directly with the models.
	 */
	public PTNDesktop(PTNAppController appController, PTNNet net) {

		this.net = net;
		
		/**
		 * initialize controllersand helpers
		 */
		this.appController = appController;
		desktopController = new PTNDesktopController(this, net);
		netController = new PTNNetController(net, this);
		
		/**
		 * setup listeners
		 */
		this.addMouseListener(desktopController);
		this.appController.addSimulationListener(desktopController);
		this.addKeyListener(appController);
		
		/**
		 * Basics
		 */
		this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		this.setFocusable(true);
		this.setOpaque(false);
		this.addMouseListener(this);
		this.setDoubleBuffered(true);
		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		
		/**
		 *  Start controllers as threads.
		 */
		Thread t1 = new Thread(desktopController);
		t1.start();
		Thread t2 = new Thread(netController);
		t2.start();
		
		this.init();

	}

	/**
	 * Set up the desktop and calls the set-up operations on the net controller.
	 * This method will be called when starting the app or whenever a new file 
	 * has been parsed.
	 */
	public void init() {		
		this.reset();
		arcs = new Hashtable<String, ArcView>();
		maxSize =this.getPreferredSize();
		
		synchronized (nodes) {
			netController.setUpNodeViews();			
		}
		synchronized (arcs) {
			arcs = netController.setUpArcs();			
		}
		this.repaint();
	}

	/**
	 * Deletes node and arc view lists and sets size to default values.
	 * This method will be called whenever the desktiop is cleared or 
	 * a pnml file has been parsed.
	 */
	public void reset() {
		nodes = new ArrayList<NodeView>();
		this.removeAll();
	}

	/**
	 * The method setSize is overridden because we want to be sure that all our
	 * nodes fit on the desktop. So we add the biggest current node size to the
	 * size given (+ a little fixed additional space).
	 */
	@Override
	public void setSize(Dimension size) {
		
		int additionalSpace = 10;
		Dimension placeSize = PlaceView.getCurrentSize();
		Dimension transitionSize = TransitionView.getCurrentSize();
		
		double biggestNodeWidth = placeSize.getWidth() > transitionSize.getWidth() ?
										placeSize.getWidth() : transitionSize.getWidth();
		double biggestNodeHeight = placeSize.getHeight() > transitionSize.getHeight() ?
										placeSize.getHeight() : transitionSize.getHeight();
										
		size.setSize(size.getWidth() + biggestNodeWidth + additionalSpace,
					  size.getHeight() + biggestNodeHeight + additionalSpace);
	
		super.setSize((int) size.getWidth(), (int) size.getHeight());
		
	}

	/**
	 * Computes the maximum size this pane had so we can adjust the ScrollPanes'
	 * handles.
	 * This method will also redraw the arcs on the desktop and therefore will
	 * do that in an arc synchronized block.
	 */
	@Override
	public void paintComponent(Graphics g) {

		synchronized (nodes) {
			super.paintComponent(g);
			synchronized (arcs) {
				drawArcs(g);
			}
		}

		if (getSize().width > maxSize.width || getSize().height > maxSize.height) {

			if (getSize().width > maxSize.width)
				maxSize.width = getSize().width;
			if (getSize().height > maxSize.height)
				maxSize.height = getSize().height;

			this.setPreferredSize(maxSize);

		}

	}

	/**
	 * 
	 * We use {@link JComponent#paintImmediately} whenever something has to be drawn faster
	 * than maybe the Swing Event-Queue would allow.
	 * An Example: Drawing an new arc by using mouse dragging.
	 * 
	 * @param bounds Rectangle
	 * 		The area we want to synchronize. We will always repaint the
	 * 		complete desktop dince we do not have too much objects and 
	 * 		those we have are not to complicated to draw.
	 */
	@Override
	public void paintImmediately(Rectangle bounds) {
		synchronized (nodes) {
			synchronized (arcs) {
				super.paintImmediately(bounds);
				drawArcs(this.getGraphics());
			}
		}
	}

	/**
	 * Draws all arcs currently in arcs hashTable. There'a monitor on arcs just
	 * in case another thread (like in desktop controller) is manipulating the
	 * list while this method is called at the same time.
	 * 
	 * @param g Graphics
	 */
	public void drawArcs(Graphics g) {

		Iterator<Map.Entry<String, ArcView>> it = arcs.entrySet().iterator();
		ArcView arcView = null;
		synchronized (arcs) {
			while (it.hasNext()) {
				arcView = (ArcView) it.next().getValue();
				arcView.drawArc(g);
			}
		}

	}

	/**
	 * Draws an arc and adds it to arc view list if it's a new one that was just painted.
	 * by the user.
	 * 
	 * @param id
	 * 		The new arc's id.
	 * @param start Point
	 * @param end Point
	 */
	public void updateArcs(String id, Point start, Point end) {

		synchronized (arcs) {
			if (!arcs.containsKey(id)) {
				ArcView arcView = new ArcView(id, start, end, netController);
				arcs.put(id, arcView);
			} else {
				arcs.get(id).setStart(start);
				arcs.get(id).setEnd(end);
			}
		}

		this.paintImmediately(this.getBounds());
	}

	/**
	 * Overloads {@link PTNDesktop#updateArcs}(String id, Point start, Point end) so
	 * methods can also directly use arc view objects.
	 * 
	 * @param arcView ArcView
	 * 		Methods gets information directly from a freshly created arc view object.
	 */
	public void updateArcs(ArcView arcView) {

		synchronized (arcs) {
			if (!arcs.containsKey(arcView.getId())) {
				arcs.put(arcView.getId(), arcView);
			} else {
				arcs.get(arcView.getId()).setStart(arcView.getStart());
				arcs.get(arcView.getId()).setEnd(arcView.getEnd());
			}			
		}

		this.paintImmediately(this.getBounds());

	}

	/**
	 * If id is "newArc" an arc with a non valid target will be deleted.
	 * "newArc" is a kind of temporary id given by the controller to identify easily
	 * arcs that are not yet in the desktops arc list.
	 * 
	 * @param id String
	 */
	public void removeArc(String id) {

		synchronized (arcs) {
			arcs.remove(id);
			this.repaint();			
		}

	}

	public ArrayList<NodeView> getNodeViews() {
		return this.nodes;
	}

	/**
	 * Getter.
	 * 
	 * @return Hashtable<String, ArcView>  
	 *		 All Arcs currently visible on desktop.
	 */
	public Hashtable<String, ArcView> getArcViews() {
		return this.arcs;
	}

	/**
	 * This method allows us to create new nodes in other classes.
	 * The {@link PTNDesktopController} will be called everytime
	 * a node i dragged or an arc has to be drawn.
	 * 
	 * @param nodeView NodeView
	 */
	public void addListenertoNode(NodeView nodeView) {
		nodeView.addMouseMotionListener(desktopController);
		nodeView.addMouseListener(desktopController);
	}

	/**
	 * Returns whether we have some selected nodes or none. So we may move or
	 * delete them at once.
	 * 
	 * @return boolean
	 */
	public boolean hasSelectedNodes() {
		
		Iterator<NodeView> it_n = getNodeViews().iterator();

		while (it_n.hasNext()) {
			if (it_n.next().getSelected())
				return true;
		}
		

		return false;

	}
	
	/**
	 * Returns whether we have some selected arcs or none. So we may move or
	 * delete them at once.
	 * 
	 * @return boolean
	 */
	public boolean hasSelectedArcs() {
		
		Hashtable<String, ArcView> arcViews = this.getArcViews();
		Iterator<Map.Entry<String, ArcView>> it_a = arcViews.entrySet().iterator();
		
		synchronized (arcs) {
			while (it_a.hasNext()) {
				if (it_a.next().getValue().getSelected())
					return true;
			}			
		}

		return false;

	}

	/**
	 * Removes selection from previously selected nodes.
	 */
	public void deselectNodes() {

		Iterator<NodeView> it = getNodeViews().iterator();
		
		synchronized(nodes) {
			while (it.hasNext()) {
				NodeView node = (NodeView) it.next();
				node.setSelected(false);
			}			
		}

	}
	
	/**
	 * Removes selection from previously selected arcs.
	 */
	public void deselectArcs() {

		Hashtable<String, ArcView> arcViews = this.getArcViews();
		Iterator<Map.Entry<String, ArcView>> it = arcViews.entrySet().iterator();
		
		synchronized (arcs) {
			while (it.hasNext()) {
				ArcView arcView = it.next().getValue();
				arcView.setSelected(false);
			}				
		}
		
		this.repaint();

	}


	/**
	 * Removes selected nodes and the connected arcs. Calls controller method
	 * {@link PTNNetController#removeNodeAndArcs} to erase their 
	 * models too and update the net.
	 */
	public void deleteSelectedNodes() {

		Iterator<NodeView> it = getNodeViews().iterator();
		/**
		 * We store the nodes we want to delete so we don't manipulate the
		 * iterator and remove stuff while it's still going through our list
		 */
		ArrayList<NodeView> nodesToRemove = new ArrayList<NodeView>();

		synchronized (nodes) {
			if (!getNodeViews().isEmpty()) {
				while (it.hasNext()) {
					NodeView node = (NodeView) it.next();
					if (node.getSelected()) {
						nodesToRemove.add(node);
						node.setVisible(false);
					}
				}

				it = nodesToRemove.iterator();
				// now we remove them nodes from our precious list

				while (it.hasNext()) {
					netController.removeNodeAndArcs(it.next());
				}
				this.repaint();
			}
		}
	}

	/**
	 * Removes selected arcs and tells the controller to erase their models too
	 * and update the net.
	 */
	public void deleteSelectedArcs() {

		Iterator<Map.Entry<String, ArcView>> it = arcs.entrySet().iterator();
		HashMap<String, ArcView> arcsToRemove = new HashMap<String, ArcView>();
		ArcView arcView = null;
		
		/** 
		* We store the nodes we want to delete so we don't manipulate the
		* iterator and remove stuff while it's still going through our list
		*/
		synchronized(arcs) {		
			if (!getArcViews().isEmpty()) {
				while (it.hasNext()) {
					arcView = (ArcView) it.next().getValue();
					if (arcView.getSelected()) {
						arcsToRemove.put(arcView.getId(), arcView);

					}
				}
				
				it = arcsToRemove.entrySet().iterator();
				// now we remove them nodes from our precious list
				while (it.hasNext()) {
					netController.removeArcFromNetAndDesktop(it.next().getKey());
				}
				this.repaint();
			}
		}

	}

	/**
	 * Stub for redrawing all incoming and outgoing arcs for a node that has
	 * been moved.
	 * 
	 * @param source {@link NodeView}
	 */
	public void redrawArcs(NodeView source) {
			netController.updateArcsForNode(source);			
	}
	
	/**
	 * This method works as connection between desktopController which handles
	 * the drawing event and netController which does the net building work after
	 * user wants to add a new arc. 
	 * It will then repaint the desktop and update the complete net view. This method 
	 * would then - in a next development stage - show briefly a label that informs 
	 * the user that an arc was successfully added to the net.
	 * 
	 * @param source {@link NodeView}
	 * @param target {@link NodeView}
	 */
	public void addNewArc(NodeView source, NodeView target) {

		netController.addNewArc(source, target);
		this.paintImmediately(this.getBounds());

	}

	/**
	 * Each dialog has its own corresponding window. Since this windows shall be
	 * modal we will call them from our desktop instead of simply calling them
	 * from the controller.
	 * 
	 * @param source {@link NodeView}
	 * 		Node whose attributes have to be changed.
	 */
	public void callNodeAttributeDialog(NodeView source) {

		EditNodeWindow popUp = new EditNodeWindow(source);
		popUp.setModal(true);
		popUp.setVisible(true);

		PTNINodeDTO nodeUpdate = popUp.sendUpdatedNode();
		
		if (null != nodeUpdate) {
			netController.updateNodeAttributes(source, nodeUpdate);		
			// so our arcs won't be obscured
			this.paintImmediately(this.getBounds());
		}
	}

	/**
	 * Dialog window when a new node shall be added.
	 * 
	 * @param nodeLocation
	 */
	public void callNewNodeDialog(Point nodeLocation) {

		NewNodeWindow popUp = new NewNodeWindow(nodeLocation, this);
		popUp.setModal(true);
		popUp.setVisible(true);

		PTNINodeDTO nodeParams = popUp.sendParams();

		/**
		 * We just allow a unique id per node regardless if it' a place or a
		 * transition
		 */
		if (null != nodeParams) {

			netController.addNewNodeFromDialog(nodeParams);
			this.repaint();

		}

	}

	/**
	 * Dialog window requesting the width an height of the enlarged desktop.
	 */
	public void callResizeDesktopDialog() {

		ResizeDesktopWindow popUp = new ResizeDesktopWindow(this.getPreferredSize());
		popUp.setModal(true);
		popUp.setVisible(true);

		Dimension newSize = popUp.sendSize();

		/**
		 * We just allow a unique id per node regardless if it' a place or a
		 * transition
		 */
		if (null != newSize) {

			this.setPreferredSize(newSize);
			this.revalidate();
		}

	}

	/**
	 * Stars Dialog for deleting Arcs. Since we want to delete them both
	 * from our view and from the net model, we will pass them to the controller
	 * and let him do the job.
	 * 
	 * @param sourceView NodeView
	 */
	public void callDeleteArcsDialog(NodeView sourceView) {

		DeleteArcWindow popUp = new DeleteArcWindow(net, sourceView);
		popUp.setModal(true);
		popUp.setVisible(true);

		HashMap<String, PTNIArcDTO> arcsToDelete = popUp.sendArcsToDelete();

		synchronized (arcs) {
			
			if (0 < arcsToDelete.size())
				if (0 == (JOptionPane.showConfirmDialog(this, "Wollen Sie die Kanten wirklich löschen?", 
						"Löschen", JOptionPane.WARNING_MESSAGE)))
					netController.removeArcsFromNetAndDesktop(arcsToDelete);
		}

	}

	/**
	 * Getter
	 * 
	 * @param id
	 * 		The id of the node this method shall look up.
	 * @return {@link NodeView}
	 * 		Corresponding Node if an node with that id exists
	 * 		in the desktop's node list.
	 * 		Null if it does not (Even though NULL may generally not be
	 * 		a too nice return value...)
	 */
	public NodeView getNodeViewById(String id) {

		Iterator<NodeView> it = this.getNodeViews().iterator();

		while (it.hasNext()) {
			NodeView nodeView = it.next();

			if (nodeView.getId().equals(id))
				return nodeView;

		}

		return null;

	}

	/**
	 * Event registered when user wants to create a new node by
	 * double clicking on desktop.
	 * 
	 * @param e {@link MouseEvent}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			e = SwingUtilities.convertMouseEvent(e.getComponent(), e, this);
			Point mouseLocation = e.getPoint();
			//mouseLocation = new Point(mouseLocation.x, mouseLocation.y);
			this.callNewNodeDialog(mouseLocation);
		}
		
	}

	/**
	 * Not implemented.
	 */
	@Override
	public void mousePressed(MouseEvent e) {}

	/**
	 * Not implemented.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {}

	/**
	 * Not implemented.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 * Not implemented.
	 */
	@Override
	public void mouseExited(MouseEvent e) {}

}
