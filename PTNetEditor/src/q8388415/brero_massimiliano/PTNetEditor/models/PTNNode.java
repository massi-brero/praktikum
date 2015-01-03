package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Point;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

/**
 * Base model class for our node types. By implementing PTNINodeDTO
 * we may use it as data store object.
 * {@link PTNINodeDTO}
 * 
 * @author brero
 *
 */
public abstract class PTNNode implements PTNINodeDTO {

	/**
	 * Basic attributes of a node. Corresponding information needed by the PNML file.
	 */
    private String name;
    private String id;
    private Point location;
    static Point DEFAULT_POSITION = new Point(0, 0);
    /**
     * Place or Transition? {@link PTNNodeTypes}
     */
    private PTNNodeTypes type;

    /**
     * 
     * @param name String
     * @param id String
     * @param pos Dimension
     * @throws PTNNodeConstructionException
     */
    public PTNNode(String name, String id, Point pos) throws PTNNodeConstructionException {

        if ("" == id) {

            throw new PTNNodeConstructionException("Vital information for this node is missing (id)!");
        } else {
            this.setName(name);
            this.setId(id);
            this.setLocation(pos);
            this.init();
        }

    }

    /**
     * 
     * @param id String
     * @throws PTNNodeConstructionException
     */
    public PTNNode(String id) throws PTNNodeConstructionException {
        this("", id, DEFAULT_POSITION);
    }

    /**
     * Must be implemented by child class to do som basic initialization work.
     */
    protected abstract void init();

    /**
     * Getter 
     * @return String
     * 		Node's name label.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for node#s name label.
     * 
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Setter
     * 
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter
     * 
     * @return Dimension
     * 		This is are the coordinates that are read from or stored
     * 		to a pnml file.
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Setter
     * 		This is are the coordinates that are read from or stored
     * 		to a pnml file.
     */
    public void setLocation(Point position) {
        this.location = position;
    }

    /**
     * Place or Transition?
     * @return {@link PTNNodeTypes}
     */
    public PTNNodeTypes getType() {
        return type;
    }

    /**
     * Place or Transition?
     * @param type {@link PTNNodeTypes}
     */
    public void setType(PTNNodeTypes type) {
        this.type = type;
    }

}
