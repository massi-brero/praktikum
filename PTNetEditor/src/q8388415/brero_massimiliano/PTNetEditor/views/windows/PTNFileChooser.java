package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import javax.swing.JFileChooser;

import q8388415.brero_massimiliano.PTNetEditor.utils.PTNFileFilter;

/**
 * 
 * Extends JFileChooser with allowed file types. Feel free to extend filter variable.
 * 
 * @see JFileChooser
 * @author Laptop
 *
 */
public class PTNFileChooser extends JFileChooser  {
    
	private static final long serialVersionUID = 1L;
    
    public PTNFileChooser() {
        super();
        this.init();
    }
    
    
    
    public PTNFileChooser(String currentDirectory) {
        super(currentDirectory);
        this.init();
    }
    
    private void init() {
        this.setFileFilter(new PTNFileFilter());
    }
}
