package q8388415.brero_massimiliano.PTNetEditor.controllers;

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
 * 
 * @author Laptop
 * 
 */
public class PTNAppController implements KeyListener, PTNIScaleListener {

	public static boolean isDrawing = false;
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

	/**
	 * Works like a mutex variable for our two modes
	 */
	// private void updateMode() {
	// PTNAppController.editMode = !PTNAppController.editMode;
	// PTNAppController.simMode = !PTNAppController.simMode;
	// System.out.println(PTNAppController.editMode);
	// System.out.println(PTNAppController.simMode);
	// }

	public void addSimulationListener(PTNIModeListener listener) {
		modeListeners.add(listener);
	}

	public void removeSimulationListener(PTNIModeListener listener) {
		modeListeners.remove(listener);
	}

	private void startSimulationMode() {

		Iterator<PTNIModeListener> it = modeListeners.iterator();

		while (it.hasNext()) {
			it.next().startSimulationMode();
		}

	}

	private void startEditorMode() {
		Iterator<PTNIModeListener> it = modeListeners.iterator();

		while (it.hasNext()) {
			it.next().startEditorMode();
		}
	}

}
