package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Point;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class PTNTransition extends PTNNode {
	
	private Boolean isActivated = false;

	public PTNTransition(String name, String id, Point pos) throws PTNNodeConstructionException {
		super(name, id, pos);
	}
	
	public PTNTransition(String id) throws PTNNodeConstructionException {
		super(id);
	}
	
	protected void init() {
		this.setType(PTNNodeTypes.TRANSITION);
	}

	public Boolean isActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean activated) {
		this.isActivated = activated;
	}

	
	@Override
	public String getNodeName() {
		return this.getName();
	}

	@Override
	public Integer getToken() {
		return null;
	}
	

}
