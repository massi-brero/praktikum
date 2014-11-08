package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JLayeredPane;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNDesktopController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.views.ArcView;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PTNNetViewHandler;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.EditNodeWindow;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.NewArcWindow;

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
	
	private final int D_HEIGHT = 300;
	private final int D_WIDTH = 600;
	private ArrayList<NodeView> nodes;
	private PTNNetViewHandler netHandler;
	private PTNNet net;
	private BufferedImage offscreenI;
	private Graphics offscreenG;
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
		
		offscreenI = new BufferedImage(D_WIDTH, D_HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.net = net;
		this.netHandler = new PTNNetViewHandler(net, this);
		setFocusable(true);
		addKeyListener(appControl);
		setDoubleBuffered(true);
		this.init();

	}
	
	/**
	 * set up the desktop;
	 */
	private void init() {
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(D_WIDTH, D_HEIGHT));
		maxSize = getSize();
		nodes = netHandler.setUpNodes();
		arcs = netHandler.setUpArcs();
		Iterator<NodeView> it = getNodeViews().iterator();
		PTNDesktopController desktopListener = new PTNDesktopController(this);
		while (it.hasNext()) {
			NodeView nodeView = it.next();
			nodeView.addMouseMotionListener(desktopListener);
			nodeView.addMouseListener(desktopListener);	
			this.add(nodeView);
		}
		
		setBackground(Color.WHITE);
		Thread t = new Thread(desktopListener);
		t.start();
	}

	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		drawArcs();
		
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
		drawArcs();
	}
	
	/**
	 * Draws all arcs currently in arcs hashTable.
	 */
	public void drawArcs() {
		
		Iterator<Map.Entry<String, ArcView>> it = arcs.entrySet().iterator();
		ArcView arcView = null;

		while (it.hasNext()) {
			arcView = (ArcView)it.next().getValue();
			this.drawArrow(arcView);
		}
		
	}
	
	/**
	 * Draws an arc and adds it to arcs if it#s a new one that was just painted by the user.
	 * @param id
	 * @param start
	 * @param end
	 */
	public void updateArcs(String id, Point start, Point end) {
		
		if (!arcs.containsKey(id)) {
			arcs.put(id, new ArcView(id, start, end));
		} else {
			arcs.get(id).setStart(start);
			arcs.get(id).setEnd(end);
		}
		
		this.paintImmediately(this.getBounds());
	}
	
	public void drawArrow(ArcView arc) {
		
		Graphics2D g2 = (Graphics2D)this.getGraphics();
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Polygon p = new Polygon();
		Point end = arc.getEnd();
		
		g2.drawLine(arc.getStart().x, arc.getStart().y, end.x,
				end.y);
		
		p.addPoint(end.x, end.y);
		p.addPoint(end.x - 5, end.y - 2);
		p.addPoint(end.x - 5, end.y + 2);
		g2.drawPolygon(p);

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
			((PlaceView)paintedNode).updateTokenLabel(nodeUpdate.getToken());
		
		paintedNode.setNodeLabelText(nodeUpdate.getNodeLabelText());
		
	}
	
	public ArrayList<NodeView> getNodeViews() {
		return this.nodes;
	}
	
	public Hashtable<String, ArcView>getArcViews() {
		return this.arcs;
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
				netHandler.removeNodeAndArcs(it.next());
			}
			
		}
		
	}

	/**
	 * Stub for redrawing all incoming and outgoing arcs for a node that has been moved.
	 * @param source
	 */
	public void redrawArcs(NodeView source) {
		netHandler.upDateNetAndView(source);
	}
	
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
		netHandler.addNewArc(id, source, target);
		
		this.paintImmediately(this.getBounds());
			
	}
	
	
	
}
