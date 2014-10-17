package snippet;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class ButtListener implements MouseMotionListener, MouseListener, ActionListener, Runnable {

	private PTNDesktop board;
	private volatile Point oldLocation;
	static boolean isDragged = false;

	public ButtListener(PTNDesktop dt) {

		this.board = dt;
		oldLocation = new Point(-1, -1);

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		JComponent source = (JComponent) e.getComponent();
		e.translatePoint((int)(source.getX()*1.15), (int)(source.getY()*1.15));

		if (PTNAppController.isDrawing) {
			
			int widthFactor = (source instanceof PlaceView) ? 4 : 8;

			Point start = new Point(source.getLocation().x + source.getWidth()/widthFactor, source.getLocation().y + source.getHeight()/2);
			Point end = new Point(e.getX(), e.getY());
			
			board.drawEdge(source.getName(), start, end);
			
		} else {
			
			if (!isDragged) {
				isDragged = true;
			} else {
				
				int diffX = e.getX() - (int) oldLocation.getX();
				int diffY = e.getY() - (int) oldLocation.getY();
				
				if (board.hasSelected()) {
					
					ArrayList<NodeView> nodes = board.getNodes();
					Iterator<NodeView> it = nodes.iterator();
					
					while (it.hasNext()) {
						NodeView node = (NodeView) it.next();
						if(node.isSelected())
							node.setLocation(node.getX() + diffX, node.getY() + diffY);			
					}
					
				} else {
					source.setLocation(source.getX() + diffX, source.getY() + diffY);
				}
				
			}
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
		
		NodeView source = (NodeView) e.getComponent();
		
		if (source instanceof JLabel && 3 == e.getButton()) {

			board.callDialog(source);
			
		} else if (PTNAppController.selectMode) {
			
			source.setSelected(!source.isSelected());
			
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
		JComponent target = (JComponent) board.findComponentAt(mouseLocation);

		boolean isAllowedTarget = (source instanceof PlaceView && target instanceof TransitionView) || 
									(source instanceof TransitionView && target instanceof PlaceView);
		
		
		if (isDragged) {
			
			isDragged = false;
			oldLocation.setLocation(-1, -1);
			
		} else if (PTNAppController.isDrawing && isAllowedTarget) {
			
			drawEdge(source, target);

		} else {
			board.deleteLineFromDeskTop(source.getName());
		}
		
		// set global variables to default and deselect buttons
		board.requestFocus();

	}

	private void drawEdge(JComponent source, JComponent target) {
		//TODO dynamic factors!!
		int sourceWidthFactor = (source instanceof PlaceView) ? 4 : 8;
		int targetWidthFactor = (target instanceof PlaceView) ? 4 : 8;
		
		Point start = new Point(source.getLocation().x + source.getWidth()/sourceWidthFactor, source.getLocation().y + source.getHeight()/2);
		Point end = new Point(target.getLocation().x + target.getWidth()/targetWidthFactor, target.getLocation().y + target.getHeight()/2);;
		board.drawEdge(source.getName(), start, end);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("action");
		
	}

	@Override
	public void run() {
		
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// continue waitung even if interruoted
			}
			if (PTNAppController.deselectAll) {
				board.deselectNodes();
				PTNAppController.deselectAll = false;
			} else if (PTNAppController.deleteSelection) {
				board.deleteSelected();
				PTNAppController.deleteSelection = false;
			}
			
			
		}
		
		
	}

}
