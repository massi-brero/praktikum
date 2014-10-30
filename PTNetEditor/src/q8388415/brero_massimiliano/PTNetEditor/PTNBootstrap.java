package q8388415.brero_massimiliano.PTNetEditor;
import java.awt.Dimension;

import javax.swing.JFrame;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.MainFrame;


public class PTNBootstrap {
	
	private static PTNBootstrap bootstrap;
	
	public PTNBootstrap() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		
		bootstrap = new PTNBootstrap();
		bootstrap.init();
		

	}
	
	public void init() {
				
		PTNAppController appControl = new PTNAppController();
		PTNDesktop desktop = new PTNDesktop(appControl, this.setUpNet());
		PTNControlPanel controlPanel = new PTNControlPanel(appControl, desktop);
		JFrame frame = new MainFrame(desktop, controlPanel);
		
	}
	
	
	

	private PTNNet setUpNet() {
		
		PTNNet net = new PTNNet();
		
		try {
			PTNPlace node1 = new PTNPlace("node1", "n1", new Dimension(100, 100));
			PTNTransition node2 = new PTNTransition("node2", "n2", new Dimension(10, 10));
			PTNTransition node3 = new PTNTransition("node3", "n3", new Dimension(20, 20));
			PTNArc arc1 = new PTNArc("a1", node1, node2);
			PTNArc arc2 = new PTNArc("a2", node1, node3);
			net.addNode(node1);
			net.addNode(node2);
			net.addNode(node3);
			net.addArc(arc1);
			net.addArc(arc2);
			
		} catch (Exception e) {
			//TODO Fehler-Dialog Fenster aufrufen
		}

		return net;
		
	}
	
	

}
