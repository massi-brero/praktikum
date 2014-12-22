package q8388415.brero_massimiliano.PTNetEditor.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import q8388415.brero_massimiliano.PTNetEditor.PTNBootstrap;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNNetController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class PTNSimulationTest {
	
	private PTNNet net;
	private PTNPlace node1, node4, node5;
	private PTNTransition node2, node3;
	@SuppressWarnings("unused")
	private PTNArc arc1, arc2, arc3, arc4;
	private PTNNetController netHandler;

	@Before
	public void setUp() throws Exception {
		(new PTNBootstrap()).init();
		net = new PTNNet();
		node1 = new PTNPlace("node1", "n1", new Point(100, 100));
		node1.setToken(1);
		node2 = new PTNTransition("node2", "n2", new Point(10, 10));
		node3 = new PTNTransition("node3", "n3", new Point(20, 20));
		node4 = new PTNPlace("node4", "n4", new Point(120, 120));
		node4.setToken(1);
		node5 = new PTNPlace("node4", "n4", new Point(120, 120));
		node5.setToken(0);
		arc1 = new PTNArc("a1", node1, node2);
		arc2 = new PTNArc("a2", node1, node3);
		arc3 = new PTNArc("a3", node4, node2);
		arc4 = new PTNArc("a3", node5, node3);
		net.addNode(node1);
		net.addNode(node2);
		net.addNode(node3);
		net.addNode(node4);
		net.addNode(node5);
		net.addArc(arc1);
		net.addArc(arc2);
		net.addArc(arc3);
		PTNDesktop desktop  = new PTNDesktop(new PTNAppController(), net);
		netHandler = new PTNNetController(net, desktop);
		netHandler.setUpNodeViews();
		netHandler.setUpArcs();
	}
	
	@Test 
	public void checkActivationTrueTest() {
		assertTrue(node2.isActivated());
	}
	
	@Test 
	public void checkActivationFalseTest() {
		assertFalse(node3.isActivated());
	}
	

}
