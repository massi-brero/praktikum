package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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

	public ContextMenuNodeWindow(ActionListener listener, Point location) {
		this.listener = listener;
		this.init();
		this.setFocusable(true);
		this.setLocation(location);
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
