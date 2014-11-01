package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Point;

public class ArcView {
	
	private Point source;
	private Point target;
	private String id;
	
	public ArcView(String id, Point s, Point e) {
		
		this.setSource(s);
		this.setTarget(e);
		this.setId(id);
		
	}
	
	public Point getSource() {
		return source;
	}
	
	public void setSource(Point start) {
		this.source = start;
	}
	
	public Point getTarget() {
		return target;
	}
	
	public void setTarget(Point end) {
		this.target = end;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
