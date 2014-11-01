package q8388415.brero_massimiliano.PTNetEditor.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNArcConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

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

	public PTNNetViewHandler(PTNNet net) {
		this.net = net;
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
					nodeView = new PlaceView(((PTNPlace) node).getToken());
					break;
				case transition:
					nodeView = new TransitionView();
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
						|| null == arc.getSource().getLocation()
							|| null == arc.getTarget().getLocation()) {
					throw new PTNArcConstructionException("Fehler: Allgemeiner Fehler beim Aufbau der Kanten");
					
				}
				arcView = new ArcView(arc.getId(), arc.getSource().getLocation(), arc.getTarget().getLocation());
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

}
