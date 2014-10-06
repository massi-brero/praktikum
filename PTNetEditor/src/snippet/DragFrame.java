package snippet;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;

class DragFrame extends JFrame {
	
	public PlaceView butt1;
	public JLabel butt2;
	
	public DragFrame() {
		
		butt1 = new PlaceView("1");
		butt2 = new TransitionView();
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(400,400));
		ButtListener mListernerButt1 = new ButtListener(this);
		ButtListener mListernerButt2 = new ButtListener(this);
		
		butt2.setLocation(20, 10);
		butt1.addMouseMotionListener(mListernerButt1);
		butt1.addMouseListener(mListernerButt1);
		butt1.setLabelText("testtext");
		
		panel.add(butt1);
		
		butt2.setLocation(60, 10);
		butt2.addMouseMotionListener(mListernerButt2);
		butt2.addMouseListener(mListernerButt2);
		//butt2.setContentAreaFilled(false);
		butt2.setBorder(null);
		
		panel.add(butt2);
	
		getContentPane().add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	public void repaint() {
		
		super.repaint();
		//update(this.getGraphics());
		System.out.println(butt2.getText());
		
	}
	
	public static void main(String[] args) {
		
		JFrame frame = new DragFrame();
		
	}

	public void drawTransition() {
		// TODO Auto-generated method stub
		
	}
	
}

