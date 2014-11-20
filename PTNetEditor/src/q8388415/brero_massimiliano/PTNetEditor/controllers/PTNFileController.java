package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.io.File;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNFileReader;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIFileListener;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class PTNFileController implements PTNIFileListener {
    
    private PTNDesktop desktop = null;
    private PTNNet net;
    private PTNFileReader readModel;

    public PTNFileController(PTNDesktop desktop, PTNNet net) {
        readModel = new PTNFileReader();
        this.desktop = desktop;
        this.net = net;
    }
    
    /**
     * Calls read method in file model. So views do not really have to know the file model.
     * And we don not need to make a model a listener.
     * 
     */
    public void readFromFile(File pnm, PTNNet net) {
        readModel.readFromFile(pnm, net);
        desktop.init();
    }

    @Override
    public int writeToFile(File file, PTNNet net) {
        // TODO Auto-generated method stub
        return 0;
    }

}
