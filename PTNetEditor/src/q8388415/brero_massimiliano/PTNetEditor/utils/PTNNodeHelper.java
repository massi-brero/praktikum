package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNInitializationException;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.ContextMenuNodeWindow;

/**
 * Offers methods for dealing with node computations, like getting an instance of the control panel
 * for adding listeners for new nodes. Kind of a lightweight delegate class.
 * @author Laptop
 *
 */
public class PTNNodeHelper implements ActionListener {
	
	private PTNControlPanel controlPanel;
	private PTNDesktop desktop;
	private NodeView sourceView = null;
	private ContextMenuNodeWindow cMenu = null;
	
	public PTNNodeHelper(PTNDesktop desktop) {
		this.desktop = desktop;
		controlPanel = PTNControlPanel.getInstance();
	}
	
	public void addPlaceListener(PlaceView place) {
		
		try {
			controlPanel.addPlaceScaleListener(place);
		} catch (PTNInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addTransitionListener(TransitionView transition) {
		
		try {
			controlPanel.addTransitionScaleListener(transition);
		} catch (PTNInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int showErrorPaneIdExists() {
		return JOptionPane.showConfirmDialog(desktop, "Diese Knoten-ID ist bereits vergeben.", 
				"Ungültige ID", JOptionPane.WARNING_MESSAGE);
	}
	
	public int showErrorPaneEmptyId() {
		return JOptionPane.showConfirmDialog(desktop, "Sie müssen eine Knoten-ID mit mind. einem Zeichen eingeben.", 
				"Ungültige ID", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * 
	 * @param source
	 */
	public void handleContextmenu(NodeView sourceView) {
		this.sourceView = sourceView;
		cMenu = new ContextMenuNodeWindow(this);
		cMenu.setLocation((int)sourceView.getLocation().getX() + 20, (int)sourceView.getLocation().getY() +20);
		cMenu.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		case "Attribute ändern":
			this.handleChangeAttributes();
			break;
		case "Kanten löschen":
			this.handleDeleteArcsDialog();
			break;
		default:
			break;
		}
		
	}

	private void handleChangeAttributes() {
		
		if (null != cMenu)
			cMenu.setVisible(false);
		
		
		if (null != sourceView)
			desktop.callNodeAttributeDialog(sourceView);
		
	}
	
	private void handleDeleteArcsDialog() {
		if (null != cMenu)
			cMenu.setVisible(false);
		
		
		if (null != sourceView)
			desktop.callDeleteArcsDialog(sourceView);
	}


}
