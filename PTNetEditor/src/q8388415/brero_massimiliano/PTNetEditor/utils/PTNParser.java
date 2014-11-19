package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.Point;
import java.io.File;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class PTNParser extends PNMLParser {

	/**
	 * Highest position of a node. So we know how high the desktop has to be.
	 */
	private int maxHeight;

	/**
	 * Position that lies furtherest on the right side. So we know how wide the
	 * desktop has to be.
	 */
	private int maxWidth;

	/**
	 * Instance of the net model. We'll generate the views from it later on.
	 */
	private PTNNet net;

	public PTNParser(File pnm, PTNNet net) {
		super(pnm);
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
                 node.setLocation(new Point(xPos, yPos));
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

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

}
