package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

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
 * singleton pattern since we need in it multiple classes e.g. to add scale
 * listeners for new nodes and arcs. Beware that PTNControlPanel must be
 * initialized before other classes may use it.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * main panel
	 */
	private JPanel controllerPanel;
	
	/**
	 * Sub panels
	 */
	private PTNEnlargementPanel placeSizePanel;
	private PTNEnlargementPanel transitionSizePanel;
	private PTNEnlargementPanel allNodesSizePanel;
	private PTNEnlargementPanel arrowHeadSizePanel;
	
	/**
	 * Listeners for scaling events. 
	 */
	private PTNDesktop desktop = null;
	private PTNAppController appController = null;

	/**
	 * Singleton params.
	 */
	private static PTNControlPanel instance = null;
	private Boolean isInitialized = false;
	
	/**
	 * Icons for size panels partial.
	 * @return
	 */
	private final String PLACE_ICON = "/resources/icons/size-place-icon.png";
	private final String TRANSITION_ICON = "/resources/icons/size-rectangle-icon.png";
	private final String ARROW_ICON = "/resources/icons/size-arrow-icon.png";
	private final String ALL_NODES__ICON = "/resources/icons/size-nodes-icon.png";

	/**
	 * After being initialized other classes can get an instance of the control panel.
	 * This may be the case when we want to add new nodes and arcs as listeners for scaling events.
	 * 
	 * @return PTNControlPanel
	 */
	public static PTNControlPanel getInstance() {

		if (null == instance)
			instance = new PTNControlPanel();

		return instance;

	}

	/**
	 * This methods sets desktop an appController as listeners for
	 * scaling events.
	 * The app controller is responsible to set a flag so that other classes know
	 * that the arcs must be redrawn because the node size has changed.
	 * 
	 * The arcs are listeners for themselves since they will be informed when their 
	 * arrowhead size must be changed.
	 * 
	 * @param desktop
	 * @param appController
	 */
	public void initialize(PTNDesktop desktop, PTNAppController appController) {
		this.desktop = desktop;
		this.appController = appController;
		isInitialized = true;
		
		/**
		 * Set up panels.
		 */
		controllerPanel = new JPanel();
		// add enlargement Panels
		placeSizePanel = new PTNEnlargementPanel("Stellen", PLACE_ICON);
		transitionSizePanel = new PTNEnlargementPanel("Transitionen", TRANSITION_ICON);
		allNodesSizePanel = new PTNEnlargementPanel("Alle Knoten", ALL_NODES__ICON);
		arrowHeadSizePanel = new PTNEnlargementPanel("Pfeilspitzen", ARROW_ICON);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		controllerPanel.add(arrowHeadSizePanel);
		controllerPanel.add(placeSizePanel);
		controllerPanel.add(transitionSizePanel);
		controllerPanel.add(allNodesSizePanel);
		

		this.setSize(new Dimension((int)(this.desktop.getSize().getWidth()), 20));
		
		this.add(controllerPanel);
		setDoubleBuffered(true);
		
		//add listeners
		this.init();
	}

	/**
	 * Sets up listeners when first initialized.
	 */
	public void init() {

		this.setUpScaleListeners();

	}

	/**
	 * Sets global listener for any scaling event.
	 * So this method sets Listeners when scaling for any node type 
	 * or arc heads is fired.
	 * This way arcs can be redrawn when the node size changes.
	 * 
	 */
	private void setUpScaleListeners() {

		placeSizePanel.addScaleListener(appController);
		transitionSizePanel.addScaleListener(appController);
		allNodesSizePanel.addScaleListener(appController);

	}

	/**
	 * Adds an individual node view as a listener.
	 * @param place {@link PlaceView}
	 * 		Place observing change scale events 
	 * 		that will update its size when event for this node type 
	 * 		is fired.
	 * @throws PTNInitializationException
	 */
	public void addPlaceScaleListener(PlaceView place)
			throws PTNInitializationException {
		if (isInitialized) {
			placeSizePanel.addScaleListener(place);
			allNodesSizePanel.addScaleListener(place);
		} else {
			throw new PTNInitializationException();
		}

	}

	/**
	 * Adds an individual node view as a listener.
	 * @param place {@link TransitionView}
	 * 		Transition observing change scale events 
	 * 		that will update its size when event for this node type 
	 * 		is fired.
	 * @throws PTNInitializationException
	 */
	public void addTransitionScaleListener(TransitionView transition)
			throws PTNInitializationException {
		if (isInitialized) {
			transitionSizePanel.addScaleListener(transition);
			allNodesSizePanel.addScaleListener(transition);
		} else {
			throw new PTNInitializationException();
		}
	}

	/**
	 * Adds an arc view as a listener.
	 * @param arcView {@link ArcView}
	 * 		Arc observing change scale events 
	 * 		that will update its size when scaling event fpr arcs
	 * 		is fired.
	 * @throws PTNInitializationException
	 */
	public void addArcScaleListener(ArcView arcView)
			throws PTNInitializationException {
		if (isInitialized) {
			arrowHeadSizePanel.addScaleListener(arcView);
		} else {
			throw new PTNInitializationException();
		}
	}

}
