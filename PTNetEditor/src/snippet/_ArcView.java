package snippet;

import java.awt.Point;

public class _ArcView {
	
	private Point start;
	private Point end;
	
	public _ArcView(Point s, Point e) {
		
		setStart(s);
		setEnd(e);
		
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

}
