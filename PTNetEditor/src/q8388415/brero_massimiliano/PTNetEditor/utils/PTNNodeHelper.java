package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNInitializationException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
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
	final private String PREFIX_ID = "PTNNode_";	
	
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
	
	/**
	 * 
	 * @param source
	 */
	public void handleContextmenu(NodeView sourceView) {
		this.sourceView = sourceView;
		cMenu = new ContextMenuNodeWindow(this, sourceView);
		cMenu.setVisible(true);
		desktop.addMouseListener(cMenu);
		desktop.addAncestorListener(cMenu);
		controlPanel.addMouseListener(cMenu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		case "Attribute ändern":
			this.handleChangeAttributesDialog();
			break;
		case "Kanten löschen":
			this.handleDeleteArcsDialog();
			break;
		default:
			break;
		}
		
	}

	private void handleChangeAttributesDialog() {
		
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
     * Prepares a new node view so it my be displayed on the desktop.
     * A new transtion view's status wil be set to activated.
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
        this.updateTransitionState(nodeView);
        desktop.moveToFront(nodeView);
        desktop.repaint();
    }
    
    /**
     * 
     * @param nodeView
     */
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
    public void updateAdjacentTransitionsState(PTNINodeDTO source) {
    	
    	/**
    	 * We get the transitions by following the outgoing arcs.
    	 * This way we have all the parameters needed for PTNNet#
    	 */
    	Iterator<Map.Entry<String, PTNNode>> it = net.getSuccessors(source.getId()).entrySet().iterator();

    	while (it.hasNext()) {
    		
    		PTNTransition transitionModel = (PTNTransition)it.next().getValue();
    		this.updateTransitionState(transitionModel);

    	}
    	
    }

    /**
     * This method checks if the transition's activation state has to be
     * changed, e. g. after an arc has been deleted.
     * Accepts either node views or node models.
     * 
     * @param sourceView2
     * 		PTNINodeDTO ... So we may pass either a node view or a node model.
     */
	public void updateTransitionState(PTNINodeDTO node) {
		
		if (null != node && PTNNodeTypes.TRANSITION == node.getType()) {
    		/**
    		 * Since the source is a place the target of the arc really must be a transition.
    		 * Casting is one more time safe. 
    		 */
    		// Update the transitionView
			PTNTransition transitionModel = (PTNTransition)net.getNodeById(node.getId());
    		NodeView transitionView = desktop.getNodeViewById(transitionModel.getId());
    		((TransitionView)transitionView).setIsActivated(net.activateTransition(transitionModel));
		}
		
	}
	
	/**
	 * Generates a unique id concatenating id prefix and number of nodes 
	 * added so far +1.
	 * 
	 * @return
	 * 		String new id for an arc
	 */
	public String generateId() {
		return PREFIX_ID.concat(String.valueOf(net.getNumberOfNodes()+1));
	}

	/**
	 * Relocates node so the node's icon enter is placed on the given location.
	 * This way a new node is places after a double click where the user 
	 * would expect it.
	 * 
	 * @param location
	 * 		Point
	 * @return
	 */
	public Point centerNodeLocation(PTNINodeDTO nodeInformation) {
		Dimension size = null;
		Point location = null;
		Point inputLocation = nodeInformation.getLocation();
		
		if (nodeInformation.getType() == PTNNodeTypes.STELLE)
			size = PlaceView.getCurrentSize();
		else if (nodeInformation.getType() == PTNNodeTypes.TRANSITION)
			size = TransitionView.getCurrentSize();
		
		location = new Point(inputLocation.x - (int)size.width/2, inputLocation.y - (int)size.height/2);
		
		//Correct if node would be places outside the desktop.
		location.x = location.x < 0 ? 0 : location.x;
		location.y = location.y < 0 ? 0 : location.y;
		
		return location;
	}
	
	/**
	 * Checks if an icon in a node contains the given point.
	 * 
	 * @param sourceNodeView
	 *            {@link NodeView} The coordinates relative to the node#s JLabel are used here.
	 * @param point
	 *            {@link Point} 
	 * @return {@link Boolean}
	 */
	public boolean iconContainsPoint(NodeView sourceNodeView, Point point) {

		if (sourceNodeView != null && point != null) {
			Dimension viewSize = (sourceNodeView.getType() == PTNNodeTypes.STELLE) ? PlaceView.getCurrentSize() : TransitionView.getCurrentSize();
			int viewWidth = (int) viewSize.getWidth();
			int viewHeight = (int) viewSize.getHeight();
			int iconWidth = sourceNodeView.getIcon().getIconWidth();
			int iconHeight = sourceNodeView.getIcon().getIconHeight();

			Boolean xPositionInIcon = point.x >= (int) ((viewWidth - iconWidth) / 2) && point.x <= (int) ((viewWidth - iconWidth) / 2) + iconWidth;
			Boolean yPositionInIcon = point.x >= (int) ((viewHeight - iconHeight) / 2) && point.x <= (int) ((viewHeight - iconHeight) / 2) + iconHeight;

			return xPositionInIcon && yPositionInIcon;
		}
		return false;
	}

}
