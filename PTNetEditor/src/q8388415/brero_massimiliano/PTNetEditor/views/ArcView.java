package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNNetController;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;

/**
 * Visual reprensentation of an arc connecting 2 nodes. This class just knows about
 * starting and ending points. It does not know anything about a source or target 
 * node since it's just a view and we do no want to implement any logic in here.
 * So e.g. the adjusting process for start and end point is taking care of by the 
 * net controller and its delegates: the arc helper and the node helper classes.
 * 
 * @author Laptop
 *
 */
public class ArcView implements PTNIScaleListener {
	
	private Point start;
	private Point end;
	private String id;
	private static double scale = 1.0;
	private PTNNetController netController;
	// We need this flag to check if an arc was just drawn (and thus can be deleted safely on the desktop).
	private final int ARROW_SIZE_X = 8;
	private final int ARROW_SIZE_Y = 4;
	
	public ArcView(String id, Point s, Point e, PTNNetController netController) {
		
		this.netController = netController;
		this.setStart(s);
		this.setEnd(e);
		this.setId(id);
		
	}
	
	/**
	 * We compute the slope angle of our line here. We can cat that simply by
	 * using our start and end points for calculating the arc steepness (interpreted as a function
	 * Then this method uses the Math.atan2() method to get the angle from the steepness.
	 * We need that so drawArrowHead can be rotated and its tip always faces the right direction.
	 * 
	 * @param g
	 */
	public void drawArc(Graphics  g) {
		double gradient = Math.atan2(this.getEnd().y - this.getStart().y, this.getEnd().x - this.getStart().x);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLUE);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Point end = this.getEnd();
		g2.drawLine(this.getStart().x, this.getStart().y, end.x, end.y);
		this.drawArrowHead(g2, end, gradient);
	
	}
	
	/**
	 * 	Draws the arrow head at the end of the lines and rotates it according to line 
	 * position. The arrow head will always face the right direction.
	 * 
	 * @param g2
	 * @param end
	 * @param gradient
	 */
	private void drawArrowHead(Graphics2D g2, Point end, double gradient) {

		AffineTransform rotateTrans = new AffineTransform();
		Polygon p = this.getArrowHeadPolygon();

		// setToRotation allows us to set fixed anchor point, so we don't have to
		// calculate for the "classic" translate method of AffineTransform.
		rotateTrans.setToIdentity();
		rotateTrans.setToRotation(gradient, end.getX(), end.getY());

		Shape shape = rotateTrans.createTransformedShape(p);
		
		g2.fill(shape);
		g2.draw(shape);
		
	}
	
	private Polygon getArrowHeadPolygon() {
		Polygon p = new Polygon();
		p.addPoint(end.x, end.y);
		// Okay, this can also be done with an AffineTransorm. But since we already use it for rotating
		// it would involve quite some translating computations and multiplying those matrices is also 
		// a little more complex than this simple but effective approach;
		p.addPoint((int)(end.x - ARROW_SIZE_X * ArcView.scale), (int)(end.y - ARROW_SIZE_Y * ArcView.scale));
		p.addPoint((int)(end.x - ARROW_SIZE_X * ArcView.scale), (int)(end.y + ARROW_SIZE_Y * ArcView.scale));
		
		return p;
	}
	
	
	public Point getStart() {
		return start;
	}
	
	public void setStart(Point start) {
		this.start = start;
	}
	
	public Point getEnd() {
		return end;
	}
	
	public void setEnd(Point end) {
		this.end = end;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "+") {
			this.increaseScale();

		}
		else if (e.getActionCommand() == "-") {
			this.decreaseScale();
		}
		
	}

	@Override
	public void increaseScale() {
		if (ArcView.scale < 5) {
			ArcView.scale += 0.2;
			netController.repaintDesktop();
		}
	}

	@Override
	public void decreaseScale() {
		if (ArcView.scale > 1) {
			ArcView.scale -= 0.2;
			netController.repaintDesktop();			
		}
	}
	
}
