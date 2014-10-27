package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Dimension;
import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;

public class PTNPlace extends PTNNode {
	
	private int token;
	
	public PTNPlace(String name, String id, Dimension pos) throws PTNNodeConstructionException {
		super(name, id, pos);
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}
	
}
