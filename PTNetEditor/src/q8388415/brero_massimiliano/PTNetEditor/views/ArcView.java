package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;

public class ArcView implements PTNIScaleListener {
	
	private Point start;
	private Point end;
	private String id;
	
	public ArcView(String id, Point s, Point e) {
		
		this.setStart(s);
		this.setEnd(e);
		this.setId(id);
		
	}
	
	public void drawArc(Graphics  g) {
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Polygon p = new Polygon();
		Point end = this.getEnd();
		
		g2.drawLine(this.getStart().x, this.getStart().y, end.x, end.y);

		p.addPoint(end.x, end.y);
		p.addPoint(end.x - 5, end.y - 2);
		p.addPoint(end.x - 5, end.y + 2);
		g2.drawPolygon(p);
		
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
	
}
