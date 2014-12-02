package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNInitializationException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.ContextMenuNodeWindow;

/**
 * Offers methods for dealing with node computations, like getting an instance of the control panel
 * for adding listeners for new nodes. Kind of a lightweight delegate class.
 * @author Laptop
 *
 */
public class PTNNodeHelper implements ActionListener {
	
	private PTNControlPanel controlPanel;
	private PTNDesktop desktop;
	private PTNNet net = null;
	private NodeView sourceView = null;
	private ContextMenuNodeWindow cMenu = null;
	
	public PTNNodeHelper(PTNDesktop desktop, PTNNet net) {
		this.desktop = desktop;
		this.net = net;
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
	
	public int showErrorPaneIdExists() {
		return JOptionPane.showConfirmDialog(desktop, "Diese Knoten-ID ist bereits vergeben.", 
				"Ung�ltige ID", JOptionPane.WARNING_MESSAGE);
	}
	
	public int showErrorPaneEmptyId() {
		return JOptionPane.showConfirmDialog(desktop, "Sie m�ssen eine Knoten-ID mit mind. einem Zeichen eingeben.", 
				"Ung�ltige ID", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * 
	 * @param source
	 */
	public void handleContextmenu(NodeView sourceView) {
		this.sourceView = sourceView;
		cMenu = new ContextMenuNodeWindow(this);
		cMenu.setLocation((int)sourceView.getLocation().getX() + 20, (int)sourceView.getLocation().getY() +20);
		cMenu.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		case "Attribute �ndern":
			this.handleChangeAttributes();
			break;
		case "Kanten l�schen":
			this.handleDeleteArcsDialog();
			break;
		default:
			break;
		}
		
	}

	private void handleChangeAttributes() {
		
		if (null != cMenu)
			cMenu.setVisible(false);
		
		
		if (null != sourceView)
			desktop.callNodeAttributeDialog(sourceView);
		
	}
	
	private void handleDeleteArcsDialog() {
		if (null != cMenu)
			cMenu.setVisible(false);
		
		
		if (null != sourceView)
			desktop.callDeleteArcsDialog(sourceView);
	}

    /**
     * Prepares a node view so it my be displayed on the desktop.
     * 
     * @param name
     * @param nodeView
     * @param nodeLocation
     */
    public void initNodeView(String name, NodeView nodeView, Point nodeLocation) {
        nodeView.setName(name);
        nodeView.setLocation(nodeLocation);
        desktop.addListenertoNode(nodeView);
        desktop.getNodeViews().add(nodeView);
        desktop.add(nodeView);
    }
    
    public void updateNodeModelLocation(NodeView nodeView) {
    	PTNNode nodeModel = net.getNodeById(nodeView.getId());
    	nodeModel.setLocation(nodeView.getLocation());
    }
    
    /**
     * When the token attribute of a place has been changed, this methods checks
     * if the state of succeeding transitions must be updated.
     * @param sourceView2 
     * 
     */
    public void updateAdjacentTransitions(PlaceView sourceView) {
    	
    	/**
    	 * We get the transitions by following the outgoing arcs.
    	 * This way we have all the parameters needed for PTNNet#
    	 */
    	Iterator<Map.Entry<String, PTNNode>> it = net.getSuccessors(sourceView.getId()).entrySet().iterator();
    	
    	while (it.hasNext()) {
    		
    		/**
    		 * Since the source is a place the target of the arc really must be a transition.
    		 * Casting is one more time safe. 
    		 */
    		PTNTransition transitionModel = (PTNTransition)it.next().getValue();
    		// Update the transitionView
    		NodeView transitionView = desktop.getNodeViewById(transitionModel.getId());
    		((TransitionView)transitionView).setIsActivated(net.activateTransition(transitionModel));

    	}
    	
    }

}
