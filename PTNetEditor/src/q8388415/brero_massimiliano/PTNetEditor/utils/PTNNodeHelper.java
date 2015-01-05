package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JPopupMenu;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNInitializationException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;
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
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNNodeHelper implements ActionListener {
	
	private PTNControlPanel controlPanel;
	private PTNDesktop desktop;
	private PTNNet net = null;
	private NodeView sourceView = null;
	private ContextMenuNodeWindow cMenu = null;
	final private String PREFIX_ID = "PTNNode_";	
	
	/**
	 * 
	 * @param desktop {@link PTNDesktop}
	 * @param net {@link PTNNet}
	 */
	public PTNNodeHelper(PTNDesktop desktop, PTNNet net) {
		this.desktop = desktop;
		this.net = net;
		controlPanel = PTNControlPanel.getInstance();
	}
	
	/**
	 * Adds a scale listener {@link PTNIScaleListener} to a
	 * place node. So view will react to scale events.
	 * 
	 * @param place {@link PlaceView}
	 */
	public void addPlaceListener(PlaceView place) {
		
		try {
			controlPanel.addPlaceScaleListener(place);
		} catch (PTNInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Adds a scale listener {@link PTNIScaleListener} to a
	 * transition node. So view will react to scale events.
	 * 
	 * @param transition {@link TransitionView}
	 */
	public void addTransitionListener(TransitionView transition) {
		
		try {
			controlPanel.addTransitionScaleListener(transition);
		} catch (PTNInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Generates a context menu for given node. Adds needed listeners to this
	 * {@link JPopupMenu} and will so make sure that the context menu: *
	 * <ul>
	 * <li>gets mouse events</li>
	 * <li>disappears with a click on the desktop (or the control panel)</li>
	 * <li>moves with the main frame</li>
	 * </ul>
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

	/**
	 * Handles the two kinds of dialogs coming from the node's context menu:
	 * <ul>
	 * <li>changing node attributes</li>
	 * <li>deleting arcs</li>
	 * </ul>
	 */
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

	/**
	 * Handles the dialogs coming from the node's context menu
	 * for changing node attributes like name or token number.
	 */
	private void handleChangeAttributesDialog() {
		
		if (null != cMenu)
			cMenu.setVisible(false);
		
		
		if (null != sourceView)
			desktop.callNodeAttributeDialog(sourceView);
		
	}
	
	/**
	 * Handles the dialogs coming from the node's context menu
	 * when user wants to delete some or all incoming and outgoing arcs.
	 */
	private void handleDeleteArcsDialog() {
		if (null != cMenu)
			cMenu.setVisible(false);
		
		
		if (null != sourceView)
			desktop.callDeleteArcsDialog(sourceView);
	}

    /**
     * Prepares a new node view so it my be displayed on the desktop.
     * A new transition view's status will be set to activated.
     * 
     * @param name String
     * 		Node name.
     * @param nodeView {@link NodeView}
     * @param nodeLocation Point
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
     * Sets the new location in node model when node was moved or created.
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
     * @param sourceView2 {@link PTNINodeDTO}
     * 		By using {@link PTNINodeDTO} we may pass either a node view or a node model.
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
	 * Generates a unique id concatenating id prefix and a random number.
	 * It also checks id number is already present
	 * 
	 * @return String
	 * 		New id for an arc.
	 */
	public String generateId() {

		Boolean idIsUnique = true;
		HashMap<String, PTNNode> nodes = net.getNodes();
        PTNNode node = null;
        int number = 0;
        String id = "";
		
		do {
			idIsUnique = true;
			number = (int)(Math.random()*1E6);
			id = PREFIX_ID.concat(String.valueOf(number));
	        Iterator<Map.Entry<String, PTNNode>> it = nodes.entrySet().iterator();
			
			while (it.hasNext()) {
				node = it.next().getValue();

				if (id.equals(node.getId())) {
					idIsUnique = false;
					break;
				}

			}
			
		} while (!idIsUnique);
		
		
		return id;
	}

	/**
	 * Relocates node so the node's icon enter is placed on the given location.
	 * This way a new node is places after a double click where the user 
	 * would expect it.
	 * 
	 * @param location Point
	 * @return Point 
	 * 		The center of the node.
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
	 * We use this when we want to make sure that an method is only called when the mouse is 
	 * right on the icon and not somewhere else in the node's JLabel object.
	 * 
	 * @param sourceNodeView {@link NodeView} 
	 * 		The coordinates are those relative to the node's JLabel (0,0) point.
	 * @param point {@link Point} 
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
