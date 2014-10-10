package snippet;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;

public class ButtListener implements MouseMotionListener, MouseListener,
		KeyListener {

	private DragFrame board;
	private volatile Point oldLocation;
	static boolean isDragged = false;
	private boolean drawLine = false;

	public ButtListener(DragFrame dragFrame) {

		this.board = dragFrame;
		oldLocation = new Point(-1, -1);

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		JComponent source = (JComponent) e.getComponent();
		e.translatePoint(source.getX(), source.getY());

		if (isDrawLine()) {
			int widthFactor = (source instanceof PlaceView) ? 4 : 8;
			Point start = new Point(source.getLocation().x + source.getWidth()/widthFactor, source.getLocation().y + source.getHeight()/2);
			Point end = new Point(e.getX(), e.getY());
			
			board.drawEdge(source.getName(), start, end);
			board.repaint();
			
		} else {
			
			if (!isDragged)
				isDragged = true;
			else
				source.setLocation(source.getX() + e.getX() - (int) oldLocation.getX(), source.getY() + e.getY() - (int) oldLocation.getY());
			
			board.repaint(2000);
			
		}


		oldLocation = e.getPoint();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		JLabel source = (JLabel) e.getComponent();

		if (source instanceof JLabel && 3 == e.getButton()) {

			int newTokenCount = Integer.parseInt(source.getText()) + 1;
			source.setText(String.valueOf(newTokenCount));

		}
		
		board.repaint();

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		JComponent source = (JComponent) e.getComponent();
		JComponent target = (JComponent) board.getDesktop().findComponentAt(mouseLocation);

		boolean isAllowedTarget = (source instanceof PlaceView && target instanceof TransitionView) || 
									(source instanceof TransitionView && target instanceof PlaceView);

		System.out.println("source: " + source.getClass());
		System.out.println("target: " + target.getClass());

		if (isDragged) {
			
			isDragged = false;
			oldLocation.setLocation(-1, -1);
			
		} else if (drawLine && isAllowedTarget) {
			
			
			int widthFactorSource =  (source instanceof PlaceView) ? 4 : 8;
			int widthFactorTarget = (target instanceof PlaceView) ? 4 : 8;
			
			Point start = new Point(source.getLocation().x + source.getWidth()/widthFactorSource, source.getLocation().y + source.getHeight()/2);
			Point end = new Point(target.getLocation().x + target.getWidth()/widthFactorTarget, target.getLocation().y + target.getHeight()/2);
			
			board.drawEdge(source.getName(), start, end);

		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_CONTROL:
			this.setDrawLine(true);
			break;

		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_CONTROL:
			System.out.println(("active strg"));
			this.setDrawLine(false);
			break;
			
		default:
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean isDrawLine() {
		return drawLine;
	}

	public void setDrawLine(boolean drawLine) {
		this.drawLine = drawLine;
	}

}
