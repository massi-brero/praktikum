package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Point;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
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
public class PTNPlace extends PTNNode {
	/**
	 * Aka markings.
	 */
	private int token;
	
	/**
	 * 
	 * @param name String
	 * @param id String
	 * @param pos Dimension
	 * @throws PTNNodeConstructionException
	 */
	public PTNPlace(String name, String id, Point pos) throws PTNNodeConstructionException {
		super(name, id, pos);
	}
	
	/**
	 * 
	 * @param id String
	 * @throws PTNNodeConstructionException
	 */
	public PTNPlace(String id) throws PTNNodeConstructionException {
		super(id);
	}
	
	/**
	 * Just set the node type.
	 */
	protected void init() {
		this.setType(PTNNodeTypes.STELLE);
	}

	/**
	 * Getter
	 * 
	 * @ret int
	 */
	public Integer getToken() {
		return token;
	}

	/**
	 * Setter
	 * 
	 * @param token int
	 */
	public void setToken(int token) {
		this.token = token;
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
	
}
