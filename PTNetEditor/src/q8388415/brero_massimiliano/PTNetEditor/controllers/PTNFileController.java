package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.io.File;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIFileListener;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class PTNFileController implements PTNIFileListener {
    
    private PTNDesktop desktop;
    private PTNNet net;

    public PTNFileController(PTNDesktop desktop, PTNNet net) {
        this.desktop = desktop;
        this.net = net;
    }
    
    @Override
    public void readFromFile(File pnm, PTNNet net) {
        // TODO Auto-generated method stub

    }

    @Override
    public int writeToFile(PTNNet net) {
        // TODO Auto-generated method stub
        return 0;
    }

}
