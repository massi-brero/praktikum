package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Dimension;
import java.io.File;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNetContructionException;
import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeConstructionException;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNParser;

/**
 * Handles the file reading operations. Since this could also be derived from a
 * database or any other source this kind of code is put in a model.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNFileReader {

	/**
	 * {@link PTNParser}
	 */
    private PTNParser parser = null;
    /**
     * Needed to update the desktop to the net that is described in the file.
     */
    private Dimension desktopSize = null;

    /**
     * Getter 
     * @return {@link PTNParser}
     */
    public PTNParser getParser() {
        return parser;
    }

    /**
     * Setter
     * @param parser {@link PTNParser}
     */
    public void setParser(PTNParser parser) {
        this.parser = parser;
    }

    /**
     * Reads file and builds the net that is used throughout this application.
     * The desktop size is set according to {@link PTNParser#getMaxHeight()}
     * and {@link PTNParser#getMaxWidth()}.
     * 
     * @param pnm
     *            PNML file
     * @param net
     *            Net model which practically all classes work with.
     * @throws PTNNetContructionException 
     * @throws PTNNodeConstructionException 
     */
    public void readFromFile(File pnm, PTNNet net) throws PTNNetContructionException, PTNNodeConstructionException {

        parser = new PTNParser(pnm, net);
        parser = new PTNParser(pnm, net);
        parser.initParser();
        parser.parse();

        this.setDesktopSize(new Dimension((int)parser.getMaxWidth(), (int)parser.getMaxHeight()));


    }
    
    /**
     * Getter
     * 
     * @return Dimension
     * 		The size we need to display the net that was parsed.
     */
    public Dimension getDesktopSize() {
        return desktopSize;
    }

   /**
    * Setter
    * 
    * @param desktopSize Dimension
    * 		The size we need to display the net that was parsed.
    */
    public void setDesktopSize(Dimension desktopSize) {
        this.desktopSize = desktopSize;
    }

}
