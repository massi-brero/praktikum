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
		message.concat("");
		
		return message;
	}
	
	/**
	 * 
	 * @return StringBuffer 	
	 * 		HTML formatted text.
	 */
	public static StringBuffer getCredits() {
		StringBuffer message = new StringBuffer("<strong>Credits</strong> <br/>");
		message.append("<p>The place symbol was designed by IconLand http://iconLand.com.<br/>");
		message.append("The place symbol was designed by OCAL http://www.openclipart.org.<br/>");
		message.append("The scale transition and scale place icons were designe by http://www.customicondesign.com/.<br/>");
		message.append("All menu icons were downloaded from http://www.iconarchive.com.<br/>");
		message.append("They are intellectual property of the resptive designers listed there.</p><br/><br/>");
		message.append("<p>All images are distributed under GNU license model, CC Attribution-Noncommercial-No Derivate 4.0 <br/>or are free to use for non-commercial purposes.</p>");
		
		return message;
	}
	
}
