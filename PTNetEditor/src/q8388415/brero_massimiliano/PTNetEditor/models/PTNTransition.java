package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Point;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class PTNTransition extends PTNNode {
	
	private Boolean activated;
	private Boolean hasMarking = false;
	
	public PTNTransition(String name, String id, Point pos) throws PTNNodeConstructionException {
		super(name, id, pos);
	}
	
	public PTNTransition(String id) throws PTNNodeConstructionException {
		super(id);
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

	public Boolean getHasMarking() {
		return hasMarking;
	}

	public void setHasMarking(Boolean hasMarking) {
		this.hasMarking = hasMarking;
	}

	@Override
	public String getNodeName() {
		return this.getName();
	}

	@Override
	public int getToken() {
		return 0;
	}
	

}
