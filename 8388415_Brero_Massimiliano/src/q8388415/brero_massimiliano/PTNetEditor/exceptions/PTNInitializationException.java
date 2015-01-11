package q8388415.brero_massimiliano.PTNetEditor.exceptions;

/**
 * Special exception for classes that need to be initialized before some of there methods can be used.
 * 
 * @author 8388415 - Massimiliano Brero
 *
 */
public class PTNInitializationException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Generates Exception with a "Not initialized" message.
	 */
	public PTNInitializationException() {
		super("This class has to be initialized before you can get an instance");
	}

}
