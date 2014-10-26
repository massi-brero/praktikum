package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Dimension;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeContructionException;

public class Place extends PTNNode {
	
	public Place(String name, String id, Dimension pos) throws PTNNodeContructionException {
		super(name, id, pos);
	}

}
