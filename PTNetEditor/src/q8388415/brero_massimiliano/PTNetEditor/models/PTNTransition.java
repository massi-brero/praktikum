package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Point;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

/**
 * Model representing a place. Needed e. g. for all writing and reading operations.
 * Since the business logic is completely in {@link PTNNet} this is more of a data 
 * container.
 * Derived from {@link PTNNode}.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNTransition extends PTNNode {
	
	/**
	 * Is user allowed to switch this transition in simulation mode?
	 */
	private Boolean isActivated = false;

	/**
	 * 
	 * @param name String
	 * @param id String
	 * @param pos Dimension
	 * @throws PTNNodeConstructionException
	 */
	public PTNTransition(String name, String id, Point pos) throws PTNNodeConstructionException {
		super(name, id, pos);
	}
	
	/**
	 * 
	 * @param id String
	 * @throws PTNNodeConstructionException
	 */
	public PTNTransition(String id) throws PTNNodeConstructionException {
		super(id);
	}
	
	/**
	 * Just set the node type.
	 */
	protected void init() {
		this.setType(PTNNodeTypes.TRANSITION);
	}

	/**
	 * Getter
	 * @return Boolean
	 */
	public Boolean isActivated() {
		return isActivated;
	}

	/**
	 * Setter 
	 * @param activated Booelan
	 */
	public void setIsActivated(Boolean activated) {
		this.isActivated = activated;
	}

	/**
	 * Getter
	 * 
	 * name of the node name label.
	 */
	@Override
	public String getNodeName() {
		return this.getName();
	}

	/**
	 * We don not really this for transitions, but then we can implement
	 * {@link PTNINodeDTO}.
	 */
	@Override
	public Integer getToken() {
		return null;
	}
	

}
