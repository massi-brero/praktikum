package q8388415.brero_massimiliano.PTNetEditor.exceptions;

/**
 * Should be thrown whenever a problem with a node occurs.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNNetContructionException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public PTNNetContructionException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param message
	 */
	public PTNNetContructionException(String message) {
		super(message);
	}

}
