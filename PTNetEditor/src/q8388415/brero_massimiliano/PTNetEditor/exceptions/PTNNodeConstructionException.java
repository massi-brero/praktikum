package q8388415.brero_massimiliano.PTNetEditor.exceptions;

/**
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNNodeConstructionException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Should be thrown whenever a problem with a node occurs.
	 * 
	 * @param message
	 * @param cause
	 */
	public PTNNodeConstructionException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param message
	 */
	public PTNNodeConstructionException(String message) {
		super(message);
	}

}
