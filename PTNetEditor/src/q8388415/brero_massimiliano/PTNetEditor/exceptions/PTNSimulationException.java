package q8388415.brero_massimiliano.PTNetEditor.exceptions;

/**
 * Use this when a invalid simulation action occurs.
 * 
 * @author Laptop
 *
 */
public class PTNSimulationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PTNSimulationException(String message, Exception cause) {
		super(message, cause);
	}

	public PTNSimulationException(String message) {
		super(message);
	}

	
}
