package q8388415.brero_massimiliano.PTNetEditor.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;

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
	
	public int getNumberOfNodes() {
		return nodes.size();
	}
	
	public int getNumberOfArcs() {
		return arcs.size();
	}
	
	public int numberOfPlaces() {
		
		return 1;
	}
	
	public int numberOfTransitions() {
		
		return 1;
	}

	public HashMap<String,PTNNode> getNodes() {
		return nodes;
	}
	
	public HashMap<String,PTNArc> getArcs() {
		return arcs;
	}
	
	public HashMap<String,PTNNode> getSuccessors(String id) {
		PTNArc arc;
		HashMap<String, PTNNode> list = new HashMap<String, PTNNode>();
		Iterator<Map.Entry<String, PTNArc>> it = arcs.entrySet().iterator();
		
		while(it.hasNext()) {
			arc = (PTNArc) it.next().getValue();
			if (id.equals(arc.getSource().getId()))
				list.put(arc.getTarget().getId(), arc.getTarget());
		}
		
		return list;
		
	}
	
	public HashMap<String,PTNNode> getPredecessors(String id) {
		PTNArc arc;
		HashMap<String, PTNNode> list = new HashMap<String, PTNNode>();
		Iterator<Map.Entry<String, PTNArc>> it = arcs.entrySet().iterator();
		
		while(it.hasNext()) {
			arc = (PTNArc) it.next().getValue();
			if (id.equals(arc.getTarget().getId()))
				list.put(arc.getSource().getId(), arc.getSource());
		}
		
		return list;
		
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
	public boolean isCorrectPair(PTNNode source, PTNNode target) {
		return source.getType() != target.getType();
	}
	
	/**
	 * Returns all arcs that have the given source.
	 * 
	 * @param source
	 * @return
	 */
	public HashMap<String, PTNArc> getArcsBySource(PTNNode source) {
		
		return getArcsBySourceOrTarget(source, true);
		
	}
	
	/**
	 * Returns all arcs that have the given target.
	 * 
	 * @param source
	 * @return
	 */
	public HashMap<String, PTNArc> getArcsByTarget(PTNNode source) {
		
		return getArcsBySourceOrTarget(source, false);
		
	}
	
	/**
	 * Delegate method for getArcsBySource(PTNNode source) and getArcsByTarget(PTNNode source).
	 * 
	 * @param node
	 * @param isSource
	 * @return
	 */
	private HashMap<String, PTNArc> getArcsBySourceOrTarget(PTNNode node, Boolean isSource) {
		
		String id = node.getId();
		PTNArc arc;
		HashMap<String, PTNArc> list = new HashMap<String, PTNArc>();
		Iterator<Map.Entry<String, PTNArc>> it = arcs.entrySet().iterator();
		
		while(it.hasNext()) {
			arc = (PTNArc) it.next().getValue();
			
			if (isSource) {
				if (id.equals(arc.getSource().getId()))
					list.put(arc.getId(), arc);				
			} else {
				if (id.equals(arc.getTarget().getId()))
					list.put(arc.getId(), arc);	
			}
		}
		
		return list;
		
	}

	public void removeNode(PTNNode node) {
		
		this.getNodes().remove(node.getId());
		
	}

}
