package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Point;

public class ArcView {
	
	private Point start;
	private Point end;
	private String id;
	
	public ArcView(String id, Point s, Point e) {
		
		this.setStart(s);
		this.setEnd(e);
		this.setId(id);
		
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
	
}
