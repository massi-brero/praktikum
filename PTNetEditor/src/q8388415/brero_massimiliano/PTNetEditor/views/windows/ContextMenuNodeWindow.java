package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ContextMenuNodeWindow extends JPopupMenu {
	
	ActionListener listener;

	public ContextMenuNodeWindow(ActionListener listener) {
		this.listener = listener;
		this.init();
	}

	private void init() {
		
		JMenuItem menu1 = new JMenuItem("Attribute ändern");
		JMenuItem menu2 = new JMenuItem("Kanten löschen");
		
		menu1.addActionListener(listener);
		menu2.addActionListener(listener);
		
		this.add(menu1);
		this.add(menu2);
		
	}
	
}
