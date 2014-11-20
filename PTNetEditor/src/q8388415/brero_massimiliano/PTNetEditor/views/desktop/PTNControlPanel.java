package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNInitializationException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.views.ArcView;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.partials.PTNEnlargementPanel;

/**
 * Panel for scaling nodes and arrow heads. It's instatiated with a modified
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

	public static PTNControlPanel getInstance() {

		if (null == instance)
			instance = new PTNControlPanel();

		return instance;

	}

	public void initialize(PTNDesktop desktop, PTNAppController appController) {
		this.desktop = desktop;
		this.appController = appController;
		isInitialized = true;
		this.init();
	}

	private void init() {

		controllerPanel = new JPanel();
		// controllerPanel.setLayout(new FlowLayout());

		// add enlargement Panels
		placeSizePanel = new PTNEnlargementPanel("Place Size");
		transitionSizePanel = new PTNEnlargementPanel("Transition Size");
		arrowHeadSizePanel = new PTNEnlargementPanel("Arrowhead Size");
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		controllerPanel.add(placeSizePanel, BorderLayout.WEST);
		controllerPanel.add(transitionSizePanel, BorderLayout.CENTER);
		controllerPanel.add(arrowHeadSizePanel, BorderLayout.EAST);

		this.setSize(new Dimension(700, 20));
		this.setUpScaleListeners();
		this.add(controllerPanel);
		setDoubleBuffered(true);
	}

	/**
	 * Listen when scale for node changes
	 */
	private void setUpScaleListeners() {

		// for global arcs update.
		placeSizePanel.addScaleListener(appController);
		transitionSizePanel.addScaleListener(appController);

		try {
			if (isInitialized) {
				// for nodes.... They will upgrade themselves "individually".
				Iterator<NodeView> it_n = desktop.getNodeViews().iterator();
				while (it_n.hasNext()) {

					NodeView node = it_n.next();

					switch (node.getType()) {
					case place:
						this.addPlaceScaleListener((PlaceView) node);
						break;
					case transition:
						this.addTransitionScaleListener((TransitionView) node);
						break;
					default:
						break;
					}
				}

				// add all arcs as listener to controls
				Iterator<Map.Entry<String, ArcView>> it_a = desktop.getArcViews().entrySet().iterator();
				while (it_a.hasNext()) {
					ArcView arc = it_a.next().getValue();
					this.addArcScaleListener(arc);
				}

			} else {
				throw new PTNInitializationException();
			}
		} catch (PTNInitializationException e) {
			e.printStackTrace();
		}

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
