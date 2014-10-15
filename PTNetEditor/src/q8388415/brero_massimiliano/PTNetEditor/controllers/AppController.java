package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AppController implements KeyListener, ActionListener {
	
	public static boolean isDrawing = false;
	public static boolean moveSelection = false;
	public static boolean deleteSelection = false;
	public static boolean selectMode = false;
	
	public AppController() {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_CONTROL:
			setDrawLine(true);
			break;
		case KeyEvent.VK_SHIFT:
			System.out.println("hit");
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
			System.out.println("release");
			setSelectMode(false);
			break;	
		default:
			break;
		}
	}
	
	public static void setDrawLine(boolean b) {
		
		AppController.isDrawing = b;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();

		switch (cmd) {
		case "move":
			AppController.moveSelection = true;
			break;
		case "erase":
			AppController.deleteSelection = true;
			break;
		default:
			break;
		}
		
	}

	public static boolean isSelectMode() {
		return selectMode;
	}

	public static void setSelectMode(boolean selectMode) {
		AppController.selectMode = selectMode;
	}
	
}
