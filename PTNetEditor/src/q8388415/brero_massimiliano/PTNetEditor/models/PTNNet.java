package q8388415.brero_massimiliano.PTNetEditor.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNArcDirections;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

/**
 * 
 * Base class where all the net business logic is in. 
 * 
 * Protocol: If the node and arc list are to be used in another thread (like net or desktop
 * controller) a monitor must be put on those lists.
 *
 * @author Laptop
 *
 */
public class PTNNet {
	
	private HashMap<String,PTNNode> nodes;
	private HashMap<String,PTNArc> arcs;
	
	public PTNNet() {
		
		nodes = new HashMap<String,PTNNode>();
		arcs = new HashMap<String,PTNArc>();
		
	}
	
	/**
	 * 
	 * @param id
	 * 		{@link String}
	 * @return
	 * 		{@link PTNNode}N
	 */
	public PTNNode getNodeById(String id) {
		return nodes.get(id);
	}
	
	/**
	 * 
	 * @param id
	 * 		{@link String}
	 * @return
	 * 		{@link PTNArc}
	 */
	public PTNArc getArcById(String id) {
		return arcs.get(id);
	}
	
	/**
	 * @return
	 * 		int
	 */
	public int getNumberOfNodes() {
		return nodes.size();
	}
	
	/**
	 * 
	 * @return
	 * 		int
	 */
	public int getNumberOfArcs() {
		return arcs.size();
	}
	
	/**
	 * 
	 * @return
	 * 		int
	 */
	public int numberOfPlaces() {
		
		return 1;
	}
	
	/**
	 * 
	 * @return
	 * 		int
	 */
	public int numberOfTransitions() {
		
		return 1;
	}

	/**
	 * 
	 * @return
	 * 		HashMap<String,PTNNode>
	 */
	public HashMap<String,PTNNode> getNodes() {
		return nodes;
	}
	
	/**
	 * 
	 * @return
	 * 		HashMap<String,PTNArc>
	 */
	public HashMap<String,PTNArc> getArcs() {
		return arcs;
	}
	
	/**
	 * 
	 * @param id
	 * 		{@link String}
	 * @return
	 * 		HashMap<String,PTNNode> Successors of a node with the given id.
	 */
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
	
	/**
	 * 
	 * @param id
	 * 		{@link String}
	 * @return
	 * 		HashMap<String,PTNNode> Nodes the arcs are coming from.
	 */
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
	
	/**
	 * Returns all incoming or outgoing arcs in a directed graph for a given node.
	 * @param node
	 * @param direction PTNArcDirection: incoming/outgoing
	 * @return
	 */
	private HashMap<String, PTNArc> getIncomingOutgoingArcs(PTNNode node,
			PTNArcDirections direction) {
		HashMap<String, PTNArc> resultList = new HashMap<String, PTNArc>();
		HashMap<String, PTNArc> arcList = this.getArcs();
		PTNArc arc;

		Iterator<Map.Entry<String, PTNArc>> it = arcList.entrySet().iterator();

		while (it.hasNext()) {
			arc = it.next().getValue();

			if (direction == PTNArcDirections.incoming) {
				if (arc.getTarget().getId().equals(node.getId())) {
					resultList.put(arc.getId(), arc);
				}
			} else {
				if (arc.getSource().getId().equals(node.getId())) {
					resultList.put(arc.getId(), arc);
				}
			}

		}

		return resultList;

	}
	
	/**
	 * 
	 * @param node
	 * 		{@link PTNNode}
	 * @return
	 * 		HashMap<String, PTNArc>
	 */
	public HashMap<String, PTNArc> getOutgoingArcs(PTNNode node) {
		return this.getIncomingOutgoingArcs(node, PTNArcDirections.outgoing);
	}
	
	/**
	 * 
	 * @param node
	 * 		{@link PTNNode}
	 * @return
	 * 		HashMap<String, PTNArc>
	 */
	public HashMap<String, PTNArc> getIncomingArcs(PTNNode node) {
		return this.getIncomingOutgoingArcs(node, PTNArcDirections.incoming);
	}
	
	/**
	 * 
	 * @param id
	 * 		{@link String}
	 * @return
	 * 		int
	 */
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
	 * When we add a new arc and the target is a transition node we have to
	 * check if activation status of that node must be changed.
	 * @param PTNArc a
	 */
	public void addArc(PTNArc a) {
		
		if (a.getTarget().getType() == PTNNodeTypes.TRANSITION)
			this.updateActivationAfterAddingNewPredecessor(a, (PTNTransition)a.getTarget());
		
		arcs.put(a.getId(), a);
	}

	/**
	 * When we add a new arc and the target is a transition node we have to
	 * check if activation status of that node must be changed.
	 * Thus:
	 * 1. If transition is not activated the new source will not change that, so we'll do
	 *    nothing. Exception: the new source is the first node connected with the transition.
	 *    Then the transition will be activated depending in the token number of the
	 *    source.
	 * 2. If the transition is activated and the source node has at least one token we 
	 *    will leave the transition status activated. If the source node has no tokens 
	 *    we have to set the activation status to false.
	 *    
	 * @param arc 
	 * 
	 * @param transition
	 */
	public void updateActivationAfterAddingNewPredecessor(PTNArc arc, PTNTransition transition) {

		PTNPlace place = (PTNPlace)arc.getSource();
		
		if (transition.isActivated()) {
			
			if (0 < place.getToken()) 
				transition.setIsActivated(true);
			else
				transition.setIsActivated(false);
			
		} else {
			/**
			 * Check if we connect the transition with its first predecessor.
			 */
			if (0 == this.getPredecessors(transition.getId()).size() && 0 < place.getToken())
				transition.setIsActivated(true);	
		}
		
	}

	/**
	 * 
	 * @param 
	 * 		PTNNode n ...	New node. Can be a place or a transition. If it's a transition's
	 * 						status will be set to activated.
	 */
	public void addNode(PTNNode n) {
		nodes.put(n.getId(), n);
		
		if (n.getType() == PTNNodeTypes.TRANSITION)
			this.activateTransition((PTNTransition)n);
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
	 * (Ok, we could put that into two methods. But since the code would be almsot identical
	 * it seemed apt to use the switch isSource)
	 *
	 * @param node
	 * @param isSource
	 * 		Boolean source or target given?
	 * @return
	 */
	private HashMap<String, PTNArc> getArcsBySourceOrTarget(PTNNode node, Boolean isSource) {
		
		
		PTNArc arc;
		HashMap<String, PTNArc> list = new HashMap<String, PTNArc>();
		
		if(node != null) {
			
			String id = node.getId();
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
			
		}
		
		return list;
		
	}

	/**
	 * 
	 * @param node
	 */
	public void removeNode(PTNNode node) {
		
		this.getNodes().remove(node.getId());
		
	}

	/**
	 * Empties nodes and arcs lists.
	 */
    public void reset() {
        nodes = new HashMap<String,PTNNode>();
        arcs = new HashMap<String,PTNArc>();      
    }

    
    /**
     * Activates a transition and returns true if every predecessors
     * has at least one token or if there are no predecessors at all.
     * 
     * @param PTNTransition
     * 		transition
     * @return Boolean
     * 		True if transition is now activated, else false.
     */
	public Boolean activateTransition(PTNTransition transition) {
		
		Boolean isActivated = true;
		HashMap<String, PTNNode> predecessors =  this.getPredecessors(transition.getId());
		Iterator<Map.Entry<String, PTNNode>> it = predecessors.entrySet().iterator();

		if (!predecessors.isEmpty()) {
			while(it.hasNext()) {
				
				PTNPlace place = (PTNPlace)it.next().getValue();
				isActivated = place.getToken() > 0;
				
				if (!isActivated)
					break;
				
			}
			
		}

		transition.setIsActivated(isActivated);
		
		return isActivated;
		
	}

}
