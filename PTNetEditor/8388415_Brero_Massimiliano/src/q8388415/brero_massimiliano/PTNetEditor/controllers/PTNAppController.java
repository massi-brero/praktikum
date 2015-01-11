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
 * But we will use listeners for changing between editor and simulation mode. 
 * 
 * @author 8388415 - Massimiliano Brero
 * 
 */
public class PTNAppController implements KeyListener, PTNIScaleListener {

	/**
	 * Location a node is placed when it is created by the menu ba option.
	 */
	public static final Point DEFAULT_NODE_LOCATION = new Point(0, 0);
	
	/*
	 * Global values needed by other classes
	 */
	public static final String DEFAULT_TOKEN_NUMBER = "0";
	public static final int MAX_TOKEN = 999;
	public static boolean moveNodes = false;
	public static boolean deleteSelection = false;
	public static boolean deselectAll = false;
	public static boolean selectMode = false;
	public static boolean redrawArcs = false;
	/**
	 * Classes that must be notified when we change from editor to simulation
	 * modus and vice versa.
	 */
	private ArrayList<PTNIModeListener> modeListeners;

	/**
	 * Standard Constructor
	 */
	public PTNAppController() {
		modeListeners = new ArrayList<PTNIModeListener>();
	}

	/**
	 * Key Listener override
	 */
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

	/**
	 * Key Listener override
	 */
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

	/**
	 * Setter for static variable PTNAppController.moveNodes
	 * @param b Boolean
	 */
	public static void setMoveNodes(boolean b) {

		PTNAppController.moveNodes = b;

	}

	/**
	 * Sets static variables for:
	 * <ul>
	 * <li>deselect all</li>
	 * <li>delete selection</li>
	 * <li>increase scale</li>
	 * <li>decrease scale</li>
	 * <li>editor mode</li>
	 * <li>simulation mode</li>
	 * </ul>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		switch (cmd) {
		case "Markierung aufheben":
			PTNAppController.deselectAll = true;
			break;
		case "Markierte Elemente löschen":
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

	/**
	 * Getter.
	 * @return Boolean
	 */
	public static Boolean isSelectMode() {
		return selectMode;
	}

	/**
	 * Setter.
	 * @param selectMode
	 */
	public static void setSelectMode(boolean selectMode) {
		PTNAppController.selectMode = selectMode;
	}

	/**
	 * Listener method for increasing elements.
	 * Sets flag if it's time to redraw the arcs.
	 */
	@Override
	public void increaseScale() {
		redrawArcs = true;
	}

	/**
	 * Listener method for decreasing elements.
	 * Sets flag if it's time to redraw the arcs.
	 */
	@Override
	public void decreaseScale() {
		redrawArcs = true;
	}

	/**
	 * Adds mode listener to PTNIModeListener list. So when Appcontroller
	 * is notified that a mode change is fired it can inform the mode listeners.
	 * @param listener {@link PTNIModeListener}
	 */
	public void addSimulationListener(PTNIModeListener listener) {
		modeListeners.add(listener);
	}

	/**
	 * 
	 * @param listener
	 */
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
	
	/**
	 * Not implemented.
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {}

}
