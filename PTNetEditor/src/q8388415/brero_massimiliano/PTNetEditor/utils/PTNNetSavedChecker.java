package q8388415.brero_massimiliano.PTNetEditor.utils;

import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;

/**
 * A little helper that checks if the klatest changes made have been saved.
 * @author Laptop 
 */
public class PTNNetSavedChecker {

	/**
	 * Returns true if changes have been saved and false if not.
	 * @return
	 */
	public static Boolean netHasBeenSaved() {
		if (!PTNAppController.isNewestStateSaved()) {
			int val = JOptionPane.showConfirmDialog(null, "Wollen Sie diese Aktion wirklich durchführen ohne die gemachten Änderungen abzuspeichern?", 
					"Änderungen nicht gespeichert.", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			
			if (val == JOptionPane.YES_OPTION)
				return true;
			else 
				return false;
		}
		
		return true;
	}

}
