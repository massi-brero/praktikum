package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNArcConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNArcHelper;
import q8388415.brero_massimiliano.PTNetEditor.views.ArcView;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

/**
 * This controller handles net related operations like update and set-up operations.
 * If we want to add more types of nodes or change some view logic we just have
 * to change, replace this class.
 * 
 * @author brero
 * 
 */
public class PTNNetController {

	private PTNNet net;
	private PTNDesktop desktop;
	private PTNArcHelper arcHelper;
	final Point START_LOCATION_NEW_NODE = new Point(15,15);

	public PTNNetController(PTNNet net, PTNDesktop desktop) {
		this.net = net;
		this.desktop = desktop;
		this.arcHelper = new PTNArcHelper();
	}

	public ArrayList<NodeView> setUpNodes() {

		HashMap<String, PTNNode> nodes = net.getNodes();
		ArrayList<NodeView> nodeViewList = new ArrayList<NodeView>();
		NodeView nodeView = null;
		PTNNode node;
		Iterator<Map.Entry<String, PTNNode>> it = nodes.entrySet().iterator();

		try {
			while (it.hasNext()) {
				node = it.next().getValue();
				PTNNodeTypes type = node.getType();

				switch (type) {
				case place:
					nodeView = new PlaceView(node.getId(), ((PTNPlace) node).getToken());
					break;
				case transition:
					nodeView = new TransitionView(node.getId());
					break;
				default:
					throw new PTNNodeConstructionException(
							"Fehler: Kein korrekter Knotentyp!");
				}

				if (null == node.getLocation()) {
					throw new PTNNodeConstructionException(
							"Fehler: Knoten ohne Position");
				}

				nodeView.setName(node.getName());
				nodeView.setLocation(node.getLocation());
				nodeView.setNameLabel(node.getLabel());
				nodeViewList.add(nodeView);

			}
		} catch (PTNNodeConstructionException e) {
			// TODO Fehler-Dialog öffnen
			e.printStackTrace();
		}

		return nodeViewList;

	}

	public Hashtable<String, ArcView> setUpArcs() {

		HashMap<String, PTNArc> arcs = net.getArcs();
		Hashtable<String, ArcView> arcViewList = new Hashtable<String, ArcView>();
		ArcView arcView = null;
		PTNArc arc;

		Iterator<Map.Entry<String, PTNArc>> it = arcs.entrySet().iterator();

		try {
			while (it.hasNext()) {
				arc = (PTNArc) it.next().getValue();
				if (null == arc.getTarget().getLocation()
						|| null == arc.getSource().getLocation()) {
					throw new PTNArcConstructionException("Fehler: Allgemeiner Fehler beim Aufbau der Kanten");				
				}
				Point start = arcHelper.normalizeLocation(arc.getSource());
				Point end = arcHelper.normalizeLocation(arc.getTarget());
				arcView = new ArcView(arc.getId(), start, end);
				arcViewList.put(arc.getId(), arcView);
			}
		} catch (PTNArcConstructionException e) {
			// TODO Fehler-Dialog öffnen
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO Fehler-Dialog öffnen
			System.out.println(e.getMessage());
		}

		return arcViewList;

	}


	/**
	 * Handles redrawing all incoming and outgoing arcs for a node that has been moved for the view.
	 * This method will also update the arc and corresponding node in our net model.
	 * 
	 * @param source
	 */
	public void updateArcsForNode(NodeView nodeView) {
		
		PTNArc arc;
		PTNNode node = net.getNodeById(nodeView.getId());
		HashMap<String, PTNArc>arcsToMoveSource = net.getArcsBySource(node);
		HashMap<String, PTNArc>arcsToMoveTarget = net.getArcsByTarget(node);
		Iterator<Map.Entry<String, PTNArc>> it_s = arcsToMoveSource.entrySet().iterator();
		Iterator<Map.Entry<String, PTNArc>> it_t = arcsToMoveTarget.entrySet().iterator();
		
		while (it_s.hasNext()) {
			arc = (PTNArc) it_s.next().getValue();
			node.setLocation(nodeView.getLocation());
			arc.setSource(node);
			desktop.updateArc(arc.getId(), arcHelper.normalizeLocation(node), arcHelper.normalizeLocation(arc.getTarget()));
		}
		
		while (it_t.hasNext()) {
			arc = (PTNArc) it_t.next().getValue();
			node.setLocation(nodeView.getLocation());
			arc.setTarget(node);
			desktop.updateArc(arc.getId(), arcHelper.normalizeLocation(arc.getSource()), arcHelper.normalizeLocation(node));
		}
		
	}

	/**
	 * Removes node from net model an its view reprensatation.
	 * Removes also all incoming and outgoing arcs of given node.
	 * 
	 * @param nodeView
	 */
	public void removeNodeAndArcs(NodeView nodeView) {
		
		PTNNode node = net.getNodeById(nodeView.getId());
		HashMap<String, PTNArc>arcsToRemoveBySource = net.getArcsBySource(node);
		HashMap<String, PTNArc>arcsToRemoveByTarget = net.getArcsByTarget(node);
		Iterator<Map.Entry<String, PTNArc>> it_s = arcsToRemoveBySource.entrySet().iterator();
		Iterator<Map.Entry<String, PTNArc>> it_t = arcsToRemoveByTarget.entrySet().iterator();
		
		net.removeNode(node);
		desktop.getNodeViews().remove(nodeView);
		
		while (it_s.hasNext()) 
			this.removeArcsFromNetAndDesktop(it_s.next().getValue());
			
		
		while (it_t.hasNext())
			this.removeArcsFromNetAndDesktop(it_t.next().getValue());

		desktop.paintImmediately(desktop.getBounds());
	}
	
	private void removeArcsFromNetAndDesktop (PTNArc arc) {
		arc = (PTNArc) arc;
		desktop.removeArc(arc.getId());
		net.getArcs().remove(arc.getId());
	}

	/**
	 * Adds a new arc to desktop view and net model.
	 * Will show error panes if id = "" or there's already
	 * the id that was given.
	 * 
	 * @param id
	 * @param sourceView
	 * @param targetView
	 */
	public void addNewArc(String id, NodeView sourceView, NodeView targetView) {
		
		if (net.getArcs().containsKey(id)) {
			if(0 == this.showErrorPaneIdExists())
				desktop.callNewArcDialog(sourceView, targetView);
		} else if (id.equals("")) {
			if(0 == this.showErrorPaneEmptyId())
				desktop.callNewArcDialog(sourceView, targetView);
		} else {

			PTNNode source = net.getNodeById(sourceView.getId());
			source.setLocation(sourceView.getLocation());
			PTNNode target = net.getNodeById(targetView.getId());
			target.setLocation(targetView.getLocation());
			net.addArc(new PTNArc(id, source, target));
			desktop.updateArc(id, arcHelper.normalizeLocation(source).getLocation(), arcHelper.normalizeLocation(target).getLocation());
			
		}		
	}
	
	/**
	 * Adds a new node to desktop view and the net model. 
	 * Place token are initialized with 0. You may change initial 
	 * positioning with START_LOCATION_NEW_NODE.
	 * @param id
	 * @param name
	 * @param type
	 */
	public void addNewNode(PTNINodeDTO nodeParams) {
		
		String id = nodeParams.getId();
		String name = nodeParams.getNodeName();
		PTNNodeTypes type = nodeParams.getType();
		int token = nodeParams.getToken();
		NodeView nodeView = null;
		
		if (net.getArcs().containsKey(id)) {
			if(0 == this.showErrorPaneIdExists())
				desktop.callNewNodeDialog();
		} else if (id.equals("")) {
			if(0 == this.showErrorPaneEmptyId())
				desktop.callNewNodeDialog();
		} else {
			
			try {
				if (type == PTNNodeTypes.place) {
					net.addNode(new PTNPlace(name, id, START_LOCATION_NEW_NODE));
					nodeView = new PlaceView(id, 0);				
					((PlaceView)nodeView).updateToken(token);
				} else if (type == PTNNodeTypes.transition) {
					net.addNode(new PTNTransition(name, id, START_LOCATION_NEW_NODE));
					nodeView = new TransitionView(id);
				}
				
				nodeView.setNameLabel(name);
				nodeView.setLocation(START_LOCATION_NEW_NODE);
				desktop.addListenertoNode(nodeView);
				desktop.getNodeViews().add(nodeView);
				desktop.add(nodeView);
				
			} catch (PTNNodeConstructionException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private int showErrorPaneIdExists() {
		return JOptionPane.showConfirmDialog(desktop, "Diese ID ist bereits vergeben.", 
				"Ungültige ID", JOptionPane.WARNING_MESSAGE);
	}
	
	private int showErrorPaneEmptyId() {
		return JOptionPane.showConfirmDialog(desktop, "Sie müssen eine ID mit mind. einem Zeichen eingeben.", 
				"Ungültige ID", JOptionPane.WARNING_MESSAGE);
	}

}
