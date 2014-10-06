package snippet;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

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
		
		JComponent b = (JComponent)e.getComponent();
		e.translatePoint(b.getX(), b.getY());
		
		if (!isDragged) 
			isDragged = true;
		else
			b.setLocation(b.getX() + e.getX() - (int)oldLocation.getX(), b.getY() + e.getY() - (int)oldLocation.getY());
		
		oldLocation = e.getPoint();
		board.repaint();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("clicked");
		JLabel comp = (JLabel)e.getComponent();
		
		if (comp instanceof JLabel && 3 == e.getButton()) {
			
			JLabel b = (JLabel) comp;
			int newTokenCount = Integer.parseInt(b.getText()) + 1;
			b.setText(String.valueOf(newTokenCount));
			
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
		System.out.println(("pressed"));
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (isDragged) {
			isDragged = false;
			oldLocation.setLocation(-1, -1);
		}
		
		
	}

}
