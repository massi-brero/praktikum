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
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIArcDTO;
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
 * This is a crucial controller for this application. 
 * <ul>
 * <li>It basically synchronizes and controls all data updates for nodes and arcs resulting <br/>
 * 		from drawing actions. (Adding and Removing elements)</li>
* <li>It handles all the data sync between models and views.</li>
* <li>It also controls the initial set up (also when loading a pnml file)</li>
* <li>It also syncs the arc redrawing. This of course could be done by the Desktop controller.<br/> 
* 		But since it involves updating the models when arcs are deleted this is the better place.
* 		This is done in an extra thread so computation happens in an parallel thread to the Swing thread.</li>
* </ul>
 * 
 * @author 8388415 - Massimiliano Brero
 * 
 */
public class PTNNetController implements Runnable {

    private PTNNet net;
    private PTNDesktop desktop;
    /**
     * Delegates for some basic an repeatedly used node and arc operations
     */
    private PTNArcHelper arcHelper;
    private PTNNodeHelper nodeHelper;

    public PTNNetController(PTNNet net, PTNDesktop desktop) {
        this.net = net;
        this.desktop = desktop;
        this.arcHelper = new PTNArcHelper(desktop, net);
        this.nodeHelper = new PTNNodeHelper(desktop, net);
    }

    /**
     * Setting up nodes when application reads a PNML file.
     * Needs a set up net model as base.
     */
    public void setUpNodeViews() {

        HashMap<String, PTNNode> nodes = net.getNodes();
        PTNNode node;
        NodeView nodeView = null;
        Iterator<Map.Entry<String, PTNNode>> it = nodes.entrySet().iterator();

        while (it.hasNext()) {
            node = it.next().getValue();

            PTNNodeTypes type = node.getType();

            if (type == PTNNodeTypes.STELLE) {

                nodeView = new PlaceView(node.getId(), node.getToken());
                nodeHelper.addPlaceListener((PlaceView) nodeView);

            } else if (type == PTNNodeTypes.TRANSITION) {

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
                Point start = arcHelper.normalizeLocation(arc, true);
                Point end = arcHelper.normalizeLocation(arc, false);
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
     * Updates node attributes after an update Window was closed.
     * 
     * @param sourceView
     * @param nodeUpdate
     */
    public void updateNodeAttributes(NodeView sourceView, PTNINodeDTO nodeUpdate) {
    	
    	String name = nodeUpdate.getNodeName();
    	PTNNode nodeModel = net.getNodeById(sourceView.getId());
    	
    	/**
    	 * update view and model information
    	 */
        if (sourceView instanceof PlaceView) {
        	int token = nodeUpdate.getToken();
        	((PlaceView) sourceView).updateToken(token);
        	// check if any transitions must be activated
        	((PTNPlace) nodeModel).setToken(token);
        	nodeHelper.updateAdjacentTransitionsState((PlaceView) sourceView);
        }

        sourceView.setName(name);
        nodeModel.setName(name);
        
    }
    
    /**
     * 
     * @param sourceView
     */
    public void updateNodeModelLocation(NodeView sourceView) {
    	PTNNode nodeModel = net.getNodeById(sourceView.getId());
    	nodeModel.setLocation(sourceView.getLocation());
    }
    

    /**
     * Handles redrawing all incoming and outgoing arcs for a node that has been
     * moved for the view. This method will also update the arc and
     * corresponding node in our net model.
     * 
     * @param nodeView {@link NodeView}
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
            desktop.updateArcs(arc.getId(), arcHelper.normalizeLocation(arc, true), arcHelper.normalizeLocation(arc, false));
        }

        while (it_t.hasNext()) {
            arc = (PTNArc) it_t.next().getValue();
            node.setLocation(nodeView.getLocation());
            arc.setTarget(node);
            desktop.updateArcs(arc.getId(), arcHelper.normalizeLocation(arc, true), arcHelper.normalizeLocation(arc, false));
        }

    }

    /**
     * Removes node from net model an its view representation. Removes also all
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

        while (it_s.hasNext())
            this.removeArcFromNetAndDesktop(it_s.next().getValue().getId());

        while (it_t.hasNext())
            this.removeArcFromNetAndDesktop(it_t.next().getValue().getId());
        
        net.removeNode(node);
        desktop.getNodeViews().remove(nodeView);
        desktop.remove(nodeView);

    }

    /**
     * Removes one arc both from the view and the net model.
     * This method will also update the activation status of a 
     * transition regardless if it's the source or the target of
     * the given arc.
     * 
     * @param id String
     */
    public void removeArcFromNetAndDesktop(String id) {
        PTNArc arcModel = net.getArcById(id);
        desktop.removeArc(id);
        net.getArcs().remove(id);

       /**
        *  Check which transition status has to be updated. Arc model is now removed from the list but we still 
        *  hold a reference.
        */
        if (arcModel.getTarget() != null && arcModel.getTarget().getType() == PTNNodeTypes.TRANSITION) {
        	nodeHelper.updateTransitionState((PTNTransition)arcModel.getTarget());
        }
        
    }

    /**
     * 
     * @param arcs HashMap<String, PTNIArcDTO>
     */
    public void removeArcsFromNetAndDesktop(HashMap<String, PTNIArcDTO> arcs) {
        Iterator<Map.Entry<String, PTNIArcDTO>> it = arcs.entrySet().iterator();
        PTNIArcDTO arc;

        while (it.hasNext()) {
            arc = it.next().getValue();
            this.removeArcFromNetAndDesktop(arc.getId());
        }
    }

    /**
     * Adds a new arc to desktop view and net model.
     * ID will be generated by the arc helper.
     * 
     * @param sourceView NodeView
     * @param targetView NodeView
     */
    public void addNewArc(NodeView sourceView, NodeView targetView) {
    	String id = arcHelper.generateId();
        PTNNode source = net.getNodeById(sourceView.getId());
        source.setLocation(sourceView.getLocation());
        PTNNode target = net.getNodeById(targetView.getId());
        target.setLocation(targetView.getLocation());
        PTNArc arcModel = new PTNArc(id, source, target);
        net.addArc(arcModel);
        Point normalizedSourceLocation = arcHelper.normalizeLocation(arcModel, true);
        Point normalizedTargetLocation = arcHelper.normalizeLocation(arcModel, false);

       if (arcHelper.isAlreadyOnDesktop(normalizedSourceLocation, normalizedTargetLocation)) {
            arcHelper.showErrorPaneDoubleArc();
            net.removeArc(arcModel);
        } else {

            ArcView arcView = new ArcView(id, normalizedSourceLocation, normalizedTargetLocation, this);
            arcHelper.initArcView(arcView, target, targetView);

        }
    }

    /**
     * Adds a new node to desktop view and the net model. Place token are
     * initialized with 0. You may change initial positioning with
     * START_LOCATION_NEW_NODE.
     * 
     * @param nodeInformation {@link PTNINodeDTO}
     */
    public void addNewNodeFromDialog(PTNINodeDTO nodeInformation) {
    	
        String id = nodeHelper.generateId();
        String name = nodeInformation.getNodeName();
        PTNNodeTypes type = nodeInformation.getType();
        int token = nodeInformation.getToken();
        NodeView nodeView = null;
        Point nodeLocation = nodeHelper.centerNodeLocation(nodeInformation);

            try {
                if (type == PTNNodeTypes.STELLE) {
                	
                	PTNPlace place = new PTNPlace(name, id, nodeLocation);
                	place.setToken(token);
                    net.addNode(place);

                    nodeView = new PlaceView(id, token);
                    nodeHelper.addPlaceListener((PlaceView) nodeView);

                } else if (type == PTNNodeTypes.TRANSITION) {

                    net.addNode(new PTNTransition(name, id, nodeLocation));

                    nodeView = new TransitionView(id);
                    nodeHelper.addTransitionListener((TransitionView) nodeView);

                }

                nodeHelper.initNodeView(name, nodeView, nodeLocation);

            } catch (PTNNodeConstructionException e) {
                e.printStackTrace();
            }

        
    }

    public void repaintDesktop() {
        desktop.paintImmediately(desktop.getBounds());
    }

    /**
     * Check if arcs must be redrawn e. g. because of scale
     * increasement/decreasement of nodes.
     * In this case both view and model are updated. 
     * A monitor on both the views an model list ensures that only
     * one thread may manipulate those lists at the same time.
     * 
     * @see PTNDesktop
     * @see PTNDesktopController
     * 
     */
    @Override
    public void run() {

        while (true) {
            
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // continue waiting even if interrupted
            }

            if (PTNAppController.redrawArcs) {

                HashMap<String, PTNArc> arcs = net.getArcs();
                ArcView arcView = null;
                Hashtable<String, ArcView> arcViewList = desktop.getArcViews();
                PTNArc arc;

                	synchronized (arcViewList) {
                        synchronized (arcs) {
                        Iterator<Map.Entry<String, PTNArc>> it = arcs.entrySet().iterator();
                		while (it.hasNext()) {
                			
                			arc = it.next().getValue();
                			arcView = arcViewList.get(arc.getId());
                			
                			Point start = arcHelper.normalizeLocation(arc, true);
                			Point end = arcHelper.normalizeLocation(arc, false);
                			
                			arcView.setStart(start);
                			arcView.setEnd(end);
                			
                			desktop.repaint();
                			PTNAppController.redrawArcs = false;
                			
                		}
					}
                        arcViewList.notifyAll();
				}
                
            }
        }
    }
}
