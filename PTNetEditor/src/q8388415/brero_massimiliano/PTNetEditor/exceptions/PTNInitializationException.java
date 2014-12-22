package q8388415.brero_massimiliano.PTNetEditor.exceptions;

/**
 * 
 * @author 8388415 - Massimiliano Brero
 *
 */
public class PTNInitializationException extends Exception {

	private static final long serialVersionUID = 1L;

	public PTNInitializationException() {
		super("This class has to be initialized before you can get an instance");
	}

}
