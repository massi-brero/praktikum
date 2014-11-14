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

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;

public class ArcView implements PTNIScaleListener {
	
	private Point start;
	private Point end;
	private String id;
	private double scale = 1;
	private AffineTransform at = new AffineTransform();
	
	public ArcView(String id, Point s, Point e) {
		
		this.setStart(s);
		this.setEnd(e);
		this.setId(id);
		
	}
	
	public void drawArc(Graphics  g) {
		
		double gradient = Math.atan2(this.getEnd().y - this.getStart().y, this.getEnd().x - this.getStart().x);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Point end = this.getEnd();
		
		g2.drawLine(this.getStart().x, this.getStart().y, end.x, end.y);
		this.drawArrowHead(g2, end, gradient);
	
	}
	
	/**
	 * Draws the arrow head at the end of the lines and rotates it according to line 
	 * position.
	 * @param end
	 * @return
	 */
	private void drawArrowHead(Graphics2D g2, Point end, double gradient) {
		Polygon p = new Polygon();
		p.addPoint(end.x, end.y);
		p.addPoint(end.x - 5, end.y - 2);
		p.addPoint(end.x - 5, end.y + 2);
		Point midPoint = midpoint(this.getStart(), this.getEnd());
		at.setToIdentity();
		at.translate(midPoint.x, midPoint.y);
		at.scale(scale, scale);
		at.rotate(gradient);
		Shape shape = at.createTransformedShape(p);
		g2.fill(shape);
		g2.draw(shape);
		
	}
	
	private static Point midpoint(Point p1, Point p2) {
	    return new Point((int)((p1.x + p2.x)/2.0), 
	                     (int)((p1.y + p2.y)/2.0));
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
		System.out.println("arc_increase");
	}

	@Override
	public void decreaseScale() {
		System.out.println("arc_decrease");
		
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
	
	
	
}
