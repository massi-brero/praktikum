package q8388415.brero_massimiliano.PTNetEditor.exceptions;

/**
 * This exception will be thrown when a PNML file with not valid input is parsed
 * or an arc with not valid attributes is drawn.
 * 
 * @author 8388415 - Massimiliano Brero
 *
 */
public class PTNArcConstructionException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PTNArcConstructionException(String message, Exception cause) {
		super(message, cause);
	}

	public PTNArcConstructionException(String message) {
		super(message);
	}

	
}
