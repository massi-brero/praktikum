package q8388415.brero_massimiliano.PTNetEditor.tests;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNNetController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class PTNNetHandlerTest {
	
	private PTNNet net;
	private PTNNode node1, node2, node3, node4;
	private PTNArc arc1, arc2, arc3;
	private PTNNetController netHandler;

	@Before
	public void setUp() throws Exception {
		net = new PTNNet();
		
		PTNDesktop desktop  = new PTNDesktop(new PTNAppController(), net);
		
		// Of course one must do this with a mock. I'll do that next time
		// when there is sufficient time for me to check how this is done
		// properly with JUnit. ;-)
		PTNControlPanel controlPanel = PTNControlPanel.getInstance();
		controlPanel.initialize(desktop, new PTNAppController());
		netHandler = new PTNNetController(net, desktop);
		
		node1 = new PTNPlace("node1", "n1", new Point(100, 100));
		node2 = new PTNTransition("node2", "n2", new Point(10, 10));
		node3 = new PTNTransition("node3", "n3", new Point(20, 20));
		node4 = new PTNPlace("node4", "n4", new Point(200, 200));
		arc1 = new PTNArc("a1", node1, node2);
		arc2 = new PTNArc("a2", node1, node3);
		arc3 = new PTNArc("a3", node4, node2);
		net.addNode(node1);
		net.addNode(node2);
		net.addNode(node3);
		net.addNode(node4);
		net.addArc(arc1);
		net.addArc(arc2);
		net.addArc(arc3);
		

	}
	
	@Test 
	public void setUpNodesTest() {
		PTNControlPanel p = PTNControlPanel.getInstance();
		System.out.println(p.getIsInitialized());
		netHandler.setUpNodeViews();
		assertEquals(4, net.getNumberOfNodes());
	}
	


}
