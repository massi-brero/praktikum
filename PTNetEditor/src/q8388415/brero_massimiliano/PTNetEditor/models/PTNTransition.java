package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Point;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class PTNTransition extends PTNNode {
	
	private Boolean activated;
	
	public PTNTransition(String name, String id, Point pos) throws PTNNodeConstructionException {
		super(name, id, pos);
	}
	
	protected void init() {
		this.setType(PTNNodeTypes.transition);
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	
	
	
	

}
