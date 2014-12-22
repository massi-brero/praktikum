package q8388415.brero_massimiliano.PTNetEditor.types;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;

/**
 * This type is used for a simple observer pattern.
 * 
 * @author brero
 *
 */
public interface PTNIFileListener {
    void readFromFile(PTNNet net);
    int writeToFile(PTNNet net);
}
