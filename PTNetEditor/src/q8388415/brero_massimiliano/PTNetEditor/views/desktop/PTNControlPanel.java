package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNInitializationException;
import q8388415.brero_massimiliano.PTNetEditor.views.ArcView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.partials.PTNEnlargementPanel;

/**
 * Panel for scaling nodes and arrow heads. It's instantiated with a modified
 * singleton pattern since we need in in multiple classes e.g. to add scale
 * listeners for new nodes and arcs. Beware that PTNControlPanel must be
 * initialized before other classes may use it.
 * 
 * @author Laptop
 *
 */
public class PTNControlPanel extends JPanel {

	private JPanel controllerPanel;
	private PTNDesktop desktop = null;
	private PTNAppController appController = null;
	private PTNEnlargementPanel placeSizePanel;
	private PTNEnlargementPanel transitionSizePanel;
	private PTNEnlargementPanel arrowHeadSizePanel;
	private static PTNControlPanel instance = null;
	private Boolean isInitialized = false;
	/**
	 * Icons for size panels.
	 * @return
	 */
	private final String PLACE_ICON = "icons/size-place-icon.png";
	private final String TRANSITION_ICON = "icons/size-rectangle-icon.png";
	private final String ARROW_ICON = "icons/size-arrow-icon.png";

	public static PTNControlPanel getInstance() {

		if (null == instance)
			instance = new PTNControlPanel();

		return instance;

	}

	public void initialize(PTNDesktop desktop, PTNAppController appController) {
		this.desktop = desktop;
		this.appController = appController;
		isInitialized = true;
		
		/**
		 * Set up panel.
		 */
		controllerPanel = new JPanel();
		// controllerPanel.setLayout(new FlowLayout());

		// add enlargement Panels
		placeSizePanel = new PTNEnlargementPanel("Place Size", PLACE_ICON);
		transitionSizePanel = new PTNEnlargementPanel("Transition Size", TRANSITION_ICON);
		arrowHeadSizePanel = new PTNEnlargementPanel("Arrowhead Size", ARROW_ICON);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		controllerPanel.add(placeSizePanel);
		controllerPanel.add(transitionSizePanel);
		controllerPanel.add(arrowHeadSizePanel);

		this.setSize(new Dimension((int)(desktop.getSize().getWidth()), 20));
		
		this.add(controllerPanel);
		setDoubleBuffered(true);
		
		//add listeners
		this.init();
	}

	public void init() {


		this.setUpScaleListeners();

	}

	/**
	 * Listen when scaling for node or arc heads is chosen.
	 */
	private void setUpScaleListeners() {

		// for global arcs update.
		placeSizePanel.addScaleListener(appController);
		transitionSizePanel.addScaleListener(appController);

	}

	public void addPlaceScaleListener(PlaceView place)
			throws PTNInitializationException {
		if (isInitialized) {
			placeSizePanel.addScaleListener(place);
		} else {
			throw new PTNInitializationException();
		}

	}

	public void addTransitionScaleListener(TransitionView transition)
			throws PTNInitializationException {
		if (isInitialized) {
			transitionSizePanel.addScaleListener(transition);
		} else {
			throw new PTNInitializationException();
		}
	}

	public void addArcScaleListener(ArcView arcView)
			throws PTNInitializationException {
		if (isInitialized) {
			arrowHeadSizePanel.addScaleListener(arcView);
		} else {
			throw new PTNInitializationException();
		}
	}

}
