package q8388415.brero_massimiliano.PTNetEditor.types;


/**
 * This type is used for a simple observer pattern.
 * The AppController may then inform all listed observers, 
 * when the editor mode changes to simulation mode and
 * vice versa.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public interface PTNIModeListener {
    void startSimulationMode();
    void startEditorMode();
}
