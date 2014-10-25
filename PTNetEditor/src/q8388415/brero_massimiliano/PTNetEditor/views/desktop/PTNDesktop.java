package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.EditNodeWindow;
import snippet.ButtListener;
import snippet.Edge;

/**
 * 
 * @author q8388415
 *
 */
public class PTNDesktop extends JPanel {
	
	public PlaceView butt1;
	public TransitionView butt2;
	private ArrayList<NodeView> nodes;
	private Hashtable<String, Edge> edges;
	private double scale;
	// biggest size of desktop so far; we need this to adapt the scroll pane's bars 
	private Dimension maxSize;
	
	public PTNDesktop(PTNAppController appControl) {

		edges = new Hashtable<String, Edge>();
		nodes = new ArrayList<NodeView>();
		butt1 = new PlaceView("1");
		butt1.setName("butt1");
		butt1.setLocation(120, 200);
		butt2 = new TransitionView();
		butt2.setName("butt2");
		butt2.setLocation(100, 100);
		nodes.add(butt1);
		nodes.add(butt2);
		setFocusable(true);
		addKeyListener(appControl);
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(500, 300));
		ButtListener mListernerButt1 = new ButtListener(this);
		
		Iterator<NodeView> it = nodes.iterator();
		
		while (it.hasNext()) {
			NodeView node = it.next();
			node.addMouseMotionListener(mListernerButt1);
			node.addMouseListener(mListernerButt1);		
			node.setLabelText("testtext");
			Thread t = new Thread(mListernerButt1);
			t.start();
		}

		this.add(butt2);
		this.add(butt1);
		maxSize = getSize();
		setDoubleBuffered(true);

	}
	
	public void paint(Graphics g) {
		
		super.paint(g);
		if (getSize().width > maxSize.width || getSize().height > maxSize.height) {
			
			if (getSize().width > maxSize.width) 
				maxSize.width = getSize().width;				
			if (getSize().height > maxSize.height)
				maxSize.height = getSize().height;
	
			this.setPreferredSize(maxSize);
			this.revalidate();
			
		}
			
		
	}
	
	public void drawEdge(String id, Point start, Point end) {

		if (!edges.containsKey(id)) {
			edges.put(id, new Edge(start, end));
		} else {
			edges.get(id).setStart(start);
			edges.get(id).setEnd(end);
		}
		
		setDoubleBuffered(true);
		this.paintImmediately(this.getBounds());
	}
	
	@Override
	public void paintImmediately(Rectangle bounds) {
		
		super.paintImmediately(bounds);
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Set<String> keys = edges.keySet();
		Iterator<String> it = keys.iterator();
		
		while (it.hasNext()) {
			
			Edge edge = edges.get(it.next());
			drawArrow(g2, edge);

		}
		
	}
	
	private void drawArrow(Graphics2D g2, Edge edge) {
		Polygon p = new Polygon();
		Point end = edge.getEnd();
		
		g2.drawLine(edge.getStart().x, edge.getStart().y, end.x,
				end.y);
		
		p.addPoint(end.x, end.y);
		p.addPoint(end.x - 5, end.y - 2);
		p.addPoint(end.x - 5, end.y + 2);
		
		g2.drawPolygon(p);
	}
	
	public void deleteLineFromDeskTop(String name) {
		
		edges.remove(name);
		repaint();
		
	}

	public void callDialog(NodeView source) {
		
		EditNodeWindow popUp = new EditNodeWindow(source);
		popUp.setModal(true);
		popUp.setVisible(true);
		
		NodeView newNode = popUp.sendUpdatedNode();
		
		this.updateNode(source, newNode);
			
	}
	
	private void updateNode(NodeView oldNode, NodeView newNode) {
		
		System.out.println(newNode.getText());
		oldNode.setText(newNode.getText());
		oldNode.setLabelText(newNode.getNodeLabel().getText());
		repaint();
		
	}
	
	public ArrayList<NodeView> getNodes() {
		return this.nodes;
	}
	
	/**
	 * returns wether we have some selected nodes or none. So we may move
	 * or delete them at once.
	 * @return boolean
	 */
	public boolean hasSelected() {
		
		Iterator<NodeView> it = getNodes().iterator();
		
		while (it.hasNext()) {
			
			if (it.next().isSelected())
				return true;
			
		}
		
		return false;
		
	}
	
	public void deselectNodes() {
		
		Iterator<NodeView> it = getNodes().iterator();
		
		while (it.hasNext()) {
			NodeView node = (NodeView) it.next();
			node.setSelected(false);
		}
		
	}
	
	public void deleteSelected() {
		
		Iterator<NodeView> it = getNodes().iterator();
		// we store the nodes we want to delete so we don't manipulate the iterator and remove
		// stuff while it's still going through our list
		ArrayList<NodeView> nodesToRemove = new ArrayList<NodeView>();
		
		if (!nodes.isEmpty()) {
			while (it.hasNext()) {
				NodeView node = (NodeView) it.next();
				if (node.isSelected()) {
					nodesToRemove.add(node);
					//TODO richtig löschen!?
					node.setVisible(false);					
				}
			}	
			
			//now we remove them nodes from our precious list
			while (it.hasNext()) {
				nodes.remove(it.next());
			}
			
		}
		
	}

	
//	private void transformGraphicsToUserCoordinateSystem(Graphics2D g2D) {
//		scale = Math.min(getParent().getWidth()*2, getParent().getHeight());
//		AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
//		g2D.transform(at);
//	}
	
	

	
}
