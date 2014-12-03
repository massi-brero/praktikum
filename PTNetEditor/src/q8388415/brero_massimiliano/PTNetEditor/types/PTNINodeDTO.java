package q8388415.brero_massimiliano.PTNetEditor.types;

import java.awt.Point;

/**
 * A simple interface intended to generate  simple DTOs to transfer
 * node data. 
 * to pass node data. It is basically the public interface of our node views
 * and models, but this way we can use to pass node information regardless if
 * a method expects a node view or a node model type.
 * 
 * @author 8388415
 *
 */
public interface PTNINodeDTO {
	
	String getId();
	String getNodeName();
	PTNNodeTypes getType();
	int getToken();
	Point getLocation();
	
}
