package q8388415.brero_massimiliano.PTNetEditor.models;

public class PTNArc {
	
	private PTNNode source;
	private PTNNode target;
	private String id;
	
	public PTNArc(String id, PTNNode s, PTNNode t) {
		
		this.id = id;
		this.source = s;
		this.target = t;
		
	}

	public PTNNode getSource() {
		return source;
	}

	public void setSource(PTNNode source) {
		this.source = source;
	}

	public PTNNode getTarget() {
		return target;
	}

	public void setTarget(PTNNode target) {
		this.target = target;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
