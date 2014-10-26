package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Dimension;
import java.util.ArrayList;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeContructionException;

public class Net {
	
	private ArrayList<PTNNode> PTNNodes;
	
	public Net() {
		
	}
	
	public void addArc() {
		
	}
	
	public PTNNode getPTNNodeById(String id) throws PTNNodeContructionException {
		
		return new Place("","", new Dimension(0,0));
	}
	
	public ArrayList<PTNNode> getSuccessors(String id) {
		
		return this.getPTNNodes();
		
	}
	
	public int numberOfPlaces() {
		
		return 1;
	}
	
	public int numberOfTransitions() {
		
		return 1;
	}

	public ArrayList<PTNNode> getPTNNodes() {
		return PTNNodes;
	}

	public void insertPTNNode(PTNNode PTNNode) {
		//this.PTNNodes = PTNNode;
	}
	
	public void getnumberOfSuccessors(String id) {
		
	}
	
	public boolean hasPTNNode(String id) {
		return true;
	}
	
	public boolean hasArc(String id) {
		return true;
	}
	
	public void addArc(PTNNode source, PTNNode target) {
		
	}

}
