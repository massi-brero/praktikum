package q8388415.brero_massimiliano.PTNetEditor.controllers;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNIModeListener;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;

/**
 * This class features some basic state variables that can be checked by other
 * components.
 * For the drawing process static variables are used. Because: 
 * 1. They're kind of global variables 
 * 2. The application controller does not have to know any other class instances and vice versa. 
 * 3. We can use them in Threads an do not have to set up a listener for each event
 * (select, draw etc.).
 * 
 * But we will use listener for changing between editor and simulation mode. 
 * Here more classes are involved and we do not want them to meddle with static variables.
 * 
 * The AppController 
 * 
 * @author Laptop
 * 
 */
public class PTNAppController implements KeyListener, PTNIScaleListener {

	/*
	 * Global values needed by other classes
	 */
	public static final String DEFAULT_TOKEN_NUMBER = "0";
	public static final Point DEFAULT_NODE_LOCATION = new Point(0, 0);
	public static boolean moveNodes = false;
	public static boolean deleteSelection = false;
	public static boolean deselectAll = false;
	public static boolean selectMode = false;
	public static boolean redrawArcs = false;
	private ArrayList<PTNIModeListener> modeListeners;

	public PTNAppController() {
		modeListeners = new ArrayList<PTNIModeListener>();
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_CONTROL:
			setMoveNodes(true);
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
			setMoveNodes(false);
			break;
		case KeyEvent.VK_SHIFT:
			setSelectMode(false);
			break;
		default:
			break;
		}
	}

	public static void setMoveNodes(boolean b) {

		PTNAppController.moveNodes = b;

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();
		// System.out.println(cmd);

		switch (cmd) {
		case "Markierung aufheben":
			PTNAppController.deselectAll = true;
			break;
		case "Markierte Knoten löschen":
			PTNAppController.deleteSelection = true;
			break;
		case "+":
			this.increaseScale();
			break;
		case "-":
			this.decreaseScale();
			break;
		case "Editor":
			this.startEditorMode();
			break;
		case "Simulation":
			this.startSimulationMode();
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

	@Override
	public void increaseScale() {
		redrawArcs = true;
	}

	@Override
	public void decreaseScale() {
		redrawArcs = true;
	}

	public void addSimulationListener(PTNIModeListener listener) {
		modeListeners.add(listener);
	}

	public void removeSimulationListener(PTNIModeListener listener) {
		modeListeners.remove(listener);
	}

	/**
	 * Notify all listeners that it's time to start the simulation mode
	 * preparations.
	 */
	private void startSimulationMode() {

		Iterator<PTNIModeListener> it = modeListeners.iterator();

		while (it.hasNext()) {
			it.next().startSimulationMode();
		}

	}

	/**
	 * Notify all listeners that it's time to start the editor mode
	 * preparations..
	 */
	private void startEditorMode() {
		Iterator<PTNIModeListener> it = modeListeners.iterator();

		while (it.hasNext()) {
			it.next().startEditorMode();
		}

	}

}
