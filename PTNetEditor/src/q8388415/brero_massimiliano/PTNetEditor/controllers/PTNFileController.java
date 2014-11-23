package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.io.File;

import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNetContructionException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNFileReader;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIFileListener;
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
    private static File lastChosenReadFile = null;

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
        return 0;
    }

    /**
     * Calls read method in file model. So views do not really have to know the
     * file model. And we don not need to make a model a listener.
     * 
     */
    public void readFromFile(PTNNet net) {
        
        PlaceView.resetSize();
        TransitionView.resetSize();
        this.openFileDialog();

        try {
            if (null != lastChosenReadFile) {
                net.reset();

                readModel.readFromFile(lastChosenReadFile, net);

            }
        } catch (PTNNetContructionException e) {
            this.callNetContructionWarning(e.getMessage());
        }

        desktop.init();
        desktop.setSize(readModel.getDesktopSize());


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
        int val = fileDialog.showDialog(desktop, "Netz-Datei wählen");

        if (0 == val) {
            lastChosenReadFile = fileDialog.getSelectedFile();
        }

    }

}
