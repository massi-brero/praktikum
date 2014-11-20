package q8388415.brero_massimiliano.PTNetEditor.models;

import java.io.File;
import java.io.IOException;

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
  
    /**
     * Reads file and builds the net that is used throughout this application.
     * 
     * @param pnm
     *      PNML file
     * @param net
     *      Net model which practically all classes work with.
     */
    public void readFromFile(File pnm, PTNNet net) {

            
            PTNParser parser = new PTNParser(pnm, net);
            
            try {
                
                parser = new PTNParser(pnm, net);
                parser.initParser();
                parser.parse();
                
                //desktopWidth = parser.getMaxWidth();
                //desktopHeight = parser.getMaxWidth();
                
                
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
