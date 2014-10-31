package q8388415.brero_massimiliano.PTNetEditor.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
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
		PTNNode node;
		ArrayList<NodeView> nodeViewList = new ArrayList<NodeView>();
		NodeView nodeView = null;

		Iterator<Map.Entry<String, PTNNode>> it = nodes.entrySet().iterator();

		try {
			while (it.hasNext()) {
				node = it.next().getValue();
				PTNNodeTypes type = node.getType();

				switch (type) {
				case place:
					nodeView = new PlaceView(((PTNPlace)node).getToken());

					break;
				case transition:
					nodeView = new TransitionView();
					break;
				default:
					throw new PTNNodeConstructionException("Not a node type");
				}
				
				nodeView.setName(node.getName());
				nodeView.setLocation(node.getLocation());
				nodeView.setLabelText(node.getLabel());
				nodeViewList.add(nodeView);

			}
		} catch (PTNNodeConstructionException e) {
			// TODO Fehler-Dialog �ffnen
			e.printStackTrace();
		}
		
		return nodeViewList;
		
	}

}
