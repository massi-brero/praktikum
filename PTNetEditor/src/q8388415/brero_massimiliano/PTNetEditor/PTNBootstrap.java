package q8388415.brero_massimiliano.PTNetEditor;

import java.awt.Point;

import javax.swing.JFrame;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.views.PTNMenu;
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
		PTNMenu menu = new PTNMenu(desktop, appControl);
		
		JFrame frame = new MainFrame(desktop, controlPanel, menu);
		
	}

	private PTNNet setUpNet() {
		
		PTNNet net = new PTNNet();
		
		try {
			PTNPlace node1 = new PTNPlace("node1", "n1", new Point(100, 100));
			node1.setLabel("testtext");
			PTNTransition node2 = new PTNTransition("node2", "n2", new Point(10, 10));
			PTNTransition node3 = new PTNTransition("node3", "n3", new Point(200, 250));
			PTNTransition node4 = new PTNTransition("node4", "n4", new Point(200, 70));
			node2.setLabel("testtext");
			node3.setLabel("testtext");
			node4.setLabel("testtext");
			PTNArc arc1 = new PTNArc("a1", node1, node2);
			PTNArc arc2 = new PTNArc("a2", node1, node3);
			net.addNode(node1);
			net.addNode(node2);
			net.addNode(node3);
			net.addNode(node4);
			net.addArc(arc1);
			net.addArc(arc2);
			
		} catch (Exception e) {
			//TODO Fehler-Dialog Fenster aufrufen
		}

		return net;
		
	}
	
	

}
