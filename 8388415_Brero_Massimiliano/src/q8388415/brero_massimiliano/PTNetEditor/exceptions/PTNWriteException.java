package q8388415.brero_massimiliano.PTNetEditor.exceptions;

/**
 * Will be thread when a general or PNML special error occurs.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNWriteException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public PTNWriteException(String message, Exception cause) {
		super(message, cause);
	}
	
	/**
	 * 
	 * @param message
	 */
	public PTNWriteException(String message) {
		super(message);
	}

}
