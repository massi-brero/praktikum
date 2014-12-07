package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.w3c.dom.events.MouseEvent;

/**
 * Context menu for node. Will show options to change node attributes 
 * or to delete arcs.
 * Vanishes when focus user clicks somewhere else on the desktop or
 * on the control panel.
 * 
 * @author Laptop
 *
 */
public class ContextMenuNodeWindow extends JPopupMenu implements MouseListener {
	
	ActionListener listener;

	public ContextMenuNodeWindow(ActionListener listener) {
		this.listener = listener;
		this.init();
		this.setFocusable(true);
	}

	private void init() {
		this.requestFocusInWindow(true);
		JMenuItem menu1 = new JMenuItem("Attribute ändern");
		JMenuItem menu2 = new JMenuItem("Kanten löschen");
		
		menu1.addActionListener(listener);
		menu2.addActionListener(listener);
		
		this.add(menu1);
		this.add(menu2);
		
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		if (!isPopupTrigger(e)) {
			this.setVisible(false);
		}
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {}

	
}
