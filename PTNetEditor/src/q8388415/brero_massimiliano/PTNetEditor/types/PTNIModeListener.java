package q8388415.brero_massimiliano.PTNetEditor.types;

import java.io.File;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;

/**
 * This type is used for a simple observer pattern.
 * The AppController may then inform all listed observers, 
 * when the editor mode changes to simulation mode and
 * vice versa.
 * 
 * @author brero
 *
 */
public interface PTNIModeListener {
    void startSimulationMode();
    void startEditorMode();
}
