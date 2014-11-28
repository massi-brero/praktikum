package q8388415.brero_massimiliano.PTNetEditor.utils;

import javax.swing.JOptionPane;

/**
 * offers some quick to apply static methods to ensure our net is consistent 
 * and does not violate any constraints.
 * 
 * @author Laptop
 *
 */
public class PTNNetValidator {
    
    public static Boolean isValidToken(String token) {
        
        Boolean isValid = true;

        try {
            if (token.equals("")) {
                isValid = false;
            } else if (Integer.parseInt(token) > 999 || Integer.parseInt(token) < 0) {
               isValid = false;
            }
        } catch (NumberFormatException e) {
           isValid = false;
        }
        
        return isValid;

    }
    
    public static Boolean isValidToken(int token) {
        
        return PTNNetValidator.isValidToken(String.valueOf(token));
        
    }

}
