package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Point;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public abstract class PTNNode {

    private String name;
    private String id;
    private Point location;
    static Point DEFAULT_POSITION = new Point(0, 0);
    private PTNNodeTypes type;

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

    public PTNNode(String id) throws PTNNodeConstructionException {
        this("", id, DEFAULT_POSITION);
    }

    protected abstract void init();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point position) {
        this.location = position;
    }

    public PTNNodeTypes getType() {
        return type;
    }

    public void setType(PTNNodeTypes type) {
        this.type = type;
    }

}
