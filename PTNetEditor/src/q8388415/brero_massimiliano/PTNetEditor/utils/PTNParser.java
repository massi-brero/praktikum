package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNetContructionException;
import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

/**
 * 
 * 
 * @author Laptop
 *
 */
public class PTNParser extends PNMLParser {

    /**
     * Highest position of a node. So we know how high the desktop has to be.
     */
    private double maxHeight;

    /**
     * Position that lies furtherest on the right side. So we know how wide the
     * desktop has to be.
     */
    private double maxWidth;

    /**
     * Instance of the net model. We'll generate the views from it later on.
     */
    private PTNNet net;

    /**
     * This list is used to store the arcs while the parsing process is going
     * on. After every tag was parsed we will generate the arcs from this list.
     * This way we can use node objects and check if they were created
     * correctly.
     */
    ArrayList<ArcInformationListItem> arcList;

    /**
     * This class attributes are used to check if we have multiple ids.
     */
    ArrayList<String> usedIdNodes;
    ArrayList<String> usedIdArcs;

    /**
     * 
     * @param pnm
     * @param net
     */
    public PTNParser(File pnm, PTNNet net) {
        super(pnm);
        usedIdNodes = new ArrayList<String>();
        usedIdArcs = new ArrayList<String>();
        arcList = new ArrayList<ArcInformationListItem>();
        this.net = net;
    }

    @Override
    public void newPlace(final String id) throws PTNNodeConstructionException {

            PTNPlace place = new PTNPlace(id);
            if (!usedIdNodes.contains(id)) {
                usedIdNodes.add(id);
                net.addNode(place);
            } else {
                throw new PTNNodeConstructionException("Die Knoten-ID " + id + " wurde mehrfach vergeben");
            }


    }

    @Override
    public void newTransition(String id) throws PTNNodeConstructionException{

            PTNTransition transition = new PTNTransition(id);
            if (!usedIdNodes.contains(id)) {
                usedIdNodes.add(id);
                net.addNode(transition);
            } else {
                throw new PTNNodeConstructionException("Die Knoten-ID " + id + " wurde mehrfach vergeben");
            }

    }

    /**
     * If 2 arcs have the same id or the same source/target combination an {@link PTNNetContructionException}
     * is thrown.
     */
    @Override
    public void newArc(final String id, final String source, final String target) throws PTNNetContructionException {
        /**
         * TODO Abfangen von zwei Kanten mit gleicher Start/Ziel-Kombi.
         */
        if (usedIdArcs.contains(id)) {
        	throw new PTNNetContructionException("Die Kanten-ID " + id + " wurde mehrfach vergeben");
        } else if (sourceTargetCombinationAlreadyExists(id, source, target)){
        	throw new PTNNetContructionException("Die Kante " + id + " existiert bereits unter einer anderen ID");
        } else {
        	usedIdArcs.add(id);
            arcList.add(new ArcInformationListItem(id, source, target));
        }

    }


	@Override
    /**
     * If no node with the given id was found we choose to throw an error,
     * because something definitely went wrong.
     */
    public void setPosition(final String id, final String x, final String y) {
        PTNNode node = net.getNodeById(id);

        try {
            int xPos = (int)Math.floor(Double.parseDouble(x));
            int yPos = (int)Math.floor(Double.parseDouble(y));

            if (null != node) {
                Point pos = new Point(xPos, yPos);
                node.setLocation(pos);
                this.updateMaxDimensions(pos);
            }
        } catch (NumberFormatException e) {
            // TODO Fehlermeldung falsches Format
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setName(final String id, final String name) {
        net.getNodeById(id).setName(name);
    }

    /**
     * Checks if the token fulfills our constraints. We simply ignore the token
     * attribute for a node that is not a place.
     * 
     */
    @Override
    public void setMarking(final String id, final String marking) throws PTNNodeConstructionException {
        PTNNode node = net.getNodeById(id);

        if (!PTNNetValidator.isValidToken(marking)) {
            throw new PTNNodeConstructionException("Als Token sind nur Zahlen von 0-999 zugelassen");
        } else if (node.getType() == PTNNodeTypes.STELLE) {
            ((PTNPlace) node).setToken(Integer.parseInt(marking));
        } else {
            // Do nothing.
        }

    }

    /**
     * 
     * @return
     */
    public double getMaxHeight() {
        return maxHeight;
    }

    /**
     * 
     * @return
     */
    public double getMaxWidth() {
        return maxWidth;
    }

    /**
     * Updates maxHeight and maxWidth. This way we know the dimensions dor our
     * desktop.
     * 
     * @param pos
     *            node position
     */
    private void updateMaxDimensions(Point pos) {

        maxHeight = pos.getY() > maxHeight ? pos.getY() : maxHeight;
        maxWidth = pos.getX() > maxWidth ? pos.getX() : maxWidth;

    }
    
    /**
     * 
     * Checks if there already is an arc with the same source/target combination
     * but different id.
     * 
     * @param id String
     * @param source arcList
     * @param target arcList
     * @return Boolean
     */
	private boolean sourceTargetCombinationAlreadyExists(String id, String source, String target) {
		Boolean combiExists = false;
		
		if (!id.isEmpty() && !source.isEmpty() && !target.isEmpty()) {
			Iterator<ArcInformationListItem> it = arcList.iterator();
			
			while (it.hasNext()) {
				ArcInformationListItem arc = it.next();
				if (arc.getSource().equals(source) 
						&& arc.getTarget().equals(target)
							&& !arc.getId().equals(id)) {
					combiExists = true;
					break;
				}
				
			}
			
		}
		return combiExists;
	}

    /**
     * Generates arcs after parsing finished. Here we check if the 2 nodes
     * needed for our arc really exists. Otherwise an Exception will be thrown.
     * @throws PTNNetContructionException 
     */
    protected void handleParsingFinished() throws PTNNetContructionException {

        Iterator<ArcInformationListItem> it = arcList.iterator();
        ArcInformationListItem item;

        while (it.hasNext()) {
            item = it.next();

            if (null != net.getNodeById(item.getSource()) && null != net.getNodeById(item.getTarget()) && !item.getId().isEmpty()) {
                net.addArc(new PTNArc(item.getId(), net.getNodeById(item.getSource()), net.getNodeById(item.getTarget())));
            } else {
                throw new PTNNetContructionException("Fehlerhafte Kante in der Datei vorhanden");
            }

        }

    }

    /**
     * This inner class is used as data storage object. We will save arcs we
     * have to generate in it. At the end of the parsing process the arc objects
     * can be instantiated with the help of the arc item list.
     * 
     * @author brero
     *
     */
    protected class ArcInformationListItem {

        private String id = null;
        private String source = null;
        private String target = null;

        public ArcInformationListItem(String id, String source, String target) {
            this.id = id;
            this.source = source;
            this.target = target;
        }

        public String getId() {
            return id;
        }

        public String getSource() {
            return source;
        }

        public String getTarget() {
            return target;
        }

    }

}
