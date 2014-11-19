package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

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
	 * This list is used to store the arcs while the parsing process is going on.
	 * After every tag was parsed we will generate the arcs from this list. This way we can 
	 * use node objects and check if they were created correctly.
	 */
	ArrayList<ArcInformationListItem> arcList;

	public PTNParser(File pnm, PTNNet net) {
		super(pnm);
		arcList = new ArrayList<ArcInformationListItem>();
		this.net = net;
	}

	@Override
	public void newPlace(final String id) {

		try {
			PTNPlace place = new PTNPlace(id);
			net.addNode(place);
		} catch (PTNNodeConstructionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void newTransition(String id) {
		try {
			PTNTransition transition = new PTNTransition(id);
			net.addNode(transition);
		} catch (PTNNodeConstructionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void newArc(final String id, final String source, final String target) {
//	    PTNArc arc = new PTNArc(id, s, t)
//		net.addArc(arc);
	}

	@Override
	/**
	 * If no doe with the given id was found we choose to throw an error,
	 * because something definitely went wrong.
	 */
	public void setPosition(final String id, final String x, final String y) {
		PTNNode node = net.getNodeById(id);
	
		 try {
		     int xPos = Integer.parseInt(x);
		     int yPos = Integer.parseInt(y);
		     
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

	@Override
	public void setMarking(final String id, final String marking) {
	    PTNNode node = net.getNodeById(id);
	    
	    try {
	        int token = Integer.parseInt(marking);
            if(node.getType() == PTNNodeTypes.place)
               ( (PTNPlace)node).setToken(token);
        } catch (NumberFormatException e) {
            // TODO Fehlermeldung falsches Format
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
	 * Updates maxHeight and maxWidth. This way we know the dimensions dor our desktop.
	 * @param pos
	 * 		node position
	 */
	private void updateMaxDimensions(Point pos) {
		
		maxHeight = pos.getY() > maxHeight ? pos.getY() : maxHeight;
		maxWidth = pos.getX() > maxWidth ? pos.getX() : maxWidth;
		
	}
	
	/**
	 * Generates arcs after parsing finished.
	 * 
	 */
    private void handleParsingFinished() {
        
    	Iterator<ArcInformationListItem> it = arcList.iterator();
    	
    }
	
	/**
	 * This inner class is used as data sorage object. We will save arcs we have to generate in it.
	 * At the end of the parsing process the arc objects can be instantiated with the help of
	 * the arc item list.
	 * @author brero
	 *
	 */
	private class ArcInformationListItem {
		
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
