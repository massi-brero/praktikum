package snippet;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

class DragFrame extends JFrame {

	private PTNDesktop desktop;

	public DragFrame() {

		desktop = new PTNDesktop();
		getContentPane().add(desktop, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {

		JFrame frame = new DragFrame();

	}

	public PTNDesktop getPTNDesktop() {
		return this.desktop;
	}


}
