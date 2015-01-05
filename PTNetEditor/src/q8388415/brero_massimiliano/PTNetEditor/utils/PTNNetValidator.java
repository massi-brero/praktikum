package q8388415.brero_massimiliano.PTNetEditor.utils;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;

/**
 * Offers some quick to apply static methods to ensure our net is consistent 
 * and does not violate any constraints.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNNetValidator {
    
	/**
	 * Checks if the input can be converted to a valid token number
	 * which would be an integer from 0 to 999;
	 * 
	 * @param token
	 * @return
	 */
    public static Boolean isValidToken(String token) {
        
        Boolean isValid = true;

        try {
            if (token.equals("")) {
                isValid = false;
            } else if (Integer.parseInt(token) > PTNAppController.MAX_TOKEN || Integer.parseInt(token) < 0) {
               isValid = false;
            }
        } catch (NumberFormatException e) {
           isValid = false;
        }
        
        return isValid;

    }
    
    /**
     * Checks basically if a negative width or height is given for the desktop.
     * 
     * @param width
     * 		int user input for desktop width
     * @param height
     * 		int user input for desktop width
     * @return
     * 		Boolean is input a valid desktop size
     */
    public static Boolean isValidDesktopDimensions(String width, String height) {
    	
        Boolean isValid = true;

        try {
            if (width.equals("") || height.equals("")) {
                isValid = false;
            } else if (Integer.parseInt(width) < 0 || Integer.parseInt(height) < 0) {
               isValid = false;
            }
        } catch (NumberFormatException e) {
           isValid = false;
        }
        
        return isValid;
    	
    }
    
    /**
     * Uses {@link PTNNetValidator#isValidToken(int)}
     * 
     * @param token int
     * @return Boolean
     */
    public static Boolean isValidToken(int token) {
        
        return PTNNetValidator.isValidToken(String.valueOf(token));
        
    }

}
