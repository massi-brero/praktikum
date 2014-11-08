package q8388415.brero_massimiliano.PTNetEditor.views;

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
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

/**
 * We may delegate net view drawing, update and set-up operations to this class.
 * If we want to add more types of nodes or change some view logic we just have
 * to change, replace this classs.
 * 
 * @author brero
 * 
 */
public class PTNNetViewHandler {

	private PTNNet net;
	private PTNDesktop desktop;

	public PTNNetViewHandler(PTNNet net, PTNDesktop desktop) {
		this.net = net;
		this.desktop = desktop;
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
				nodeView.setLabelText(node.getLabel());
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
				Point start = this.normalizeLocation(arc.getSource());
				Point end = this.normalizeLocation(arc.getTarget());
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
	 * 
	 * @param location
	 * @return Dimension now in the center of our NodeView
	 */
	private Point normalizeLocation(PTNNode node) {
		
		PTNNodeTypes type = node.getType();
		Dimension size = null;
		Point normalizedLocation = null;
		
		if (type == PTNNodeTypes.place) {
			//@todo handle this without initializing an object
			size = (new PlaceView("", 0)).getSize();
		} else if (type == PTNNodeTypes.transition) {
			size = (new TransitionView("")).getSize();
		}
		
		normalizedLocation = new Point(node.getLocation().x + (int)size.getWidth()/2, node.getLocation().y + (int)size.getHeight()/2);
		
		return normalizedLocation;
	}

	/**
	 * Handles redrawing all incoming and outgoing arcs for a node that has been moved for the view.
	 * This method will also update the arc and corresponding node in our net model.
	 * 
	 * @param source
	 */
	public void upDateNetAndView(NodeView nodeView) {
		
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
			desktop.updateArcs(arc.getId(), normalizeLocation(node), normalizeLocation(arc.getTarget()));
		}
		
		while (it_t.hasNext()) {
			arc = (PTNArc) it_t.next().getValue();
			node.setLocation(nodeView.getLocation());
			arc.setTarget(node);
			desktop.updateArcs(arc.getId(), normalizeLocation(arc.getSource()), normalizeLocation(node));
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

	public void addNewArc(String id, NodeView sourceView, NodeView targetView) {
		
		
		if (net.getArcs().containsKey(id)) {
			System.out.println("null");
			JOptionPane.showConfirmDialog(desktop, "Diese ID ist bereits vergeben.", "Ungültige ID", JOptionPane.WARNING_MESSAGE);
			
		} else if (id.equals("")) {
			System.out.println("leer");
			System.out.println("nix");
			JOptionPane.showConfirmDialog(desktop, "Sie müssen eine ID mit mind. einem Zeichen eingeben.", "Ungültige ID", JOptionPane.WARNING_MESSAGE);
			
		} else {

			PTNNode source = net.getNodeById(sourceView.getId());
			PTNNode target = net.getNodeById(targetView.getId());
			net.addArc(new PTNArc(id, source, target));
			desktop.updateArcs(id, normalizeLocation(source).getLocation(), normalizeLocation(target).getLocation());
			
		}
		
		
	}

}
