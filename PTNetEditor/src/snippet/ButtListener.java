package snippet;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;

public class ButtListener implements MouseMotionListener, MouseListener {

	private DragFrame board;
	private volatile Point oldLocation;
	boolean isDragged = false;

	public ButtListener(DragFrame dragFrame) {

		this.board = dragFrame;
		oldLocation = new Point(-1, -1);

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		if (!isDragged) {
			isDragged = true;
			
		} else {
			
			JButton b = (JButton)e.getComponent();
			e.translatePoint(b.getX(), b.getY());
			b.setBounds(b.getX() + e.getX() - (int)oldLocation.getX(), b.getY() + e.getY() - (int)oldLocation.getY(), b.getWidth(), b.getHeight());
			
			System.out.println(("new: " + e.getPoint()));
			System.out.println(("button: " + b.getLocation()));
//			System.out.println(("diff: " + (e.getY() - (int)oldLocation.getY())));
//			
//			System.out.println(("dragged: " + isDragged));
			System.out.println("");

		}
		oldLocation = e.getPoint();
		board.repaint();



	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		isDragged = false;
		System.out.println("release");
		
		
	}

}
