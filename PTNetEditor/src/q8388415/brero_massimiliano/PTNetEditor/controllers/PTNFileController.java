package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNetContructionException;
import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNFileReader;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIFileListener;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.utils.PNMLWriter;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.PTNFileChooser;

/**
 * Basic class to read from PNML files or write in those files. Handles the file
 * dialog windows. This class has a file read and a file write model.
 * 
 * @see PTNFileReader
 * @see
 * 
 * @author Laptop
 *
 */
public class PTNFileController implements PTNIFileListener {

    private PTNDesktop desktop = null;
    private PTNNet net;
    private PTNFileReader readModel;
    private PNMLWriter xmlWriter;
    private static File lastChosenReadFile = null;
    private File destinationFile = null;

    /**
     * 
     * @param desktop
     * @param net
     */
    public PTNFileController(PTNDesktop desktop, PTNNet net) {
        readModel = new PTNFileReader();
        lastChosenReadFile = new File("");
        this.desktop = desktop;
        this.net = net;
    }

    /**
     * 
     */
    @Override
    public int writeToFile(PTNNet net) {
    	
    	this.writeFileDialog();
    	xmlWriter = new PNMLWriter(destinationFile);
        xmlWriter.startXMLDocument();
        PTNNode node;
        PTNArc arc;
        HashMap<String, PTNNode> nodes = net.getNodes();
        HashMap<String, PTNArc> arcs = net.getArcs();
        Iterator<Map.Entry<String, PTNNode>> it_n = nodes.entrySet().iterator();
        Iterator<Map.Entry<String, PTNArc>> it_a = arcs.entrySet().iterator();
        
        while (it_n.hasNext()) {
        	node = it_n.next().getValue();
        	
        	/**
        	 * This is the place to extend if we have more node types
        	 */
        	if (node.getType() == PTNNodeTypes.place) {
        		
        	}
        	
        }
        
        while (it_a.hasNext()) {
        	arc = it_a.next().getValue();
        }
        
        return 0;
    }

    /**
     * Calls read method in file model. So views do not really have to know the
     * file model. And we don not need to make a model a listener.
     * If there was an error while reading the method will stop the reading
     * process.
     * 
     * @return void
     * 
     */
    @Override
    public void readFromFile(PTNNet net) {
        
        PlaceView.resetSize();
        TransitionView.resetSize();
        this.openFileDialog();

        try {
            if (null != lastChosenReadFile) {
                net.reset();
                readModel.readFromFile(lastChosenReadFile, net);
            }
            
            desktop.init();
            desktop.setSize(readModel.getDesktopSize());
            
        } catch (PTNNetContructionException e) {
            this.callNetContructionWarning(e.getMessage());
        } catch (PTNNodeConstructionException e) {
            this.callNetContructionWarning(e.getMessage());
        }


    }

    private void callNetContructionWarning(String message) {
        
        JOptionPane.showConfirmDialog(desktop, message, "Import Fehler", JOptionPane.WARNING_MESSAGE);

    }

    /**
     * Let's the user choose a file and store last chosen file path so the
     * window will display that folder next time when it's opened.
     */
    private void openFileDialog() {

        PTNFileChooser fileDialog = new PTNFileChooser(lastChosenReadFile.getParent());
        int val = fileDialog.showDialog(desktop, "Netz-Datei w�hlen");

        if (0 == val) {
            lastChosenReadFile = fileDialog.getSelectedFile();
        }

    }
    
    /**
     * Let's the user choose a file and store last chosen file path so the
     * window will display that folder next time when it's opened.
     */
    private void writeFileDialog() {

        PTNFileChooser fileDialog = new PTNFileChooser(lastChosenReadFile.getParent());
        int val = fileDialog.showDialog(desktop, "Netz-Datei w�hlen");

        if (0 == val) {
            lastChosenReadFile = fileDialog.getSelectedFile();
        }

    }

}
