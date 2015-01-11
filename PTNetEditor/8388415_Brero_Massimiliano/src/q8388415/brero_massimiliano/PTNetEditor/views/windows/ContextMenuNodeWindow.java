package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;

/**
 * Context menu for node. Will show options to change node attributes 
 * or to delete arcs.
 * Vanishes when focus user clicks somewhere else on the desktop or
 * on the control panel.
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class ContextMenuNodeWindow extends JPopupMenu implements MouseListener, AncestorListener {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Listener(s) for the context menu options.
	 */
	ActionListener listener;
	NodeView nodeView;

	/**
	 * @param listener {@link ActionListener}
	 * @param nodeView {@link NodeView}
	 * 		Node view for whom the context menu was called.
	 */
	public ContextMenuNodeWindow(ActionListener listener, NodeView nodeView) {
		this.nodeView = nodeView;
		this.listener = listener;
		this.init();
		this.setFocusable(true);
		this.setLocation(nodeView.getLocationOnScreen());
	}

	/**
	 * Basic initialization.
	 */
	private void init() {
		this.requestFocusInWindow(true);
		JMenuItem menu1 = new JMenuItem("Attribute ändern");
		JMenuItem menu2 = new JMenuItem("Kanten löschen");
		
		menu1.addActionListener(listener);
		menu2.addActionListener(listener);
		
		this.add(menu1);
		this.add(menu2);
		
	}

	/**
	 * Hide context menu when a click on desktop or control panel occured.
	 * 
	 * @param e {@link MouseEvent}
	 */
	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		if (!isPopupTrigger(e)) {
			this.setVisible(false);
		}
	}
	
	/**
	 * Move context menu with when desktop(Main frame are dragged over the monitor.
	 * @param event AncestorEvent
	 */
	@Override
	public void ancestorMoved(AncestorEvent event) {
		if (this.isVisible())
			this.setLocation(nodeView.getLocationOnScreen());
	}
	
	/**
	 * Not implemented.
	 */
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {}

	/**
	 * Not implemented.
	 */
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {}

	/**
	 * Not implemented.
	 */
	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {}

	/**
	 * Not implemented.
	 */
	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {}

	/**
	 * Not implemented.
	 */
	@Override
	public void ancestorAdded(AncestorEvent event) {}

	/**
	 * Not implemented.
	 */
	@Override
	public void ancestorRemoved(AncestorEvent event) {}



}
