package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Point;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class PTNPlace extends PTNNode {
	
	private int token;
	
	public PTNPlace(String name, String id, Point pos) throws PTNNodeConstructionException {
		super(name, id, pos);
	}
	
	public PTNPlace(String id) throws PTNNodeConstructionException {
		super(id);
	}
	
	protected void init() {
		this.setType(PTNNodeTypes.place);
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	@Override
	public String getNodeName() {
		return this.getName();
	}
	
}
