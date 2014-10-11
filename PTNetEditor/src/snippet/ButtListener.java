package snippet;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.controllers.AppController;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.EditNodeWindow;

public class ButtListener implements MouseMotionListener, MouseListener {

	private DragFrame board;
	private volatile Point oldLocation;
	static boolean isDragged = false;

	public ButtListener(DragFrame dragFrame) {

		this.board = dragFrame;
		oldLocation = new Point(-1, -1);

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		JComponent source = (JComponent) e.getComponent();
		e.translatePoint(source.getX(), source.getY());

		if (AppController.isDrawing) {
			
			int widthFactor = (source instanceof PlaceView) ? 4 : 8;

			Point start = new Point(source.getLocation().x + source.getWidth()/widthFactor, source.getLocation().y + source.getHeight()/2);
			Point end = new Point(e.getX(), e.getY());
			
			board.drawEdge(source.getName(), start, end);
			
		} else {
			
			if (!isDragged)
				isDragged = true;
			else
				source.setLocation(source.getX() + e.getX() - (int) oldLocation.getX(), source.getY() + e.getY() - (int) oldLocation.getY());
			
			board.repaint();
			
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

//		if (source instanceof JLabel && 3 == e.getButton()) {
//
//			int newTokenCount = Integer.parseInt(source.getText()) + 1;
//			source.setText(String.valueOf(newTokenCount));
//
//		}
		
		if (source instanceof JLabel && 3 == e.getButton()) {

			EditNodeWindow popUp = new EditNodeWindow();
			popUp.show(e.getComponent(), e.getX(), e.getY());

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


		if (isDragged) {
			
			isDragged = false;
			oldLocation.setLocation(-1, -1);
			
		} else if (AppController.isDrawing && isAllowedTarget) {
			
			int sourceWidthFactor = (source instanceof PlaceView) ? 4 : 8;
			int targetWidthFactor = (target instanceof PlaceView) ? 4 : 8;
			
			Point start = new Point(source.getLocation().x + source.getWidth()/sourceWidthFactor, source.getLocation().y + source.getHeight()/2);
			Point end = new Point(target.getLocation().x + target.getWidth()/targetWidthFactor, target.getLocation().y + target.getHeight()/2);;
			board.drawEdge(source.getName(), start, end);

		} else {
			board.deleteLineFromDeskTop(source.getName());
		}
		
		
	}

}
