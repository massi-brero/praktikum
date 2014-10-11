package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AppController implements KeyListener {
	
	public static boolean isDrawing = false;
	
	public AppController() {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_CONTROL:
			setDrawLine(true);
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
	
	

}
