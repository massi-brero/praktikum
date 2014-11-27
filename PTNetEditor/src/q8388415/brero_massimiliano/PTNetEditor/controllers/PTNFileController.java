package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNetContructionException;
import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNFileReader;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNFileWriter;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIFileListener;
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
    private static File lastOpenedFile = null;
    private static File lastSavedFile = null;
    private File destinationFile = null;

    /**
     * 
     * @param desktop
     * @param net
     */
    public PTNFileController(PTNDesktop desktop, PTNNet net) {
	readModel = new PTNFileReader();
	lastOpenedFile = new File("");
	this.desktop = desktop;
	this.net = net;
    }

    /**
     * 
     */
    @Override
    public int writeToFile(PTNNet net) {

	this.writeFileDialog();

	return 0;
    }

    /**
     * Calls read method in file model. So views do not really have to know the
     * file model. And we don not need to make a model a listener. If there was
     * an error while reading the method will stop the reading process.
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

	    if (null != lastOpenedFile) {
		net.reset();
		readModel.readFromFile(lastOpenedFile, net);
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

	JOptionPane.showConfirmDialog(desktop, message, "Import Fehler",
		JOptionPane.WARNING_MESSAGE);

    }

    /**
     * Let's the user choose a file and store last chosen file path so the
     * window will display that folder next time when it's opened.
     */
    private void openFileDialog() {

	PTNFileChooser fileDialog = new PTNFileChooser(
		lastOpenedFile.getParent());
	int val = fileDialog.showDialog(desktop, "Netz-Datei wählen");

	if (0 == val) {
	    lastOpenedFile = fileDialog.getSelectedFile();
	}

    }

    /**
     * Let's the user choose a directory and a filename. Those two will be
     * stored.
     */
    private void writeFileDialog() {

	PTNFileChooser fileDialog = new PTNFileChooser();
	fileDialog.setCurrentDirectory(lastSavedFile);

	int val = fileDialog.showSaveDialog(desktop);

	if (0 == val) {
	    lastSavedFile = fileDialog.getSelectedFile();

	    //
	    if (null != lastSavedFile) {
		this.correctPNMLExtension(lastSavedFile);
		//System.out.println(lastSavedFile.getAbsolutePath());
		if (confirmSave(lastSavedFile)) {
		    PTNFileWriter writeModel = new PTNFileWriter(net);
		    writeModel.writePNMLFile(lastSavedFile);
		}
	    }
	}
    }

    /**
     * Corrects extension suffif If the user did not add any PNML 
     * extension or misspelled it,
     * 
     * @param lastSavedFile
     * 		File from the file dialog
     */
    private void correctPNMLExtension(File file) {
	
	StringBuffer filePath = new StringBuffer (file.getAbsolutePath());
	final String pnmlExtension = ".pnml";
	filePath.ensureCapacity(filePath.length() + pnmlExtension.length());
	
	if (-1 == filePath.lastIndexOf(".pnml")) {
	    int index = -1 == filePath.lastIndexOf(".") ? filePath.length() : filePath.lastIndexOf(".") ;
	    lastSavedFile = new File(filePath.substring(0, index) + pnmlExtension);
	}
	
    }

    /**
     * Check if user really wants to overwrite an existing file;
     * 
     * @param file
     * @return
     */
    private Boolean confirmSave(File file) {

	if (file.exists()) {

	    int val = JOptionPane.showConfirmDialog(desktop,
		    "Wollen Sie die bestehende Datei überschreiben?",
		    "Datei exitiert bereits.", JOptionPane.YES_NO_OPTION,
		    JOptionPane.WARNING_MESSAGE);

	    if (val == JOptionPane.OK_OPTION) {
		return true;
	    }

	}

	return true;

    }

}
