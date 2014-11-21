package q8388415.brero_massimiliano.PTNetEditor.types;

import java.io.File;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;

public interface PTNIFileListener {
    void readFromFile(PTNNet net);
    int writeToFile(PTNNet net);
}
