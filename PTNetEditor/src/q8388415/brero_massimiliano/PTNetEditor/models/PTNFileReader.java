package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Dimension;
import java.io.File;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNetContructionException;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNParser;

/**
 * Handles the file reading operations. Since this could also be derived from a
 * database or any other source this kind of code is put in a model.
 * 
 * @author Laptop
 *
 */
public class PTNFileReader {

    private PTNParser parser = null;
    private Dimension desktopSize = null;

    public PTNParser getParser() {
        return parser;
    }

    public void setParser(PTNParser parser) {
        this.parser = parser;
    }

    /**
     * Reads file and builds the net that is used throughout this application.
     * 
     * @param pnm
     *            PNML file
     * @param net
     *            Net model which practically all classes work with.
     * @throws PTNNetContructionException 
     */
    public void readFromFile(File pnm, PTNNet net) throws PTNNetContructionException {

        parser = new PTNParser(pnm, net);
        parser = new PTNParser(pnm, net);
        parser.initParser();
        parser.parse();

        this.setDesktopSize(new Dimension((int)parser.getMaxWidth(), (int)parser.getMaxHeight()));


    }
    public Dimension getDesktopSize() {
        return desktopSize;
    }

    public void setDesktopSize(Dimension desktopSite) {
        this.desktopSize = desktopSite;
    }

    
    
}
