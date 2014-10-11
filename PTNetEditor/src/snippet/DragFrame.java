package snippet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.controllers.AppController;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;

class DragFrame extends JFrame {

	public PlaceView butt1;
	public TransitionView butt2;
	private JPanel desktop;
	private Hashtable<String, Edge> edges;

	public DragFrame() {

		edges = new Hashtable<String, Edge>();
		butt1 = new PlaceView("1");
		butt1.setName("butt1");
		butt2 = new TransitionView();
		butt2.setName("butt2");
		
		desktop = new JPanel();
		desktop.setLayout(null);
		desktop.setPreferredSize(new Dimension(400, 400));
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
				
		desktop.add(butt2);
		desktop.add(butt1);
		butt1.addKeyListener(new AppController());
		butt1.addKeyListener(new AppController());
		
		getContentPane().add(desktop, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();

		this.setVisible(true);
	}

	public static void main(String[] args) {

		JFrame frame = new DragFrame();

	}

	public void drawEdge(String id, Point start, Point end) {

		if (!edges.containsKey(id)) {
			edges.put(id, new Edge(start, end));
		} else {
			edges.get(id).setStart(start);
			edges.get(id).setEnd(end);
		}
		
		repaint();

	}
	
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) desktop.getGraphics();
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

	public JPanel getDesktop() {
		return this.desktop;
	}

	public void deleteLineFromDeskTop(String name) {
		
		edges.remove(name);
		repaint();
		
	}

}
