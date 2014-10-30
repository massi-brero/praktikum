package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Dimension;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class PTNTransition extends PTNNode {
	
	public PTNTransition(String name, String id, Dimension pos) throws PTNNodeConstructionException {
		super(name, id, pos);
	}
	
	protected void init() {
		this.setType(PTNNodeTypes.transition);
	}

}
