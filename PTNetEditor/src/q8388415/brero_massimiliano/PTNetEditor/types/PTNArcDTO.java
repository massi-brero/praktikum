package q8388415.brero_massimiliano.PTNetEditor.types;

/**
 * A simple interface intended to generate  simple DTOs to transfer
 * node data. This way we can use either node views oder node models
 * to pass arc data.
 * 
 * @author 8388415
 *
 */
public interface PTNArcDTO {
	
	String getId();
	String getArcName();
	PTNNodeTypes getType();
	int getToken();
	
}
