package q8388415.brero_massimiliano.PTNetEditor.utils;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNInitializationException;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;

/**
 * Offers methods for dealing with node computations, like getting an instance of the control panel
 * for adding listeners for new nodes. Kind of a lightweight delegate class.
 * @author Laptop
 *
 */
public class PTNNodeHelper {
	
	private PTNControlPanel controlPanel;
	
	public PTNNodeHelper() {
		controlPanel = PTNControlPanel.getInstance();
	}
	
	public void addPlaceListener(PlaceView place) {
		
		try {
			controlPanel.addPlaceScaleListener(place);
		} catch (PTNInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addTransitionListener(TransitionView transition) {
		
		try {
			controlPanel.addTransitionScaleListener(transition);
		} catch (PTNInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
