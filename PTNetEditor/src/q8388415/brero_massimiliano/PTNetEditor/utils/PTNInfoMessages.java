package q8388415.brero_massimiliano.PTNetEditor.utils;

/**
 *  A simple Container for information messages used more than once 
 *  that can be called in a static manner.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public final class PTNInfoMessages {
	
	/**
	 * 
	 * @return String 	
	 * 		HTML formatted text.
	 */
	public static String getVersionInformation() {
		String message = "Version 0.9 2015<br />";
		
		return message;
	}
	
	/**
	 * 
	 * @return String 	
	 * 		HTML formatted text.
	 */
	public static String getCopyrightInformation() {
		String message = "&copy; (well kind of) Massimiliano Brero 2015";
		
		return message;
	}
	
	/**
	 * 
	 * @return String 	
	 * 		HTML formatted text.
	 */
	public static String getCredits() {
		String message = "<strong>Credits</strong>";
		message += "";
		
		return message;
	}
	
}
