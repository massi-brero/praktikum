package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

/**
 * This class features some basic state variables that can be checkes by other components.
 * @author Laptop
 *
 */
public class PTNAppController implements KeyListener, ActionListener {
	
	public static boolean isDrawing = false;
	public static boolean deleteSelection = false;
	public static boolean deselectAll = false;
	public static boolean selectMode = false;	
	public PTNAppController() {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_CONTROL:
			setDrawLine(true);
			break;
		case KeyEvent.VK_SHIFT:
			setSelectMode(true);
			break;	
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_CONTROL:
			setDrawLine(false);
			break;
		case KeyEvent.VK_SHIFT:
			setSelectMode(false);
			break;	
		default:
			break;
		}
	}
	
	public static void setDrawLine(boolean b) {
		
		PTNAppController.isDrawing = b;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		System.out.println(cmd);

		switch (cmd) {
		case "Markierung aufheben":
			this.deselectAll = true;			
			break;
		case "Markierte Knoten löschen":
			PTNAppController.deleteSelection = true;
			break;
		default:
			break;
		}
		
	}

	public static boolean isSelectMode() {
		return selectMode;
	}

	public static void setSelectMode(boolean selectMode) {
		PTNAppController.selectMode = selectMode;
	}
	
}
