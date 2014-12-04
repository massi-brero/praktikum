package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * Context menu for node. Will show options to change node attributes 
 * or to delete atcs.
 * Vanishes when focus is lost.
 * 
 * @author Laptop
 *
 */
public class ContextMenuNodeWindow extends JPopupMenu implements FocusListener {
	
	ActionListener listener;

	public ContextMenuNodeWindow(ActionListener listener) {
		this.listener = listener;
		this.init();
		this.setFocusable(true);
	}

	private void init() {
		
		this.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JMenuItem menu1 = new JMenuItem("Attribute ändern");
		JMenuItem menu2 = new JMenuItem("Kanten löschen");
		
		menu1.addActionListener(listener);
		menu2.addActionListener(listener);
		
		this.add(menu1);
		this.add(menu2);
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		System.out.println("weg");
	}
	
	
	
}
