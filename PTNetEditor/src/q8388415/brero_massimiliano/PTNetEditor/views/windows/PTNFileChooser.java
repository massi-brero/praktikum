package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import javax.swing.JFileChooser;

import q8388415.brero_massimiliano.PTNetEditor.types.AllowedFileTypes;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNFileFilter;

/**
 * 
 * Extends JFileChooser with allowed file types. Feel free to extend filter variable.
 * 
 * @see JFileChooser
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class PTNFileChooser extends JFileChooser  {
    
	private static final long serialVersionUID = 1L;
    
	/**
	 * Standard constructor.
	 */
    public PTNFileChooser() {
        super();
        this.init();
    }
    
    
    /**
     * Start from given directory.
     * 
     * @param currentDirectory String
     */
    public PTNFileChooser(String currentDirectory) {
        super(currentDirectory);
        this.init();
    }
    
    /**
     * Sets file filter so we may restrict 
     * choices to file types found in {@link AllowedFileTypes}.
     * 
     */
    private void init() {
        this.setFileFilter(new PTNFileFilter());
    }
}
