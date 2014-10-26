package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Dimension;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeContructionException;

public class Transition extends PTNNode {
	
	public Transition(String name, String id, Dimension pos) throws PTNNodeContructionException {
		super(name, id, pos);
	}

}
