package q8388415.brero_massimiliano.PTNetEditor.models;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.utils.PNMLWriter;

/**
 * Handles the file reading operations. Since this could also be derived from a
 * database or any other source this kind of code is put in a model.
 * 
 * @author Laptop
 *
 */
public class PTNFileWriter {

    private PNMLWriter writer = null;
    private PTNNet net;
    private File file = null;
    private PNMLWriter pnmlWriter;

    public PTNFileWriter(PTNNet net) {

	this.net = net;

    }

    public void writePNMLFile(File file) {

	writer = new PNMLWriter(file);

	try {

	    pnmlWriter = new PNMLWriter(file);
	    pnmlWriter.startXMLDocument();
	    PTNNode node;
	    PTNArc arc;
	    HashMap<String, PTNNode> nodes = net.getNodes();
	    HashMap<String, PTNArc> arcs = net.getArcs();
	    Iterator<Map.Entry<String, PTNNode>> it_n = nodes.entrySet()
		    .iterator();
	    Iterator<Map.Entry<String, PTNArc>> it_a = arcs.entrySet()
		    .iterator();

	    while (it_n.hasNext()) {
		node = it_n.next().getValue();

		/**
		 * This is the place to extend if we have more node types
		 */
		if (node.getType() == PTNNodeTypes.place) {
		    pnmlWriter.addPlace(node.getId(), node.getName(),
			    String.valueOf(node.getLocation().getX()),
			    String.valueOf(node.getLocation().getY()),
			    String.valueOf(((PTNPlace) node).getToken()));
		} else if (node.getType() == PTNNodeTypes.place) {
		    pnmlWriter.addTransition(node.getId(), node.getName(),
			    String.valueOf(node.getLocation().getX()),
			    String.valueOf(node.getLocation().getY()));
		} else {
		    // add further node types
		}

	    }

	    while (it_a.hasNext()) {
		arc = it_a.next().getValue();
		pnmlWriter.addArc(arc.getId(), arc.getSource().getId(), 
				  arc.getTarget().getId());
	    }

	    pnmlWriter.finishXMLDocument();

	} catch (Exception e) {
	    // TODO: handle exception
	}

    }

}
