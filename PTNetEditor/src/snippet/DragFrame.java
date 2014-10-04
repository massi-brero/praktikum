package snippet;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class DragFrame extends JFrame {
	
	private JButton butt1;
	private JButton butt2;
	
	public DragFrame() {
		butt1 = new JButton("Button 1");
		butt2 = new JButton("Button 2");
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400,400));
		butt1.setBounds(10, 10, 100, 100);
		butt1.addMouseMotionListener(new ButtListener(this));
		butt1.addMouseListener(new ButtListener(this));
		panel.add(butt1);
		butt2.setBounds(10, 10, 100, 100);
		butt2.addMouseMotionListener(new ButtListener(this));
		butt2.addMouseListener(new ButtListener(this));
		panel.add(butt2);
		panel.setLayout(null);
		
		getContentPane().add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		JFrame frame = new DragFrame();
		
	}
	
}

