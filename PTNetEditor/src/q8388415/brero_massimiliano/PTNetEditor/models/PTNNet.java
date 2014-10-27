package q8388415.brero_massimiliano.PTNetEditor.models;

import java.util.HashMap;
import java.util.Map;

public class PTNNet {
	
	private HashMap<String,PTNNode> nodes;
	private HashMap<String,PTNArc> arcs;
	
	public PTNNet() {
		
		nodes = new HashMap<String,PTNNode>();
		arcs = new HashMap<String,PTNArc>();
		
	}
	
	public PTNNode getNodeById(String id) {
		return nodes.get(id);
	}
	
	public PTNArc getArcById(String id) {
		return arcs.get(id);
	}
	
	public Map<String,PTNNode> getSuccessors(String id) {
		
		return this.getNodes();
		
	}
	
	public int numberOfPlaces() {
		
		return 1;
	}
	
	public int numberOfTransitions() {
		
		return 1;
	}

	public Map<String,PTNNode> getNodes() {
		return nodes;
	}
	
	public int getNumberOfSuccessors(String id) {
		return 1;
	}
	
	/**
	 * Returns true if net contains a node with given id.
	 * @param id
	 * @return Boolean
	 */
	public boolean hasNodeWithId(String id) {
		return nodes.containsKey(id);
	}
	
	/**
	 * Returns true if net contains given node object
	 * @param PTNNode n
	 * @return
	 */
	public boolean hasNode(PTNNode n) {
		return nodes.containsValue(n);
	}
	
	/**
	 * Returns true if net contains an arc with given id.
	 * @param id
	 * @return Boolean
	 */
	public boolean hasArcWithId(String id) {
		return arcs.containsKey(id);
	}
	
	/**
	 * Returns true if net contains given arc object.
	 * @param PTNNode n
	 * @return
	 */
	public boolean hasArc(PTNNode n) {
		return arcs.containsValue(n);
	}
	
	/**
	 * 
	 * @param PTNArc a
	 */
	public void addArc(PTNArc a) {
		arcs.put(a.getId(), a);
	}
	
	/**
	 * 
	 * @param PTNNode n
	 */
	public void addNode(PTNNode n) {
		nodes.put(n.getId(), n);
	}
	
	
	/**
	 * Checks wether source aand target are two different kinds of nodes.
	 * @param source
	 * @param target
	 * @return true if nodes are different types
	 */
	private boolean isCorrectPair(PTNNode source, PTNNode target) {
		return source.getType() != target.getType();
	}

}
