package q8388415.brero_massimiliano.PTNetEditor.models;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIArcDTO;

/**
 * Model representing an arc. Needed e. g. for all writing and reading operations.
 * Since the business logic is completely in {@link PTNNet} this is more of a data 
 * container.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNArc implements PTNIArcDTO {
	
	private PTNNode source;
	private PTNNode target;
	private String id;
	
	/**
	 * 
	 * @param id String
	 * 		Node id.
	 * @param s {@link PTNNode}
	 * 		Source node.
	 * @param t {@link PTNNode}
	 * 		Target Node
	 */
	public PTNArc(String id, PTNNode s, PTNNode t) {
		
		this.id = id;
		this.source = s;
		this.target = t;
		
	}

	/**
	 * Getter
	 * 
	 * @return {@link PTNNode}
	 */
	public PTNNode getSource() {
		return source;
	}

	/**
	 * Setter
	 * 
	 * @param source {@link PTNNode}
	 */
	public void setSource(PTNNode source) {
		this.source = source;
	}

	/**
	 * Getter
	 * 
	 * @return {@link PTNNode}
	 */
	public PTNNode getTarget() {
		return target;
	}

	/**
	 * Setter
	 * 
	 * @param target {@link PTNNode}
	 */
	public void setTarget(PTNNode target) {
		this.target = target;
	}
	
	/**
	 * Getter
	 * 
	 * @return String
	 * 		Unique arc id (not even a node is allowed to have the same id)
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter
	 * @param id String
	 * 		Unique arc id (not even a node is allowed to have the same id)
 	 */
	public void setId(String id) {
		this.id = id;
	}

}
