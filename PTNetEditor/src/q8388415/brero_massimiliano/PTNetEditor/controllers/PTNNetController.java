package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.awt.Point;
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
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNArcHelper;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNNodeHelper;
import q8388415.brero_massimiliano.PTNetEditor.views.ArcView;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

/**
 * This controller handles net related operations like update and set-up
 * operations. If we want to add more types of nodes or change some view logic
 * we just have to change, replace this class.
 * 
 * @author brero
 * 
 */
public class PTNNetController implements Runnable {

    private PTNNet net;
    private PTNDesktop desktop;
    private PTNArcHelper arcHelper;
    private PTNNodeHelper nodeHelper;
    final Point START_LOCATION_NEW_NODE = new Point(15, 15);

    public PTNNetController(PTNNet net, PTNDesktop desktop) {
        this.net = net;
        this.desktop = desktop;
        this.arcHelper = new PTNArcHelper(desktop);
        this.nodeHelper = new PTNNodeHelper(desktop);
    }

    /**
     * Setting up nodes when application reads a PNML file.
     * 
     * @return void
     */
    public void setUpNodeViews() {

        HashMap<String, PTNNode> nodes = net.getNodes();
        PTNNode node;
        NodeView nodeView = null;
        Iterator<Map.Entry<String, PTNNode>> it = nodes.entrySet().iterator();

        while (it.hasNext()) {
            node = it.next().getValue();

            PTNNodeTypes type = node.getType();

            if (type == PTNNodeTypes.place) {

                nodeView = new PlaceView(node.getId(), node.getToken());
                nodeHelper.addPlaceListener((PlaceView) nodeView);

            } else if (type == PTNNodeTypes.transition) {

                nodeView = new TransitionView(node.getId());
                ((TransitionView)nodeView).setIsActivated(((PTNTransition)node).isActivated());
                nodeHelper.addTransitionListener((TransitionView) nodeView);

            }

            nodeHelper.initNodeView(node.getName(), nodeView, node.getLocation());

        }

    }

    /**
     * Setting up arcs when application reads a PNML file.
     * 
     * @return void
     */
    public Hashtable<String, ArcView> setUpArcs() {

        HashMap<String, PTNArc> arcs = net.getArcs();
        Hashtable<String, ArcView> arcViewList = new Hashtable<String, ArcView>();
        ArcView arcView = null;
        PTNArc arc;

        Iterator<Map.Entry<String, PTNArc>> it = arcs.entrySet().iterator();

        try {
            while (it.hasNext()) {
                arc = (PTNArc) it.next().getValue();
                if (null == arc.getTarget().getLocation() || null == arc.getSource().getLocation()) {
                    throw new PTNArcConstructionException("Fehler: Allgemeiner Fehler beim Aufbau der Kanten");
                }
                Point start = arcHelper.normalizeLocation(arc.getSource());
                Point end = arcHelper.normalizeLocation(arc.getTarget());
                arcView = new ArcView(arc.getId(), start, end, this);
                arcHelper.addArcListener(arcView);
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
     * Handles redrawing all incoming and outgoing arcs for a node that has been
     * moved for the view. This method will also update the arc and
     * corresponding node in our net model.
     * 
     * @param source
     */
    public void updateArcsForNode(NodeView nodeView) {

        PTNArc arc;
        PTNNode node = net.getNodeById(nodeView.getId());
        HashMap<String, PTNArc> arcsToMoveSource = net.getArcsBySource(node);
        HashMap<String, PTNArc> arcsToMoveTarget = net.getArcsByTarget(node);
        Iterator<Map.Entry<String, PTNArc>> it_s = arcsToMoveSource.entrySet().iterator();
        Iterator<Map.Entry<String, PTNArc>> it_t = arcsToMoveTarget.entrySet().iterator();

        while (it_s.hasNext()) {
            arc = (PTNArc) it_s.next().getValue();
            node.setLocation(nodeView.getLocation());
            arc.setSource(node);
            desktop.updateArcs(arc.getId(), arcHelper.normalizeLocation(node), arcHelper.normalizeLocation(arc.getTarget()));
        }

        while (it_t.hasNext()) {
            arc = (PTNArc) it_t.next().getValue();
            node.setLocation(nodeView.getLocation());
            arc.setTarget(node);
            desktop.updateArcs(arc.getId(), arcHelper.normalizeLocation(arc.getSource()), arcHelper.normalizeLocation(node));
        }

    }

    /**
     * Removes node from net model an its view reprensatation. Removes also all
     * incoming and outgoing arcs of given node.
     * 
     * @param nodeView
     */
    public void removeNodeAndArcs(NodeView nodeView) {

        PTNNode node = net.getNodeById(nodeView.getId());
        HashMap<String, PTNArc> arcsToRemoveBySource = net.getArcsBySource(node);
        HashMap<String, PTNArc> arcsToRemoveByTarget = net.getArcsByTarget(node);
        Iterator<Map.Entry<String, PTNArc>> it_s = arcsToRemoveBySource.entrySet().iterator();
        Iterator<Map.Entry<String, PTNArc>> it_t = arcsToRemoveByTarget.entrySet().iterator();

        net.removeNode(node);
        desktop.getNodeViews().remove(nodeView);

        while (it_s.hasNext())
            this.removeArcFromNetAndDesktop(it_s.next().getValue());

        while (it_t.hasNext())
            this.removeArcFromNetAndDesktop(it_t.next().getValue());

        desktop.paintImmediately(desktop.getBounds());
    }

    /**
     * Removes one arc both from the view and the net model.
     * 
     * @param arc
     *            PTNArc
     */
    private void removeArcFromNetAndDesktop(PTNArc arc) {
        arc = (PTNArc) arc;
        desktop.removeArc(arc.getId());
        net.getArcs().remove(arc.getId());
    }

    /**
     * 
     * @param arc
     */
    public void removeArcsFromNetAndDesktop(HashMap<String, PTNArc> arcs) {
        Iterator<Map.Entry<String, PTNArc>> it = arcs.entrySet().iterator();
        PTNArc arc;

        while (it.hasNext()) {
            arc = it.next().getValue();
            this.removeArcFromNetAndDesktop(arc);
        }

        desktop.repaint();

    }

    /**
     * Adds a new arc to desktop view and net model after user calls the new arc
     * dialog. Will show error panes if id = "" or the given id already already
     * exists.
     * 
     * @param id
     * @param sourceView
     * @param targetView
     */
    public void addNewArcFromDialog(String id, NodeView sourceView, NodeView targetView) {
        PTNNode source = net.getNodeById(sourceView.getId());
        source.setLocation(sourceView.getLocation());
        PTNNode target = net.getNodeById(targetView.getId());
        target.setLocation(targetView.getLocation());
        Point normalizedSourceLocation = arcHelper.normalizeLocation(source);
        Point normalizedTargetLocation = arcHelper.normalizeLocation(target);

        if (net.getArcs().containsKey(id)) {
            if (0 == arcHelper.showErrorPaneIdExists())
                desktop.callNewArcDialog(sourceView, targetView);
        } else if (id.equals("")) {
            if (0 == arcHelper.showErrorPaneEmptyId())
                desktop.callNewArcDialog(sourceView, targetView);
        } else if (arcHelper.isAlreadyOnDesktop(normalizedSourceLocation, normalizedTargetLocation)) {
            arcHelper.showErrorPaneDoubleArc();
        } else {

            net.addArc(new PTNArc(id, source, target));
            arcHelper.initArcView(id, normalizedSourceLocation, normalizedTargetLocation, this);

        }
    }

    /**
     * Adds a new node to desktop view and the net model. Place token are
     * initialized with 0. You may change initial positioning with
     * START_LOCATION_NEW_NODE.
     * 
     * @param id
     * @param name
     * @param type
     */
    public void addNewNodeFromDialog(PTNINodeDTO nodeInformation) {

        String id = nodeInformation.getId();
        String name = nodeInformation.getNodeName();
        PTNNodeTypes type = nodeInformation.getType();
        int token = nodeInformation.getToken();
        NodeView nodeView = null;
        Point nodeLocation = this.determineNodeLocation(nodeInformation);

        if (net.getNodes().containsKey(id)) {
            if (0 == nodeHelper.showErrorPaneIdExists())
                desktop.callNewNodeDialog();
        } else if (id.equals("")) {
            if (0 == nodeHelper.showErrorPaneEmptyId())
                desktop.callNewNodeDialog();
        } else {

            try {
                if (type == PTNNodeTypes.place) {

                    net.addNode(new PTNPlace(name, id, nodeLocation));

                    nodeView = new PlaceView(id, token);
                    nodeHelper.addPlaceListener((PlaceView) nodeView);

                } else if (type == PTNNodeTypes.transition) {

                    net.addNode(new PTNTransition(name, id, nodeLocation));

                    nodeView = new TransitionView(id);
                    nodeHelper.addTransitionListener((TransitionView) nodeView);

                }

                nodeHelper.initNodeView(name, nodeView, nodeLocation);

            } catch (PTNNodeConstructionException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Checks and returns node location by determining if nodeInformation
     * contains information about a new node or it is information about a node
     * from a file that already has a location.
     * 
     * @param nodeInformation
     * @return Point default location or given location for node
     */
    private Point determineNodeLocation(PTNINodeDTO nodeInformation) {

        if (nodeInformation instanceof PTNNode)
            return ((PTNNode) nodeInformation).getLocation();
        else
            return START_LOCATION_NEW_NODE;

    }

    public void repaintDesktop() {
        desktop.paintImmediately(desktop.getBounds());
    }

    public void run() {

        while (true) {
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // continue waiting even if interrupted
            }
            /**
             * Check if arcs must be redrawn e. g. because of scale
             * increasement/decreasement of nodes
             */
            if (PTNAppController.redrawArcs) {
                System.out.println("redraw_listener");
                HashMap<String, PTNArc> arcs = net.getArcs();
                ArcView arcView = null;
                Hashtable<String, ArcView> arcViewList = desktop.getArcViews();
                PTNArc arc;

                Iterator<Map.Entry<String, PTNArc>> it = arcs.entrySet().iterator();

                while (it.hasNext()) {

                    arc = it.next().getValue();
                    arcView = arcViewList.get(arc.getId());

                    Point start = arcHelper.normalizeLocation(arc.getSource());
                    Point end = arcHelper.normalizeLocation(arc.getTarget());

                    arcView.setStart(start);
                    arcView.setEnd(end);

                }

                desktop.repaint();
                PTNAppController.redrawArcs = false;

            }

        }

    }

}
