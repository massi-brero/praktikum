package q8388415.brero_massimiliano.PTNetEditor.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;
import q8388415.brero_massimiliano.PTNetEditor.views.PTNNetViewHandler;

public class netHandlerTest {
	
	private PTNNet net;
	private PTNNode node1, node2, node3;
	private PTNArc arc1, arc2;
	private PTNNetViewHandler netHandler;

	@Before
	public void setUp() throws Exception {
		net = new PTNNet();
		node1 = new PTNPlace("node1", "n1", new Point(100, 100));
		node2 = new PTNTransition("node2", "n2", new Point(10, 10));
		node3 = new PTNTransition("node3", "n3", new Point(20, 20));
		arc1 = new PTNArc("a1", node1, node2);
		arc2 = new PTNArc("a2", node1, node3);
		net.addNode(node1);
		net.addNode(node2);
		net.addNode(node3);
		net.addArc(arc1);
		net.addArc(arc2);
		netHandler = new PTNNetViewHandler(net);
	}
	
	@Test 
	public void setUpNodesTest() {
		assertEquals(3, netHandler.setUpNodes().size());
	}

}
