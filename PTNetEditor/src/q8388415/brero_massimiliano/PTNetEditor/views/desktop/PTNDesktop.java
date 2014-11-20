package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNDesktopController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNNetController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.views.ArcView;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.DeleteArcWindow;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.EditNodeWindow;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.NewArcWindow;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.NewNodeWindow;

/**
 * When we set up the desktop we'll translate our net structure into node views.
 * 1. We will draw our arcs direct from our net arcs.
 * 
 * 2. But every node model type will have its own view representation in an own list for our node views. 
 * This way we may have to update nodes in the view list and in our net model, but we'll gain on
 * performance since we don't have to iterate the complete net and re-add all buttons when some update action
 * on the nodes is due.
 * 
 * @author q8388415
 *
 */
public class PTNDesktop extends JLayeredPane {
	
	private final int DEFAULT_HEIGHT = 300;
	private final int DEFAULT_WIDTH = 600;
	private ArrayList<NodeView> nodes;
	private PTNNetController netController;
	PTNDesktopController desktopController;
	private PTNNet net;
	// Using a Hashtable instead of an ArrayList like nodes makes it easier to identify arcs by id for our drawing operations.
	private Hashtable<String, ArcView> arcs;
	
	// biggest size of desktop so far; we need this to adapt the scroll pane's bars 
	private Dimension maxSize; 
	
	/**
	 * Basic operations will be put in the controller, like initializing
	 * attributes for buffered painting;
	 * 
	 * @param appControl
	 * @param net
	 */
	public PTNDesktop(PTNAppController appControl, PTNNet net) {
		
		this.net = net;
		this.netController = new PTNNetController(net, this);
		setFocusable(true);
		this.setOpaque(false);
		addKeyListener(appControl);
		setDoubleBuffered(true);
		this.init();

	}
	
	/**
	 * set up the desktop;
	 */
	private void init() {
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(DEFAULT_HEIGHT, DEFAULT_HEIGHT));
		maxSize = getSize();
		nodes = netController.setUpNodes();
		arcs = netController.setUpArcs();
		Iterator<NodeView> it = getNodeViews().iterator();
		desktopController = new PTNDesktopController(this);
		while (it.hasNext()) {
			NodeView nodeView = it.next();
			this.addListenertoNode(nodeView);
			this.add(nodeView);
		}
		
		setBackground(Color.WHITE);
		//start controller threads
		Thread t1 = new Thread(desktopController);
		t1.start();
		Thread t2 = new Thread(netController);
		t2.start();
	}
	
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
			arcView = (ArcView)it.next().getValue();
			arcView.drawArc(g);
		}
		
	}
	
	/**
	 * Draws an arc and adds it to arcs if it's a new one that was just painted by the user.
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
	 * Overloads public void updateArcs(String id, Point start, Point end) so methods
	 * can also directly use Arview objects.
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
	 * @param id
	 */
	public void removeArc(String id) {
		
		arcs.remove(id);
		this.repaint();
		
	}
	
	
	private void updateNodeAttributes(NodeView paintedNode, PTNINodeDTO nodeUpdate) {

		if (paintedNode instanceof PlaceView)
			((PlaceView)paintedNode).updateToken(nodeUpdate.getToken());
		
		paintedNode.setName(nodeUpdate.getNodeName());
		
	}
	
	public ArrayList<NodeView> getNodeViews() {
		return this.nodes;
	}
	
	public Hashtable<String, ArcView>getArcViews() {
		return this.arcs;
	}
	
	/**
	 * This method allows us to create new nodes in other classes.
	 * @return
	 */
	public void addListenertoNode(NodeView nodeView) {
		nodeView.addMouseMotionListener(desktopController);
		nodeView.addMouseListener(desktopController);	
	}
	
	/**
	 * returns wether we have some selected nodes or none. So we may move
	 * or delete them at once.
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
		// we store the nodes we want to delete so we don't manipulate the iterator and remove
		// stuff while it's still going through our list
		ArrayList<NodeView> nodesToRemove = new ArrayList<NodeView>();
		
		if (!getNodeViews().isEmpty()) {
			while (it.hasNext()) {
				NodeView node = (NodeView) it.next();
				if (node.isSelected()) {
					nodesToRemove.add(node);
					//TODO richtig löschen!?
					node.setVisible(false);					
				}
			}	
			
			it = nodesToRemove.iterator();
			//now we remove them nodes from our precious list
			while (it.hasNext()) {
				netController.removeNodeAndArcs(it.next());
			}
			
		}
		
	}

	/**
	 * Stub for redrawing all incoming and outgoing arcs for a node that has been moved.
	 * @param source
	 */
	public void redrawArcs(NodeView source) {
		netController.updateArcsForNode(source);
	}
	
	
	/**
	 * Each dialog has its own corresponding window.
	 * Since this windows shall be modal we will call them
	 * from our desktop instead of simply calling them from the controller.
	 */
	
	public void callNodeAttributeDialog(NodeView source) {
		
		EditNodeWindow popUp = new EditNodeWindow(source);
		popUp.setModal(true);
		popUp.setVisible(true);
		
		PTNINodeDTO nodeUpdate = popUp.sendUpdatedNode();
		this.updateNodeAttributes(source, nodeUpdate);
		
		// so our arcs won't be obscured
		this.paintImmediately(this.getBounds());
			
	}
	
	public void callNewArcDialog(NodeView source, NodeView target) {
		
		NewArcWindow popUp = new NewArcWindow();
		popUp.setModal(true);
		popUp.setVisible(true);
		
		String id = popUp.sendId();
		netController.addNewArc(id, source, target);
		
		this.paintImmediately(this.getBounds());
			
	}

	public void callNewNodeDialog() {
		
		NewNodeWindow popUp = new NewNodeWindow();
		popUp.setModal(true);
		popUp.setVisible(true);
		
		PTNINodeDTO nodeParams = popUp.sendParams();
		netController.addNewNode(nodeParams);
		
		this.repaint();
		
	}
	
	/*
	 * initializes Dialog for deleting Arcs. Since we want to delete them both from our view and
	 * from the net model, we will pass them to the controller and let him do the job.
	 */
	public void callDeleteArcsDialog(NodeView sourceView) {
		
		DeleteArcWindow popUp = new DeleteArcWindow(net, sourceView);
		popUp.setModal(true);
		popUp.setVisible(true);
		
		HashMap<String, PTNArc> arcsToDelete = popUp.sendArcsToDelete();
		
		if (0 < arcsToDelete.size()) 
			if (0 == (JOptionPane.showConfirmDialog(this, "Wollen Sie die Kanten wirklich löschen?", "Löschen?", JOptionPane.WARNING_MESSAGE)))
				netController.removeArcsFromNetAndDesktop(arcsToDelete);
				

	}
	
	
	
}
