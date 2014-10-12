package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.plaf.PopupMenuUI;

import q8388415.brero_massimiliano.PTNetEditor.controllers.AppController;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.EditNodeWindow;
import snippet.ButtListener;
import snippet.Edge;

public class PTNDesktop extends JPanel {
	
	public PlaceView butt1;
	public TransitionView butt2;
	private Hashtable<String, Edge> edges;
	
	public PTNDesktop() {

		edges = new Hashtable<String, Edge>();
		butt1 = new PlaceView("1");
		butt1.setName("butt1");
		butt2 = new TransitionView();
		butt2.setName("butt2");
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(700, 550));
		ButtListener mListernerButt1 = new ButtListener(this);
		ButtListener mListernerButt2 = new ButtListener(this);
		
		butt1.setLocation(120, 0);
		butt1.addMouseMotionListener(mListernerButt1);
		butt1.addMouseListener(mListernerButt1);
		
		butt1.setLabelText("testtext");
		
		butt2.setLocation(100, 100);
		butt2.addMouseMotionListener(mListernerButt2);
		butt2.addMouseListener(mListernerButt2);
		butt2.setLabelText("Hallooo");
		butt1.addKeyListener(new AppController());
		butt1.addKeyListener(new AppController());
				
		this.add(butt2);
		this.add(butt1);
		setDoubleBuffered(true);
		

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
		oldNode.setLabelText(newNode.getLabel().getText());
		repaint();
		
	}
	
	

	
}
