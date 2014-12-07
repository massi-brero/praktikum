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

import javax.security.auth.login.AppConfigurationEntry;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNDesktopController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNNetController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIModeListener;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNNodeHelper;
import q8388415.brero_massimiliano.PTNetEditor.views.ArcView;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.DeleteArcWindow;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.EditNodeWindow;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.NewNodeWindow;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.ResizeDesktopWindow;

/**
 * When we set up the desktop we'll translate our net structure into node views.
 * 1. We will draw our arcs direct from our net arcs.
 * 
 * 2. But every node model type will have its own view representation in an own
 * list for our node views. This way we may have to update nodes in the view
 * list and in our net model, but we'll gain on performance since we don't have
 * to iterate the complete net and re-add all buttons when some update action on
 * the nodes is due.
 * 
 * @author q8388415
 *
 */
public class PTNDesktop extends JLayeredPane implements PTNIModeListener, MouseListener {

	private final int DEFAULT_HEIGHT = 300;
	private final int DEFAULT_WIDTH = 600;
	private ArrayList<NodeView> nodes;
	private PTNNetController netController;
	private PTNDesktopController desktopController;
	private PTNAppController appController;
	private PTNNet net;
	private PTNNodeHelper nodeHelper;
	// Using a Hashtable instead of an ArrayList like nodes makes it easier to
	// identify arcs by id for our drawing operations.
	private Hashtable<String, ArcView> arcs;

	// biggest size of desktop so far; we need this to adapt the scroll pane's
	// bars
	private Dimension maxSize;

	/**
	 * Basic operations will be put in the controller, like initializing
	 * attributes for buffered painting;
	 * 
	 * @param appControl
	 * @param net
	 */
	public PTNDesktop(PTNAppController appController, PTNNet net) {

		this.net = net;
		this.appController = appController;
		setFocusable(true);
		this.setOpaque(false);
		addKeyListener(appController);
		addMouseListener(this);
		setDoubleBuffered(true);
		this.setLayout(null);
		this.init();

	}

	/**
	 * Set up the desktop and calls the set-up operations on the net controller
	 */
	public void init() {
		
		this.reset();
		maxSize = getSize();
		/**
		 * initialize controllers and helpers
		 */
		this.netController = new PTNNetController(net, this);
		netController.setUpNodeViews();
		arcs = netController.setUpArcs();
		desktopController = new PTNDesktopController(this, net);
		appController.addSimulationListener(desktopController);
		nodeHelper = new PTNNodeHelper(this, net);
		
		/**
		 * basic panel work
		 */
		this.setBackground(Color.WHITE);
		
		/**
		 *  Start controllers as threads.
		 */
		Thread t1 = new Thread(desktopController);
		t1.start();
		Thread t2 = new Thread(netController);
		t2.start();
		this.repaint();

	}

	/**
	 * deletes node and arc view lists and sets size to default values.
	 * 
	 * @return void
	 */
	public void reset() {
		nodes = new ArrayList<NodeView>();
		arcs = new Hashtable<String, ArcView>();

		/**
		 * Default size is not set if we have another preference set elsewhere
		 */
		if (this.getPreferredSize().getWidth() == 0 && this.getPreferredSize().getHeight() == 0)
			this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		this.removeAll();
		this.repaint();
	}

	/**
	 * The method setSize is overridden because we want to be sure that all our
	 * nodes fit on the desktop. So we add the biggest current node size to the
	 * size given
	 */
	@Override
	public void setSize(Dimension size) {
		this.setSize((int) size.getWidth(), (int) size.getHeight());
	}

	/**
	 * Computes the maximum size this pane had so we can adjust the ScrollPanes'
	 * handles.
	 * 
	 * @return void
	 */
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		drawArcs(g);

		if (getSize().width > maxSize.width || getSize().height > maxSize.height) {

			if (getSize().width > maxSize.width)
				maxSize.width = getSize().width;
			if (getSize().height > maxSize.height)
				maxSize.height = getSize().height;

			this.setPreferredSize(maxSize);

		}

	}

	@Override
	public void paintImmediately(Rectangle bounds) {
		super.paintImmediately(bounds);
		drawArcs(this.getGraphics());
	}

	/**
	 * Draws all arcs currently in arcs hashTable.
	 */
	public void drawArcs(Graphics g) {

		Iterator<Map.Entry<String, ArcView>> it = arcs.entrySet().iterator();
		ArcView arcView = null;
		while (it.hasNext()) {
			arcView = (ArcView) it.next().getValue();
			arcView.drawArc(g);
		}

	}

	/**
	 * Draws an arc and adds it to arcs if it's a new one that was just painted
	 * by the user.
	 * 
	 * @param id
	 * @param start
	 * @param end
	 */
	public void updateArcs(String id, Point start, Point end) {

		if (!arcs.containsKey(id)) {
			ArcView arcView = new ArcView(id, start, end, netController);
			arcs.put(id, arcView);
		} else {
			arcs.get(id).setStart(start);
			arcs.get(id).setEnd(end);
		}

		this.paintImmediately(this.getBounds());
	}

	/**
	 * Overloads public void updateArcs(String id, Point start, Point end) so
	 * methods can also directly use Arview objects.
	 * 
	 * @param arcView
	 */
	public void updateArcs(ArcView arcView) {

		if (!arcs.containsKey(arcView.getId())) {
			arcs.put(arcView.getId(), arcView);
		} else {
			arcs.get(arcView.getId()).setStart(arcView.getStart());
			arcs.get(arcView.getId()).setEnd(arcView.getEnd());
		}

		this.paintImmediately(this.getBounds());

	}

	/**
	 * If id is "newArc" an arc with a non valid target will be deleted.
	 * 
	 * @param id
	 */
	public void removeArc(String id) {

		arcs.remove(id);
		this.repaint();

	}

	public ArrayList<NodeView> getNodeViews() {
		return this.nodes;
	}

	public Hashtable<String, ArcView> getArcViews() {
		return this.arcs;
	}

	/**
	 * This method allows us to create new nodes in other classes.
	 * 
	 * @return
	 */
	public void addListenertoNode(NodeView nodeView) {
		nodeView.addMouseMotionListener(desktopController);
		nodeView.addMouseListener(desktopController);
	}

	/**
	 * returns wether we have some selected nodes or none. So we may move or
	 * delete them at once.
	 * 
	 * @return boolean
	 */
	public boolean hasSelected() {

		Iterator<NodeView> it = getNodeViews().iterator();

		while (it.hasNext()) {
			if (it.next().isSelected())
				return true;
		}

		return false;

	}

	public void deselectNodes() {

		Iterator<NodeView> it = getNodeViews().iterator();

		while (it.hasNext()) {
			NodeView node = (NodeView) it.next();
			node.setSelected(false);
		}

	}

	/**
	 * 
	 */
	public void deleteSelected() {

		Iterator<NodeView> it = getNodeViews().iterator();
		// we store the nodes we want to delete so we don't manipulate the
		// iterator and remove
		// stuff while it's still going through our list
		ArrayList<NodeView> nodesToRemove = new ArrayList<NodeView>();

		if (!getNodeViews().isEmpty()) {
			while (it.hasNext()) {
				NodeView node = (NodeView) it.next();
				if (node.isSelected()) {
					nodesToRemove.add(node);
					// TODO richtig löschen!?
					node.setVisible(false);
				}
			}

			it = nodesToRemove.iterator();
			// now we remove them nodes from our precious list
			while (it.hasNext()) {
				netController.removeNodeAndArcs(it.next());
			}

		}

	}

	/**
	 * Stub for redrawing all incoming and outgoing arcs for a node that has
	 * been moved.
	 * 
	 * @param source
	 */
	public void redrawArcs(NodeView source) {
		netController.updateArcsForNode(source);
	}

	/**
	 * Each dialog has its own corresponding window. Since this windows shall be
	 * modal we will call them from our desktop instead of simply calling them
	 * from the controller.
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
	 * This method works as connection between desktopController which handles
	 * the drawing event an netController which does the net building work after
	 * user wants to add a new arc. It will then repaint the desktop and update
	 * the complete net view. This method would then - in a further development
	 * loop - show briefly a label that informs the user that an arc was
	 * successfully added to the net.
	 * 
	 * 
	 * @param source
	 * @param target
	 */
	public void addNewArc(NodeView source, NodeView target) {

		netController.addNewArc(source, target);
		this.paintImmediately(this.getBounds());

	}

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

	/*
	 * initializes Dialog for deleting Arcs. Since we want to delete them both
	 * from our view and from the net model, we will pass them to the controller
	 * and let him do the job.
	 */
	public void callDeleteArcsDialog(NodeView sourceView) {

		DeleteArcWindow popUp = new DeleteArcWindow(net, sourceView);
		popUp.setModal(true);
		popUp.setVisible(true);

		HashMap<String, PTNArc> arcsToDelete = popUp.sendArcsToDelete();

		if (0 < arcsToDelete.size())
			if (0 == (JOptionPane.showConfirmDialog(this, "Wollen Sie die Kanten wirklich löschen?", "Löschen", JOptionPane.WARNING_MESSAGE)))
				netController.removeArcsFromNetAndDesktop(arcsToDelete);

	}

	public NodeView getNodeViewById(String id) {

		Iterator<NodeView> it = this.getNodeViews().iterator();

		while (it.hasNext()) {
			NodeView nodeView = it.next();

			if (nodeView.getId().equals(id))
				return nodeView;

		}

		return null;

	}

	@Override
	public void startSimulationMode() {

	}

	@Override
	public void startEditorMode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getClickCount() == 2) {
			e = SwingUtilities.convertMouseEvent(e.getComponent(), e, this);
			Point mouseLocation = e.getPoint();
			//mouseLocation = new Point(mouseLocation.x, mouseLocation.y);
			this.callNewNodeDialog(mouseLocation);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
